package algorithms.backtracking;

/**
 * Created by jieluo on 2017-10-12.
 * KnightTour, same idea as puzzles.
 *
 */
public class KT {

    public static void main(String[] args) {
        new KT().run();
    }
    public void run(){
        // initiate the puzzle
        int[][] b = {
                {-1,0,0,-1},
                {0,0,-1,0},
                {0,0,0,0},
                {-1,0,0,-1}
        };
        int x = 4, y = 4;
        int attempts = 0;
        // calculate the obstacle number
        int obstacle = 0;
        for(int i = 0;i<b.length;i++){
            for (int j = 0;j <b[i].length;j++){
                if(b[i][j] == -1){
                    obstacle ++;
                }
            }
        }
        int rest = x * y - obstacle;      // determine how many cells need to be check.
        // 4 types of movements, up, left, right ,down
        int[][] moves = {
                {1,0},{0,1},{-1,0},{0,-1}
        };
        boolean completed= false;
        // let the game starts from all the possible cells
        for(int i = 0;i<b.length;i++){
            if(completed) break;
            for(int j = 0;j<b[i].length;j++){
                if(b[i][j] != -1){
                    attempts++;
                    completed = track(b, i, j, moves,rest);
                    if(completed) break;
                }
            }
        }
        System.out.println("attempts: "+ attempts);
        if(completed){
            System.out.println("success");
            display(b);
        }else {
            System.out.println("Fail");
            display(b);
        }
    }

    private boolean track(int[][] b, int x, int y,int[][] moves, int rest){
        if(isValid(b, x, y)){
            b[x][y] = rest;
            rest --;
            for(int i = 0;i<moves.length;i++){
                int tx = x+moves[i][0];
                int ty = y+moves[i][1];
                System.out.println("tx "+tx+", ty "+ty);
                if(track(b,tx,ty,moves,rest)){
                    b[x][y] = rest;
                    return true;
                }else{
                    System.out.println("return to "+x+", "+y);
                }
            }
            display(b);
            if (rest == 0){     // last cell will need to be set
                b[x][y] = rest;
                return true;
            }else{              // dead end, so go back
                b[x][y] = 0;
                return false;
            }
        }
        return false;
    }

    private boolean isValid(int[][] matrix, int x, int y){
        return x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length && matrix[x][y] == 0;
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
