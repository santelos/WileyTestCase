/*
 * Created by PStroganov 03/10/18 00:52
 */

package ru.pstroganov.cache;

import ru.pstroganov.cache.cacheNodes.CacheNodeInterface;
import ru.pstroganov.cache.cacheNodes.MemoryCacheNode;
import ru.pstroganov.cache.cacheStrategy.CacheStrategyInterface;
import ru.pstroganov.cache.cacheStrategy.LRUCacheStrategy;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CacheHolder<K, V extends Serializable> {

    Integer maxMemorySize;

    Integer maxFileSize;

    Map<K, CacheNodeInterface> cache = new HashMap<>();

    // По идее, можно реализовать любую стратегию кэширования
    CacheStrategyInterface cacheStrategy = new LRUCacheStrategy();

    public CacheHolder(Integer maxMemorySize, Integer maxFileSize) {
        if (maxMemorySize < 0) this.maxMemorySize = 0;
        if (maxFileSize <= 0) this.maxFileSize = 0;
        this.maxMemorySize = maxMemorySize;
        this.maxFileSize = maxFileSize;
    }

    public void save(K key, V value) {
        tryToRechache();
        if (cache.size() + 1 <maxMemorySize+maxFileSize) cache.put(key, new MemoryCacheNode<>(value));
    }

    public V get(K key) {
        try {
            if (!cache.containsKey(key)) return null;
            V ret = (V) cache.get(key).get();
            return ret;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to access the Cache Node. It doesn't exist or has been removed by Cache Strategy");
            return null;
        }
    }

    public void deleteValue(K key) {
        try {
            if(cache.containsKey(key)) cache.get(key).removeValue();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Unable to delete value for key " + key);
        }
    }

    public void delete(K key) {
        if(cache.containsKey(key)){
            deleteValue(key);
            cache.remove(key);
        }
    }

    public void flush() {
        cache.forEach((k, v) -> deleteValue(k));
        cache.clear();
    }

    private void tryToRechache() {
        final int[] counter = {0};
        cache.forEach((k, v) -> {
            if (v.inMemory()) {
                counter[0]++;
            }
        });
        // Проверка только по объектам в оперативной памяти потому что все элементы кладутся сначала в оперативку
        // - 1 потому что, новый элемент еще не добавлен, но ему нужно место в оперативной памяти
        if (counter[0] > maxMemorySize - 1) {
            cacheStrategy.sortAndDivide(cache, maxMemorySize - 1, maxFileSize);
        }
    }

}
