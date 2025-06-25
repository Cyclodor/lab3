package com.example.studentapi.controller;

import com.example.studentapi.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {
    
    @Autowired
    private CacheService cacheService;
    
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getCacheInfo() {
        Map<String, Object> info = new HashMap<>();
        int size = cacheService.size();
        info.put("size", size);
        System.out.println("=== CACHE CONTROLLER ===");
        System.out.println("Cache info requested. Current size: " + size);
        System.out.println("Returning: " + info);
        return ResponseEntity.ok(info);
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCache() {
        cacheService.clear();
        System.out.println("Cache cleared successfully");
        return ResponseEntity.ok("Cache cleared successfully");
    }
    
    @DeleteMapping("/{key}")
    public ResponseEntity<String> removeFromCache(@PathVariable String key) {
        cacheService.remove(key);
        System.out.println("Key '" + key + "' removed from cache");
        return ResponseEntity.ok("Key '" + key + "' removed from cache");
    }
} 