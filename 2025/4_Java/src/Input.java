import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Input {
    public char [][] matrix;

    public void getInput(String input_path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(input_path));
        int rows = lines.size();
        int cols = lines.getFirst().length();

        matrix = new char[rows][cols];
        int line_pos = 0;
        for (String line : lines){
            int row_pos = 0;
            for (char c : line.toCharArray()){
                this.matrix[line_pos][row_pos] = c;
                row_pos++;
            }
            line_pos++;
        }
    }

    public Input(String input_path) throws IOException {
        getInput(input_path);
    }
}
