/*
 * Created by PStroganov 02/10/18 22:03
 */

package ru.pstroganov.cache.cacheStrategy;


import ru.pstroganov.cache.cacheNodes.CacheNode;

import java.util.LinkedHashMap;

@FunctionalInterface
public interface CacheStrategy {

    <K,E extends CacheNode> void sortAndDivide(LinkedHashMap<K, E> cacheTable);

}
