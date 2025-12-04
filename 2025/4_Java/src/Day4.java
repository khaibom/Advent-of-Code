import java.io.IOException;

public class Day4 {
    public static boolean check_paper_roll_p1(char[][] matrix, int i, int j) {
        if (matrix[i][j] != '@') return false;
        int count = 0;
        for (int i_adj = i-1; i_adj <= i+1; i_adj++) {
            for (int j_adj = j-1; j_adj <= j+1; j_adj++) {
                if(i_adj == i && j_adj == j) continue;
                if(i_adj < 0 || j_adj < 0 ) continue;
                if(i_adj >= matrix[0].length || j_adj >= matrix[1].length ) continue;

                if (matrix[i_adj][j_adj] == '@') count++;
            }
        }
        return count < 4;
    }
    public static void part1(char[][] matrix) {
        int count = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix[1].length; j++) {
                if(check_paper_roll_p1(matrix, i, j)) count++;
            }
        }
        System.out.println("Part 1: " + count); //1356
    }


    public static void main(String[] args) throws IOException {
        Input input = new Input("src/input.txt");
        part1(input.matrix);
    }

}
