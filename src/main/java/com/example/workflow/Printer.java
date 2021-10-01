package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Printer implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("PRINTER: getProcessInstanceId=" + delegateExecution.getProcessInstanceId());
        System.out.println("PRINTER: " + delegateExecution.getProcessBusinessKey());
    }

}
