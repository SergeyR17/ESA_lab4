package com.example.company_lab4.controllers;

import com.example.company_lab4.entity.Machine;
import com.example.company_lab4.entity.MachineList;
import com.example.company_lab4.entity.Worker;
import com.example.company_lab4.repository.MachineRepository;
import com.example.company_lab4.repository.WorkerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.company_lab4.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Реализация двух вариантов API для сотрудников
@Controller
@RequestMapping("/RESTapi/machines/")
public class ApiForMachine extends MessageController {
    @Autowired
    // Подключим (свяжем) репозитории (которые являются оболочкой баз Postgress)
    MachineRepository machineRepository;
    @Autowired
    WorkerRepository workerRepository;

    //
    // Получить все записи в формате json ( далее следует реализация на xml)
    //@GetMapping
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private List<Machine> getMachines() {

        return this.machineRepository.findAll();
    }

    // Получить запись по id
    //@GetMapping("{id}")
    @RequestMapping(value="{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Machine> getMachineById(@PathVariable(value = "id") Integer machineId)
            throws NotFoundException {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new NotFoundException("Машина отсутствует по этому id :: " + machineId));
        return ResponseEntity.ok().body(machine);
    }

    // Создать запись
    //@PostMapping
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Machine addMachine(@RequestBody Machine machine) {
        Machine created = machineRepository.save(machine);
        // Отправка сообщения о создании
        //sendCreateMessage(machine);
        return created;
    }

    // Обновить запись
    //@PutMapping("{id}")
    @RequestMapping(value="{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Machine> updateMachine(@PathVariable Integer id, @Validated @RequestBody Machine machineDetails)
            throws NotFoundException {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Машина отсутствует по этому id :: " + id));
        machine.setType(machineDetails.getType());
        // Отправка сообщения об изменении
        //sendUpdateMessage(machine);
        return ResponseEntity.ok(machineRepository.save(machine));
    }

    //удалить запись
    //@DeleteMapping("{id}")
    @RequestMapping(value="{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Boolean> deleteMachine(@PathVariable Integer id)
            throws NotFoundException {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Машина отсутствует по этому id : " + id));

        List<Worker> workers = workerRepository.findAll();
        workers.forEach(worker -> {
            if (worker.getMachine().getId().equals(machine.getId())) {
                worker.setMachine(null);
                workerRepository.save(worker);
            }
        });
        machineRepository.delete(machine);
        // Отправка сообщения об удалении
        //sendDeleteMessage(machine);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }



    // Получить все записи  В формате xml
    @GetMapping("xml")
    private ResponseEntity<String> getMachinesXml() throws JsonProcessingException {
        List<Machine> machines = new ArrayList<Machine>();
        machines = this.machineRepository.findAll();
        return getXmlResponse(new MachineList(machines));
    }

    // Получить запись по id
    @GetMapping("xml/{id}")
    public ResponseEntity<String> getMachineByIdXml(@PathVariable(value = "id") Integer machineId)
            throws NotFoundException, JsonProcessingException {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new NotFoundException("Станок не находится по этому id :: " + machineId));
        return getXmlResponse(machine);
    }

    // Создать запись
    @PostMapping("xml")
    @ResponseBody
    public Machine addMachineXml(@RequestBody Machine machine) {
        Machine created = machineRepository.save(machine);
        sendCreateMessage(created);
        return created;
    }

    // Обновить запись
    @PutMapping("xml/{id}")
    @ResponseBody
    public ResponseEntity<Machine> updateMachineXml(@PathVariable Integer id, @Validated @RequestBody Machine machineDetails)
            throws NotFoundException {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Машина отсутствует по этому id :: " + id));
        machine.setType(machineDetails.getType());
        sendUpdateMessage(machine);

        return ResponseEntity.ok(machineRepository.save(machine));
    }

    //удалить запись
    @DeleteMapping("xml/{id}")
    @ResponseBody
    public Map<String, Boolean> deleteMachineXml(@PathVariable Integer id)
            throws NotFoundException {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Машина отсутствует по этому idd :: " + id));

        List<Worker> workers = workerRepository.findAll();
        workers.forEach(worker -> {
            if (worker.getMachine().getId().equals(machine.getId())) {
                worker.setMachine(null);
                workerRepository.save(worker);
            }
        });

        machineRepository.delete(machine);
        sendDeleteMessage(machine);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }


}
