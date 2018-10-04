/*
 * Created by PStroganov 02/10/18 22:05
 */

package ru.pstroganov.cache.cacheStrategy;

import ru.pstroganov.cache.cacheNodes.CacheNodeInterface;
import ru.pstroganov.cache.cacheNodes.FileCacheNode;
import ru.pstroganov.cache.cacheNodes.MemoryCacheNode;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LRU(Least Recently Used) стратегия кэширования.<br></br>
 * Если переполнен оперативный кэш, то самый не используемый элемент переходит в файловый.<br></br>
 * Если переполнен файловый кэш, то самый не используемый элемент удаляется насовсем.
 */
public class LRUCacheStrategy implements CacheStrategyInterface {

    @Override
    public <K> void sortAndDivide(Map<K, CacheNodeInterface> cacheTable, Integer maxMem, Integer maxFile) {
        if (cacheTable == null) return;

        //Сортировка по убыванию количества использований. Находится в value().count()
        List<Map.Entry<K, CacheNodeInterface>> resList = cacheTable.entrySet().stream()
                .sorted(Comparator.comparing(o -> o.getValue().count(), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Проходит по отсортированному листу и
        // если среди часто используемых объектов есть те, которые лежат в фаловом кэше, то переводит их в память
        // И наоборот, если среди объектов в памяти есть те, которые не часто используются, то переводит их в файлы
        resList.forEach(e -> {
            try {
                // Если объект не находится в оперативке, но должен там находится
                if (!e.getValue().inMemory() && resList.indexOf(e) < maxMem) {
                    cacheTable.replace(e.getKey(), new MemoryCacheNode<>(e.getValue().get(true), e.getValue().count()));
                }
                // Если объект находится в оперативке, но не должен там находится
                if (e.getValue().inMemory() && resList.indexOf(e) >= maxMem && resList.indexOf(e) < maxMem + maxFile) {
                    cacheTable.replace(e.getKey(), new FileCacheNode<>(e.getValue().get(true), e.getValue().count()));
                }
                // Если объект должен быть вытеснен из кэша насовсем
                if (resList.indexOf(e) >= maxMem + maxFile) {
                    cacheTable.get(e.getKey()).removeValue();
                    cacheTable.remove(e.getKey());
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Object access error occurred while applying cache strategy. Key=\"" + e.getKey() + "\"");
            }
        });
    }
}
