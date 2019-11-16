import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamSample {

    public static void main(String[] args) {

//        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
//        List<Integer> wordLengths = words.stream().map(String::length)
//                .collect(Collectors.toList());

//        System.out.println("******************");
//        IntStream.rangeClosed(1, 21).filter(n->n%2 == 0).forEach(System.out::println);
//
//        System.out.println("******************");
//        Stream<int[]> str = IntStream.rangeClosed(1, 4).boxed().flatMap(a->IntStream.rangeClosed(1,4).filter(b->b!=a).mapToObj(b->new int[]{a,b}));
//        List<int[]> strList = str.collect(Collectors.toList());
//        strList.stream().forEach(el->System.out.println(Arrays.toString(el)));

        System.out.println("******************");

        IntStream
                .rangeClosed(1, 4)
                .boxed()
                .flatMap(
                        a -> IntStream
                                .rangeClosed(1, 4)
                                .boxed()
                                .filter(b -> !b.equals(a))
                                .flatMap(
                                        b -> IntStream
                                                .rangeClosed(1, 4)
                                                .boxed()
                                                .filter(c -> !c.equals(a) && !c.equals(b))
                                                .flatMap(
                                                        c -> IntStream
                                                                .rangeClosed(1, 4)
                                                                .filter(d -> d != a && d != b && d != c)
                                                                .mapToObj(d -> new int[]{a, b, c, d})
                                                )
                                )
                )
                .forEach(el -> System.out.println(Arrays.toString(el)));

    }

}
