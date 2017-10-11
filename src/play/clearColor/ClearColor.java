package play.clearColor;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by jieluo on 2017-06-03.
 */
public class ClearColor {
    private int x = 12;
    private int y = 8;
    private int[][] board;
    private int[] color = {1,2,3,4};
    boolean[][] check = new boolean[x][y];
    private int[] columnsCheck = new int[x];
    private int numOfConnect = 0;
    private int scoreTotal = 0;
    private int restBrick = 1;

    public static void main(String[] args) {
        new ClearColor().run();
    }

    private void run(){
        initiate();
        play();
    }

    private void initiate(){
        board  = new int[x][y];
        for(int i = 0;i<x;i++){
            for(int j = 0;j<y; j++){
                board[i][j] = getRandomColor();
            }
        }
    }

    private void play(){
        System.out.println("Game Start!");
        while(checkEnd()){
            print();
            numOfConnect = 1;
            check = new boolean[x][y];
            System.out.println("Please select the brick:");
            Scanner in = new Scanner(System.in);
            int x_=0, y_=0;
            System.out.println("Target Point: ");
            String n = in.nextLine();
            String[] strInput = n.split(",");
            x_ = Integer.valueOf(strInput[0].trim());
            y_ = Integer.valueOf(strInput[1].trim());
            if(validInput(x_, y_)) {
                checkConnected(x_, y_);
                reConstruct();
                calScore();
            }else {
                System.out.println("Invalid input!");
            }
        }
        print();
        checkRestBrick(x-1,0);
        System.out.println("Total score: score: "+ scoreTotal+" + bonus: "+calBonus()+" = "+(scoreTotal+calBonus()));
        System.out.println("Game end");
    }

    // check if there are 2 same color connected
    private boolean checkEnd(){
        for(int i = 0;i<x;i++){
            for(int j = 0;j<y;j++){
                if(board[i][j] == 0) continue;
                int c = board[i][j];
                if(i == x-1 && j< y-1){
                    if(board[i][j+1] == c) return true;
                }else if(j == y-1 && i<x-1){
                    if(board[i+1][j] == c) return true;
                }else if(i != x-1 && j != y-1){
                    if(board[i][j+1] == c || board[i+1][j] == c) return true;
                }
            }
        }
        return false;
    }

    private boolean validInput(int x_,int y_){
        // point out of the board
        if(x_<0 || x_>=x || y_<0 || y_>=y || board[x_][y_] == 0)
            return false;
        // if the point is connected
        int c = board[x_][y_];
        if(x_-1>=0 && board[x_-1][y_] == c){
            return true;
        }else if(x_+1<x && board[x_+1][y_] == c){
            return true;
        }else if(y_-1>=0 && board[x_][y_-1] == c ){
            return true;
        }else if(y_+1<y && board[x_][y_+1] == c ){
            return true;
        }

        return false;
    }

    private void checkConnected(int x_, int y_){
        check[x_][y_] = true;
        int c = board[x_][y_];
        board[x_][y_] = 0;
        columnsCheck[y_] += 1;
        // top
        if(x_-1>=0 && board[x_-1][y_] == c && !check[x_-1][y_]){
            numOfConnect++;
            checkConnected(x_-1,y_);
        }
        // bottom
        if(x_+1<x && board[x_+1][y_] == c  && !check[x_+1][y_]){
            numOfConnect++;
            checkConnected(x_+1,y_);
        }
        // left
        if(y_-1>=0 && board[x_][y_-1] == c  && !check[x_][y_-1]){
            numOfConnect++;
            checkConnected(x_, y_-1);
        }
        // right
        if(y_+1<y && board[x_][y_+1] == c  && !check[x_][y_+1]){
            numOfConnect++;
            checkConnected(x_, y_+1);
        }
    }

    private void calScore(){
        if(numOfConnect>1){
            scoreTotal += numOfConnect*10+(numOfConnect-2)*10;
        }else{
            System.out.println("No same color connected!");
        }
    }

    private void checkRestBrick(int x_, int y_){
        // cal from the bottom left, [x-1][0]
        if(board[x_][y_] == 0) return;
        restBrick ++;
        int cx = x_;
        int cy = y_;
        if(board[cx-1][cy] > 0){
            board[cx-1][cy] = 0;
            if(cx-1>=0)
                checkConnected(cx-1, cy);
        }
        if(board[cx][cy+1] > 0){
            board[cx][cy+1] = 0;
            if(cy+1<y)
                checkConnected(cx, cy+1);
        }
        return;
    }

    private int calBonus(){
        if(restBrick<10)
            return (10-restBrick)*100;
        return 0;
    }

    private void reConstruct(){
        moveDown();
        moveLeft();
    }

    private void moveDown(){
        for(int i =0; i<columnsCheck.length;i++){
            if(columnsCheck[i] >0){
                int row = x-1;
                for(int j = x - 1; j>=0;j--){
                    if(board[j][i] > 0){
                        int temp = board[row][i];
                        board[row][i] = board[j][i];
                        board[j][i] = temp;
                        row--;
                    }
                }
            }
        }
    }

    private void moveLeft(){
        int column = -1;
        for(int i = 0;i<y;i++){
            if(column == -1 && board[x-1][i] == 0){
                column = i;
            }else if(column != -1){
                for(int j = 0; j<x;j++){
                    board[j][column] = board[j][i];
                    board[j][i] = 0;
                }
                column++;
            }
        }
    }

    private int getRandomColor(){
        Random rand = new Random();
        return rand.nextInt(4) + 1;
    }

    private void print(){
        for(int i = 0;i<x;i++){
            for(int j = 0;j<y; j++){
                System.out.printf("%-2d", board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Score: "+ scoreTotal);
        System.out.println();
    }
}
