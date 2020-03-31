import java.io.*;

public class DivideEtImp14T1 {



    public static void main(String[] args) throws IOException {

       punct.init("data.in","data.out");
       punct start = punct.getStartingPoint();
       Hashura(start);
       System.out.println("-------");
       punct.flushMatrix();
       punct.printMatrix();

    }

    public static void Hashura(punct current_pos){

        if(current_pos.isOutOfBounds() || current_pos.getMatrixVal() == 1  )
                return;

        current_pos.hashuieste();
        Hashura(current_pos.getVecin(VECIN.STANGA));
        Hashura(current_pos.getVecin(VECIN.IN_FATA));
        Hashura(current_pos.getVecin(VECIN.DREAPTA));
        Hashura(current_pos.getVecin(VECIN.IN_SPATE));
    }



}
 enum VECIN {
    STANGA,
    DREAPTA,
    IN_FATA,
    IN_SPATE
}
class punct{


    public static Integer[][] matrix =  null;
    private static File input;
    private static File output;
    private static punct start_point;
    private int x,y;

    punct(int x,int y){
        this.x = x;
        this.y = y;
    }
   public static  punct getStartingPoint(){
        return punct.start_point;
    }
   void hashuieste(){
        matrix[x][y]=1;
   }

   int getMatrixVal(){
        return matrix[x][y];
    }

    boolean isOutOfBounds(){
        try{
            int a = matrix[x][y];
        return false;
        }catch (Exception e){ return  true;}
    }

    punct getVecin(VECIN v){

        int next_x = v.equals(VECIN.IN_SPATE)?x-1:v.equals(VECIN.IN_FATA)?x+1:x;
        int next_y = v.equals(VECIN.STANGA)?y-1:v.equals(VECIN.DREAPTA)?y+1:y;

        return new punct(next_x,next_y);
    }

    public static void printMatrix(){
        for(int i = 0; i<punct.matrix.length;i++){
            for(int j =0; j<punct.matrix[0].length;j++){
                System.out.print(punct.matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void init(String input_file,String output_file) throws IOException {
        input = new File(input_file);
        output = new File(output_file);

        BufferedReader reader = new BufferedReader(new FileReader(input));
        String[] M_N = reader.readLine().split(" ");
        matrix = new Integer[Integer.parseInt(M_N[0])][Integer.parseInt(M_N[1])];

        for(int i = 0; i<Integer.parseInt(M_N[0]); i++){
            String[] line_cells = reader.readLine().split(" ");
            for(int j = 0; j < line_cells.length; j++)
                matrix[i][j] = Integer.valueOf(line_cells[j]);
        }

        String[] coords =  reader.readLine().split(" ");
        start_point = new punct(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
        reader.close();
    }



    public static void flushMatrix() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        for(int i = 0; i<matrix.length; i++){
            for (int j = 0; j<matrix[0].length; j++)
                writer.write(matrix[i][j]+  (j == matrix[0].length-1?"":" "));
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

}