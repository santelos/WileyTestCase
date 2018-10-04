/*
 * Created by PStroganov 02/10/18 22:03
 */

package ru.pstroganov.cache.cacheStrategy;


import ru.pstroganov.cache.cacheNodes.CacheNodeInterface;

import java.util.Map;

@FunctionalInterface
public interface CacheStrategy {

    <K> void sortAndDivide(Map<K, CacheNodeInterface> cacheTable, Integer xamMem, Integer maxFile);

}
