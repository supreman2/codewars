import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemovedNumbers {

    public static void main(String[] args) {

        List<long[]> res1 = removNb(325);
        List<long[]> res2 = removNb(1000003);

        for (int i=1;i<400;i++) {

            System.out.println("*** n = " + i + ": ");
            List<long[]> res = removNb(i);

            res.stream().flatMap(t->Arrays.stream(t).boxed()).forEach(System.out::print);
            System.out.println();

            //res.forEach(el->{Arrays.stream(el).forEach(System.out::print); /*System.out.println("");*/});
            //res.forEach(el->{Arrays.stream(el).collect(Collectors.joining(",")); /*System.out.println("");*/});
            res.forEach(el->{System.out.println(Arrays.toString(el));});

            res.stream().forEach(t->{
                System.out.print("< ");
                Arrays.stream(t).boxed().forEach(el-> {
                    System.out.print(el); System.out.print(" ");
                });
                System.out.println(">");
            });

            //res.forEach(el->{System.out.println(Arrays.stream(el).map(ex->Long.toString(ex)).collect(Collectors.joining(","))toString(el));});

            //System.out.println(removNb(i));
            //System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(",")));
            //System.out.println(removNb(i).stream().map(String::valueOf).collect(Collectors.joining(",")));
            //removNb(i).stream().map(array -> array.::stream().map(value -> true));
            //removNb(i).forEach(System.out::println);//stream().flatMap(elem ->System.out.println(elem));
        }
    }

    public static List<long[]> removNb(long n) {

        List<long[]> res = new ArrayList<long[]>();

        long summ = (1+n)*n/2;

        long max = n;
        for(long a=1;a<=max;a++) {

            long b = (summ-a)/(a+1);

            if (b>n) continue;

            if (b*(a+1) == summ-a) {
                res.add(new long[] {a, b});
                //if(max == n) max = b;
                // res.add(new long[] {b, a}); // так как пар может быть несколько, а задании требуется упорядочивание по
                // break; // иногда бывает несколько пар подходящих чисел
            }

        }

        return res;

    }
}