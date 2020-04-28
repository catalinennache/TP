public class DivideEtImp14T2 {


    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Incorrect number of arguments");
            System.exit(0);
        }
        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect argument format -- expected a number as a first argument");
            System.exit(0);
        }

        Double tmp = Math.pow(2, n); //Am facut boxing pentru ca mi s-a parut cea mai safe conversie
        int m = tmp.intValue();


        int[][] mtrx = new int[m][m];
        for (int i = 0; i < mtrx.length; i++) {
            for (int j = 0; j < mtrx[0].length; j++)
                mtrx[i][j] = 0;
        }
        fillMatrix(mtrx, 4, 0, 0);

        for (int i = 0; i < mtrx.length; i++) {
            for (int j = 0; j < mtrx[0].length; j++)
                System.out.print(mtrx[i][j]);
            System.out.println();
        }

    }

    public static void fillMatrix(int[][] matrix, int bound, int x_start, int y_start) {
        if (bound <= 1)
            return;
        if (bound == 2) {
            matrix[x_start][y_start] = 1;
            return;
        }

        for (int i = 0; i < bound / 2; i++) {
            for (int j = 0; j < bound / 2; j++)
                matrix[i + x_start][j + y_start] = 1;
        }
        for (int k = 1; k < 4; k++) {
            fillMatrix(matrix, bound / 2, x_start + (k / 2) * (bound / 2), y_start + ((k) % 2) * (bound / 2));
        }

    }


}

