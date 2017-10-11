package play.cheses.pieces;

import common.Point;

/**
 * Created by jieluo on 2017-04-10.
 */
public class King extends Piece{
//    String type = "King";
//    char sign = 'K';
    public King(String name, int color, Point position){
        super(name,color,position);
        setSign('K');
        setType("King");
    }

    // move and check if it's a legal move
    public boolean checkValidMovePattern(Point target){
        Point position = super.getPosition();
        if(Math.abs(target.y - position.y) == 1 && Math.abs(target.x - position.x) == 1)
            return true;
        else if(Math.abs(target.y-position.y)+Math.abs(target.x-position.x) == 1)
            return true;

        return false;
    }

}
