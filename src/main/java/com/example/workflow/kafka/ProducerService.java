package com.example.workflow.kafka;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.kafka.core.KafkaTemplate;

public class ProducerService implements JavaDelegate {

    private KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("ProducerService: CurrentActivityId=" + execution.getProcessInstanceId());
        kafkaTemplate.send("dms.load.test.1", execution.getProcessBusinessKey());
    }
}
