package play.poker.mcVersion;

/**
 * Created by jieluo on 2017-03-30.
 */
public class Player {
    private String name;
    public String[] deckOnHand;
    public Player next;

    public Player(String name, int handCardsNumber){
        this.name = name;
        deckOnHand = new String[handCardsNumber];
        next = null;
    }

    public void drawCard(String card){
        if(deckOnHand[0] == null){
            deckOnHand[0] = card;
        }else {
            sortCards(card);
        }
    }

    private void sortCards(String newCard){
        int num = Integer.valueOf(newCard.substring(0, newCard.length()-1));
        for(int i=0;i<deckOnHand.length;i++){
            if(deckOnHand[i] != null){
                int n = Integer.valueOf(deckOnHand[i].substring(0, deckOnHand[i].length()-1));
                if(n <= num){
                    continue;
                }else{
                    String oldTemp=newCard;
                    while(i<deckOnHand.length && deckOnHand[i] != null){
                        String newTemp = deckOnHand[i];
                        deckOnHand[i] = oldTemp;
                        oldTemp = newTemp;
                        i++;
                    }
                    deckOnHand[i] = oldTemp;
                    break;
                }
            }else{
                deckOnHand[i] = newCard;
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getDeckOnHand() {
        return deckOnHand;
    }

    public void setDeckOnHand(String[] deckOnHand) {
        this.deckOnHand = deckOnHand;
    }
}
