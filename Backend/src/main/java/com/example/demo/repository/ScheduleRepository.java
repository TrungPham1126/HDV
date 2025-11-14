package com.example.demo.repository;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Lấy lịch học của một lớp
    List<Schedule> findByClassEntry(Class classEntry);

    // Lấy lịch học trong một khoảng thời gian
    List<Schedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}