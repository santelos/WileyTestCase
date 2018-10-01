package ru.pstroganov.cache.cacheImpl.subClasses;


import java.io.Serializable;
import java.util.Objects;

public class CacheNode<T> implements Serializable {

    T value;
    Integer usageCount;

    public CacheNode(T value){
        this.value = value;
        usageCount=1;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public void plusOneUasge(){
        usageCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheNode<?> cacheNode = (CacheNode<?>) o;
        return value.equals(cacheNode.getValue()) &&
                usageCount.equals(cacheNode.getUsageCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, usageCount);
    }
}
