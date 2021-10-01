package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartProcessController {

    @Autowired
    RuntimeService runtimeService;

    @GetMapping("/start/{processKey}/{businessKey}/{count}")
    public String startProcess(@PathVariable(name = "processKey") String processKey,
                               @PathVariable(name = "businessKey") String businessKey,
                               @PathVariable(name = "count") int count) {
        for (int i = 1; i <= count; i++) {
            runtimeService.startProcessInstanceByKey(processKey, businessKey + "_" + i);
        }
        return "Process Started";
    }

    @GetMapping("/correlate/{messageName}/{businessKey}")
    public String correlateMessage(@PathVariable(name = "messageName") String messageName,
                                   @PathVariable(name = "businessKey") String businessKey) {
        runtimeService.correlateMessage(messageName, businessKey);
        return "Message correlated";
    }
}
