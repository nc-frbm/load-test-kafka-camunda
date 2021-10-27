package com.example.workflow.integration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.kafka.annotation.KafkaListener;

public class ProcessManager implements JavaDelegate {
    @KafkaListener(topics = "dms.load.test.validation.result", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGroupFoo(String processId) {
        System.out.println("Read validation result event with processId=" + processId);
        updateVariable(processId);
    }

    private void updateVariable(String processId){
        System.out.println("I should update the variable for process=" + processId);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("I should send a request to validation");
    }
}
