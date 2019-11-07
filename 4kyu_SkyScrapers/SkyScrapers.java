import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkyScrapers {

    public static void main(String[] args) {

        List<int[]> res;

        int key1, key2;

        System.out.println();
        key1 = 1;
        key2 = 4;
        System.out.println("*** key1 = " + key1 + " key2 = " + key2);
        res = doStr(4, key1, key2);
        res.forEach(el->System.out.println(Arrays.toString(el)));

        System.out.println();
        key1 = 2;
        key2 = 2;
        System.out.println("*** key1 = " + key1 + " key2 = " + key2);
        res = doStr(4, key1, key2);
        res.forEach(el->System.out.println(Arrays.toString(el)));

        System.out.println();
        key1 = 0;
        key2 = 2;
        System.out.println("*** key1 = " + key1 + " key2 = " + key2);
        res = doStr(4, key1, key2);
        res.forEach(el->System.out.println(Arrays.toString(el)));

    }

    static int[][] solvePuzzle (int[] clues) {

        return new int[4][4];

    }

    static private List<int[]> doStr( int n, int key1, int key2 ) {

        List<int[]> res = new ArrayList<int[]>();

        for (int n1 = 1; n1 < 5; n1++) {

            int[] cur = new int[4+2];

            cur[0] = n1;

            for (int n2 = 1; n2 < 5; n2++) {
                if (n2 == n1) continue;

                cur[1] = n2;

                for (int n3 = 1; n3 < 5; n3++) {
                    if (n3 == n2 || n3 == n1) continue;

                    cur[2] = n3;

                    for (int n4 = 1; n4 < 5; n4++) {
                        if (n4 == n3 || n4 == n2 || n4 == n1) continue;

                        cur[3] = n4;

                        int left = 1;
                        int h=cur[0];
                        for(int i=1;i<4;i++){
                            if(cur[i]>h) {
                                left++;
                                h=cur[i];
                            }
                        }
                        cur[4]=left;

                        int right = 1;
                        h=cur[3];
                        for(int i=1;i<4;i++){
                            if(cur[3-i]>h) {
                                right++;
                                h=cur[3-i];
                            }
                        }
                        cur[5]=right;

                        if(key1 != 0 && cur[4] != key1) continue;
                        if(key2 != 0 && cur[5] != key2) continue;

                        res.add(cur.clone());

                    }
                }
            }
        }

        return res;

    }

}
