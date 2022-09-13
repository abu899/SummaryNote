package stream;

import lambda.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Student {

    public enum City {
        SEOUL,
        PUSAN };

    private final String name;
    private final int score;
    private final Gender gender;
    private final City city;
}
