import java.util.ArrayList;
import java.util.List;

public class RemovedNumbersSlowly {

    public static List<long[]> removNb(long n) {

        List<long[]> res = new ArrayList<long[]>();

        long summ = (1+n)*n/2;

        boolean founded = false;

        for(long i=1;i<=n;i++) {
            for(long k=1;k<=n;k++) {
                if(i==k) continue;
                if (i*k == summ-i-k) {
                    res.add(new long[] {i, k});
                    res.add(new long[] {k, i});
                    founded = true;
                    break;
                }
            }
            if (founded) break;
        }

        return res;

    }
}