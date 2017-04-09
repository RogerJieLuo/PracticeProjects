package play.SimpleAttack.mcVersion;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by jieluo on 2017-04-07.
 */
public class Player {
    String name ;
    Player next;
    HashMap<String, Hero> heroesOwn;
    boolean initiated = false;

    public Player(String name){
        this.name = name;
        heroesOwn = new HashMap<>();
    }

    public void chooseHero(HashMap<String, Hero> allHeroes){
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        // input format, H1, H2
        String[] choices = s.split(",");
        for (int i=0;i<choices.length;i++) {
            String heroName = choices[i].trim();
            Hero hero = allHeroes.get(heroName);
            Hero owned = new Hero(this.name, this.name+"-"+hero.name, hero.attack, hero.defence, hero.health);
            heroesOwn.put(owned.name, owned);
        }
    }
    public String chooseAction(){
        Scanner in = new Scanner(System.in);
        String a = in.nextLine();
        return a;
    }

    public void move(Hero hero, Point target){
        hero.move(target);
    }

    public void attack(Hero hero, Hero enemy){
        hero.attackEnemy(enemy);
    }

    Point getTarget(){
        Scanner in = new Scanner(System.in);
        System.out.println("Target Point: ");
        String n = in.nextLine();
        String[] strInput = n.split(",");
        return new Point(Integer.valueOf(strInput[1].trim()), Integer.valueOf(strInput[0].trim()));

    }

    Hero pointHero(){
        Hero hero = null;
        Scanner in = new Scanner(System.in);
        while(hero == null) {
            System.out.println("Choose your Hero: ");
            String n = in.nextLine();
            hero = heroesOwn.get(n);
        }
        return hero;
    }

    Hero getEnemy(HashMap<String, Hero> heroesOnboard){
        Hero enemy = null;
        Scanner in = new Scanner(System.in);
        while(enemy == null) {
            System.out.println("Choose your enemy: ");
            String n = in.nextLine();
            enemy = heroesOnboard.get(n);
            if(enemy!=null){
                // check it's not your ally
                if(enemy.belong.equals(this.name)){
                    enemy = null;
                    System.out.println("It's your ally");
                }
            }
        }
        return enemy;
    }
    void defence(Hero hero){
        hero.defence();
    }

}
