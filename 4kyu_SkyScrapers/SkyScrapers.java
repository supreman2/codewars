import javax.swing.*;
import java.sql.SQLOutput;
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

        public Sol clone() {
            Sol res = new Sol(size);
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    ArrayList<Integer> cell = (ArrayList) this.arwork[r][c];
                    res.arwork[r][c] = (ArrayList) cell.clone();
                }
            }
            return res;
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
            int k = key;
            while (k != 0) {
//                if (key == 1) {
//                    res[0] = new ArrayList<Integer>(List.of(Integer.valueOf(max)));
//                    break;
//                }
                for (int i = 0; i < k - 1; i++) {
                    res[i].remove(Integer.valueOf(max));
                }
                k--;
                max--;
            }

            // правило #2
            // на первом месте стоит самый высокий, за вычетом самых высоких справа налево начиная с самого края и без пропусков
            // пример: 1/2, 1/2, 3, 4, при ключе 3, на первом месте может стоять только 2: 2, 1/2, 3, 4
            k = key;
            if (k != 0) {

                int cur_max = size;

                for (int i = 0; i < size; i++) {

                    ArrayList<Integer> cell = res[size - 1 - i];
                    if (cell.size() == 1) {
                        Integer value = cell.get(0);
                        if (value.equals(cur_max)) {
                            k--;
                            cur_max--;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (k == 1) {
                    res[0] = new ArrayList<Integer>(List.of(cur_max));
                }
            }

            // правило #3
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

            // правило #4
            // если первая ячейка 1/2, а вторая 1/2/3 и ключ 1, то в первой не может быть 1, а может быть только 2.
            // т.е. при ключе 1, в первой ячейке нужно оставить только те значения, которые строго выше хоть одного из второй ячейки.
            // а во второй ячейке не может быть 3, иначе ключ был бы 2, а не 1
            // не понятно, как формализовать это правило для общего случая, когда топчик посередине, например,
            // 1/2/3, 4, 1/2/3, 1/2 и ключ справа 2.


            // правило #5
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

        public boolean isSolved() { // это проверка корректна до тех пор, пока мы удаляем элементы по правилам
            // в случае, когда мы начинаем выборочно удалять элементы, можно тут получить Истину, хотя ключи при этом не будут выполнены.
            // нужна новая проверка именно по ключам.
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    ArrayList<Integer> cell = (ArrayList) arwork[r][c];
                    if (cell.size() != 1) return false;
                }
            }
            return true;
        }

        public boolean isSolvedByClues(int[] clues) {

            for (int i = 0; i < size; i++) {
                if (clues[i] == 0) continue;
                int c = i;
                ArrayList[] in = getC(c, 1);
                int cur_clue = 0;
                int cur_max = 0;
                for (int j = 0; j < size; j++) {
                    if (in[j].size() != 1) return false;
                    Integer val = (Integer) in[j].get(0);
                    if (val.intValue() > cur_max) {
                        cur_clue++;
                        cur_max = val;
                    }
                }
                if (cur_clue != clues[i]) return false;
            }

            for (int i = size; i < size * 2; i++) {
                if (clues[i] == 0) continue;
                int r = i - size;
                ArrayList[] in = getR(r, -1);
                int cur_clue = 0;
                int cur_max = 0;
                for (int j = 0; j < size; j++) {
                    if (in[j].size() != 1) return false;
                    Integer val = (Integer) in[j].get(0);
                    if (val.intValue() > cur_max) {
                        cur_clue++;
                        cur_max = val;
                    }
                }
                if (cur_clue != clues[i]) return false;
            }

            for (int i = size * 2; i < size * 3; i++) {
                if (clues[i] == 0) continue;
                int c = size * 3 - 1 - i;
                ArrayList[] in = getC(c, -1);
                int cur_clue = 0;
                int cur_max = 0;
                for (int j = 0; j < size; j++) {
                    if (in[j].size() != 1) return false;
                    Integer val = (Integer) in[j].get(0);
                    if (val.intValue() > cur_max) {
                        cur_clue++;
                        cur_max = val;
                    }
                }
                if (cur_clue != clues[i]) return false;
            }

            for (int i = size * 3; i < size * 4; i++) {
                if (clues[i] == 0) continue;
                int r = size * 4 - 1 - i;
                ArrayList[] in = getR(r, 1);
                int cur_clue = 0;
                int cur_max = 0;
                for (int j = 0; j < size; j++) {
                    if (in[j].size() != 1) return false;
                    Integer val = (Integer) in[j].get(0);
                    if (val.intValue() > cur_max) {
                        cur_clue++;
                        cur_max = val;
                    }
                }
                if (cur_clue != clues[i]) return false;
            }

            return true;
        }

        public int[][] getArray() {

            int[][] res = new int[size][size];

            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    ArrayList<Integer> cell = (ArrayList) arwork[r][c];
                    Integer value = cell.get(0);
                    res[r][c] = value;
                }
            }

            return res;

        }

        public int[][] printArray() {
            int[][] res = getArray();
            for (int j = 0; j < size; j++) {
                int[] row = res[j];
                System.out.println(Arrays.toString(row));
            }
            return res;
        }

        public int tryToSolvePuzzle(int[] clues) {

            int steps = 0;

            while (true) {

                if (steps == size * size) {
                    // System.out.printf("- Решение НЕ найдено, превышено число шагов %d", steps);
                    return -1; // превышен лимит шагов
                }

                steps++;

                boolean changed = false;

                for (int i = 0; i < size; i++) {
                    int c = i;
                    ArrayList[] in = getC(c, 1);
                    ArrayList[] out = simplify(in, clues[i]);
                    if (!equals(in, out)) {
                        setC(c, 1, out);
                        changed = true;
                    }
                }

                for (int i = size; i < size * 2; i++) {
                    int r = i - size;
                    ArrayList[] in = getR(r, -1);
                    ArrayList[] out = simplify(in, clues[i]);
                    if (!equals(in, out)) {
                        setR(r, -1, out);
                        changed = true;
                    }
                }

                for (int i = size * 2; i < size * 3; i++) {
                    int c = size * 3 - 1 - i;
                    ArrayList[] in = getC(c, -1);
                    ArrayList[] out = simplify(in, clues[i]);
                    if (!equals(in, out)) {
                        setC(c, -1, out);
                        changed = true;
                    }
                }

                for (int i = size * 3; i < size * 4; i++) {
                    int r = size * 4 - 1 - i;
                    ArrayList[] in = getR(r, 1);
                    ArrayList[] out = simplify(in, clues[i]);
                    if (!equals(in, out)) {
                        setR(r, 1, out);
                        changed = true;
                    }
                }

                if (!changed) return steps;

            }
        }

    }

    public static void main(String[] args) {

    }

    static int[][] solvePuzzle(int[] clues) {

        int size = 4;

        Sol sol = new Sol(size);

        int steps = sol.tryToSolvePuzzle(clues);

        if (sol.isSolved()) {
            System.out.printf("+ Решение найдено за %d шагов%n", steps);
            int[][] res = sol.printArray();
            return res;
        }

        System.out.printf("...начинаем тупое удаление элементов и попытку решить без них%n");

        int cur_min = size;
        int r_min = -1, c_min = -1;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                ArrayList<Integer> cell = (ArrayList) sol.arwork[r][c];

                if (cell.size() == 1) continue;

                if (cell.size() < cur_min) {
                    r_min = r;
                    c_min = c;
                    cur_min = cell.size();
                }
            }
        }

        ArrayList<Integer> cell = (ArrayList) sol.arwork[r_min][c_min];

        Sol solClone = sol.clone();

        for (int i = 0; i < cell.size(); i++) {
            ArrayList<Integer> cellClone = (ArrayList) cell.clone();
            int value = cellClone.get(i);
            cellClone.remove(i);
            solClone.arwork[r_min][c_min] = cellClone;
            solClone.tryToSolvePuzzle(clues);
            if (solClone.isSolvedByClues(clues)) {
                System.out.printf("+ Решение найдено после удаления значения %d из R%dC%d%n", value, r_min, c_min);
                int[][] res = solClone.printArray();
                return res;
            }
        }

        System.out.printf("- Решение НЕ найдено! Ничего не помогло!!!");
        return new int[0][0];

    }

}
