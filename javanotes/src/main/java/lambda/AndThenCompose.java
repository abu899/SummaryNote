package lambda;

import java.util.function.Consumer;
import java.util.function.Function;

public class AndThenCompose {

    public void doAndThenExample() {
        consumerAndThenExample();
        functionAndThenExample();
        functionComposeExample();
    }

    private void consumerAndThenExample() {
        Consumer<Member> consumerA =
                m -> {
                    System.out.println("m.getName() = " + m.getName());
                };

        Consumer<Member> consumerB =
                m -> {
                    System.out.println("m.getId() = " + m.getId());
                };

        Consumer<Member> consumerAB = consumerA.andThen(consumerB);
        consumerAB.accept(new Member(1L, "brett", null));
    }

    private void functionAndThenExample() {
        Function<Member, Address> functionA = Member::getAddress;
        Function<Address, String> functionB = Address::getCity;
        Function<Member, String> functionAB = functionA.andThen(functionB);

        String city =
                functionAB.apply(new Member(1L, "brett", new Address("Korea", "Seoul")));

        System.out.println("city = " + city);
    }

    private void functionComposeExample() {
        Function<Member, Address> functionA = Member::getAddress;
        Function<Address, String> functionB = Address::getCity;
        Function<Member, String> functionAB = functionB.compose(functionA);

        String city =
                functionAB.apply(new Member(1L, "brett", new Address("Korea", "Seoul")));

        System.out.println("city = " + city);
    }

}
