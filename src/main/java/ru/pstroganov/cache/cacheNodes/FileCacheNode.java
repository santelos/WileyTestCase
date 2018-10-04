/*
 * Created by PStroganov 04/10/18 23:59
 */

/*
 * Created by PStroganov 03/10/18 23:00
 */

package ru.pstroganov.cache.cacheNodes;

import java.io.*;
import java.util.UUID;

/**
 * Класс реализации {@link CacheNodeInterface} как нод файлового кэша.
 * @param <V> Класс {@code extends {@link Serializable}} который будет храниться в ноде.
 */
public class FileCacheNode<V extends Serializable> implements CacheNodeInterface<V> {
    private File value;
    private Integer count = 1;

    /**
     * Конструктор для первичного создания Нода.<br></br>
     * Сохраняет объект как файл. Количество обращений выставляет в 1.
     * @param _value Объект для хранения.
     * @throws IOException В случае ошибки записи в файл.
     */
    public FileCacheNode(V _value) throws IOException {
        set(_value);
    }

    /**
     * Конструктор для создания Нода с установкой количества обращений.<br></br>
     * Сохраняет объект как файл. Количество обращений выставляет в переданное значение.
     * @param _value Объект для хранения.
     * @param usage Количство обращений.
     * @throws IOException В случае ошибки записи в файл.
     */
    public FileCacheNode(V _value, Integer usage) throws IOException {
        this(_value);
        count=usage;
    }

    @Override
    public V get() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(value);
        ObjectInputStream inStream = new ObjectInputStream(file);

        // Десериализуем полученный объект и кастим в дженерик
        V obj = (V) inStream.readObject();

        file.close();
        inStream.close();

        count++;

        return obj;
    }

    @Override
    public V get(Boolean delete) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(value);
        ObjectInputStream inStream = new ObjectInputStream(file);

        // Десериализуем полученный объект и кастим в дженерик
        V obj = (V) inStream.readObject();

        file.close();
        inStream.close();
        if(delete) removeValue();

        return obj;
    }

    @Override
    public void set(V _value) throws IOException {
        removeValue();
        count = 1;

        value = new File("tmp\\" + UUID.randomUUID() + ".tmp");
        //Если директории tmp\ не существует, надо создать
        if(!new File("tmp\\").exists()) new File("tmp\\").mkdir();
        value.createNewFile();

        FileOutputStream file = new FileOutputStream(value);
        ObjectOutputStream outStream = new ObjectOutputStream(file);

        outStream.writeObject(_value);

        outStream.close();
        file.close();
    }

    @Override
    public void removeValue() {
        // Бывает такое, что мы пытаемся удалить не существующее значение
        if (value==null) return;
        // Сбрасываем счечик обращений, потому что прежнего значения уже нет
        count=1;
        value.delete();
        value = null;
    }

    @Override
    public Integer count() {
        return count;
    }

    @Override
    public Boolean inMemory() {
        return false;
    }
}
