import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DivideEtImp14T1 {


    public static void main(String[] args) throws IOException {

        (new DivideEtImp14T1()).Rezolva();

    }


    public void Rezolva() {
        File input;
        File output;

        input = new File("data.in");

        int[][] matrix = null;
        Punct start_point = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String[] M_N = reader.readLine().split(" ");
            int M = Integer.parseInt(M_N[0]);
            int N = Integer.parseInt(M_N[1]);

            matrix = new int[M][N];

            for (int i = 0; i < Integer.parseInt(M_N[0]); i++) {
                String[] line_cells = reader.readLine().split(" ");
                for (int j = 0; j < line_cells.length; j++)
                    matrix[i][j] = Integer.parseInt(line_cells[j]);
            }

            String[] coords = reader.readLine().split(" ");
             start_point = new Punct(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            reader.close();
        }catch (IOException e){
            System.out.println("Problema la citire si initializare");
            e.printStackTrace();
            System.exit(0);
        } catch (Exception e){
            System.out.println("Problema la initializare");
            e.printStackTrace();
            System.exit(0);
        }

        Hasureaza(start_point, matrix);

        try {
            output = new File("data.out");
            writeMatrix(matrix, output);
        }catch (IOException e){
            System.out.println("Problema la scriere");
            e.printStackTrace();
            System.exit(0);
        }
        printMatrix(matrix);


    }

    public void Hasureaza(Punct current_pos, int[][] matrix) {
        if (isOutOfBounds(current_pos, matrix) || matrix[current_pos.getY()][current_pos.getX()] == 1) {

            return;
        }

        matrix[current_pos.getY()][current_pos.getX()] = 1;
        Hasureaza(current_pos.getVecin(VECIN.STANGA), matrix);
        Hasureaza(current_pos.getVecin(VECIN.IN_FATA), matrix);
        Hasureaza(current_pos.getVecin(VECIN.DREAPTA), matrix);
        Hasureaza(current_pos.getVecin(VECIN.IN_SPATE), matrix);
    }


    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void writeMatrix(int[][] matrix, File output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++)
                writer.write(matrix[i][j] + (j == matrix[0].length - 1 ? "" : " "));
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

    boolean isOutOfBounds(Punct punct, int[][] matrix) {
        int x = punct.getX();
        int y = punct.getY();
        return x >= matrix[0].length || x < 0 || y >= matrix.length || y < 0;
    }


}

enum VECIN {
    STANGA,
    DREAPTA,
    IN_FATA,
    IN_SPATE
}

class Punct {

    private int x, y;

    Punct(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }


    Punct getVecin(VECIN v) {
        int next_x = 0;
        int next_y = 0;

        if (v.equals(VECIN.IN_SPATE)) {
            next_x = x - 1;
        }
        else if (v.equals(VECIN.IN_FATA)) {
            next_x = x + 1;
        }else {
            next_x = x;
        }

        if (v.equals(VECIN.STANGA)) {
            next_y = y - 1;
        }else if (v.equals(VECIN.DREAPTA)) {
            next_y = y + 1;
        }else{
            next_y = y;
        }

        return new Punct(next_x, next_y);
    }


}
