package play.SimpleAttack.mcVersion;

/**
 * Created by jieluo on 2017-04-07.
 */
public class Board {
    private int line = 5;

    private Hero[][] board = new Hero[line][line];

    void initiateBoard(){

    }

    // update cell being occupied/null
    void udpateCell(Point point, Hero hero){
        if(hero == null)
            board[point.x][point.y] = null;
        else
            board[point.x][point.y] = hero;
    }


}
