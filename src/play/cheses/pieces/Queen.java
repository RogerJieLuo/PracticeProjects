package play.cheses.pieces;

import play.common.Point;

/**
 * Created by jieluo on 2017-04-10.
 */
public class Queen extends Piece{
    public Queen(String name, int color, Point position){
        super(name,color,position);
        setSign('Q');
        setType("Queen");
    }

    // move and check if it's a legal move
    public boolean checkValidMovePattern(Point target){
        Point position = super.getPosition();
        if(Math.abs(target.y - position.y ) == Math.abs(target.x - position.x))
            return true;
        if(target.x == position.x || target.y == position.y )
            return true;
        return false;
    }

}
