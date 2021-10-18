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

    @GetMapping("/start/{processKey}/{businessKey}")
    public String startProcess(@PathVariable(name = "processKey") String processKey,
                               @PathVariable(name = "businessKey") String businessKey) {
        runtimeService.startProcessInstanceByKey(processKey, businessKey);
        return "Process Started";
    }

    @GetMapping("/correlate/{messageName}/{businessKey}")
    public String correlateMessage(@PathVariable(name = "messageName") String messageName,
                                   @PathVariable(name = "businessKey") String businessKey) {
        runtimeService.correlateMessage(messageName, businessKey);
        return "Message correlated";
    }

    @GetMapping("/update-variable/{processInstanceId}")
    public String updateVariable(@PathVariable(name = "processInstanceId") String processInstanceId) {
        runtimeService.setVariable(processInstanceId, "requestDone", true);
        return "Variable updated to true";
    }

}
