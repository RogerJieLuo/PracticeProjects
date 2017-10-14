package algorithms.backtracking;

/**
 * Created by jie luo on 2017-10-13.
 * Maze is a simple class of solving maze via backtracking algorithm
 */
public class Maze {
    public static void main(String[] args) {
        new Maze().run();
    }

    private void run(){
        int[][] maze = {
                {0,0,0,-1},
                {-1,0,-1,0},
                {0,0,0,0},
                {-1,0,-1,0}
        };
        int[][] moves = {
                {1,0},{-1,0},{0,1},{0,-1}
        };
        int rest = maze.length * maze[0].length;
        for(int[] r : maze){
            for(int c : r){
                if(c == -1){
                    rest--;
                }
            }
        }
        int startX = 0,startY=0,endX=3,endY=3;
        boolean completed = false;
        completed = solve(maze, startX,startY,rest,moves,endX,endY);
        if(completed){
            System.out.println("Success");
            display(maze);
        }else{
            System.out.println("Fail");
            display(maze);
        }

    }

    private boolean solve(int[][] maze, int x, int y, int rest, int[][] moves, int endx, int endy){
        if(isValid(maze,x,y)){
            maze[x][y] = rest;
            rest --;
            if(x == endx && y == endy){
                return true;
            }
            // param moves could be removed from solve method in the way below
            if(solve(maze,x+1,y,rest,moves, endx, endy))
                return true;
            if(solve(maze, x,y+1,rest,moves,endx,endy))
                return true;
            if(solve(maze,x-1,y,rest,moves, endx, endy))
                return true;
            if(solve(maze, x,y-1,rest,moves,endx,endy))
                return true;
//            for(int i =0;i<moves.length;i++){
//                int tx = x+moves[i][0];
//                int ty = y +moves[i][1];
//                if(solve(maze, tx, ty, rest,moves, endx, endy)){
//                    maze[tx][ty] = rest;
//                    return true;
//                }
//            }
//            if(rest == 0){
//                return true;
//            }else{
//                maze[x][y] = 0;
//                System.out.println("Return to x: "+x+", y: "+y);
//                return false;
//            }
            maze[x][y] = 0;
            return false;
        }
        return false;
    }

    private boolean isValid(int[][] maze, int x, int y){
        return (x >= 0 && y >= 0 && x<maze.length && y<maze[0].length && maze[x][y]==0);
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
