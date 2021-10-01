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

    @GetMapping("/start/{processId}")
    public String startProcess(@PathVariable(name = "processId") String processId) {
        runtimeService.startProcessInstanceById(processId);
        return "Process Started";
    }
}
