package play.SimpleAttack.mcVersion;

import java.util.Scanner;

/**
 * Created by jieluo on 2017-04-07.
 */
public class Hero{
    String belong;
    String name;
    int attack;
    int defence;
    int health;
    String action;
    Hero next;
    int moveStep = 3;
    int attackRange = 2;
    Point position = new Point(0,0);

    Board board = new Board();

    public Hero(String belong, String name, int attack, int defence, int health){
        this.belong = belong;
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.health = health;
    }

    void setHealth(int health){
        if(health<0) {
            this.health = 0;
        }
    }

    void attackEnemy(Hero enemy){
        enemy.setHealth(enemy.health -= this.attack);
    }

    boolean move(Point target){ // move, 2,2
        this.position = target;
        return true;
    }
    void defence(){
        System.out.println("Your defence UP!");
        defence += 10;
//        heroesOnBoard.put(name, this);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "belong='" + belong + '\'' +
                ", name='" + name + '\'' +
                ", attack=" + attack +
                ", defence=" + defence +
                ", health=" + health +
                ", position='" + position.x + " " +position.y + '\'' +
                '}';
    }


}
