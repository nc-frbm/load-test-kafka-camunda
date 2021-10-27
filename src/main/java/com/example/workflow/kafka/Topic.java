package com.example.workflow.kafka;

public enum Topic {
    DMS_LOAD_TEST("dms.load.test.1"),
    PROCESS_START("dms.load.test.process.start"),
    VALIDATION_STARTED("dms.load.test.validation.start"),
    VALIDATION_COMPLETED("dms.load.test.validation.completed");

    private String topic;

    Topic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
