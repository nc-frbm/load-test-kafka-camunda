package com.example.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class StuckFlowUtil implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int incremental = (int) execution.getVariable("incremental");

        if(incremental < 2){
            throw new RuntimeException("I should die");
        }
    }
}
