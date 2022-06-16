package stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

public class StreamSort {

    @RequiredArgsConstructor
    @Getter
    @ToString
    private static class Student implements Comparable<Student> {
        private final String name;
        private final int score;

        @Override
        public int compareTo(Student o) {
            return Integer.compare(score, o.getScore());
        }
    }

    public static void sortWithComparable() {
        List<StreamSort.Student> students = List.of(
                new StreamSort.Student("ahn", 70),
                new StreamSort.Student("kim", 40),
                new StreamSort.Student("park", 100));

        students.stream()
                .sorted()
                .forEach(s -> System.out.println("student = " + s));

        students.stream()
                .sorted((a, b) -> {
                    return Integer.compare(b.getScore(), a.getScore());
                })
                .forEach(s -> System.out.println("student = " + s));
    }

}
