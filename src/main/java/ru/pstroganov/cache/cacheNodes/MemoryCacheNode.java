/*
 * Created by PStroganov 04/10/18 23:59
 */

/*
 * Created by PStroganov 03/10/18 23:03
 */

package ru.pstroganov.cache.cacheNodes;

import java.io.IOException;
import java.io.Serializable;

public class MemoryCacheNode<V extends Serializable> implements CacheNodeInterface<V> {

    V value;
    Integer count=1;

    public MemoryCacheNode(V _value){
        set(_value);
    }

    public MemoryCacheNode(V _value, Integer usage){
        set(_value);
        count=usage;
    }

    @Override
    public V get(){
        count++;
        return value;
    }

    @Override
    public V get(Boolean delete) throws IOException, ClassNotFoundException {
        if (delete) {
            V tmpVal = value;
            removeValue();
            return tmpVal;
        }
        return value;
    }

    @Override
    public void set(V _value){
        count=1;
        value=_value;
    }

    @Override
    public void removeValue() throws IOException, ClassNotFoundException {
        if (value==null) return;
        value=null;
    }

    @Override
    public Integer count() {
        return count;
    }

    @Override
    public Boolean inMemory() {
        return true;
    }
}
