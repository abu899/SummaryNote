import lambda.AndThenCompose;
import lambda.FunctionalInterfaceApi;
import stream.*;

public class JavaNotesMain {
    public static void main(String[] args) {
        FunctionalInterfaceApi fiStudy = new FunctionalInterfaceApi();
        fiStudy.doFunctionInterfaceExample();

        AndThenCompose andThenCompose = new AndThenCompose();
        andThenCompose.doAndThenExample();

        FilterAndDistinct.filterAndDistinct();
        StreamMapping.streamMapping();
        StreamMatching.matching();
        StreamSort.sortWithComparable();
        StreamAggregation.aggregation();
        StreamCollect.doCollect();
    }
}
