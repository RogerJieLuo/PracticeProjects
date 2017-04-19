package play.cheses.pieces;

import play.common.Point;

/**
 * Created by jieluo on 2017-04-10.
 */
public abstract class Piece {
    private String name;
    private int color; // -1 is black, 1 is white
    private Point position;
    char sign;
    String type;
    private int forward = 0;
    private int moveCount = 0;

    public Piece(String name, int color, Point position){
        this.name = name;
        this.color = color;
        this.position = position;
    }

    public void move(Point point){
//        if(checkValidMovePattern(point))
        setPosition(point);
        setMoveCount(++moveCount);
    }
    public boolean checkValidMovePattern(Point target){
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public char getSign() {
        return sign;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getForward() {
        return forward;
    }

    public void setForward(int forward) {
        this.forward = forward;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}
