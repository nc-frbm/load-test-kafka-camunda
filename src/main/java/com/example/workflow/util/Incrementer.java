package com.example.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Incrementer implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Object incremental = execution.getVariable("incremental");
        int incrementalValue = 0;

        if (incremental != null) {
            incrementalValue = (int) incremental + 1;
        }

        execution.setVariable("incremental", incrementalValue);
    }
}
