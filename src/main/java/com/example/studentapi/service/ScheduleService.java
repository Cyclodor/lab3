package com.example.studentapi.service;

import com.example.studentapi.dao.ScheduleDAO;
import com.example.studentapi.model.Course;
import com.example.studentapi.dto.BsuirGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleDAO scheduleDAO;
    @Autowired
    private CacheService cacheService;
    public List<Course> getStudentCourses() {
        String cacheKey = "student_courses";
        if (cacheService.containsKey(cacheKey)) {
            return (List<Course>) cacheService.get(cacheKey);
        }
        List<Course> courses = scheduleDAO.getStudentCourses();
        cacheService.put(cacheKey, courses);
        return courses;
    }
    public List<BsuirGroupDto> getBsuirGroups() {
        String cacheKey = "bsuir_groups";
        if (cacheService.containsKey(cacheKey)) {
            return (List<BsuirGroupDto>) cacheService.get(cacheKey);
        }
        List<BsuirGroupDto> groups = scheduleDAO.getBsuirGroups();
        cacheService.put(cacheKey, groups);
        return groups;
    }
} 