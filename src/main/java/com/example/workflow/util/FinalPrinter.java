package com.example.workflow.util;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FinalPrinter implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = (LocalDateTime) execution.getVariable("startTime");
        long millis = ChronoUnit.MILLIS.between(startTime, now);
        System.out.println("I am printing a final statement for business key " + execution.getBusinessKey() + " duration: " + millis);
    }
}
