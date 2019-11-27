import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkyScrapers {

    public static void main(String[] args) {


    }

    static int[][] solvePuzzle(int[] clues) {

        int size = 4;

        ArrayList<Integer> cell = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            cell.add(i + 1);
        }

        ArrayList[][] arwork = new ArrayList[size][size];
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                arwork[i][k] = (ArrayList) cell.clone();
            }
        }

        for (int i = 0; i < size; i++) {
            if (clues[i] == 0) continue;
            if (clues[i] == 1) {
                int column = i;
                int row = 0;
                for (int j = 0; j < size; j++) {
                    arwork[column][j].remove(Integer.valueOf(size));
                    arwork[j][row].remove(Integer.valueOf(size));
                }
                arwork[column][row] = new ArrayList<Integer>(List.of(size));
            }
            if (clues[i] == size) {
                int column = i;
                for (int row = 0; row < size; row++) {
                    arwork[column][row] = new ArrayList<Integer>(List.of(row + 1));
                }
            }
        }

        for (int i = size; i < size * 2; i++) {
            if (clues[i] == 0) continue;
            if (clues[i] == 1) {
                int column = 3;
                int row = i - size;
                for (int j = 0; j < size; j++) {
                    arwork[column][j].remove(Integer.valueOf(size));
                    arwork[j][row].remove(Integer.valueOf(size));
                }
                arwork[column][row] = new ArrayList<Integer>(List.of(size));
            }
            if (clues[i] == size) {
                int row = i - size;
                for (int column = 0; column < size; column++) {
                    arwork[column][row] = new ArrayList<Integer>(List.of(size - column));
                }
            }
        }

        for (int i = size * 2; i < size * 3; i++) {
            if (clues[i] == 0) continue;
            if (clues[i] == 1) {
                int column = size * 3 - 1 - i;
                int row = size - 1;
                for (int j = 0; j < size; j++) {
                    arwork[column][j].remove(Integer.valueOf(size));
                    arwork[j][row].remove(Integer.valueOf(size));
                }
                arwork[column][row] = new ArrayList<Integer>(List.of(size));
            }
            if (clues[i] == size) {
                int column = size * 3 - 1 - i;
                for (int row = 0; row < size; row++) {
                    arwork[column][row] = new ArrayList<Integer>(List.of(size - row));
                }
            }
        }

        for (int i = size * 3; i < size * 4; i++) {
            if (clues[i] == 0) continue;
            if (clues[i] == 1) {
                int column = 0;
                int row = size * 4 - 1 - i;
                for (int j = 0; j < size; j++) {
                    arwork[column][j].remove(Integer.valueOf(size));
                    arwork[j][row].remove(Integer.valueOf(size));
                }
                arwork[column][row] = new ArrayList<Integer>(List.of(size));
            }
            if (clues[i] == size) {
                int row = size * 4 - 1 - i;
                for (int column = 0; column < size; column++) {
                    arwork[column][row] = new ArrayList<Integer>(List.of(column + 1));
                }
            }
        }

        for (int i = 0; i < size * size; i++) {
            for (int column = 0; column < size; column++) {
                for (int row = 0; row < size; row++) {
                    cell = arwork[column][row];
                    if (cell.size() == 1) {
                        Integer value = cell.get(0);
                        for (int j = 0; j < size; j++) {
                            if (j != row) arwork[column][j].remove(value);
                            if (j != column) arwork[j][row].remove(value);
                        }
                    }
                }
            }
        }

        // сначала мы разматывали 1ки
        // после можно разматывать 2ки: если ключ = 2 и конец строки/колонки = 4, значит перед нами 3ка
        // аналогично, далее разматываем 3ки: если ключ 3 и конец строки 4 и 3, значит перед нами 2ка
        // в общем случае, нужно из ключа вычитать опознанные высотки с конца строки/колонки, понижая значение ключа и высотность.
        // т.е. в строке  * * 3 4 если левый ключ 3, то
        // понижаем его два раза: получаем 1 - * * при высотности 4-2 = 2, т.е. мы видим одно здание максимальной высотности 2, ставим его на первое место.

        // второй прием: каждого здания должно быть size раз. нужно периодически считать и если находим size-1, то определяем, где пропущено.

        // третий прием: если в строке все ячейки, кроме одной определены, можно точно определить и эту последнюю ячейку. этот прием будет получаться автоматически, путем удаления точно известных элементов.

        return new int[4][4];

    }

}
