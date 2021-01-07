package com.example.company_lab4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "machine")
public class Machine implements EntityInterface {
    private Integer id;
    private String type;
    private List<Worker> workers;

    public Machine() {
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "type", nullable = false, length = 40)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "machine")
    @JsonManagedReference
    @JsonIgnore
    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return id.equals(machine.id) &&
                Objects.equals(type, machine.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Transient
    @JsonIgnore
    public Map<String, String> getFieldsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id.toString());
        map.put("type", type);
        return map;
    }

    @Transient
    @JsonIgnore
    public Integer getIdentifier() {
        return getId();
    }


}
