package play.cheses.pieces;

import common.Point;

/**
 * Created by jieluo on 2017-04-10.
 */
public class Bishop extends Piece{
    public Bishop(String name, int color, Point position){
        super(name,color,position);
        setSign('B');
        setType("Bishop");
    }

    // move and check if it's a legal move
    public boolean checkValidMovePattern(Point target){
        Point position = super.getPosition();
        if(Math.abs(target.y - position.y ) == Math.abs(target.x - position.x) )
            return true;
        return false;
    }

}
