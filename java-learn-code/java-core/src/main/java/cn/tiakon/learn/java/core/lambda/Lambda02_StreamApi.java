package cn.tiakon.learn.java.core.lambda;

import org.junit.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambda02_StreamApi {

    public static void main(String[] args) {

        // 通过返回值判读一个操作是 惰性求值，还是及早求值。
        // 返回 stream  就是惰性求值；返回其它就是及早求值。

        stream01_streamAndCollect();
        stream02_map();
        stream03_filter();
        stream04_flatMap();
        stream05_maxAndMin();
        stream06_reduce();
    }

    private static void stream01_streamAndCollect() {
        List<String> collected = Stream.of("a", "b", "c", "d").collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("a", "b", "c", "d"), collected);
    }

    // 传给 map 的 lambda 表达式 ： T -> Function -> R
    private static void stream02_map() {
        List<String> collected = Stream.of("a", "b", "hello").map(String::toUpperCase).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("A", "B", "HELLO"), collected);
    }

    // 传给 filter 的 lambda 表达式 ： T -> Predicate -> Boolean
    private static void stream03_filter() {
        List<String> collected = Stream.of("a", "1b", "c").filter(word -> word.contains("1")).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("1b"), collected);
    }

    // 传给 filter 的 lambda 表达式 ： Collection -> Function -> stream
    private static void stream04_flatMap() {
//        List<String> collected = Stream.of(Arrays.asList("1", "2"), Arrays.asList("3", "4")).flatMap(number -> number.stream()).collect(Collectors.toList());
        List<String> collected = Stream.of(Arrays.asList("1", "2"), Arrays.asList("3", "4")).flatMap(Collection::stream).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("1", "2", "3", "4"), collected);
    }

    private static void stream05_maxAndMin() {
        List<Track> trackList = Arrays.asList(new Track("Bakai", 665),
                new Track("Violets for Your Furs", 786),
                new Track("Time was", 234));

        Optional<Track> optionalMinTrack = trackList.stream().min(Comparator.comparing(Track::getLength));
        Track trackMin = optionalMinTrack.get();

        Optional<Track> optionalMaxTrack = trackList.stream().max(Comparator.comparing(Track::getLength));
        Track trackMax = optionalMaxTrack.get();

        Assert.assertEquals(234, trackMin.getLength());
        Assert.assertEquals(786, trackMax.getLength());
    }

    // 传给 reduce 的 lambda 表达式 ： BinaryOperator
    private static void stream06_reduce() {
//        Integer sum = Stream.of(1, 2, 3, 4).reduce(0, (acc, element) -> acc + element);
        Integer sum = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        Assert.assertEquals(10L, sum.longValue());
    }

}

class Track {
    private String name;
    private int length;

    public Track(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
