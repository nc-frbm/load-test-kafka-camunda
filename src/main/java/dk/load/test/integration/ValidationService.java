package dk.load.test.integration;

import dk.load.test.kafka.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class ValidationService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "dms.load.test.validation.start", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGroupFoo(String processInstanceId) {
        publishValidationResult(processInstanceId);
    }

    @KafkaListener(topics = "cwm.validation.validation-results", groupId = "${spring.kafka.consumer.group-id}")
    public void listenForValidationResults(String message) {
        try{
            Pattern pattern = Pattern.compile("[0-9]*IDLOAD.[0-9]*");
            Matcher matcher = pattern.matcher(message);
            boolean matchFound = matcher.find();
            String id = matcher.group(0);
            String millis = id.split("IDLOAD")[1];

            long now = System.currentTimeMillis();
            long created = Long.parseLong(millis);
            long diff = (now - created) / 1000;

            System.out.println("Message with ID {"+id+"} took " + diff + " seconds");
        }catch (Exception e){
            System.err.println("Error happened");
        }
    }


    private void publishValidationResult(String processInstanceId){
        kafkaTemplate.send(Topic.VALIDATION_COMPLETED.getTopic(), processInstanceId);
    }
}
