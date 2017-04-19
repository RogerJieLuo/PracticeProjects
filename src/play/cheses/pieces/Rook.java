package play.cheses.pieces;

import play.common.Point;

/**
 * Created by jieluo on 2017-04-10.
 */
public class Rook extends Piece{
    final String type = "Rook";
    final char sign = 'R';
    public Rook(String name, int color, Point position){
        super(name,color,position);
        setSign('R');
        setType("Rook");
    }

    // move and check if it's a legal move
    public boolean checkValidMovePattern(Point target){
        Point position = super.getPosition();
        if(target.x == position.x || target.y == position.y )
            return true;
        return false;
    }

}
