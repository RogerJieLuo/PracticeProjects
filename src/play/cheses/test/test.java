package play.cheses.test;

/**
 * Created by jieluo on 2017-04-15.
 */
public class test {
    static int[][] arr = new int[8][8];
    public static void main(String[] args) {
        arr[0][0] = 1;
        arr[0][4] = 1;
        arr[0][7] = 1;

//        arr[2][2] = -1;
//        arr[4][4] = -1;
//        arr[1][2] = -1;

//        System.out.println(check(0, 4));
//        System.out.println(checkrook(0, 4));
        System.out.println(checkknight(0,4));
    }
    void print(){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                System.out.printf("%4s ", arr[i][j]);
            }
            System.out.println();
        }
    }
    static boolean check(int tx, int ty){
        boolean inCheck = false;
        int x = tx;
        int y = ty;
        // left top
        x -= 1;
        y-= 1;
        while( x>= 0 && y >= 0){
            if(arr[x][y] !=0  ) {
                if (arr[x][y] != 1){
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
            x--;
            y--;
        }
        // right top
        x = tx;
        y = ty;
        x-=1;
        y+=1;
        while( x >= 0 && y< 8){
            if(arr[x][y] !=0  ) {
                if (arr[x][y] != 1){
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
            x--;
            y++;
        }
        // left down
        x = tx;
        y = ty;
        x+=1;
        y-=1;
        while( x < 8 && y >= 0){
            if(arr[x][y] !=0  ) {
                if (arr[x][y] != 1){
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
            x++;
            y--;
        }
        // right down
        x = tx;
        y = ty;
        x+=1;
        y+=1;
        while( x < 8 && y < 8){
            if(arr[x][y] !=0  ) {
                if (arr[x][y] != 1){
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
            x++;
            y++;
        }
        return inCheck;
    }

    static boolean checkrook(int tx, int ty){
        int x = tx;
        int y =ty;
        boolean inCheck  = false;
        for(int i =x-1; i>= 0; i--){
            if(arr[i][y] != 0) {
                if (arr[i][y] != 1) {
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
        }
        //left
        for(int i = y-1; i>= 0; i--){
            if(arr[x][i] !=0  ) {
                if (arr[x][i] != 1){
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
        }
        //down
        for(int i =x+1; i< 8; i++){
            if(arr[i][y] != 0) {
                if (arr[i][y] != 1) {
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
        }
        //bottom
        for(int i = y+1; i< 8; i++){
            if(arr[x][i] !=0  ) {
                if (arr[x][i] != 1){
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
        }

        return inCheck;
    }

    static boolean checkknight(int tx, int ty){
        int x = tx;
        int y = ty;

        // actually only 4 points for each side
        if(x == 0){ // white side
            // x+2, y+1     x+1, y+2
            // x+2, y-1     x+1, y-2
            if( arr[x+2][y+1] == -1
                    || arr[x+1][y+2] == -1
                    || arr[x+2][y-1] == -1
                    || arr[x+1][y-2] == -1
                    )
                return true;
        }else{
            // x-2, y-1     x-1, y-2
            // x-2, y+1     x-1, y+2
            if( arr[x-2][y-1] == 1
                    || arr[x-1][y-2] == 1
                    || arr[x-2][y+1]== 1
                    || arr[x-1][y+2] == 1
                    )
                return true;
        }

        return false;
    }
}
