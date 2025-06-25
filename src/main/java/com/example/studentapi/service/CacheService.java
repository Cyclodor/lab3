package com.example.studentapi.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {
    
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    
    public void put(String key, Object value) {
        cache.put(key, value);
        System.out.println("CACHE PUT: key='" + key + "', value size=" + (value != null ? "not null" : "null"));
        System.out.println("CACHE SIZE AFTER PUT: " + cache.size());
    }
    
    public Object get(String key) {
        Object value = cache.get(key);
        System.out.println("CACHE GET: key='" + key + "', found=" + (value != null));
        return value;
    }
    
    public boolean containsKey(String key) {
        boolean contains = cache.containsKey(key);
        System.out.println("CACHE CONTAINS: key='" + key + "', result=" + contains);
        return contains;
    }
    
    public void remove(String key) {
        cache.remove(key);
        System.out.println("CACHE REMOVE: key='" + key + "'");
        System.out.println("CACHE SIZE AFTER REMOVE: " + cache.size());
    }
    
    public void clear() {
        int sizeBefore = cache.size();
        cache.clear();
        System.out.println("CACHE CLEAR: size before=" + sizeBefore + ", after=" + cache.size());
    }
    
    public int size() {
        int size = cache.size();
        System.out.println("CACHE SIZE: " + size);
        return size;
    }
} 