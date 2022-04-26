package com.example.workflow.util;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class Printer implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("PRINTER: I was called for business key: " + delegateExecution.getBusinessKey() + "__" + now);
    }
}
