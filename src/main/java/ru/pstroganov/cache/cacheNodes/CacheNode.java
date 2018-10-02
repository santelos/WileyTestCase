/*
 * Created by PStroganov 02/10/18 22:02
 */

package ru.pstroganov.cache.cacheNodes;

import java.io.IOException;
import java.io.Serializable;

public abstract class CacheNode<V extends Serializable > {

    private Integer usageCount;

    CacheNode(){
        resetCount();
    }

    abstract V get() throws IOException, ClassNotFoundException;

    abstract void set(V value) throws IOException;

    Integer increaseCount(){
        return ++usageCount;
    }

    Integer count(){
        return usageCount;
    }

    void resetCount(){
        usageCount=1;
    }

    abstract Boolean inMemory();

}
