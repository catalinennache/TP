public class DivideEtImp14T2 {


    public static void main(String[] args){
        Integer[][] mtrx = new Integer[4][4];
        for(int i =0;i<mtrx.length;i++){
            for(int j = 0; j<mtrx[0].length;j++)
              mtrx[i][j]= 0;
        }
        fillMatrix(mtrx,4,0,0);

        for(int i =0;i<mtrx.length;i++){
            for(int j = 0; j<mtrx[0].length;j++)
                System.out.print(mtrx[i][j]);
            System.out.println();
        }

    }

    public static void fillMatrix(Integer[][] matrix,int bound,int x_start,int y_start){
       // System.out.println("function called with bound "+bound+" x_start "+x_start+" y_Start "+y_start);
        if(bound <= 1)
            return;
        if(bound == 2){
            matrix[x_start][y_start]=1;
            return;
        }

        for(int i =0;i<bound/2;i++){
            for(int j = 0;j<bound/2;j++)
              matrix[i+x_start][j+y_start] = 1;
        }
        for(int k = 1; k < 4 ;k++) {
           // System.out.println("entering recursive for k = "+k+" "+bound+" nth = "+k);
            fillMatrix(matrix, bound/2, x_start+ (k/2)*(bound/2),y_start+((k)%2)*(bound/2));

        }


    }


}
