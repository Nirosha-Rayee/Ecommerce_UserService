package org.example.ecommerce_userservice.clients;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerClient {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    // Message: jsonString of whatever data you want to send to the topic. so, String message. sending a json format string
    // {
    //   id: 1,
    //   name: Naman Bhalla,
    //   email: "naman@scaler.com"
    // }


}
