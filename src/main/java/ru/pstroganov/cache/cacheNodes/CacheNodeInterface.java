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

public interface CacheNodeInterface<V extends Serializable> {

    V get() throws IOException, ClassNotFoundException;

    V get(Boolean delete) throws IOException, ClassNotFoundException;

    void removeValue() throws IOException, ClassNotFoundException;

    void set(V value) throws IOException;

    Integer count();

    Boolean inMemory();

}
