package com.example.company_lab4.entitymessages;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
// Сущность для таблицы с информацией об изменениях
@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private ChangeType changeType ;

    private String entity;

    private Integer idEntity;

    private String fieldName;

    private String fieldNewValue;

    private Date created;

    private String email;

    public Message() {
    }

    public Message(ChangeType changeType, String entity, Integer idEntity, String fieldName, String fieldNewValue, Date created, String email) {
        this.changeType = changeType;
        this.entity = entity;
        this.idEntity = idEntity;
        this.fieldName = fieldName;
        this.fieldNewValue = fieldNewValue;
        this.created = created;
        this.email = email;
    }

}
