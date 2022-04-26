package com.example.workflow.api;

import com.example.workflow.kafka.Topic;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StartProcessController {

    @Autowired
    RuntimeService runtimeService;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/ermis/validate")
    public String startProcess() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("example.json");
        kafkaTemplate.send("cwm.validation.validation-requested", "22DKPCCAEBGVUBDER5", getContent(stream));
        return "Process Started";
    }

    private String getContent(InputStream stream){
        StringBuilder contents = new StringBuilder();
        try (InputStreamReader isr = new InputStreamReader(stream);
             BufferedReader br = new BufferedReader(isr);)
        {
            String line;
            while ((line = br.readLine()) != null) {
                contents.append(line);
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contents.toString();
    }

    @GetMapping("/start/{businessKey}/{count}")
    public String startProcess(@PathVariable(name = "businessKey") String businessKey,
                               @PathVariable(name = "count") int count) {
        for (int i = 1; i <= count; i++) {
            String id = businessKey + "-" + i;
            kafkaTemplate.send(Topic.PROCESS_START.getTopic(), id);
        }
        return "Process Started";
    }

    @GetMapping("/start/kafka/{processKey}/{businessKey}")
    public String startProcessThroughKafka(@PathVariable(name = "processKey") String processKey,
                               @PathVariable(name = "businessKey") String businessKey) {
        kafkaTemplate.send(Topic.PROCESS_START.getTopic(), "test");
        return "Kafka message produced";
    }


    @GetMapping("/correlate/{messageName}/{businessKey}")
    public String correlateMessage(@PathVariable(name = "messageName") String messageName,
                                   @PathVariable(name = "businessKey") String businessKey) {
        runtimeService.correlateMessage(messageName, businessKey);
        return "Message correlated";
    }

    @GetMapping("/get-active/{processId}")
    public String getActive(@PathVariable(name = "processId") String processId) {
        try{
            List<String> activeActivityIds = runtimeService.getActiveActivityIds(processId);
            return "Active flows: " + activeActivityIds;
        }catch(Exception e){
            return "Error happened... " + e.getMessage();
        }
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


    @GetMapping("/retry/{processId}")
    public String retry(@PathVariable(name = "processId") String processId) {
        ProcessInstanceModificationBuilder processInstanceModification = runtimeService.createProcessInstanceModification(processId);

        processInstanceModification.cancelActivityInstance(processId);
        processInstanceModification.startBeforeActivity("stuck_flow_one");

        processInstanceModification.execute();

        return "process retried";
    }
}
