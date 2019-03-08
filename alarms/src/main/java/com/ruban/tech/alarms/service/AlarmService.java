package com.ruban.tech.alarms.service;

import com.ruban.tech.alarms.entity.Alarm;
import com.ruban.tech.alarms.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AlarmService {
    @Autowired
    AlarmRepository alarmRepository;

    public void upvoteAlarm(Long alarmId) {
        Optional<Alarm> alarm = alarmRepository.findById(alarmId);
        if (!alarm.isPresent()) {
            throw new IllegalArgumentException("Invalid alarm");
        } else {
            Alarm alarmToUpdate = alarm.get();
            Long upvotes = alarmToUpdate.getUpvotes();
            if (upvotes == null ){
                upvotes = new Long(0);
            }
            alarmToUpdate.setUpvotes(++upvotes);
            alarmRepository.save(alarmToUpdate);
        }

    }

    public Alarm createAlarm(Alarm alarm){
        Alarm savedAlarm = alarmRepository.save(alarm);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject("https://bellbird.joinhandshake-internal.com/push", alarm, Alarm.class);
        return savedAlarm;
    }
}
