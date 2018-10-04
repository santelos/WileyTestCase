/*
 * Created by PStroganov 02/10/18 22:03
 */

package ru.pstroganov.cache.cacheStrategy;


import ru.pstroganov.cache.cacheNodes.CacheNodeInterface;

import java.util.Map;

/**
 * Функциональный интерфейс для реализции стратегии кэширования.<br></br>
 * Содержит метод, который оценивает, какие объекты в кэше должны храниться в файловой системе, а какие в оперативной памяти<br></br>
 * Таким образом, можно реализовать другие стратегии кэширования
 */
@FunctionalInterface
public interface CacheStrategyInterface {

    /**
     * Метод для реализации стратегии кэширования.<br></br>
     * Принимает Map с кэшем, а так же максимальное кол-во кэша в оперативной памяти и в файловой.<br></br>
     * Определяет, какие объекты должны храниться в оперативной памяти, а какие в файловой системе.<br></br>
     * Изменяет способ хранения объекта, если это необходимо.<br></br>
     * Полностью удаляет то, что должно быть вытеснено из кэша на совсем.
     *
     * @param cacheTable Ассоциативная таблица с кэшем
     * @param maxMem     Максимальное кол-во элементов в оперативной памяти
     * @param maxFile    Максимальное кол-во элементов в файловой системе
     * @param <K>        Дженерик, определяющий ключ
     */
    <K> void sortAndDivide(Map<K, CacheNodeInterface> cacheTable, Integer maxMem, Integer maxFile);

}
