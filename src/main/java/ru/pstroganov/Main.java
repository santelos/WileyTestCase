package ru.pstroganov;

import ru.pstroganov.cache.CacheHolder;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        try {
            CacheHolder<Integer,ClassForCache> cache = new CacheHolder<>(15,15);

            cache.flush();

            System.out.println("\nAdd");
            System.out.println("=================================");
            IntStream.range(45,80).forEach(i->{
                cache.save(i,new ClassForCache(i,Integer.toString(i),(double) i));
                System.out.println(i+" "+ ((cache.get(i) == null) ? null : cache.get(i).toString()));
            });
            cache.flush();

            System.out.println("\nDelete");
            System.out.println("=================================");
            IntStream.range(45,80).forEach(i->{
                cache.save(i,new ClassForCache(i,Integer.toString(i),(double) i));
                System.out.println(i+" "+ ((cache.get(i) == null) ? null : cache.get(i).toString()));
            });
            System.out.println("Start to delete");
            IntStream.range(45,80).forEach(i->{
                cache.delete(i);
                System.out.println(i+" "+ ((cache.get(i) == null) ? null : cache.get(i).toString()));
            });
            cache.flush();

            System.out.println("\nZero max sizes");
            System.out.println("=================================");
            CacheHolder<Integer,ClassForCache> zeroCache = new CacheHolder<>(0,0);
            zeroCache.save(15,new ClassForCache());
            System.out.println(zeroCache.get(15) == null ? null : zeroCache.get(15).toString());

            System.out.println();
            cache.flush();
            zeroCache.flush();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

}
