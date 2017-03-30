package play.poker.mcVersion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by jieluo on 2017-03-30.
 */
public class Cards {
    public int deckSize = 52;
    public String[] deck = new String[deckSize];
    public String[] types = {"a","b","c","d"}; // heart, spade, diamond, club
    public int nums = 13; // 1,2 ... 10, J, Q, K


    public Cards(){
        // prepare the cards
        int count = 0;
        for(int i=1;i<=nums;i++){
            for(int j=0;j<types.length;j++){
                deck[count] = i + types[j];
                count++;
            }
        }
        shuffle();
    }

    private void shuffle(){
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        //rand.setSeed();

        for(int i=0;i<deckSize;i++){
            int index = rand.nextInt(deckSize);
            String temp = deck[i];
            deck[i] = deck[index];
            deck[index] = temp;
        }
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
        }else if(playcards.length == 2 && playcards[0] == playcards[1]){
            value = playcards[0];
            number = playcards.length / 2;
            type = "double";
        }else if(playcards.length == 3){ // only it's the last 3 same card and it's count as triple

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
            }

        }
        tv.type = type;
        tv.value = value;
        tv.number = number;
        return tv;
    }

    public boolean greaterPlay(TypeValue currernt, TypeValue previous){
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


}
