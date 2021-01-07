package com.example.company_lab4.mqactive;

import com.example.company_lab4.entitymessages.Message;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.Collections;

@Configuration
public class ActiveConfig {
    @Bean
    public MappingJackson2MessageConverter messageConverter(){
        val messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("content-type");
        messageConverter.setTypeIdMappings(Collections.singletonMap("click", Message.class));
        return messageConverter;
    }
}
