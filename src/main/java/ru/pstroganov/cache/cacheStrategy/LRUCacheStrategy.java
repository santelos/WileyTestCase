/*
 * Created by PStroganov 02/10/18 22:05
 */

package ru.pstroganov.cache.cacheStrategy;

import ru.pstroganov.cache.cacheNodes.CacheNode;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LRUCacheStrategy implements CacheStrategy{

    @Override
    public <K, E extends CacheNode> void sortAndDivide(LinkedHashMap<K, E> cacheTable) {
        Set<Map.Entry<K,E>> entrySet = cacheTable.entrySet();

        cacheTable.clear();

        entrySet.stream().sorted(new Comparator<Map.Entry<K, E>>() {
            @Override
            public int compare(Map.Entry<K, E> o1, Map.Entry<K, E> o2) {
                o1.getValue().
            }
        });
    }
}
