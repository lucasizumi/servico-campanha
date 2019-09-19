package com.challenge.campanha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

import com.challenge.campanha.dto.CampanhaDto;
import com.challenge.campanha.model.Campanha;

public class CampanhaUpdateAlerter {

	@Value("${update.campanha.topic}")
    private String topicName;

    @Autowired
    private JmsTemplate template;

    public void sendMenssage(CampanhaDto campanha) {
        template.convertAndSend(topicName, campanha);
    }
    
    public void sendMenssage(List<Campanha> campanhas) {
        template.convertAndSend(topicName, campanhas);
    }
	
}
