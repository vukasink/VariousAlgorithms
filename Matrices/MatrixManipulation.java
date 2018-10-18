import java.util.Random;

/**
 * Class with static methods for dealing with
 * matrices.
 * @author Vukasin Karadzic, vukasin.karadzic@gmail.com
 */
public class MatrixManipulation {
    
    /**
     * Method for printing the matrix.
     * @param a matrix to be printed.
     */
    public static void printMatrix(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
    /**
     * Method that makes a new matrix made of the sum of the two passed.
     * @param a first matrix to be added.
     * @param b second matrix to be added.
     * @return new matrix that represents a sum of matrices a and b.
     */
    public static int[][] matrixAdd(int[][] a, int[][] b) {
        if (a == null || b == null)     throw new NullPointerException("Error! Cannot multiply matrix that is null.");
        if (a.length != b.length || a[0].length != b[0].length) throw new RuntimeException("Error! Invalid dimensions"
                                                                        + "of the matrices you are trying to add.");        
        int[][] c = new int[a.length][a[0].length];  
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }
    
    /**
     * Method that makes a new matrix made of subtracting the first one passed
     * with the second one
     * @param a first matrix.
     * @param b second matrix that is being subtracted.
     * @return new matrix that represents: matrix a - matrix b.
     */    
    public static int[][] matrixSub(int[][] a, int[][] b) {
        if (a == null || b == null)     throw new NullPointerException("Error! Cannot multiply matrix that is null.");
        if (a.length != b.length || a[0].length != b[0].length) throw new RuntimeException("Error! Invalid dimensions"
                                                                        + "of the matrices you are trying to add.");        
        int[][] c = new int[a.length][a[0].length];  
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[i][j] = a[i][j] - b[i][j];
            }
        }
        return c;
    }
    
    /**
     * Classical method for multiplying two matrices. In order for multiplication
     * to be possible, number of columns of the first matrix must be the same as
     * number of rows of the second one matrix.
     * @param a
     * @param b
     * @return New matrix, the result of multiplication of matrix a with matrix b.
     */
    public static int[][] matrixMultiplication(int[][] a, int[][] b) {
        if (a == null || b == null)     throw new NullPointerException("Error! Cannot multiply matrix that is null.");
        if (a[0].length != b.length)    throw new RuntimeException("Error! Invalid dimensions of the"
                                                + " matrices you are trying to multiply.");
        
        int[][] c = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++)
            for(int j = 0; j < b[0].length; j++)
                for(int k = 0; k < a[0].length; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }
    
    /**
     * Method that copies the matrix.
     * @param m matrix to be copied.
     * @return new matrix, identical to the matrix m.
     */
    public static int[][] clone(int[][] m) {
        if (m == null)      throw new NullPointerException("Error! Cannot multiply matrix that is null.");
        int[][] copyM = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                copyM[i][j] = m[i][j];
            }
        }
        return copyM;
    }
    
    /**
     * Method that generates a random permutation matrix.
     * @param n the size of the permutation matrix.
     * @return random permutation matrix.
     */
    public static int[][] generatePermutationMatrix(int n) {
        boolean[] columns = new boolean[n];
        int[][] matrix = new int[n][n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            boolean filled = false;
            while (!filled) {
                int j = rand.nextInt(n);
                if(!columns[j]) {
                    matrix[i][j] = 1;
                    columns[j] = true;
                    filled = true;
                }
            }
        }
        return matrix;
    }    
}
