package ru.pstroganov.cache.interfaces;

import ru.pstroganov.cache.cacheImpl.subClasses.CacheNode;

public interface Cache<K,V> {

    void save(K key, V object);

    V get(K key);

    void delete(K key);

}
