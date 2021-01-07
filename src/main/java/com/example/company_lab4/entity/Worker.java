package com.example.company_lab4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "worker")
public class Worker implements EntityInterface{
    private Integer personnelNumber;
    private String fullName;
    private Integer category;
    private Machine machine;
    public Worker() {
    }

    @Id
    @Column(name = "personnel_number", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getPersonnelNumber() {
        return personnelNumber;
    }

    public void setPersonnelNumber(Integer personnelNumber) {
        this.personnelNumber = personnelNumber;
    }

    @Column(name = "full_name", nullable = false, length = 40)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "category", nullable = false)
    public Integer getCategory() { return category; }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return personnelNumber == worker.personnelNumber &&
                Objects.equals(fullName, worker.fullName) &&
                category.equals(worker.category);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mashine_id")
    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    @Transient
    @JsonIgnore
    public Map<String, String> getFieldsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("personnelNumber", personnelNumber.toString());
        map.put("fullName", fullName);
        map.put("category", category.toString());
        map.put("machineId", machine.getId().toString());
        return map;
    }

    @Transient
    @JsonIgnore
    public Integer getIdentifier() {
        return getPersonnelNumber();
    }

}
