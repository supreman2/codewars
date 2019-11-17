import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class SkyScrapers_4x4 {

    public static void main(String[] args) {

//        TCMath.Permutations<Integer> perm = new TCMath.Permutations<Integer>(new Integer[]{3,3,4,4,4,5,5});
//        int count = 0;
//        while(perm.hasNext()){
//            System.out.println(Arrays.toString(perm.next()));
//            count++;
//        }
//        System.out.println("total: " + count);

//        Stream<int[]> komb1 = IntStream.rangeClosed(1,4).flatMap(a->{
//            return IntStream.rangeClosed(1, 4).mapToObj(b -> new int[]{a, b});
//        });
//        Stream<int[]> komb =
//                IntStream.rangeClosed(1,4).boxed()
//                .flatMap(a->IntStream.rangeClosed(1,4)
////                        .map(b->IntStream.rangeClosed(1,4)
//                        .mapToObj(b->new int[]{a,b})
////                        .flatMap(b->IntStream.rangeClosed(1,4).boxed()
////                                .flatMap(c->IntStream.rangeClosed(1,4)
////                                        .filter(d->a!=b && a!=c && a!=d && b!=c && b!=d && c!=d)
////                                        .collect(Collectors.toList())
////                                )
//                        );//);


        List<int[]> res;

        int key1, key2;

        System.out.println();
        key1 = 1;
        key2 = 4;
        System.out.println("*** key1 = " + key1 + " key2 = " + key2);
        res = doStr(4, key1, key2);
        res.forEach(el->System.out.println(Arrays.toString(el)));

//        System.out.println();
//        key1 = 2;
//        key2 = 2;
//        System.out.println("*** key1 = " + key1 + " key2 = " + key2);
//        res = doStr(4, key1, key2);
//        res.forEach(el->System.out.println(Arrays.toString(el)));
//
//        System.out.println();
//        key1 = 0;
//        key2 = 2;
//        System.out.println("*** key1 = " + key1 + " key2 = " + key2);
//        res = doStr(4, key1, key2);
//        res.forEach(el->System.out.println(Arrays.toString(el)));

    }

    static int[][] solvePuzzle (int[] clues) {

        //int[][] res = new int[4][4];

        List<int[]>  var1 = doStr(4, clues[15], clues[4]);
        List<int[]>  var2 = doStr(4, clues[14], clues[5]);
        List<int[]>  var3 = doStr(4, clues[13], clues[6]);
        List<int[]>  var4 = doStr(4, clues[12], clues[7]);

        for (int[] v1: var1 ){

            for (int[] v2: var2 ){

                for (int[] v3: var3 ){

                    for (int[] v4: var4 ){

                        int[] col = new int[4+2];
                        int key1, key2;

                        col[0] = v1[0];
                        col[1] = v2[0];
                        col[2] = v3[0];
                        col[3] = v4[0];
                        if (Stream.of(col[0],col[1],col[2],col[3]).distinct().count() != 4) continue;
                        calc(4, col);
                        key1 = clues[0];
                        key2 = clues[11];
                        if(key1 != 0 && col[4] != key1) continue;
                        if(key2 != 0 && col[5] != key2) continue;

                        col[0] = v1[1];
                        col[1] = v2[1];
                        col[2] = v3[1];
                        col[3] = v4[1];
                        if (Stream.of(col[0],col[1],col[2],col[3]).distinct().count() != 4) continue;
                        calc(4, col);
                        key1 = clues[1];
                        key2 = clues[10];
                        if(key1 != 0 && col[4] != key1) continue;
                        if(key2 != 0 && col[5] != key2) continue;

                        col[0] = v1[2];
                        col[1] = v2[2];
                        col[2] = v3[2];
                        col[3] = v4[2];
                        if (Stream.of(col[0],col[1],col[2],col[3]).distinct().count() != 4) continue;
                        calc(4, col);
                        key1 = clues[2];
                        key2 = clues[9];
                        if(key1 != 0 && col[4] != key1) continue;
                        if(key2 != 0 && col[5] != key2) continue;

                        col[0] = v1[3];
                        col[1] = v2[3];
                        col[2] = v3[3];
                        col[3] = v4[3];
                        if (Stream.of(col[0],col[1],col[2],col[3]).distinct().count() != 4) continue;
                        calc(4, col);
                        key1 = clues[3];
                        key2 = clues[8];
                        if(key1 != 0 && col[4] != key1) continue;
                        if(key2 != 0 && col[5] != key2) continue;

                        System.out.println(Arrays.toString(v1));
                        System.out.println(Arrays.toString(v2));
                        System.out.println(Arrays.toString(v3));
                        System.out.println(Arrays.toString(v4));

                        int[][] res = new int[4][4];
                        for(int i=0;i<4;i++) {
                            res[0][i] = v1[i];
                            res[1][i] = v2[i];
                            res[2][i] = v3[i];
                            res[3][i] = v4[i];
                        }

                        return res;

                    }
                }
            }
        }
        return new int[4][4];
    }

    static private List<int[]> doStr( int n, int key1, int key2 ) {

        List<int[]> res = new ArrayList<>();

        int[] cur = new int[n + 2]; // две последние ячейки под высоту с одной и с другой стороны

        int count = 1;
        for (int i = 0; i < n; i++) {
            cur[i] = i + 1; // заполняем 1,2,3,4....
            count = count * (i + 1); // общее число перестановок
        }

//        calc(n, cur);
//        System.out.println(Arrays.toString(cur));

//        if ((key1 == 0 || cur[n] == key1) && (key2 == 0 || cur[n + 1] == key2)) {
//            res.add(cur.clone());
//        }

        int max = n - 1;
        // System.out.println("Вариантов " + count);
        int shift = max;
        while (count > 0) {
            int t = cur[shift];
            cur[shift] = cur[shift - 1];
            cur[shift - 1] = t;



            calc(n, cur);

            System.out.println(Arrays.toString(cur));

            if ((key1 == 0 || cur[n] == key1) && (key2 == 0 || cur[n + 1] == key2)) {
                res.add(cur.clone());
            }

            count--;
            if (shift < 2) {
                shift = max;
            } else {
                shift--;
            }
        }

        return res;
    }
//        Stream<int[]> komb =
//                IntStream.rangeClosed(1,4).boxed()
//                .flatMap(a->IntStream.rangeClosed(1,4)
//                        .map(b->IntStream.rangeClosed(1,4)
//                        .mapToObj(с->new int[]{a,b,c})
////                        .flatMap(b->IntStream.rangeClosed(1,4).boxed()
////                                .flatMap(c->IntStream.rangeClosed(1,4)
////                                        .filter(d->a!=b && a!=c && a!=d && b!=c && b!=d && c!=d)
////                                        .collect(Collectors.toList())
////                                )
//                        ));
//
//        komb.forEach(el->System.out.println(Arrays.toString(el)));
                //);



                //.filter(b->a!=b)

//        while (true) {
//
//            for(i=0;i<n;i++) {
//                if(cur[i])
//            }
//            if()
//
//
//        }

//        for (int n1 = 1; n1 < 5; n1++) {
//
//            //int[] cur = new int[4+2];
//
//            cur[0] = 0;cur[1] = 0;cur[2] = 0;
//            cur[3] = 0;cur[4] = 0;cur[5] = 0;
//
//            cur[0] = n1;
//
//            for (int n2 = 1; n2 < 5; n2++) {
//                if (n2 == n1) continue;
//
//                cur[1] = n2;
//
//                for (int n3 = 1; n3 < 5; n3++) {
//                    if (n3 == n2 || n3 == n1) continue;
//
//                    cur[2] = n3;
//
//                    for (int n4 = 1; n4 < 5; n4++) {
//                        if (n4 == n3 || n4 == n2 || n4 == n1) continue;
//
//                        cur[3] = n4;
//
//                        calc(4, cur);
//
//                        if(key1 != 0 && cur[4] != key1) continue;
//                        if(key2 != 0 && cur[5] != key2) continue;
//
//                        res.add(cur.clone());
//
//                    }
//                }
//            }
//        }
//
//        return res;

//    }

    static private void calc( int n, int[] cur ) {

        // заполняет в полученном массиве последние две ячейки:
        // сколько зданий видно с одной стороны и сколько с другой
        // n - размерность матрицы, для 4 на 4, n=4,
        // полученный массив cur должен иметь размер 6.

        int left = 1;
        int h=cur[0];
        for(int i=1;i<n;i++){
            if(cur[i]>h) {
                left++;
                h=cur[i];
            }
        }
        cur[n]=left;

        int right = 1;
        h=cur[n-1];
        for(int i=1;i<n;i++){
            if(cur[n-1-i]>h) {
                right++;
                h=cur[n-1-i];
            }
        }
        cur[n+1]=right;

    }
}
