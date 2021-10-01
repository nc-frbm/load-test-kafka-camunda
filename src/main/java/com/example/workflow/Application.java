package com.example.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class Application implements ApplicationRunner {

  @Autowired(required = false)
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String msg) {
    if (kafkaTemplate != null) {
      kafkaTemplate.send("dms.load.test.1", msg);
    } else {
      System.out.println("Kafka not enabled");
    }
  }

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {
    sendMessage("test test");
  }
}