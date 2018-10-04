/*
 * Created by PStroganov 04/10/18 23:59
 */

/*
 * Created by PStroganov 03/10/18 23:00
 */

/*
 * Created by PStroganov 03/10/18 22:56
 */

package ru.pstroganov.cache.cacheNodes;

import java.io.IOException;
import java.io.Serializable;

/**
 * Интерфейс, который объявляет функционал элемента кэша.<br></br>
 * Сделано для реализации кэша как одной коллекции<br></br>
 *
 * @param <V> Класс {@code extends {@link Serializable}} который будет храниться в ноде
 */
public interface CacheNodeInterface<V extends Serializable> {

    /**
     * Возвращает хранимый объект
     * @return Объект {@code extends Serialiazable}
     * @throws IOException В случае если кэш файлового уровня не смог найти хранимый файл.
     * @throws ClassNotFoundException В случае если объект из файлового кэша не может быть преобразован к дженерику.
     */
    V get() throws IOException, ClassNotFoundException;

    /**
     * Возвращает хранимый объект<br></br>
     * Если флаг - {@code true}, то также удаляет хранимый объект
     * @param delete Флаг на удаление
     * @return Объект {@code extends Serialiazable}
     * @throws IOException В случае если кэш файлового уровня не смог найти хранимый файл.
     * @throws ClassNotFoundException В случае если объект из файлового кэша не может быть преобразован к дженерику.
     */
    V get(Boolean delete) throws IOException, ClassNotFoundException;

    /**
     * Удаляет хранимый объект
     * @throws IOException В случае если кэш файлового уровня не смог найти хранимый файл.
     * @throws ClassNotFoundException В случае если объект из файлового кэша не может быть преобразован к дженерику.
     */
    void removeValue() throws IOException, ClassNotFoundException;

    /**
     * Сохраняет переданный объект
     * @param value Объект
     * @throws IOException В случае ошибки записи в файловый кэш
     */
    void set(V value) throws IOException;

    /**
     * Возвращает количество обращений к объекту
     * @return Количество обращений
     */
    Integer count();

    /**
     * Объект находится в файловом кэше или в памяти
     * @return {@code True} - в памяти, {@code False} - в файле
     */
    Boolean inMemory();

}
