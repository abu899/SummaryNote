package stream;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class StreamAggregation {
    static int intArr[] = {1, 2, 3, 4, 5, 6};

    public static void aggregation() {
        countAgg();
        sumAgg();
        avgAgg();
        minMaxAgg();
    }

    private static void countAgg() {
        long count = Arrays.stream(intArr)
                .filter(i -> i % 2 == 0)
                .count();

        System.out.println("even count = " + count);
    }

    private static void sumAgg() {
        int sum = Arrays.stream(intArr)
                .filter(i -> i % 2 == 0)
                .sum();

        System.out.println("even sum = " + sum);
    }

    private static void avgAgg() {
        OptionalDouble average = Arrays.stream(intArr)
                .filter(i -> i % 3 == 0)
                .average();

        System.out.println("odd average = " + average.orElse(0));
    }

    private static void minMaxAgg() {
        Arrays.stream(intArr)
                .filter(i -> i % 3 == 0)
                .min()
                .ifPresent(i -> System.out.println("odd min = " + i));

        Arrays.stream(intArr)
                .filter(i -> i % 3 == 0)
                .max()
                .ifPresent(i -> System.out.println("odd max = " + i));

    }
}
