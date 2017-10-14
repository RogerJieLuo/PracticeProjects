package algorithms.backtracking;

/**
 * Created by jieluo on 2017-10-13.
 * each queen in the board can not be in the attack mode.
 */
public class NQueens {
    private int N = 10;
    public static void main(String[] args) {
        new NQueens().run();
    }

    private void run(){
        int[][] maze = new int[N][N];

        int rest = N;

        boolean completed = solve(maze,0, rest);
        if(completed){
            System.out.println("Success Complete: ");
            display(maze);
        }else{
            System.out.println("Failed: ");
            display(maze);
        }

    }

    private boolean solve(int[][] maze, int col, int rest){
        if (col == maze[0].length) return true;
        for (int i = 0; i < maze.length; i++) {
            System.out.println("display:");
            display(maze);
            if (isValid(maze, i, col)) {
                maze[i][col] = rest;
                if (solve(maze, col + 1, rest - 1))
                    return true;
                else
                    maze[i][col] = 0;
            }
        }
        return rest == 0;
    }

    private boolean isValid(int[][] maze, int x, int y){
        for(int i = 0;i<y;i++){
            if(maze[x][i] != 0)
                return false;
        }
        for(int i = x-1, j = y-1; i>=0 && j>=0; i--, j--){
            if(maze[i][j] != 0)
                return false;
        }
        for(int i = x+1, j = y -1; i<maze.length && j>=0; i++, j--){
            if(maze[i][j] != 0)
                return false;
        }
        return true;
    }

    private void display(int[][] maze){
        for(int[] row: maze){
            for(int col: row){
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }
}
