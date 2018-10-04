/*
 * Created by PStroganov 04/10/18 23:59
 */

/*
 * Created by PStroganov 03/10/18 23:00
 */

package ru.pstroganov.cache.cacheNodes;

import java.io.*;
import java.util.UUID;

public class FileCacheNode<V extends Serializable> implements CacheNodeInterface<V> {

    File value;
    Integer count = 1;

    public FileCacheNode(V _value) throws IOException {
        set(_value);
    }

    public FileCacheNode(V _value, Integer usage) throws IOException {
        set(_value);
        count=usage;
    }

    @Override
    public V get() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(value);
        ObjectInputStream inStream = new ObjectInputStream(file);

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
        if (value==null) return;
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
