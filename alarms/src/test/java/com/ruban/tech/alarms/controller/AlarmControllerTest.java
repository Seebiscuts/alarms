package com.ruban.tech.alarms.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ruban.tech.alarms.entity.Alarm;
import com.ruban.tech.alarms.repository.AlarmRepository;
import com.ruban.tech.alarms.service.AlarmService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@RunWith(SpringRunner.class)
@WebMvcTest(AlarmController.class)
public class AlarmControllerTest {
    @Autowired
    private
    MockMvc mockMvc;

    @MockBean
    private AlarmService alarmService;

    @MockBean
    private AlarmRepository alarmRepository;

    private final Random random = new Random();

    @Test
    public void createAlarmCreatesNewAlarmEntry() throws Exception {
        Alarm alarm = createAlarm(true);
        Alarm savedAlarm = createAlarm(false);

        when(alarmService.createAlarm(any(Alarm.class))).thenReturn(savedAlarm);
        this.mockMvc.perform(post("/alarm").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(alarm)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void getAlarmsReturnsAlarmsInDescOrder() throws Exception {
        List<Alarm> alarms = new ArrayList<>();
        alarms.add(createAlarm(false));
        alarms.add(createAlarm(false));
        alarms.add(createAlarm(false));
        alarms.add(createAlarm(false));

        when(alarmRepository.findAll(any(Sort.class))).thenReturn(alarms);
        this.mockMvc.perform(get("/alarm").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("THIS IS")));
    }

    @Test
    public void getAlarmById() throws Exception {
        Alarm alarm = createAlarm(false);
        Optional<Alarm> optionalAlarm = Optional.of(alarm);

        when(alarmRepository.findById(any(Long.class))).thenReturn(optionalAlarm);
        this.mockMvc.perform(get(String.format("/alarm/%d",alarm.getId())).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(alarm.getId().toString())));
    }

    @Test
    public void updateUpVotes() throws Exception {
        Alarm alarm = createAlarm(false);
        alarm.setUpvotes(1L);
        when(alarmService.upvoteAlarm(any(Long.class))).thenReturn(alarm);

        this.mockMvc.perform(patch("/alarm/upvote?alarmId=12").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("\"upvotes\":1")));

    }
    private String toJson(Alarm alarm) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        System.out.println(gson.toJson(alarm));
        return gson.toJson(alarm);
    }

    private Alarm createAlarm(boolean withoutId) {
        Alarm alarm = new Alarm();
        if (!withoutId) {
            alarm.setId(random.nextLong());
        }
        alarm.setText(String.format("This is %s Alarm", random.nextInt(1000)));
        alarm.setUpvotes(0L);
        return alarm;
    }
}
