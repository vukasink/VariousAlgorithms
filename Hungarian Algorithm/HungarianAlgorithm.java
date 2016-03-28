import java.util.Arrays;

/**
 * 
 * @author Vukasin Karadzic, vukasin.karadzic@gmail.com
 */
public class HungarianAlgorithm {
    
    private int[][] costMatrix;
    private int dim;
    private int numLines;
    private int[] zerosInRow, zerosInColumn;
    private boolean[] rowCovered, columnCovered;
    private int[] assignment;

    /**
     * 
     * @return array of integers, int[i] = j represents that the i-th  job is
     * assigned to j-th worker.
     */
    public int[] getAssignment() { return assignment; }
    
    /**
     * Class constructor
     * @param costMatrix
     * @param minOrMax when MAX, algorithm finds the maximum cost assignment,
     *                 otherwise it finds a minimum cost assignment.
     */
    public HungarianAlgorithm(int[][] costMatrix){
        this.costMatrix = costMatrix;
        this.dim = costMatrix.length;
        this.dim = costMatrix[0].length;
        zerosInRow = new int[dim];
        zerosInColumn = new int[dim];
        execute();
    }
    
    /**
     * Executes the algorithm.
     * 
     * Also, the 4. step of the Hungarian algorithm described as in
     * "Hungarian algorithm.txt", the Test for Optimality, is executed in 
     * this method.
     */
    private void execute() {
        subtractMinFromRows();
        subtractMinFromColumns();
        coverZeros();
        System.out.println(numLines);
        while (numLines < dim) {
            changeCostMatrix();
            coverZeros();
        System.out.println(numLines);
        }
        printCostMatrix();
        findAssignment();
    }
    
    /**
     * Finds a minimal value in each row and then subtracts that
     * value from each element/value in that row.
     * 
     * Step 1. from the "Hungarian algorithm.txt".
     */
    public void subtractMinFromRows(){
        for(int i = 0; i < dim; i++){
            int minValue = costMatrix[i][0];
            for(int j = 1; j < dim; j++)
                if(costMatrix[i][j] < minValue)
                    minValue = costMatrix[i][j];
            for(int j = 0; j < dim; j++){
                costMatrix[i][j] -= minValue;
            }
        }
    }

    /**
     * Finds a minimal value in each column and then subtracts that
     * value from each element/value in that column.
     * 
     * Step 2. from the "Hungarian algorithm.txt". 
     */
    public void subtractMinFromColumns(){
        for(int i = 0; i < dim; i++){
            int minValue = costMatrix[0][i];
            for(int j = 1; j < dim; j++)
                if(costMatrix[j][i] < minValue)
                    minValue = costMatrix[j][i];
            for(int j = 0; j < dim; j++)
                costMatrix[j][i] -= minValue;
        }
    }
    
    /**
     * Finding a minimum number of lines that are needed
     * to cover all zeros.
     * 
     * Step 3. from the "Hungarian algorithm.txt". 
     */
    public void coverZeros(){
        rowCovered = new boolean[dim];
        columnCovered = new boolean[dim];
        numLines = 0;
        int i, j;
        for (i = 0; i < dim; i++) {
            for (j = 0; j < dim; j++) {
                if (costMatrix[i][j] == 0) {
                    zerosInRow[i]++;
                    zerosInColumn[j]++;
                }
            }
        }   
        do {
            int maxInRow = zerosInRow[0], maxInColumn = zerosInColumn[0], maxIndexRow = 0, maxIndexColumn = 0;
            for (i = 1; i < dim; i++) {
                if (zerosInRow[i] > maxInRow) {
                    maxInRow = zerosInRow[i];
                    maxIndexRow = i;
                }
            }
            for (i = 1; i < dim; i++) {
                if (zerosInColumn[i] > maxInColumn) {
                    maxInColumn = zerosInColumn[i];
                    maxIndexColumn = i;
                }
            }
            if (maxInColumn > maxInRow) {
                for(i = 0; i < dim; i++)
                    if(costMatrix[i][maxIndexColumn] == 0)
                        zerosInRow[i]--;
                zerosInColumn[maxIndexColumn] -= maxInColumn;
                columnCovered[maxIndexColumn] = true;
            }
            else {
                for(i = 0; i < dim; i++)
                    if (costMatrix[maxIndexRow][i] == 0)
                        zerosInColumn[i]--;
                zerosInRow[maxIndexRow] -= maxInRow;
                rowCovered[maxIndexRow] = true;
            }
            numLines++;
        } while (!allZerosCovered());         
    }
     
    /**
     * Checks if all zeros are covered by some line.
     * @return true if yes, false if not.
     */
    private boolean allZerosCovered() {
        for (int i = 0; i < dim; i++)
            if (zerosInRow[i] != 0)
                return false;
        for (int i = 0; i < dim; i++)
            if (zerosInColumn[i] != 0)
                return false;
          
        return true;
    }
    
    /**
     * Changes the cost matrix as described in Step 5. from the
     * "Hungarian algorithm.txt" file.
     */
    private void changeCostMatrix(){
        int minUncovered = Integer.MAX_VALUE;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (!rowCovered[i] && !columnCovered[j] && costMatrix[i][j] < minUncovered)
                    minUncovered = costMatrix[i][j];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (!rowCovered[i] && !columnCovered[j])
                    costMatrix[i][j] -= minUncovered;
                if (rowCovered[i] && columnCovered[j])
                    costMatrix[i][j] += minUncovered;
            }
        }
    }    
    
    /**
     * Method that finds assigment.
     */
    private void findAssignment() {
        assignment = new int[dim];
        Arrays.fill(assignment, Integer.MAX_VALUE);
        find(0);
    }
    
    /**
     * Recursive, backtracking method, that finally finds optimal solution
     * to assignment problem.
     * @param i 
     */
    private void find(int i){
        if (i == dim) 
           return;
        else {
            for (int j = 0; j < dim; j++)
                if (costMatrix[i][j] == 0 && !inContradiction(i, j)) {
                    assignment[i] = j;
                    find(i + 1);
                }
        }
    }
    
    /**
     * Checks if the j already exists in assignment[k], where k is in {1, ..., i-1}.
     * @param i
     * @param j
     * @return true if it does exist, false if it does not exist.
     */
    private boolean inContradiction(int i, int j) {
        for (int k = 0; k <= i; k++) {
            if(assignment[k] == j)
                return true;
        }
        return false;
    }
    
    /**
     * Prints the cost matrix.
     */
    public void printCostMatrix(){
        System.out.println("Cost matrix:");
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
               System.out.print(costMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
}
