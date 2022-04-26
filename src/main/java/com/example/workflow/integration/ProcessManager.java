package com.example.workflow.integration;

import com.example.workflow.kafka.Topic;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ProcessManager implements JavaDelegate {

    @Autowired()
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    RuntimeService runtimeService;

    @KafkaListener(topics = "dms.load.test.process.start", groupId = "${spring.kafka.consumer.group-id}")
    public void listenForProcessStart(String kafka) {
        Map<String, Object> map = new HashMap<>();
        map.put("validationDone", false);
        map.put("startTime", LocalDateTime.now());
        runtimeService.startProcessInstanceByKey("master_flow", kafka, map);
    }

    @KafkaListener(topics = "dms.load.test.validation.completed", groupId = "${spring.kafka.consumer.group-id}")
    public void listenForValidationCompleted(String completedEvent) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("validationDone", true);

        List<String> executionIds = new ArrayList<>();
        executionIds.add(completedEvent);
        runtimeService.setVariablesAsync(executionIds, variables);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String executionId = execution.getId();
        kafkaTemplate.send(Topic.VALIDATION_STARTED.getTopic(), executionId);
    }
}
