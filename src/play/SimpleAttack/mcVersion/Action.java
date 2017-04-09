package play.SimpleAttack.mcVersion;

/**
 * Created by jieluo on 2017-04-07.
 */
public class Action {
    String action;
    Point point;
    Hero hero;
    Hero enemy;
    public Action(String action, Point point, Hero hero, Hero enemy){
        this.action = action;
        this.point = point;
        this.hero = hero;
        this.enemy = enemy;
    }
}
