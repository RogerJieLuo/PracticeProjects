package play.poker;

import java.util.*;

/**
 * Created by jieluo on 2017-03-25.
 */
public class Poker {
    private int deckSize = 52;
    public String[] deck = new String[deckSize];
    private String[] types = {"a","b","c","d"}; // heart, spade, diamond, club
    private int nums = 13; // 1,2 ... 10, J, Q, K
    private String players[] = {"P1","P2","P3","P4"};
    private Node curPlayer;
    private boolean end = true;

    private boolean newRound = false;
    private Stack<PlayedCard> stack = new Stack<>();

    private HashMap<String, String[]> map; // store the poke of each player

    public Poker(){
        newGame();
        // start playing
        start();
    }

    /**
     * switch the value of current index with a value of random index
     * which looks like a shuffle
     */
    private void shuffle(){
        Random rand = new Random();
        //rand.setSeed(System.currentTimeMillis());
        //rand.setSeed();

        for(int i=0;i<deckSize;i++){
            int index = rand.nextInt(deckSize);
            String temp = deck[i];
            deck[i] = deck[index];
            deck[index] = temp;
        }
    }

    private void draw(){
        map = new HashMap<>();
        for(int i=0;i<deck.length;i++){
            String player = curPlayer.val;
            int index = i / players.length;
            if(map.containsKey(player)){
                String[] cards = map.get(player);
                //cards[index] = deck[i];
                sortCards(cards, deck[i]); // add new cards and sort them
                map.put(player, cards);
            }else{
                String[] cards = new String[deckSize/players.length];
                cards[index] = deck[i];
                map.put(player, cards);
            }
            curPlayer = curPlayer.next;
        }
    }

    /**
     *
     * @param cards     the exist cards on hands
     * @param newCard   new card
     *
     * when add a new card, insert it as order
     */
    private void sortCards(String[] cards, String newCard){
        int num = Integer.valueOf(newCard.substring(0, newCard.length()-1));
        for(int i=0;i<cards.length;i++){
            if(cards[i] != null){
                int n = Integer.valueOf(cards[i].substring(0, cards[i].length()-1));
                if(n <= num){
                    continue;
                }else{
                    String oldTemp=newCard;
                    while(i<cards.length && cards[i] != null){
                        String newTemp = cards[i];
                        cards[i] = oldTemp;
                        oldTemp = newTemp;
                        i++;
                    }
                    cards[i] = oldTemp;
                    break;
                }
            }else{
                cards[i] = newCard;
                break;
            }
        }
    }

    /**
     * make a circle link of the players, so they can take turn
     */
    private void setOrder(){
        curPlayer = new Node(players[0]);
        Node temp = curPlayer;
        for(int i=1;i<players.length;i++){
            Node n = new Node(players[i]);
            curPlayer.next = n;
            curPlayer = curPlayer.next;
        }
        curPlayer.next = temp;
        curPlayer = curPlayer.next;
    }

    private void newGame(){
        end = false;
        newRound = true;
        // prepare the cards
        int count = 0;
        for(int i=1;i<=nums;i++){
            for(int j=0;j<types.length;j++){
                deck[count] = i + types[j];
                count++;
            }
        }
        shuffle();
        // set player order
        setOrder();
        // draw cards to players
        draw();
        System.out.println();
        for(String[] strings: map.values()){
            System.out.println(Arrays.toString(strings));
        }

    }

    /**
     * rule:
     *      1. types: single card, (continuous) double card, (continuous) triple card + one single card,
     *              5+ continuous card, 4 cards as bomb
     *      2. only the new round, the first play in the new round can choose type.
     *      3. in each round, next player must play the same types and greater than the previous.
     *
     * player logic:
     *      1. get rid of the small cards
     *      2. if it's a continuous type
     *      3.
     */
    private void start(){
        System.out.println("Game Start!");
        while (!end){
            System.out.println("It's "+curPlayer.val+" turn:");
            // player take turn to play
            Scanner scanner = new Scanner(System.in);
            System.out.println(map.get(curPlayer.val).toString());
            String s =scanner.nextLine();
            boolean nextTurn = play(curPlayer.val, s);  // assume the input format is the 0-1-2-3-4
            while(!nextTurn){
                System.out.println(curPlayer.val+", Your input is not valid, please input again:");
                s = scanner.next();
                nextTurn = play(curPlayer.val, s);
            }
            if(end){
                System.out.println(curPlayer.val + " Won!");
            }
            curPlayer = curPlayer.next;
        }

    }

    private boolean play(String player, String playcard){
        if (playcard.equals("pass"))
            return true;

        if(stack.isEmpty() || player.equals(stack.peek().player))
            newRound = true;

        String[] cards = map.get(player);
        String[] oriMoved = playcard.split("-");
        // check if the cards are in hand
        boolean exist = false;
        for(int i=0;i<oriMoved.length;i++){
            for(int j=0;j<cards.length;j++){
                if(oriMoved[i].equals(cards[j]))
                    exist = true;
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
        TypeValue currentTV =checkType(arr);
        if(currentTV == null)
            return false;
        //TypeValue currentTV = getTypeValue(type, arr);
        PlayedCard currentPlayCard = new PlayedCard();
        currentPlayCard.player = player;
        currentPlayCard.tv = currentTV;
        if(newRound){
            //store the played card to stack
            stack.push(currentPlayCard);
            newRound = false;
        }else{
            // check the top of the stack
            PlayedCard previousPlayCard = stack.peek();
            TypeValue previousTV = previousPlayCard.tv;
            if(greaterPlay(currentTV, previousTV)){
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

    public TypeValue checkType(int[] playcards){
        int value = 0;
        int number = 0;
        String type = "";
        TypeValue tv = new TypeValue();

        if(playcards.length == 1){
            value = playcards[0];
            number = 1;
            type = "single";
//            return "single";
        }else if(playcards.length == 2 && playcards[0] == playcards[1]){
            value = playcards[0];
            number = playcards.length / 2;
            type = "double";
//            return "double";
        }else if(playcards.length == 3){

        }else if(playcards.length > 3 ){
            HashMap<Integer, Integer> map = new HashMap<>();
            for(int i=0;i<playcards.length;i++){
                if(map.containsKey(playcards[i])){
                    int val = map.get(playcards[i]);
                    val++;
                    map.put(playcards[i], val);
                }else{
                    map.put(playcards[i], 1);
                }
            }

            // get format
            int[] arr = new int[map.size()];
            int count = 0;
            int max = 0;
            for(int val : map.values()){
                arr[count++] = val;
                if(val > max)
                    max = val;
            }
            if (max == 4 && playcards.length > 4)
                max = 3;
            if(max == 4 && playcards.length == 4){
                value = playcards[0];
                number = 4;
                type = "bomb";
//                return "bomb";
            }
            else if(max == 3){
                int countTriple = 0;
                for(int i = 0;i<arr.length;i++){
                    if(arr[i] >= 3) // >= is to consider about the 4 same cards, but play as 3 cards
                        countTriple++;
                }
                // check the number format matched
                if(playcards.length /countTriple == 4 && playcards.length % countTriple == 0) {
                    // check the triple number is continuous
                    int[] tripleNumber = new int[countTriple];
                    int c = 0;
                    for(int key : map.keySet()){
                        if (map.get(key) >= 3){
                            tripleNumber[c] = key;
                            c++;
                        }
                    }
                    Arrays.sort(tripleNumber);
                    for(int i=1;i<tripleNumber.length;i++){
                        if(tripleNumber[i] - tripleNumber[i-1] != 1 || (tripleNumber.length > 1 && tripleNumber[i] == 13))
                            return null;
                    }
                    value = tripleNumber[0];
                    number = playcards.length / 4;
                    type = "triple";
//                    return "triple";
                }
            }
            else if(max == 2 && playcards.length % 2 == 0){
                int countDouble = 0;
                for(int i=0;i<arr.length;i++){
                    if(arr[i] == 2)
                        countDouble++;
                }
                int[] doubleNumber = new int[countDouble];
                int c = 0;
                for(int key : map.keySet()){
                    if (map.get(key) == 2){
                        doubleNumber[c] = key;
                        c++;
                    }
                }
                Arrays.sort(doubleNumber);
                for(int i=1;i<doubleNumber.length;i++){
                    if(doubleNumber[i] - doubleNumber[i-1] != 1 || (doubleNumber.length > 1 && doubleNumber[i] == 13))
                        return null;
                }
                value = doubleNumber[0];
                number = playcards.length / 2;
                type = "double";
//                return "double";
            }else if(max == 1 && playcards.length >= 5){
                int[] singleNumber = new int[playcards.length];
                int c = 0;
                for(int key : map.keySet()){
                    singleNumber[c] = key;
                    c++;
                }
                Arrays.sort(singleNumber);
                for(int i=1;i<singleNumber.length;i++){
                    if(singleNumber[i] - singleNumber[i-1] != 1 ||  singleNumber[i] == 13)
                        return null;
                }
                value = singleNumber[0];
                number = playcards.length;
                type = "continuous";
//                return "continuous";
            }

        }
        tv.type = type;
        tv.value = value;
        tv.number = number;
        return tv;
//        return "error";
    }

    /**
     *
     * @param type
     * @param playcards
     * @return
     *
     * get the value of type for compare,
     *  1. single: card value (3: 3, 1)
     *  2. double: card value, number of card number (33: 3, 1; 3344: 3, 2)
     *  3. triple: first triple card value, number of card number (3334: 3, 1; 35556667: 5, 2)
     *  4. continuous: first card value, card number ( 34567: 3, 5; 456789: 4,6)
     *  5. bomb: card value (4444: 4; 6666: 6)
     */
    private TypeValue getTypeValue(String type, int[] playcards){
        int value = 0;
        int number = 0;
        TypeValue tv = new TypeValue();

        switch (type){
            case "single":
                value = playcards[0];
                number = 1;
                break;
            case "double":
                value = playcards[0];
                number = playcards.length / 2;
            case "triple":
                // value is the first triple

                // number is the triple number
                number = playcards.length / 4;
            case "continuous":
                value = playcards[0];
                number = playcards.length;
            case "bomb":
                value = playcards[0];
                number = 4;
            default:
                break;
        }
        tv.type = type;
        tv.value = value;
        tv.number = number;
        return tv;
    }

    private boolean greaterPlay(TypeValue currernt, TypeValue previous){
        currernt.print();
        if(currernt.type == "bomb" && previous.type != "bomb")
            return true;
        if(!currernt.type.equals(previous.type) || currernt.number != previous.number) {
            System.out.println("Program info: your number is not greater than previous one");
            return false;
        }
        if(currernt.value > previous.value)
            return true;
        return false;
    }

    /**
     * created for the circle link
     */
    class Node{
        String val;
        Node next;
        public Node(String val){
            this.val = val;
            next = null;
        }
    }

    class TypeValue{
        String type;
        int value ;
        int number;

        private void print(){
            System.out.println("Type: "+type+", value: "+ value + ", number: "+number);
        }
    }

    class PlayedCard{
        TypeValue tv;
        String player;
    }


    public static void main(String[] args) {
        Poker poker= new Poker();
    }
}
