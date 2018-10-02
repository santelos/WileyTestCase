/*
 * Created by PStroganov 02/10/18 22:02
 */

/*
 * Created by PStroganov 02/10/18 21:37
 */

package ru.pstroganov.cache.cacheNodes;

import java.io.*;
import java.util.UUID;

public class FileCacheNode<V extends Serializable> extends CacheNode<V> {

    private File value;

    public FileCacheNode(V value) throws IOException{
        super();
        set(value);
    }

    @Override
    public V get()
            throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(value);
        ObjectInputStream inStream = new ObjectInputStream(file);

        V obj = (V) inStream.readObject();

        file.close();
        inStream.close();

        return obj;
    }

    @Override
    public void set(V _value)
            throws IOException {
        value = new File("tmp\\" + UUID.randomUUID() + ".tmp");

        FileOutputStream file = new FileOutputStream(value);
        ObjectOutputStream outStream = new ObjectOutputStream(file);

        outStream.writeObject(_value);

        outStream.close();
        file.close();
    }

    @Override
    public Boolean inMemory() {
        return false;
    }
}
