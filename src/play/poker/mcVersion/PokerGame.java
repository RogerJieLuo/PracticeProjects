package play.poker.mcVersion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by jieluo on 2017-03-30.
 */
public class PokerGame {
    private String players[] = {"P1","P2","P3","P4"};
    private Player curPlayer;
    private boolean end = true;

    private boolean newRound = false;
    private Stack<PlayedCard> stack = new Stack<>();
    private HashMap<String, String[]> map; // store the poke of each player

    Cards p = new Cards();

    public static void main(String[] args) {
        PokerGame pg = new PokerGame();
        pg.newGame();
        pg.start();

    }
    private void start(){
        System.out.println("Game Start!");
        while (!end){
            System.out.println("It's "+curPlayer.getName()+" turn:");
            // player take turn to play
            Scanner scanner = new Scanner(System.in);
            System.out.println(curPlayer.deckOnHand.toString());
            String s =scanner.nextLine();
            boolean nextTurn = play(curPlayer, s);  // assume the input format is the 0-1-2-3-4
            while(!nextTurn){
                System.out.println(curPlayer.getClass()+", Your input is not valid, please input again:");
                s = scanner.next();
                nextTurn = play(curPlayer, s);
            }
            if(end){
                System.out.println(curPlayer.getName() + " Won!");
            }
            curPlayer = curPlayer.next;
        }

    }

    private boolean play(Player player, String playcard){
        if (playcard.equals("pass") ) {
            // if the player start a new round, can't skip it.
            if(player.getName().equals(stack.peek().player))
                return false;

            return true;
        }
        if(stack.isEmpty() || player.getName().equals(stack.peek().player))
            newRound = true;

        String[] cards = curPlayer.deckOnHand;
        String[] oriMoved = playcard.split("-");
        // check if the cards are in hand
        boolean exist;
        for(int i=0;i<oriMoved.length;i++){
            exist = false;
            for(int j=0;j<cards.length;j++){
                if(oriMoved[i].equals(cards[j])) {
                    exist = true;
                    break;
                }
            }
            if(!exist) {
                System.out.println("Program info: " + oriMoved[i] + " not exist in the hand cards.");
                return false;
            }
        }
        String[] moved = new String[oriMoved.length];
        for(int i=0;i<oriMoved.length;i++){
            moved[i] = oriMoved[i].substring(0, oriMoved[i].length()-1);
        }
        // in case input at wrong order
        // sort the moved here
        Arrays.sort(moved);


        // convert string array to int array
        int[] arr = new int[moved.length];
        for(int i =0;i<arr.length;i++){
            arr[i] = Integer.parseInt(moved[i]);
        }

        // get the type of playcard
        TypeValue currentTV =p.checkType(arr);
        if(currentTV == null)
            return false;
        //TypeValue currentTV = getTypeValue(type, arr);
        PlayedCard currentPlayCard = new PlayedCard();
        currentPlayCard.player = player.getName();
        currentPlayCard.tv = currentTV;
        if(newRound){
            //store the played card to stack
            stack.push(currentPlayCard);
            newRound = false;
        }else{
            // check the top of the stack
            PlayedCard previousPlayCard = stack.peek();
            TypeValue previousTV = previousPlayCard.tv;
            if(p.greaterPlay(currentTV, previousTV)){
                //store the played card to stack
                stack.push(currentPlayCard);
            }else{
                System.out.println("Program info: your played is not greater than previous one.");
                return false;
            }
        }

        // remove the played cards from hand deck, which simply replace the data with null
        int deletedCount = 0;
        for(int i=0; i< cards.length;i++){
            if(cards[i].equals(oriMoved[deletedCount])){
                cards[i] = null;
                deletedCount++;
            }
            if(deletedCount>oriMoved.length-1)
                break;
        }
        // move the null to the back of the array
        int movecount =0;
        for(int i=0;i<cards.length;i++){
            if(cards[i] == null) {
                movecount++;
            }else {
                cards[i - movecount] = cards[i];
                if(movecount > 0)
                    cards[i] = null;
            }
        }
        if(cards[0] == null)
            end = true;

        return true;
    }



    private void newGame(){
        end = false;
        newRound = true;
        // set player order
        setOrder();
        // draw cards to players
        draw();
        System.out.println();
        for(int i=0;i<players.length;i++){
            System.out.println(Arrays.toString(curPlayer.deckOnHand));
            curPlayer=curPlayer.next;
        }
    }

    private void setOrder(){
        int handCardsNumber = p.deckSize / players.length;
        curPlayer = new Player(players[0], handCardsNumber);
        Player temp = curPlayer;
        for(int i=1;i<players.length;i++){
            Player p = new Player(players[i], handCardsNumber);
            curPlayer.next = p;
            curPlayer = curPlayer.next;
        }
        curPlayer.next = temp;
        curPlayer = curPlayer.next;
    }

    private void draw(){
        map = new HashMap<>();
        for(int i=0;i<p.deck.length;i++){
            curPlayer.drawCard(p.deck[i]);
            curPlayer = curPlayer.next;
        }
    }




}
