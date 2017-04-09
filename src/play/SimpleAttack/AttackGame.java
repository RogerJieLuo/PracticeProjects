//package play.SimpleAttack;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Scanner;
//
///**
// * Created by jieluo on 2017-04-06.
// */
//public class AttackGame {
//    private int line = 5;
//
//    private Hero[][] board = new Hero[line][line];
//    private HashMap<String, Hero> heroesOnBoard = new HashMap<String, Hero>();
//    private String[] players = {"B", "W"};
//    private Hero curPlayer;
//    private boolean end = false;
//
//    public AttackGame(){
//
//    }
//
//    public void initiateBoard(){
////        for(int i=0;i<line;i++){
////            for (int j =0;j<line;j++){
////                board[i][j] = 0;
////            }
////        }
//    }
//
//    public void newGame(){
//        initiateBoard();
//        placeHero();
//        printBoard();
//        end = false;
//        start();
//    }
//
//    public void start(){
//        while(!end){
//            System.out.println(curPlayer.name+", it's your turn");
//            String action = curPlayer.chooseAction();
//            if(action.equals("move")){
//                curPlayer.move();
//            }else if(action.equals("attack")){
//                curPlayer.attackEnemy();
//                // check if any hero dead
//            }else if(action.equals("defence")){
//                curPlayer.defence();
//            }
//            // if one hero's health becomes 0, then end
//            printBoard();
//            curPlayer = curPlayer.next;
//        }
//    }
//
//    public void checkDeath(){
//
//    }
//
//    public void placeHero(){
//        Hero h1 = new Hero("B","B",1000, 50, 150);
//        Hero h2 = new Hero("W", "W", 80, 60, 200);
//        h1.next = h2;
//        h2.next = h1;
//        curPlayer = h1;
//        heroesOnBoard.put(h1.name, h1);
//        heroesOnBoard.put(h2.name, h2);
//        h1.position.x = 0;
//        h1.position.y = 0;
//        h2.position.x = 0;
//        h2.position.y = 1;
//        board[0][0] = h1;
//        board[0][1] = h2;
//    }
//
//    void printBoard(){
//        for(int i=0;i<board.length;i++){
//            for(int j=0;j<board[i].length;j++){
//                if(board[i][j] == null){
//                    System.out.printf("%3s ", "_");
//                }else {
//                    System.out.printf("%3s ",board[i][j].name);
//                }
//            }
//            System.out.println();
//        }
//        System.out.println();
//        for (Hero h: heroesOnBoard.values()) {
//            System.out.println(h.toString());
//        }
//
//    }
//
//    class Hero{
//        String belong;
//        String name;
//        int attack;
//        int defence;
//        int health;
//        String action;
//        Hero next;
//        int moveStep = 3;
//        int attackRange = 2;
//        Point position = new Point(0,0);
//
//        public Hero(String belong, String name, int attack, int defence, int health){
//            this.belong = belong;
//            this.name = name;
//            this.attack = attack;
//            this.defence = defence;
//            this.health = health;
//        }
//
//        void setHealth(int health){
//            if(health<0) {
//                this.health = 0;
//            }
//        }
//
//        String chooseAction(){
//            // move, attack, defence;
//            Scanner in = new Scanner(System.in);
//            String action = in.nextLine();
//            return action;
//        }
//
//        void attackEnemy(){
//            // attack one enemy per time, what if attack multi heroes at one time.
//
//            System.out.println("Choose the enemy you want to attack:");
//            boolean validAttack = false;
//            Scanner in = new Scanner(System.in);
//            while (!validAttack) {
//                String enemyName = in.nextLine();
//
//                Hero enemy = heroesOnBoard.get(enemyName);
//                // check the attack range
//                if (Math.abs(enemy.position.x - this.position.x) + Math.abs(enemy.position.y - this.position.y)
//                        > attackRange) {
//                    System.out.println("Your enemy is out of your attack range!");
//                }else {
//                    enemy.setHealth(enemy.health -= attack);
//                    // death, removed from board
//                    if(enemy.health == 0) {
//                        board[enemy.position.x][enemy.position.y] = null;
//                        enemy = null;
//                    }
//                    heroesOnBoard.remove(enemyName);
//                    validAttack = true;
//                }
//            }
//        }
//
//        void move(){ // move, 2,2
//            boolean validTarget = false;
//            while (!validTarget) {
//                System.out.println("Please put the target you want to move:");
//                Scanner in = new Scanner(System.in);
//                String s = in.nextLine();
//                String[] strInput = s.split(",");
//                // need to calculate the step to move to the target, will discuss later
//
//                Point target = new Point(Integer.valueOf(strInput[1].trim()), Integer.valueOf(strInput[0].trim()));
//                if(Math.abs(target.x - position.x)+Math.abs(target.y - position.y) > moveStep){
//                    System.out.println("The target out of your ability.");
//                }else if(board[target.x][target.y] != null){
//                    System.out.println("The target is occupied by others.");
//                }else {
//                    board[position.x][position.y] = null;
//                    board[target.x][target.y] = this;
//                    this.position = target;
//                    validTarget = true;
//                }
//            }
//        }
//        void defence(){
//            System.out.println("Your defence UP!");
//            defence += 10;
//            heroesOnBoard.put(name, this);
//        }
//
//        @Override
//        public String toString() {
//            return "Hero{" +
//                    "belong='" + belong + '\'' +
//                    ", name='" + name + '\'' +
//                    ", attack=" + attack +
//                    ", defence=" + defence +
//                    ", health=" + health +
//                    ", action='" + action + '\'' +
//                    '}';
//        }
//    }
//
//    class Point{
//        int x;
//        int y;
//
//        public Point(int x, int y){
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//    public static void main(String[] args) {
//        new AttackGame().newGame();
//    }
//
//}
