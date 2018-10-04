package ru.pstroganov;

import ru.pstroganov.cache.CacheHolder;

import java.util.stream.IntStream;

public class Main {

    /**
     * Не писал Unit тесты потому что не хотел тратить много времени на это.<br></br>
     * Тут собственно тестируется работоспособность основных функций.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            // Принадлежность нодов к файловому или оперативному кэшу придется проверять в дебагере, потому что она динамически меняется в течении рантайма
            // Стоит доводить исполнение алгоритма до конца, потому что иначе файлы из директории tmp\ не будут удалены.

            CacheHolder<Integer, ClassForCache> cache = new CacheHolder<>(15, 15);

            cache.flush();

            // Просто добавление элементов в кэш
            System.out.println("\nAdd");
            System.out.println("=================================");
            IntStream.range(45, 80).forEach(i -> {
                cache.save(i, new ClassForCache(i, Integer.toString(i), (double) i));
                System.out.println(i + " " + ((cache.get(i) == null) ? null : cache.get(i).toString()));
            });
            cache.flush();

            // Добавление, а потом удаление элементов
            System.out.println("\nDelete");
            System.out.println("=================================");
            IntStream.range(45, 80).forEach(i -> {
                cache.save(i, new ClassForCache(i, Integer.toString(i), (double) i));
                System.out.println(i + " " + ((cache.get(i) == null) ? null : cache.get(i).toString()));
            });
            System.out.println("Start to delete");
            IntStream.range(45, 80).forEach(i -> {
                cache.delete(i);
                System.out.println(i + " " + ((cache.get(i) == null) ? null : cache.get(i).toString()));
            });
            cache.flush();

            // Если максимальные значения == 0
            System.out.println("\nZero max sizes");
            System.out.println("=================================");
            CacheHolder<Integer, ClassForCache> zeroCache = new CacheHolder<>(0, 0);
            zeroCache.save(15, new ClassForCache());
            System.out.println(zeroCache.get(15) == null ? null : zeroCache.get(15).toString());

            System.out.println();
            cache.flush();
            zeroCache.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
