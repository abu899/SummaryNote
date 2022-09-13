package lambda;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Member {
    private final Long id;
    private final String name;
    private final Address address;
}
