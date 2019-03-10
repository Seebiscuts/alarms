package com.ruban.tech.alarms.controller;

import com.ruban.tech.alarms.entity.Alarm;
import com.ruban.tech.alarms.repository.AlarmRepository;
import com.ruban.tech.alarms.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class AlarmController {

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    AlarmService alarmService;

    @RequestMapping(value = "/alarm", method = RequestMethod.GET)
    public List<Alarm> getAlarms() {
        return alarmRepository.findAll(Sort.by(Sort.Direction.DESC, "updated"));

    }

    @RequestMapping(value = "/alarm/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAlarm(@PathVariable(value = "id") Long id) {
        Optional<Alarm> alarmOptional = alarmRepository.findById(id);
        if (alarmOptional.isPresent()) {
            return ResponseEntity.ok(alarmOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(value = "/alarm", method = RequestMethod.POST)
    public ResponseEntity<Object> createAlarm(@RequestBody Alarm alarm) {
        Alarm savedAlarm = alarmService.createAlarm(alarm);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAlarm.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/alarm/upvote", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Alarm> upvoteAlarm(@RequestParam(value = "alarmId") long alarmId) {
        Alarm alarm;
        try {
            alarm = alarmService.upvoteAlarm(alarmId);
            return new ResponseEntity<>(alarm, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid AlarmId");
            return ResponseEntity.notFound().build();
        }

    }
}
