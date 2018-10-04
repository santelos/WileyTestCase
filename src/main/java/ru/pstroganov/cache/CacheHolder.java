/*
 * Created by PStroganov 03/10/18 00:52
 */

package ru.pstroganov.cache;

import ru.pstroganov.cache.cacheNodes.*;
import ru.pstroganov.cache.cacheStrategy.CacheStrategyInterface;
import ru.pstroganov.cache.cacheStrategy.LRUCacheStrategy;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс - API работы с кэшэм.<br></br>
 * Поддерживает: <br></br>
 * <pre>
 *     Сохранение объектов - {@link #save(Object, Serializable)}
 *     Обращение к объекту - {@link #get(Object)}
 *     Удаление объекта из кэша - {@link #delete(Object)}
 *     Удаление значения объекта кэша, без удаления самого объекта - {@link #deleteValue(Object)}
 *     Полная очистка кэша - {@link #flush()};
 * </pre>
 * <p>
 * Кэш реализуется на единственной хэш таблице, <br></br>
 * в которой лежат как оперативные {@link MemoryCacheNode} ноды, <br></br>
 * так и файловые {@link FileCacheNode} <p></p>
 * <p>
 * Эта таблица изменяется в процессе работы, в соответствии с стратегией кэширования.<br></br>
 * Перекэширование происходит, когда переполнен оперативный кэш.<br></br>
 * Это происходит потому что все элементы добавляются сначала в оперативный кэш и файловый не может оказаться переполненым,
 * пока не переполнится оперативный.<br></br>
 *
 * @param <K>
 * @param <V>
 */
public class CacheHolder<K, V extends Serializable> {

    private Integer maxMemorySize;

    private Integer maxFileSize;

    Map<K, CacheNodeInterface> cache = new HashMap<>();

    // По идее, можно реализовать любую стратегию кэширования
    CacheStrategyInterface cacheStrategy = new LRUCacheStrategy();

    /**
     * Создает экземпляр класса.<br></br>
     * Устанавливает максимальные размеры оперативного и файлового кэша.<br></br>
     * Если значение любого из размеров < 0, то устанавливает его в 0<br></br>
     *
     * @param maxMemorySize Максимальный размер оперативного кэша
     * @param maxFileSize   Максимальный размер файлового кэша
     */
    public CacheHolder(Integer maxMemorySize, Integer maxFileSize) {
        this.maxMemorySize = (maxMemorySize < 0) ? 0 : maxMemorySize;
        this.maxFileSize = (maxFileSize < 0) ? 0 : maxFileSize;
    }

    /**
     * Сохраняет объект в оперативный кэш.<br></br>
     * Сначала проверяет, переполнится ли оперативный кэш при добавлении элемента,<br></br>
     * Затем добавляет новый объект к оперативному кэшу
     *
     * @param key
     * @param value
     */
    public void save(K key, V value) {
        tryToRechache();
        if (cache.size() + 1 < maxMemorySize + maxFileSize) cache.put(key, new MemoryCacheNode<>(value));
    }

    /**
     * Достает объект из кэша.
     * Если по данному ключу нет никакого объекта или при его чтении возникла ошибка, то возвращает null
     *
     * @param key Ключ объекта
     * @return Хранящийся в кэше объект
     */
    public V get(K key) {
        try {
            if (!cache.containsKey(key)) return null;
            return (V) cache.get(key).get();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to access the Cache Node. It doesn't exist or has been removed by Cache Strategy");
            return null;
        }
    }

    /**
     * Удаляет значение объекта, хранящегося в кэше.
     *
     * @param key Ключ объекта
     */
    public void deleteValue(K key) {
        try {
            if (cache.containsKey(key)) cache.get(key).removeValue();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to delete value for key " + key);
        }
    }

    /**
     * Удаляет объект из кэша.
     *
     * @param key Ключ объекта
     */
    public void delete(K key) {
        if (cache.containsKey(key)) {
            // В любом случае надо удалять значение, а то в случае файлового кэша файлы останутся на диске
            deleteValue(key);
            cache.remove(key);
        }
    }

    /**
     * Полностью отчищает кэш
     */
    public void flush() {
        cache.forEach((k, v) -> deleteValue(k));
        cache.clear();
    }

    /**
     * Попытка вызвать перекэширование в соответствии со стратегией.<br></br>
     * Если оперативный кэш переполнен, то перекэширование вызывается
     */
    private void tryToRechache() {
        final int[] counter = {0};
        cache.forEach((k, v) -> {
            if (v.inMemory()) {
                counter[0]++;
            }
        });
        // Проверка только по объектам в оперативной памяти потому что все элементы кладутся сначала в оперативку
        // - 1 потому что, новый элемент еще не добавлен, но ему нужно место в оперативной памяти
        if (counter[0] > maxMemorySize - 1) {
            cacheStrategy.sortAndDivide(cache, maxMemorySize - 1, maxFileSize);
        }
    }

}
