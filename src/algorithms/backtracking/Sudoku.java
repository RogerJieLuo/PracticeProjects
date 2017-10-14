package algorithms.backtracking;

/**
 * Created by jieluo on 2017-10-14.
 */
public class Sudoku {
    public static void main(String[] args) {
        new Sudoku().run();
    }
    private void run(){
        final long startTime = System.nanoTime();
        int[][] sudoku = {
                {3,0,6,5,0,8,4,0,0},
                {5,2,0,0,0,0,0,0,0},
                {0,8,7,0,0,0,0,3,1},
                {0,0,3,0,1,0,0,8,0},
                {9,0,0,8,6,3,0,0,5},
                {0,5,0,0,9,0,6,0,0},
                {1,3,0,0,0,0,2,5,0},
                {0,0,0,0,0,0,0,7,4},
                {0,0,5,2,0,6,3,0,0}
        };
        String option = "123456789";
        boolean completed = solve(sudoku, 0, 0);
        if(completed){
            System.out.println("Success:");
            display(sudoku);
        }else {
            System.out.println("Failed");
            display(sudoku);
        }
        System.out.println("System runs: " + (System.nanoTime() - startTime)/1000000000 + " secs");
    }

    private boolean solve(int[][] sudoku, int x, int y){
        // if the cell already filled with num
        if(sudoku[x][y] != 0) {
            int[] cell = getNextCell(sudoku,x,y);
            if(cell[0] == -1 && cell[1] == -1)
                return true;
            if(solve(sudoku,cell[0],cell[1]))
                return true;
        }

        for(int i = 1;i<=9;i++){
            sudoku[x][y] = i;
            if(isValid(sudoku, x, y)){
                int[] cell = getNextCell(sudoku,x,y);
                if(cell[0] == -1 && cell[1] == -1)
                    return true;
                if(solve(sudoku,cell[0],cell[1]))
                    return true;
            }
        }
        sudoku[x][y] = 0;
        return false;
    }

    private int[] getNextCell(int[][] sudoku, int x, int y){
        if(y+1 < sudoku[x].length){
            return new int[]{x, y+1};
        }else if (x+1 < sudoku.length){
            return new int[]{x+1, 0};
        }else{
            return new int[]{-1,-1};
        }
    }

    private boolean isValid(int[][] sudoku, int x, int y){
        // vertically
        for(int i = 0;i<sudoku.length;i++){
            if(i != x && sudoku[i][y] == sudoku[x][y])
                return false;
        }
        // horizontally
        for(int i = 0;i<sudoku[x].length;i++){
            if(i != y && sudoku[x][i] == sudoku[x][y])
                return false;
        }

        // in the 3 x 3 cells
        int col =y/3, row = x/3;
        for(int i = row*3;i<row*3+3;i++){
            for(int j = col*3;j<col*3+3;j++){
                if(i != x && j != y && sudoku[i][j] == sudoku[x][y])
                    return false;
            }
        }
        return true;
    }

    private void display(int[][] b){
        for (int[] row : b) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
