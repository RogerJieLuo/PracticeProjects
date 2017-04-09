package play.SimpleAttack.mcVersion;

import java.util.HashMap;

/**
 * Created by jieluo on 2017-04-07.
 */
public class AttackGame {
    private int line = 5;

    private Hero[][] board = new Hero[line][line];
    private Player curPlayer;
    private boolean end = false;
    private HashMap<String, Hero> heroList = new HashMap<>(); // temp be the heroes played on board
    private HashMap<String, Hero> heroesOnBoard = new HashMap<>();
    private Player winner;

    public void newGame(){
        initiateHeroes();
        initiatePlayers();
        printBoard();
        end = false;
        start();
    }

    public void start(){
        chooseHeroes();
        // after choose heroes, place them at initiated place
        initiateHeroesPlace();
        printBoard();
        printOnboardHeroes();
        boolean win = false;
        while(!end){
            boolean endTurn = false;
            System.out.println(curPlayer.name + ", it's your turn");
            while (!endTurn) {
                String action = curPlayer.chooseAction();
                if (action.equals("move")) {
                    Hero warrior = curPlayer.pointHero();
                    Point target = curPlayer.getTarget();
                    if(checkTargetValid(warrior, target)){
                        curPlayer.move(warrior, target);
                        endTurn = true;
                    }
                } else if (action.equals("attack")) {
                    Hero warrior = curPlayer.pointHero();
                    Hero enemy = curPlayer.getEnemy(heroesOnBoard);
                    if(checkEnemyValid(warrior, enemy)) {
                        curPlayer.attack(warrior, enemy);
                        // check the death
                        if(checkDeath(curPlayer.next, enemy)){
                            end = true;
                            winner = curPlayer;
                        }
                        endTurn = true;
                    }
                    printOnboardHeroes();
                } else if (action.equals("defence")) {
                    Hero warrior = curPlayer.pointHero();
                    curPlayer.defence(warrior);
                    endTurn = true;
                }
                if(!endTurn)
                    System.out.println("Choose your action: ");
            }
            printBoard();
            curPlayer = curPlayer.next;
        }
        System.out.println("Congrats! "+winner.name +" won!");
    }

    public void chooseHeroes(){
        // player choose heroes
        System.out.println(curPlayer.name+", please choose your heroes: "+heroList);
        curPlayer.chooseHero(heroList);
        storeOnboardHeroes(curPlayer);

        curPlayer = curPlayer.next;
        System.out.println(curPlayer.name+", please choose your heroes: "+heroList);
        curPlayer.chooseHero(heroList);
        storeOnboardHeroes(curPlayer);

        curPlayer = curPlayer.next;
    }

    /**
     * Validate the move target:
     *      the target is free
     *      the target is in the ability of the hero
     *
     * @param hero
     * @param target
     * @return
     */
    public boolean checkTargetValid(Hero hero, Point target){
        if(Math.abs(target.x - hero.position.x)+Math.abs(target.y - hero.position.y) > hero.moveStep){
            System.out.println("The target out of your ability.");
            return false;
        }else if(board[target.x][target.y] != null){
            System.out.println("The target is occupied by others.");
            return false;
        }else {
            // update the board info
            board[hero.position.x][hero.position.y] = null;
            board[target.x][target.y] = hero;
            return true;
        }
    }

    /**
     * validate the enemy:
     *      in the attack range
     * @param hero
     * @param enemy
     * @return
     */
    public boolean checkEnemyValid(Hero hero, Hero enemy){
        // check if the enemy is in the range of attack
        if(Math.abs(hero.position.x -enemy.position.x)+Math.abs(hero.position.y - enemy.position.y)
                > hero.attackRange ) {
            System.out.println("The enemy is out of your range!");
            return false;
        }
        return true;
    }

    public boolean checkDeath(Player enemy, Hero hero){
        boolean endGame = false;
        if(hero.health <=0){
            board[hero.position.x][hero.position.y] = null;
            hero = null;
            endGame=checkLost(enemy);
        }
        return endGame;
    }

    /**
     * check if the player that being attacked fail the game
     * @param player
     * @return
     */
    public boolean checkLost(Player player){
        boolean lost = true;
        for (Hero hero: player.heroesOwn.values()) {
            if(hero.health>0)
                lost = false;
        }
        return lost;
    }

    /**
     * store the players hero info on main
     * @param player
     */
    public void storeOnboardHeroes(Player player){
        for (Hero hero : player.heroesOwn.values()) {
            heroesOnBoard.put(hero.name, hero);
        }
    }


    void initiateHeroes(){
        heroList.put("H1", new Hero(null, "H1", 100, 50, 150));
        heroList.put("H2", new Hero(null, "H2", 150, 30, 100));
        heroList.put("H3", new Hero(null, "H3", 80, 70, 200));
        heroList.put("H4", new Hero(null, "H4", 100, 100, 100));
    }
    public void initiatePlayers(){
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        p1.next = p2;
        p2.next = p1;
        curPlayer = p1;
    }

    public void initiateHeroesPlace(){
        int x = 0;
        int y = 0;
        while (!curPlayer.initiated){
            for (Hero hero: curPlayer.heroesOwn.values()) {
                board[x++][y++] = hero;
            }
            curPlayer.initiated = true;
            curPlayer = curPlayer.next;
        }
    }

    // print functions
    void printBoard(){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if(board[i][j] == null){
                    System.out.printf("%3s ", "_");
                }else {
                    Hero hero = board[i][j];
                    hero.move(new Point(i,j));
                    System.out.printf("%3s ",hero.name);

                }
            }
            System.out.println();
        }

    }
    void printOnboardHeroes(){
        for (Hero hero: heroesOnBoard.values()) {
            System.out.println(hero.toString());
        }
    }

    public static void main(String[] args) {
        new AttackGame().newGame();
    }
}
