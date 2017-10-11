package play.go;

import common.Point;

import java.util.LinkedList;

/**
 * Created by jieluo on 2017-06-14.
 */
public class GO {
    private int x = 19;
    private int y = 19;
    private int[][] board = new int[x][y];
    private int black = 1;
    private int white = 2;


    /*
    when play the stone, system will check
    1. if it kills others
    2. if it cause self been killed
     */
    private void check(){

    }

    private void kill(){

    }

    private void connect(Point target){
        boolean[][] visited = new boolean[x][y];
        LinkedList<Point> connectedPoints = new LinkedList<>();

        connectCheck(target, visited, connectedPoints);
    }

    private void connectCheck(Point target, boolean[][] visited, LinkedList<Point> points){
        if(target.x - 1 < 0 || target.x>=19 || target.y -1 <0 || target.y>=19)
            return;
        int tx = 0, ty = 0;
        // left
        tx = target.x -1;
        ty = target.y;
        if( board[tx][ty] == board[tx][ty] ) {
            visited[tx][ty] = true;
            Point point = new Point(tx , ty);
            points.add(point);
            connectCheck(point, visited, points);
        }
        // right
        tx = target.x + 1;
        ty = target.y;
        if( board[tx][ty] == board[tx][ty] ) {
            visited[tx][ty] = true;
            Point point = new Point(tx , ty);
            points.add(point);
            connectCheck(point, visited, points);
        }
        // top
        tx = target.x;
        ty = target.y - 1;
        if( board[tx][ty] == board[tx][ty] ) {
            visited[tx][ty] = true;
            Point point = new Point(tx , ty);
            points.add(point);
            connectCheck(point, visited, points);
        }

        //bottom
        tx = target.x;
        ty = target.y + 1;
        if( board[tx][ty] == board[tx][ty] ) {
            visited[tx][ty] = true;
            Point point = new Point(tx , ty);
            points.add(point);
            connectCheck(point, visited, points);
        }
    }

}
