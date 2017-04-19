package play.cheses.pieces;

import play.common.Point;

/**
 * Created by jieluo on 2017-04-10.
 */
public class Pawn extends Piece {
    private int promotionLine = 0;

    public Pawn(String name, int color, Point position){
        super(name,color,position);
        setSign('P');
        setType("Pawn");
        setPromotionLine();
        if(super.getColor() == -1) {// black
            setForward(-1);
        }else{ // white
            setForward(1);
        }
    }

    // move and check if it's a legal move
    public boolean checkValidMovePattern(Point target){
        Point position = super.getPosition();
        // confirm it's moving forward
        if(super.getColor() == -1) {// black
            if (target.x - position.x > -1)
                return false;
        }else{ // white
            if (target.x - position.x < 1)
                return false;
        }
        if(super.getMoveCount() == 0){
            if(Math.abs(target.x - position.x) <= 2 && target.y == position.y)
                return true;
        }else{
            if(Math.abs(target.x - position.x) == 1 && target.y == position.y)
                return true;
        }
        return false;
    }

    // different move when capture others
//    boolean checkMoveAndCapture(Point target){
//        Point position = super.getPosition();
//
//        if(Math.abs(target.x - position.x)==1 && target.y - position.y == 1 )
//            return true;
//
//        return false;
//    }

    private void setPromotionLine(){
        if(this.getPosition().x == 1)
            promotionLine = 7;
        else
            promotionLine = 0;
    }

    public boolean isPromotionTime(){
        if(this.getPosition().x == promotionLine)
            return true;
        return false;
    }


}
