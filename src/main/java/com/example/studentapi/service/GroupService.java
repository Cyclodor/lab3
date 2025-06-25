package com.example.studentapi.service;

import com.example.studentapi.model.Group;
import com.example.studentapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CacheService cacheService;

    public List<Group> getAllGroups() {
        String cacheKey = "all_groups";
        if (cacheService.containsKey(cacheKey)) {
            return (List<Group>) cacheService.get(cacheKey);
        }
        List<Group> groups = groupRepository.findAll();
        cacheService.put(cacheKey, groups);
        return groups;
    }

    public Optional<Group> getGroupById(Long id) {
        String cacheKey = "group_" + id;
        if (cacheService.containsKey(cacheKey)) {
            return Optional.ofNullable((Group) cacheService.get(cacheKey));
        }
        Optional<Group> group = groupRepository.findById(id);
        group.ifPresent(g -> cacheService.put(cacheKey, g));
        return group;
    }

    public Group saveGroup(Group group) {
        Group saved = groupRepository.save(group);
        cacheService.put("group_" + saved.getId(), saved);
        cacheService.remove("all_groups");
        return saved;
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
        cacheService.remove("group_" + id);
        cacheService.remove("all_groups");
    }
} 