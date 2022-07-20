package dk.load.test;

import dk.load.test.kafka.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class Application implements ApplicationRunner {

  @Autowired(required = false)
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String msg) {
    System.out.println("Sending message");
    if (kafkaTemplate != null) {
      kafkaTemplate.send(Topic.PROCESS_START.getTopic(), msg);
    } else {
      System.out.println("Kafka not enabled");
    }
  }

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

  @PreDestroy
  public void tearDown(){
    System.out.println("HELP ME IM DYING");
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
//    sendMessage("test-asr-123");
  }
}