package com.ruban.tech.alarms.repository;

import com.ruban.tech.alarms.entity.Alarms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmsRepository extends JpaRepository<Alarms, Integer> {
}
