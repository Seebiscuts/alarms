package com.ruban.tech.alarms.repository;

import com.ruban.tech.alarms.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
