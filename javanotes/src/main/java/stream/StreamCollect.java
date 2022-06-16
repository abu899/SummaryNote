package stream;

import lambda.Gender;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static lambda.Gender.*;
import static stream.Student.*;
import static stream.Student.City.PUSAN;
import static stream.Student.City.SEOUL;

public class StreamCollect {

    private static List<Student> students = List.of(
            new Student("A", 77, MALE, SEOUL),
            new Student("B", 57, FEMALE, SEOUL),
            new Student("C", 30, MALE, PUSAN),
            new Student("D", 100, FEMALE, PUSAN),
            new Student("E", 49, MALE, PUSAN)
    );

    public static void doCollect() {
        toListExample();
        toHashMapExample();
        groupingExample();
        groupingExample2();
    }

    private static void toListExample() {
        List<Student> toMaleList = students.stream()
                .filter(s -> s.getGender() == MALE)
                .collect(Collectors.toList());

        toMaleList
                .forEach(s -> System.out.println("male List = " + s));
    }

    private static void toHashMapExample() {
        HashSet<Student> femaleSet = students.stream()
                .filter(s -> s.getGender() == FEMALE)
                .collect(Collectors.toCollection(HashSet::new));

        femaleSet
                .forEach(s -> System.out.println("female hashSet = " + s));
    }

    private static void groupingExample() {
        Map<Gender, List<Student>> groupByGender = students.stream()
                .collect(Collectors.groupingBy(Student::getGender));

        groupByGender.get(MALE)
                .forEach(s -> System.out.println("group by gender male = " + s));

        groupByGender.get(FEMALE)
                .forEach(s -> System.out.println("group by gender female = " + s));
    }

    private static void groupingExample2() {
        Map<City, List<String>> groupByCity = students.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getCity,
                                Collectors.mapping(Student::getName, Collectors.toList())
                        ));


        groupByCity.get(SEOUL)
                .forEach(s -> System.out.println("group by city Seoul = " + s));

        groupByCity.get(PUSAN)
                .forEach(s -> System.out.println("group by city Pusan = " + s));
    }
}
