import lambda.AndThenCompose;
import lambda.FunctionalInterfaceApi;

public class JavaNotesMain {
    public static void main(String[] args) {
        FunctionalInterfaceApi fiStudy = new FunctionalInterfaceApi();
        fiStudy.doFunctionInterfaceExample();

        AndThenCompose andThenCompose = new AndThenCompose();
        andThenCompose.doAndThenExample();
    }
}
