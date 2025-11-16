package com.example.demo.repository;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.ClassT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByClassEntry(ClassT classEntry);

    List<Schedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}