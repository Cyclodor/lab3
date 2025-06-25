package com.example.studentapi.service;

import com.example.studentapi.model.Learner;
import com.example.studentapi.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearnerService {
    @Autowired
    private LearnerRepository learnerRepository;
    @Autowired
    private CacheService cacheService;
    public List<Learner> getAllLearners() {
        String cacheKey = "all_learners";
        if (cacheService.containsKey(cacheKey)) {
            return (List<Learner>) cacheService.get(cacheKey);
        }
        List<Learner> learners = learnerRepository.findAll();
        cacheService.put(cacheKey, learners);
        return learners;
    }
    public Optional<Learner> getLearnerById(Long id) {
        String cacheKey = "learner_" + id;
        if (cacheService.containsKey(cacheKey)) {
            return Optional.ofNullable((Learner) cacheService.get(cacheKey));
        }
        Optional<Learner> learner = learnerRepository.findById(id);
        learner.ifPresent(l -> cacheService.put(cacheKey, l));
        return learner;
    }
    public Learner saveLearner(Learner learner) {
        Learner saved = learnerRepository.save(learner);
        cacheService.put("learner_" + saved.getId(), saved);
        cacheService.remove("all_learners");
        return saved;
    }
    public void deleteLearner(Long id) {
        learnerRepository.deleteById(id);
        cacheService.remove("learner_" + id);
        cacheService.remove("all_learners");
    }
    public List<Learner> getLearnersByCourseDepartment(String department) {
        String cacheKey = "learners_by_department_" + department;
        if (cacheService.containsKey(cacheKey)) {
            List<Learner> cachedData = (List<Learner>) cacheService.get(cacheKey);
            return cachedData;
        }
        List<Learner> learners = learnerRepository.findLearnersByCourseDepartment(department);
        if (learners != null && !learners.isEmpty()) {
            cacheService.put(cacheKey, learners);
        }
        return learners;
    }
} 