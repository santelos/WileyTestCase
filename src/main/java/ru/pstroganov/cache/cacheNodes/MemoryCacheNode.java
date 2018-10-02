/*
 * Created by PStroganov 02/10/18 22:02
 */

package ru.pstroganov.cache.cacheNodes;


import java.io.Serializable;

public class MemoryCacheNode<V extends Serializable> extends CacheNode<V> {

    private V value;

    public MemoryCacheNode(V value){
        super();
        set(value);
    }

    @Override
    public V get() {
        return value;
    }

    @Override
    public void set(V value) {
        this.value=value;
    }

    @Override
    public Boolean inMemory() {
        return true;
    }
}
