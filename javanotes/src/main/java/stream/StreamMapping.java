package stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class StreamMapping {

    @Getter
    @RequiredArgsConstructor
    private static class Student {
        private final String name;
        private final int score;
    }

    public static void streamMapping() {
        flatMapExample();
        flatMapExample2();
        mapExample();
        asStreamExample();
        boxExample();
    }

    private static void flatMapExample() {
        List<String> stringList = List.of("java8 lambda", "stream mapping");

        stringList.stream()
                .flatMap(s -> {
                    return Arrays.stream(s.split(" "));
                })
                .forEach(s -> System.out.println("s = " + s));
    }

    private static void flatMapExample2() {
        List<String> stringList = List.of("1, 2, 3", "4, 5, 6");

        stringList.stream()
                .flatMapToInt(s -> {
                    String[] strArr = s.split(",");
                    int[] integerList = new int[strArr.length];
                    int cnt = 0;
                    for (String str : strArr) {
                        integerList[cnt++] = Integer.parseInt(str.trim());
                    }
                    return Arrays.stream(integerList);
                })
                .forEach(i -> System.out.println("i = " + i));
    }

    private static void mapExample() {
        List<Student> students = List.of(
                new Student("ahn", 30),
                new Student("kim", 40),
                new Student("park", 50));

        students.stream()
                .map(Student::getName)
                .forEach(n -> System.out.println("name = " + n));

        students.stream()
                .mapToInt(Student::getScore)
                .forEach(i -> System.out.println("score = " + i));

    }

    private static void asStreamExample() {
        int[] intArr = {1, 2, 3, 4, 5, 6};

        Arrays.stream(intArr)
                .asDoubleStream()
                .forEach(d -> System.out.println("d = " + d));
    }

    private static void boxExample() {
        int[] intArr = {1, 2, 3, 4, 5, 6};

        Arrays.stream(intArr)
                .boxed()
                .forEach(i -> System.out.println("i = " + i));

    }
}
