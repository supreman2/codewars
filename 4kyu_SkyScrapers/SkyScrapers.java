import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkyScrapers {

    public static void main(String[] args) {



    }

    static int[][] solvePuzzle(int[] clues) {

        ArrayList[][] arwork = new ArrayList[4][4];
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                arwork[i][k] = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
            }
        }
        arwork[0][0].remove(Integer.valueOf(2));


        return new int[4][4];
    }

}
