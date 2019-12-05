import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SkyScrapers {

    public static class Sol {

        private int size;

        private ArrayList[][] arwork;

        public Sol(int size) {

            this.size = size;

            ArrayList<Integer> cell = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                cell.add(i + 1);
            }

            this.arwork = new ArrayList[size][size];
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    this.arwork[r][c] = (ArrayList) cell.clone();
                }
            }

        }

        public ArrayList[] getR(int n, int order) {
            ArrayList[] res = new ArrayList[size];
            if (order == -1) {
                for (int i = 0; i < size; i++) res[i] = (ArrayList) this.arwork[n][size - 1 - i].clone();
            } else {
                for (int i = 0; i < size; i++) res[i] = (ArrayList) this.arwork[n][i].clone();
            }
            return res;
        }

        public void setR(int n, int order, ArrayList[] ar) {
            if (order == -1) {
                for (int i = 0; i < size; i++) this.arwork[n][size - 1 - i] = (ArrayList) ar[i].clone();
            } else {
                for (int i = 0; i < size; i++) this.arwork[n][i] = (ArrayList) ar[i].clone();
            }
        }

        public ArrayList[] getC(int n, int order) {
            ArrayList[] res = new ArrayList[size];
            if (order == -1) {
                for (int i = 0; i < size; i++) res[i] = (ArrayList) this.arwork[size - 1 - i][n].clone();
            } else {
                for (int i = 0; i < size; i++) res[i] = (ArrayList) this.arwork[i][n].clone();
            }
            return res;
        }

        public void setC(int n, int order, ArrayList[] ar) {
            if (order == -1) {
                for (int i = 0; i < size; i++) this.arwork[size - 1 - i][n] = (ArrayList) ar[i].clone();
            } else {
                for (int i = 0; i < size; i++) this.arwork[i][n] = (ArrayList) ar[i].clone();
            }
        }

        public ArrayList[] simplify(ArrayList[] ar, int key) {

            ArrayList[] res = ar.clone();
            for (int i = 0; i < size; i++) {
                res[i] = (ArrayList) res[i].clone();
            }

            // правило #1
            // самый высокий не может быть ближе ключа
            int max = size;
            while (key != 0) {
//                if (key == 1) {
//                    res[0] = new ArrayList<Integer>(List.of(Integer.valueOf(max)));
//                    break;
//                }
                for (int i = 0; i < key - 1; i++) {
                    res[i].remove(Integer.valueOf(max));
                }
                key--;
                max--;
            }

            // правило #2
            // если в какой либо ячейке только 1 значение, то в других ячейках этого значения быть не может
            // пример: 1/2/3/4, 1/2/3/4, 4, 1/2/3/4 -> 1/2/3, 1/2/3, 4, 1/2/3
            for (int i = 0; i < size; i++) {
                ArrayList<Integer> cell = res[i];
                if (cell.size() == 1) {
                    Integer value = cell.get(0);
                    for (int j = 0; j < size; j++) {
                        if (j != i) res[j].remove(Integer.valueOf(value));
                    }
                }
            }

            // правило #3
            // если какое либо значение встречается только 1 раз, то в ячейке, где оно встретилось лежит только оно
            // пример: 1/2/3, 1/2/3, 1/2/3/4, 1/2/3 -> 1/2/3, 1/2/3, 4, 1/2/3
            int[] qty = new int[size];
            for (int i = 0; i < size; i++) {
                ArrayList<Integer> cell = res[i];
                for (int v : cell) {
                    qty[v - 1] += 1;
                }
            }
            for (int i = 0; i < size; i++) {
                if (qty[i] == 1) {
                    for (int n = 0; n < size; n++) {
                        ArrayList<Integer> cell = res[n];
                        if (cell.contains(Integer.valueOf(i + 1))) {
                            res[n] = new ArrayList<Integer>(List.of(i + 1));
                        }
                    }
                }
            }

            return res;

        }

        public boolean equals(ArrayList[] ar1, ArrayList[] ar2) {
            for (int i = 0; i < size; i++) {
                if (!ar1[i].equals(ar2[i])) return false;
            }
            return true;
        }

    }

    public static void main(String[] args) {

//        Sol sol = new Sol(4);
//
//        ArrayList[] r1 = new ArrayList[4];
//        r1[0] = new ArrayList<Integer>(List.of(1,2,3,4));
//        r1[1] = new ArrayList<Integer>(List.of(5,6,7,8));
//        r1[2] = new ArrayList<Integer>(List.of(9,10,11,12));
//        r1[3] = new ArrayList<Integer>(List.of(13,14,15,16));
//        sol.setR(1, -1, r1);
//
//        ArrayList[] c0 = sol.getC(0, 1);
//        ArrayList[] c1 = sol.getC(1, 1);
//        ArrayList[] c2 = sol.getC(2,-1);
//        ArrayList[] c3 = sol.getC(3,-1);

    }

    static int[][] solvePuzzle(int[] clues) {

        int size = 4;

        Sol sol = new Sol(size);

        while (true) {

            boolean changed = false;

            for (int i = 0; i < size; i++) {
                int c = i;
                ArrayList[] in = sol.getC(c, 1);
                ArrayList[] out = sol.simplify(in, clues[i]);
                if (!sol.equals(in, out)) {
                    sol.setC(c, 1, out);
                    changed = true;
                }
            }

            for (int i = size; i < size * 2; i++) {
                int r = i - size;
                ArrayList[] in = sol.getR(r, -1);
                ArrayList[] out = sol.simplify(in, clues[i]);
                if (!sol.equals(in, out)) {
                    sol.setR(r, -1, out);
                    changed = true;
                }
            }

            for (int i = size * 2; i < size * 3; i++) {
                int c = size * 3 - 1 - i;
                ArrayList[] in = sol.getC(c, -1);
                ArrayList[] out = sol.simplify(in, clues[i]);
                if (!sol.equals(in, out)) {
                    sol.setC(c, -1, out);
                    changed = true;
                }
            }


            for (int i = size * 3; i < size * 4; i++) {
                int r = size * 4 - 1 - i;
                ArrayList[] in = sol.getR(r, 1);
                ArrayList[] out = sol.simplify(in, clues[i]);
                if (!sol.equals(in, out)) {
                    sol.setR(r, 1, out);
                    changed = true;
                }
            }

            if (!changed) break;

        }

        return new int[4][4];

    }

//        int size = 4;
//
//        int[][] res = new int[size][size];
//
//        ArrayList<Integer> cell = new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            cell.add(i + 1);
//        }
//
//        ArrayList[][] arwork = new ArrayList[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int k = 0; k < size; k++) {
//                arwork[i][k] = (ArrayList) cell.clone();
//            }
//        }
//
//        for (int i = 0; i < size; i++) {
//            if (clues[i] == 0) continue;
//            if (clues[i] == 1) {
//                int column = i;
//                int row = 0;
//                arwork[column][row] = new ArrayList<Integer>(List.of(size));
//                for (int j = 0; j < size; j++) {
//                    if (j != row) arwork[column][j].remove(Integer.valueOf(size));
//                    if (j != column) arwork[j][row].remove(Integer.valueOf(size));
//                }
//            }
//            if (clues[i] == size) {
//                int column = i;
//                for (int row = 0; row < size; row++) {
//                    arwork[column][row] = new ArrayList<Integer>(List.of(row + 1));
//                }
//            }
//        }
//
//        for (int i = size; i < size * 2; i++) {
//            if (clues[i] == 0) continue;
//            if (clues[i] == 1) {
//                int column = 3;
//                int row = i - size;
//                arwork[column][row] = new ArrayList<Integer>(List.of(size));
//                for (int j = 0; j < size; j++) {
//                    if (j != row) arwork[column][j].remove(Integer.valueOf(size));
//                    if (j != column) arwork[j][row].remove(Integer.valueOf(size));
//                }
//            }
//            if (clues[i] == size) {
//                int row = i - size;
//                for (int column = 0; column < size; column++) {
//                    arwork[column][row] = new ArrayList<Integer>(List.of(size - column));
//                }
//            }
//        }
//
//        for (int i = size * 2; i < size * 3; i++) {
//            if (clues[i] == 0) continue;
//            if (clues[i] == 1) {
//                int column = size * 3 - 1 - i;
//                int row = size - 1;
//                arwork[column][row] = new ArrayList<Integer>(List.of(size));
//                for (int j = 0; j < size; j++) {
//                    if (j != row) arwork[column][j].remove(Integer.valueOf(size));
//                    if (j != column) arwork[j][row].remove(Integer.valueOf(size));
//                }
//            }
//            if (clues[i] == size) {
//                int column = size * 3 - 1 - i;
//                for (int row = 0; row < size; row++) {
//                    arwork[column][row] = new ArrayList<Integer>(List.of(size - row));
//                }
//            }
//        }
//
//        for (int i = size * 3; i < size * 4; i++) {
//            if (clues[i] == 0) continue;
//            if (clues[i] == 1) {
//                int column = 0;
//                int row = size * 4 - 1 - i;
//                arwork[column][row] = new ArrayList<Integer>(List.of(size));
//                for (int j = 0; j < size; j++) {
//                    if (j != row) arwork[column][j].remove(Integer.valueOf(size));
//                    if (j != column) arwork[j][row].remove(Integer.valueOf(size));
//                }
//            }
//            if (clues[i] == size) {
//                int row = size * 4 - 1 - i;
//                for (int column = 0; column < size; column++) {
//                    arwork[column][row] = new ArrayList<Integer>(List.of(column + 1));
//                }
//            }
//        }
//
//        for (int r = 0; r < size * size * 10; r++) { // обход делаем n*n раз, должно хватить
//
//            for (int i = 0; i < size; i++) {
//                int column = i;
//                if (clues[i] == 2) {
//                    if (arwork[column][3].size() == 1
//                            && arwork[column][3].get(0).equals(4)) {
//                        arwork[column][0] = new ArrayList<Integer>(List.of(3));
//                    }
//                }
//                if (clues[i] == 3) {
//                    if (arwork[column][3].size() == 1
//                            && arwork[column][3].get(0).equals(4)
//                            && arwork[column][2].size() == 1
//                            && arwork[column][2].get(0).equals(3)) {
//                        arwork[column][0] = new ArrayList<Integer>(List.of(2));
//                    }
//                }
//
//                if (clues[i] == 0) continue;
//                for (int j = 0; j < clues[i] - 1; j++) {
//                    arwork[column][j].remove(Integer.valueOf(size));
//                }
//
//            }
//
//            for (int i = size; i < size * 2; i++) {
//                int row = i - size;
//                if (clues[i] == 2) {
//                    if (arwork[0][row].size() == 1
//                            && arwork[0][row].get(0).equals(4)) {
//                        arwork[size - 1][row] = new ArrayList<Integer>(List.of(3));
//                    }
//                }
//                if (clues[i] == 3) {
//                    if (arwork[0][row].size() == 1
//                            && arwork[0][row].get(0).equals(4)
//                            && arwork[1][row].size() == 1
//                            && arwork[1][row].get(0).equals(3)) {
//                        arwork[size - 1][0] = new ArrayList<Integer>(List.of(2));
//                    }
//                }
//
//                if (clues[i] == 0) continue;
//                for (int j = 0; j < clues[i] - 1; j++) {
//                    arwork[size - 1 - j][row].remove(Integer.valueOf(size));
//                }
//
//            }
//
//            for (int i = size * 2; i < size * 3; i++) {
//                int column = size * 3 - 1 - i;
//                if (clues[i] == 2) {
//                    if (arwork[column][0].size() == 1
//                            && arwork[column][0].get(0).equals(4)) {
//                        arwork[column][size - 1] = new ArrayList<Integer>(List.of(3));
//                    }
//                }
//                if (clues[i] == 3) {
//                    if (arwork[column][0].size() == 1
//                            && arwork[column][0].get(0).equals(4)
//                            && arwork[column][1].size() == 1
//                            && arwork[column][1].get(0).equals(3)) {
//                        arwork[column][size - 1] = new ArrayList<Integer>(List.of(2));
//                    }
//                }
//
//                if (clues[i] == 0) continue;
//                for (int j = 0; j < clues[i] - 1; j++) {
//                    arwork[column][size - 1 - j].remove(Integer.valueOf(size));
//                }
//
//            }
//
//            for (int i = size * 3; i < size * 4; i++) {
//                int row = size * 4 - 1 - i;
//                if (clues[i] == 2) {
//                    if (arwork[size - 1][row].size() == 1
//                            && arwork[size - 1][row].get(0).equals(4)) {
//                        arwork[0][row] = new ArrayList<Integer>(List.of(3));
//                    }
//                }
//                if (clues[i] == 3) {
//                    if (arwork[size - 1][row].size() == 1
//                            && arwork[size - 1][row].get(0).equals(4)
//                            && arwork[size - 2][row].size() == 1
//                            && arwork[size - 2][row].get(0).equals(3)) {
//                        arwork[0][row] = new ArrayList<Integer>(List.of(2));
//                    }
//                }
//
//                if (clues[i] == 0) continue;
//                for (int j = 0; j < clues[i] - 1; j++) {
//                    arwork[j][row].remove(Integer.valueOf(size));
//                }
//
//            }
//
//            for (int column = 0; column < size; column++) {
//                int[] qty = {0, 0, 0, 0};
//                for (int row = 0; row < size; row++) {
//                    cell = arwork[column][row];
//                    for (int v : cell) {
//                        qty[v - 1] += 1;
//                    }
//                }
//                for (int i = 0; i < size; i++) {
//                    if (qty[i] == 1) {
//                        for (int row = 0; row < size; row++) {
//                            cell = arwork[column][row];
//                            if (cell.contains(Integer.valueOf(i + 1))) {
//                                arwork[column][row] = new ArrayList<Integer>(List.of(i + 1));
//                            }
//                        }
//                    }
//                }
//            }
//
//            for (int row = 0; row < size; row++) {
//                int[] qty = {0, 0, 0, 0};
//                for (int column = 0; column < size; column++) {
//                    cell = arwork[column][row];
//                    for (int v : cell) {
//                        qty[v - 1] += 1;
//                    }
//                }
//                for (int i = 0; i < size; i++) {
//                    if (qty[i] == 1) {
//                        for (int column = 0; column < size; column++) {
//                            cell = arwork[column][row];
//                            if (cell.contains(Integer.valueOf(i + 1))) {
//                                arwork[column][row] = new ArrayList<Integer>(List.of(i + 1));
//                            }
//                        }
//                    }
//                }
//            }
//
//            for (int column = 0; column < size; column++) {
//                for (int row = 0; row < size; row++) {
//                    cell = arwork[column][row];
//                    if (cell.size() == 1) {
//                        Integer value = cell.get(0);
//                        res[row][column] = value;
//                        for (int j = 0; j < size; j++) {
//                            if (j != row) arwork[column][j].remove(value);
//                            if (j != column) arwork[j][row].remove(value);
//                        }
//                    }
//                }
//            }
//
//        }

    // сначала мы разматывали 1ки
    // после можно разматывать 2ки: если ключ = 2 и конец строки/колонки = 4, значит перед нами 3ка
    // аналогично, далее разматываем 3ки: если ключ 3 и конец строки 4 и 3, значит перед нами 2ка
    // в общем случае, нужно из ключа вычитать опознанные высотки с конца строки/колонки, понижая значение ключа и высотность.
    // т.е. в строке  * * 3 4 если левый ключ 3, то
    // понижаем его два раза: получаем 1 - * * при высотности 4-2 = 2, т.е. мы видим одно здание максимальной высотности 2, ставим его на первое место.
    // второй прием: каждого здания должно быть size раз. нужно периодически считать и если находим size-1, то определяем, где пропущено.

    // третий прием: если в строке все ячейки, кроме одной определены, можно точно определить и эту последнюю ячейку. этот прием будет получаться автоматически, путем удаления точно известных элементов.

    // return res;

    //}

}
