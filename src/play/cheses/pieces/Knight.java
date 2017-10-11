package play.cheses.pieces;

import common.Point;

/**
 * Created by jieluo on 2017-04-10.
 */
public class Knight extends Piece {
    public Knight(String name, int color, Point position){
        super(name,color,position);
        setSign('N');
        setType("Knight");
    }

    // move and check if it's a legal move
    public boolean checkValidMovePattern(Point target){
        Point position = super.getPosition();
        if((Math.abs(target.y -position.y) == 1 && Math.abs(target.x-position.x) == 2)
                || (Math.abs(target.y -position.y) == 2 && Math.abs(target.x-position.x) == 1))
            return true;
        return false;
    }

}
