package stream;

import java.util.Arrays;

public class StreamMatching {

    static int intArr[] = {2, 4, 6};

    public static void matching() {
        boolean allEven = Arrays.stream(intArr)
                .allMatch(i -> i % 2 == 0);
        System.out.println("allEven = " + allEven);

        boolean anyOdd = Arrays.stream(intArr)
                .anyMatch(i -> i % 3 == 0);
        System.out.println("anyOdd = " + anyOdd);

        boolean noneOdd = Arrays.stream(intArr)
                .noneMatch(i -> i % 3 == 0);
        System.out.println("noneOdd = " + noneOdd);
    }
}
