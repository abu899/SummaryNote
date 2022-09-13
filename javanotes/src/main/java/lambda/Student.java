package lambda;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Student {
    private final String name;
    private final int engScore;
    private final int mathScore;
    private final Gender gender;

    public Student(String name, int engScore, int mathScore) {
        this.name = name;
        this.engScore = engScore;
        this.mathScore = mathScore;
        this.gender = Gender.MALE;
    }
}
