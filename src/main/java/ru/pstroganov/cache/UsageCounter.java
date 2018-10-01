/*
 * Created by PStroganov 02/10/18 00:13
 */

package ru.pstroganov.cache;

import java.util.HashMap;
import java.util.TreeMap;

public class UsageCounter<K,V> extends TreeMap<K,V> {

    private static UsageCounter instance = new UsageCounter();

    public static UsageCounter getCounter() {
        return instance;
    }

    public void plusOneUsage(K key){
        if (!containsKey(key)){
            this.put(1,key)
        }
    }
}
