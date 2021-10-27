package com.example.workflow.integration;

import com.example.workflow.kafka.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class ValidationService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "dms.load.test.validation.start", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGroupFoo(String processInstanceId) {
        System.out.println(getClass().getSimpleName() + ": Read validation.request event with processInstanceId=" + processInstanceId);
        publishValidationResult(processInstanceId);
    }

    private void publishValidationResult(String processInstanceId){
        kafkaTemplate.send(Topic.VALIDATION_COMPLETED.getTopic(), processInstanceId);
        System.out.println(getClass().getSimpleName() + ": Sending response to " + Topic.VALIDATION_COMPLETED.getTopic());
    }
}
