package dk.load.test.kafka;

import org.springframework.kafka.core.KafkaTemplate;

public class ProducerService {

    private KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    @Override
//    public void execute(DelegateExecution execution) throws Exception {
//        System.out.println("ProducerService: CurrentActivityId=" + execution.getProcessInstanceId());
//        kafkaTemplate.send("dms.load.test.1", execution.getProcessBusinessKey());
//    }
}
