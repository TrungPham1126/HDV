package com.example.demo.repository;

import com.example.demo.entity.Video;
import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    // Lấy tất cả video của một khóa học
    List<Video> findByCourse(Course course);
}