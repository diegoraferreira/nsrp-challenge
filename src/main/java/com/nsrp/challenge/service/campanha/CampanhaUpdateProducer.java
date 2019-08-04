package com.nsrp.challenge.service.campanha;

import com.nsrp.challenge.model.campanha.CampanhaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class CampanhaUpdateProducer {

    @Value("nsrp.update.campanha.topic.name")
    private String topicName;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMenssage(CampanhaModel campanha) {
        jmsTemplate.convertAndSend(topicName, campanha);
    }
}