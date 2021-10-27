package com.example.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Sleeper implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Starting sleep");
        Thread.sleep(15000);
        System.out.println("Ending sleep");
    }
}
