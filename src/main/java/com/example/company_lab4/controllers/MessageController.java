package com.example.company_lab4.controllers;

import com.example.company_lab4.entity.EntityInterface;
import com.example.company_lab4.entity.Machine;
import com.example.company_lab4.entity.MachineList;
import com.example.company_lab4.entity.Worker;
import com.example.company_lab4.entity.WorkerList;
import com.example.company_lab4.entitymessages.ChangeType;
import com.example.company_lab4.entitymessages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;
import java.util.Map;

public abstract class MessageController {

    private static final String EMAIL = "jms4test@mail.ru";

    @Autowired
    private JmsTemplate jmsTemplate;

    protected <T> ResponseEntity<String> getXmlResponse(T object) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(object);
        xml = getXslTemplate(object) + xml;
        return ResponseEntity.ok().header("Content-type", "text/xml").body(xml);
    }

    protected <T> String getXslTemplate(T object) {
        String template = null;

        if (object.getClass() == Machine.class) {
            template = "Machine";
        }
        if (object.getClass() == MachineList.class) {
            template = "Machines";
        }
        if (object.getClass() == Worker.class) {
            template = "Worker";
        }
        if (object.getClass() == WorkerList.class) {
            template = "Workers";
        }

        if (template == null) {
            return "";
        } else {
            return "<?xml-stylesheet type=\"text/xsl\" href=\"/Xml2Html" + template + ".xsl\"?>";
        }

    }

    protected void sendCreateMessage(EntityInterface object) {
        sendMessage(object, ChangeType.CREATE);
    }

    protected void sendUpdateMessage(EntityInterface object) {
        sendMessage(object, ChangeType.UPDATE);
    }

    protected void sendDeleteMessage(EntityInterface object) {
        sendMessage(object, ChangeType.DELETE);
    }

    private void sendMessage(EntityInterface object, ChangeType type) {
        Map<String, String> fieldsMap = object.getFieldsMap();
        for (String fieldName : object.getFieldsMap().keySet()) {
            Message message = new Message(
                    type,
                    object.getClass().toString(),
                    object.getIdentifier(),
                    fieldName,
                    fieldsMap.get(fieldName),
                    new Date(),
                    EMAIL
            );
            jmsTemplate.convertAndSend("superqueue", message);
        }
    }

}

