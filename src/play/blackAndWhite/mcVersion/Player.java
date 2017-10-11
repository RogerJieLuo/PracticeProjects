package play.blackAndWhite.mcVersion;

import java.util.Scanner;

/**
 * Created by jieluo on 2017-04-05.
 */
class Player{
    public String name;
    public int stoneColor; // 1 for black, 2 for white
    public Player next;
    /**
     * action includes: 0: resign, 1: put, 2: pass
     */

    public Player(String name, int stoneColor){
        this.name = name;
        this.stoneColor = stoneColor;
    }

    public Action put(){
        String action = "play";
        Point point = null;
        Scanner in = new Scanner(System.in);
        String s = in.nextLine(); // assume input format is 0,1   2,2
        if (s.equals("resign")) {
            action = "resign";
        }
        else if(s.equals("pass")){
            action = "pass";
        }
        else {
            String[] strInput = s.split(",");

            point = new Point(Integer.valueOf(strInput[1].trim()), Integer.valueOf(strInput[0].trim()));
            if (point.x >= 0 && point.x < 19 && point.y >= 0 && point.y < 19) {

            } else {
                point = null;
            }
        }
        return new Action(action, point);
    }
}
