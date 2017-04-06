package play.go.mcVersion;

import java.util.ArrayList;

/**
 * Created by jieluo on 2017-04-05.
 */
public class BlackWhite {
    private int line = 4;
    private int[][] board;
    private int blackColor = -1;
    private int whiteColor = 1;
    private String[] playerArr = {"Black","White"};
    private Player curPlayer;
    private boolean end = false;
    private int countStep = 0;
    private String lastAction = "";

    Player blackPlayer;
    Player whitePlayer;

    public static void main(String[] args) {
        new BlackWhite().newGame();
    }

    private void initiateBoard(){
        board = new int[line][line];
        board[line/2-1][line/2-1] = blackColor;
        board[line/2][line/2] = blackColor;
        board[line/2-1][line/2] = whiteColor;
        board[line/2][line/2-1] = whiteColor;

        // test board
//        for(int i=0;i<board.length;i++){
//            for(int j= 0;j<board[i].length;j++){
//                board[i][j] = 1;
//            }
//        }
//        board[0][0] = -1;
//        board[line-1][line-1] =0;


        // countStep
        for(int i=0;i<board.length;i++){
            for(int j= 0;j<board[i].length;j++){
                if(board[i][j] != 0){
                    countStep++;
                }
            }
        }

    }
    private void newGame(){
        initiateBoard();
        end = false;
        setPlayerOrder();
        printBoard();
        start();
    }

    private void setPlayerOrder(){
        blackPlayer = new Player(playerArr[0], blackColor);
        whitePlayer = new Player(playerArr[1], whiteColor);
        blackPlayer.next = whitePlayer;
        whitePlayer.next = blackPlayer;
        curPlayer = blackPlayer;
    }

    private void start(){
        Player winner = null;
        while(!end){
            System.out.println(curPlayer.name+", It's your turn:");
            boolean played = false;
            String action ="";
            while(!played) {
                Action did = curPlayer.put();
                if (did.action.equals("pass") && lastAction.equals("pass")) {
                    end = true;
                    winner = countPoint();
                    break;
                } else if (did.action.equals("resign")) {
                    end = true;
                    winner = curPlayer.next;
                    break;
                } else if (did.action.equals("pass")) {
                    played = true;
                } else if (did.action.equals("play")) {
                    played = play(curPlayer, did.point);
                    if (!played) {
                        System.out.println("Your input is invalid, please try again");
                    }
                }
                action = did.action;
            }
            lastAction = action;
            printBoard();
            countStep++;
            // ends when no empty cell
            if(countStep == line*line){
                end = true;
                winner = countPoint();
                break;
            }
            curPlayer = curPlayer.next;

        }
        if(winner == null){
            System.out.println("Draw!!!");
        }else {
            System.out.println(winner.name + " Won!");
        }
    }

    private Player countPoint(){
        int total = 0;
        for(int i=0;i<board.length;i++){
            for(int j= 0;j<board[i].length;j++){
                total+=board[i][j];
            }
        }
        if(total>0)
            // white wins
            return whitePlayer;
        else if(total<0)
            // black wins
            return blackPlayer;
        else
            return null;
    }

    private boolean play(Player player, Point point){
        if(point == null){
            return false;
        }

        if(board[point.x][point.y] == 0 ) {
            if(!checkValidPoint(player,point))
                return false;
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param player
     * @param point
     * @return
     *
     * valid:
     *      the point is not been occupied
     *      the point would not kill player-self
     */
    private boolean checkValidPoint(Player player, Point point){
        boolean valid = false;
        if (checkUp(player,point))
            valid = true;
        if(checkDown(player,point))
            valid = true;
        if(checkLeft(player,point))
            valid = true;
        if(checkRight(player,point))
            valid = true;
        if (checkLeftUp(player,point))
            valid = true;
        if(checkLeftDown(player,point))
            valid = true;
        if(checkRightUp(player,point))
            valid = true;
        if(checkRightDown(player,point))
            valid = true;
        return valid;
    }

    boolean checkUp(Player player, Point point){
        int currentColor = player.stoneColor;
        if(point.x <=1 ||  currentColor == board[point.x-1][point.y] || board[point.x-1][point.y] == 0){
            return false;
        }

        boolean c= false;
        ArrayList<Point> list = new ArrayList<>();
        list.add(point);
        for(int i = point.x-1;i >=0;i--){
            if(board[i][point.y] != currentColor){
                list.add(new Point(i,point.y));
            }else{
                c = true;
                break;
            }
        }
        if(c) {
            for (Point p : list) {
                board[p.x][p.y] = currentColor;
            }
        }
        return c;
    }
    boolean checkDown(Player player, Point point){
        int currentColor = player.stoneColor;
        if(point.x >= line-2 ||  currentColor == board[point.x+1][point.y] || board[point.x+1][point.y]==0){
            return false;
        }

        boolean c= false;
        ArrayList<Point> list = new ArrayList<>();
        list.add(point);
        for(int i = point.x+1;i < line;i++){
            if(board[i][point.y] != currentColor){
                list.add(new Point(i,point.y));
            }else{
                c = true;
                break;
            }
        }
        if(c) {
            for (Point p : list) {
                board[p.x][p.y] = currentColor;
            }
        }
        return c;
    }
    boolean checkLeft(Player player, Point point){
        int currentColor = player.stoneColor;
        if(point.y <= 1 ||  currentColor == board[point.x][point.y-1] || board[point.x][point.y-1] == 0){
            return false;
        }

        boolean c= false;
        ArrayList<Point> list = new ArrayList<>();
        list.add(point);
        for(int i = point.y-1;i >= 0;i--){
            if(board[point.x][i] != currentColor){
                list.add(new Point(point.x, i));
            }else{
                c = true;
                break;
            }
        }
        if(c) {
            for (Point p : list) {
                board[p.x][p.y] = currentColor;
            }
        }
        return c;
    }
    boolean checkRight(Player player, Point point){
        int currentColor = player.stoneColor;
        if(point.y >= line - 2 ||  currentColor == board[point.x][point.y+1] || board[point.x][point.y+1]==0){
            return false;
        }

        boolean c= false;
        ArrayList<Point> list = new ArrayList<>();
        list.add(point);
        for(int i = point.y+1;i < line;i++){
            if(board[point.x][i] != currentColor){
                list.add(new Point(point.x, i));
            }else{
                c = true;
                break;
            }
        }
        if(c) {
            for (Point p : list) {
                board[p.x][p.y] = currentColor;
            }
        }
        return c;
    }

    boolean checkLeftUp(Player player, Point point){
        int currentColor = player.stoneColor;
        if(point.x <=1 ||  point.y <=1 ||
                currentColor == board[point.x-1][point.y-1] ||
                board[point.x-1][point.y-1] == 0
                ){
            return false;
        }

        boolean c= false;
        ArrayList<Point> list = new ArrayList<>();
        list.add(point);
        int _x = point.x -1;
        int _y = point.y -1;
        while(_x>=0 && _y >=0){
            if(board[_x][_y] != currentColor){
                list.add(new Point(_x, _y));
            }else{
                c = true;
                break;
            }
            _x --;
            _y --;
        }
        if(c) {
            for (Point p : list) {
                board[p.x][p.y] = currentColor;
            }
        }
        return c;
    }
    boolean checkRightUp(Player player, Point point){
        int currentColor = player.stoneColor;
        if(point.x <=1 ||  point.y >=line-2 ||
                currentColor == board[point.x-1][point.y+1] ||
                board[point.x-1][point.y+1] == 0
                ){
            return false;
        }

        boolean c= false;
        ArrayList<Point> list = new ArrayList<>();
        list.add(point);
        int _x = point.x -1;
        int _y = point.y +1;
        while(_x>=0 && _y < line){
            if(board[_x][_y] != currentColor){
                list.add(new Point(_x, _y));
            }else{
                c = true;
                break;
            }
            _x --;
            _y ++;
        }
        if(c) {
            for (Point p : list) {
                board[p.x][p.y] = currentColor;
            }
        }
        return c;
    }
    boolean checkLeftDown(Player player, Point point){
        int currentColor = player.stoneColor;
        if(point.x >= line-2 ||  point.y <= 1 ||
                currentColor == board[point.x+1][point.y-1] ||
                board[point.x+1][point.y-1] == 0
                ){
            return false;
        }

        boolean c= false;
        ArrayList<Point> list = new ArrayList<>();
        list.add(point);
        int _x = point.x +1;
        int _y = point.y -1;
        while(_x<line && _y >=0){
            if(board[_x][_y] != currentColor){
                list.add(new Point(_x, _y));
            }else{
                c = true;
                break;
            }
            _x ++;
            _y --;
        }
        if(c) {
            for (Point p : list) {
                board[p.x][p.y] = currentColor;
            }
        }
        return c;
    }
    boolean checkRightDown(Player player, Point point){
        int currentColor = player.stoneColor;
        if(point.x >= line-2 ||  point.y >= line -2 ||
                currentColor == board[point.x+1][point.y+1] ||
                board[point.x+1][point.y+1] == 0
                ){
            return false;
        }

        boolean c= false;
        ArrayList<Point> list = new ArrayList<>();
        list.add(point);
        int _x = point.x +1;
        int _y = point.y +1;
        while(_x<line && _y < line){
            if(board[_x][_y] != currentColor){
                list.add(new Point(_x, _y));
            }else{
                c = true;
                break;
            }
            _x ++;
            _y ++;
        }
        if(c) {
            for (Point p : list) {
                board[p.x][p.y] = currentColor;
            }
        }
        return c;
    }

    private void printBoard(){
        for(int i=0;i<board.length;i++){
            for(int j = 0;j<board[i].length;j++){
                System.out.printf("%3d ", board[i][j]);
            }
            System.out.println();
        }
    }

}
