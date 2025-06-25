package com.example.studentapi.repository;

import com.example.studentapi.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
} 