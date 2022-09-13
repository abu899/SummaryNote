package lambda;

import java.util.List;
import java.util.function.*;

import static lambda.Gender.FEMALE;
import static lambda.Gender.MALE;

public class FunctionalInterfaceApi {
    public void doFunctionInterfaceExample(){
        consumerExample();
        supplierExample();
        functionExample();
        operatorExample();
        predicateExample();
    }

    private void consumerExample() {
        Consumer<String> consumer
                = str -> System.out.println("consumer String = " + str);
        consumer.accept("study");

        BiConsumer<String, String> biConsumer =
                (t, u) -> {
                    System.out.println("biConsumer t = " + t + " u = " + u);
                };
        biConsumer.accept("bi", "consumer");

        DoubleConsumer doubleConsumer =
                (d) -> {
                    System.out.println("doubleConsumer = " + d);
                };
        doubleConsumer.accept(5.0);

        ObjIntConsumer<String> objIntConsumer =
                (t, i) -> {
                    System.out.println("biConsumer t = " + t + " i = " + i);
                };
        objIntConsumer.accept("java", 8);
    }

    private void supplierExample() {
        Supplier<String> supplier =
                () -> {
                    return "supplier";
                };
        String suppliedResult = supplier.get();
        System.out.println("suppliedResult = " + suppliedResult);

        IntSupplier intSupplier =
                () -> {
                    return (int)((Math.random()) * 6);
                };
        int intSupplierResult = intSupplier.getAsInt();
        System.out.println("intSupplierResult = " + intSupplierResult);
    }

    private void functionExample() {
        List<Student> students = List.of(new Student("A", 90, 67),
                new Student("B", 99, 50));
        printString(Student::getName, students);
        printInt(Student::getEngScore, students);
        printInt(Student::getMathScore, students);
        System.out.println("avg(Student::getEngScore, students) = " + avgFunction(Student::getEngScore, students));
        System.out.println("avg(Student::getMathScore, students) = " + avgFunction(Student::getMathScore, students));
    }

    private void printString(Function<Student, String> function, List<Student> studentList) {
        for (Student student : studentList) {
            System.out.println("function.apply(student.getName()) = " + function.apply(student));
        }
    }

    private void printInt(ToIntFunction<Student> func, List<Student> studentList) {
        for (Student student : studentList) {
            System.out.println("func.applyAsInt(student) = " + func.applyAsInt(student));
        }
    }

    private double avgFunction(ToIntFunction<Student> func, List<Student> studentList) {
        double sum = 0;
        for (Student student : studentList) {
            sum += func.applyAsInt(student);
        }
        return (sum / studentList.size());
    }

    private void operatorExample() {
        List<Integer> scores = List.of(92, 95, 87);

        int min = minMax(
                (left, right) -> {
                    if (left <= right) {
                        return left;
                    }
                    return right;
                },
                scores
        );

        int max = minMax(
                (left, right) -> {
                    if( left >= right ) {
                        return left;
                    }
                    return right;
                }
                , scores
        );

        System.out.println("min = " + min + " max = " + max);
    }

    private int minMax(IntBinaryOperator func, List<Integer> scoreList) {
        int result = scoreList.get(0);
        for (Integer integer : scoreList) {
            result = func.applyAsInt(result, integer);
        }

        return result;
    }

    private void predicateExample() {
        List<Student> students = List.of(
                new Student("A", 90, 67, MALE),
                new Student("B", 99, 50, MALE),
                new Student("C", 80, 87, FEMALE),
                new Student("D", 99, 80, FEMALE)

        );

        double maleAvg = avgEngScorePredicate(
                student -> {
                    return student.getGender().equals(MALE);
                },
                students
        );

        double femaleAvg = avgEngScorePredicate(
                student -> {
                    return student.getGender().equals(FEMALE);
                },
                students
        );

        System.out.println("maleEngAvg = " + maleAvg + " femaleEngAvg = " + femaleAvg);
    }

    private double avgEngScorePredicate(Predicate<Student> predicate, List<Student> studentList) {
        double sum = 0;
        int count = 0;
        for (Student student : studentList) {
            if(predicate.test(student)){
                sum += student.getEngScore();
                count++;
            }
        }

        return (sum / count);
    }
}
