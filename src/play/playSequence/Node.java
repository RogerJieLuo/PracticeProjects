package play.playSequence;

import play.SimpleAttack.mcVersion.Point;

import java.util.Arrays;

/**
 * Created by jieluo on 2017-04-09.
 */
public class Node {
    String val;
    int x, y;
    Node next ; // initiate the 9 discussion allowed
    Node discuss;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    void add(Node next){
        this.next = next;
    }

    void discuss(Node next){
        this.discuss = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", next=" + next +
                '}';
    }
}
