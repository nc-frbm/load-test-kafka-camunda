package com.example.workflow.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

public class ValidationService {

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "dms.load.test.validation.request", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGroupFoo(String processId) {
        System.out.println("Read validation.request event with businessKey=" + processId);
        publishValidationResult(processId);
    }

    private void publishValidationResult(String processId){
//        kafkaTemplate.send("dms.load.test.validation.result", processId);
        System.out.println("Send response to validation topic dms.load.test.validation.result");
    }
}
