package com.example.workflow.api;

import com.example.workflow.kafka.Topic;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartProcessController {

    @Autowired
    RuntimeService runtimeService;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/start/{processKey}/{businessKey}/{count}")
    public String startProcess(@PathVariable(name = "processKey") String processKey,
                               @PathVariable(name = "businessKey") String businessKey,
                               @PathVariable(name = "count") int count) {
        for (int i = 1; i <= count; i++) {
            kafkaTemplate.send(Topic.PROCESS_START.getTopic(), businessKey + "-" + i);
        }
        return "Process Started";
    }

    @GetMapping("/start/kafka/{processKey}/{businessKey}/{count}")
    public String startProcessThroughKafka(@PathVariable(name = "processKey") String processKey,
                               @PathVariable(name = "businessKey") String businessKey,
                               @PathVariable(name = "count") int count) {
        kafkaTemplate.send("dms.load.test.start.process", "test");
        return "Kafka message produced";
    }


    @GetMapping("/correlate/{messageName}/{businessKey}")
    public String correlateMessage(@PathVariable(name = "messageName") String messageName,
                                   @PathVariable(name = "businessKey") String businessKey) {
        runtimeService.correlateMessage(messageName, businessKey);
        return "Message correlated";
    }

    @GetMapping("/update-variable/{processId}")
    public String correlateMessage(@PathVariable(name = "processId") String processId) {
        try{
            runtimeService.setVariable(processId, "validationDone", true);
        }catch(Exception e){
            return "Error happened... " + e.getMessage();
        }
        return "Variable updated";
    }
}
