package cn.tiakon.learn.java.concurrency.imooc.jimin.concurrency;

/**
 *
 * 并发容器 J.U.C
 * ArrayList -> CopyOnWriteArrayList {@link cn.tiakon.learn.java.concurrency.imooc.jimin.concurrency.CopyOnWriteArrayListExample}
 * 特性：
 *    添加操作时，将内容复制到新数组中，再将原数组指向新的数组。
 * 缺点：
 *  写操作会消耗内存，可能会导致GC
 *  合适读多写少。
 *  遵循最终一致性，复制时需要时间不适合实时。
 *
 * HashSet、TreeSet   ->
 * CopyOnWriteArraySet  {@link cn.tiakon.learn.java.concurrency.imooc.jimin.concurrency.CopyOnWriteArraySetExample}
 * 单独操作时原子性的，
 * ConcurrentSkipListSet {@link cn.tiakon.learn.java.concurrency.imooc.jimin.concurrency.ConcurrentSkipListSetExample}
 *
 * HashMap、TreeMap   ->
 * ConcurrentHashMap    {@link cn.tiakon.learn.java.concurrency.imooc.jimin.concurrency.ConcurrentHashMapExample}
 * ConcurrentSkipListMap {@link cn.tiakon.learn.java.concurrency.imooc.jimin.concurrency.ConcurrentSkipListMapExample}
 *
 */