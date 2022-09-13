package stream;

import java.util.ArrayList;
import java.util.List;

public class FilterAndDistinct {

    private static List<String> names = List.of("Ahn", "Kim", "Cha", "Ka", "Ahn", "Kim", "Lee", "Choi", "Lim");

    public static void filterAndDistinct() {
        filterExample();
        distinctExample();
    }


    private static void filterExample() {
        System.out.println("Filter Example");
        names.stream()
                .filter( s -> s.startsWith("C"))
                .forEach(s -> System.out.println("s = " + s));
    }

    private static void distinctExample() {
        System.out.println("Distinct Example");
        names.stream()
                .distinct()
                .forEach(s -> System.out.println("s = " + s));
    }
}
