package com.ruban.tech.alarms.controller;

import com.ruban.tech.alarms.entity.Alarms;
import com.ruban.tech.alarms.repository.AlarmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlarmController {

    @Autowired
    AlarmsRepository alarmsRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public List<Alarms> getAlarms(){
        return alarmsRepository.findAll();
    }
}
