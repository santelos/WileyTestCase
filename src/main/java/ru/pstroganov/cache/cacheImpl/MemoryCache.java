package ru.pstroganov.cache.cacheImpl;

import ru.pstroganov.cache.UsageCounter;
import ru.pstroganov.cache.cacheImpl.subClasses.CacheNode;
import ru.pstroganov.cache.interfaces.Cache;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache<K, V> implements Cache<K,V> {

    private Map<K, CacheNode<V>> memoryMap;

    MemoryCache(Integer minSize) {
        memoryMap = new HashMap<>(minSize);
    }

    MemoryCache(Integer minSize, Float loadFactor) {
        memoryMap = new HashMap<>(minSize, loadFactor);
    }

    MemoryCache(){
        memoryMap = new HashMap<>();
    }

    @Override
    public void save(K key, V object) {
        if(memoryMap==null || key==null || object==null) return;
        memoryMap.put(key, new CacheNode<>(object));
        UsageCounter.getCounter().plusOneUsage();
    }

    @Override
    public V get(K key) {
        if (memoryMap.containsKey(key)){
            CacheNode<V> node = memoryMap.get(key);
            node.plusOneUasge();
            return node.getValue();
        }
        return null;
    }

    @Override
    public void delete(K key) {
        memoryMap.remove(key);
    }
}
