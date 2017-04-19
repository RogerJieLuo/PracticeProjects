package play.cheses;

import play.SimpleAttack.mcVersion.Player;
import play.cheses.pieces.King;
import play.cheses.pieces.Pawn;
import play.cheses.pieces.Piece;
import play.cheses.pieces.Rook;
import play.common.Point;

/**
 * Created by jieluo on 2017-04-10.
 */
public class Chess {
    int line = 8;
    Piece[][] board = new Piece[line][line];  // all initiated with 0
    ChessPlayer curPlayer;
    boolean end =false;
    ChessPlayer winner = null;
    boolean isCastling = false;

    void newGame(){
        initiatePlayers();
//        initiateBoard();
        printBoard();

        start();
    }

    void start(){
        while(!end){
            boolean valid =false;
            System.out.println(curPlayer.name+", It's your turn:");
            while (!valid){
                Piece piece = curPlayer.pointPiece();
                printPosition(piece);
                Point target = curPlayer.getTarget();
                if(checkTargetVlid(curPlayer, piece, target))
                    valid = true;
                if(valid) {
                    doMove(piece, target);
                    printBoard();
                }
            }
            if(end)
                winner = curPlayer;
            curPlayer = curPlayer.next;
        }
        System.out.println(winner.name +" Win!");
    }

    void doMove(Piece piece, Point target){
        if(isCastling){
            isCastling = false;
        }else {
            updateBoard(piece, target);
            curPlayer.move(piece, target);
            curPlayer.doPawnPromotion(piece);
        }
    }

    boolean checkTargetVlid(ChessPlayer player, Piece piece, Point target){
        // if the target is on board
        if(!isOnboard(target)) {
            System.out.println("The target is not on the board. ");
            return false;
        }

        // if the target is occupied by ally
        if (whoIsHere(target) == player.color) {
            System.out.println("The target is occupied by your ally. ");
            return false;
        }

        // if the path is valid, Pawn is special
        if(piece.getType().equals("Pawn")){
            if (!piece.checkValidMovePattern(target) && !checkPawnMoveAndCapture(piece, target) ) {
                System.out.println("The piece is not follow the move pattern");
                return false;
            }
        }
        // king special move, castling
        else if(piece.getType().equals("King")){
            if(!piece.checkValidMovePattern(target)){
                // check for possibility of castling
                if(piece.getMoveCount() != 0) {
                    return false;
                }else{
                    Piece p = null;
                    if(target.x == piece.getPosition().x && target.y == piece.getPosition().y - 2) {
                        p = board[piece.getPosition().x][0];
                    }else if(target.x == piece.getPosition().x && target.y == piece.getPosition().y + 2) {
                        p = board[piece.getPosition().x][7];
                    }else {
                        System.out.println("The piece is not follow the move pattern or the castling pattern. ");
                        return false;
                    }
                    if(p!= null && p.getType().equals("Rook")){
                        // castling already check the path clean, return here
                        if(castling((King) piece, (Rook) p))
                            return true;
                        System.out.println("The King is not able to castling, due to the in check situation");
                        return false;
                    }
                }
            }
        }
        else {
            if (!piece.checkValidMovePattern(target)) {
                System.out.println("The piece is not follow the move pattern");
                return false;
            }
        }

        // if any piece get in the way
        boolean valid = checkPathClean(piece, target);
        if(!valid) System.out.println("The path is not clean.");


        return valid;
    }

    boolean checkPathClean(Piece piece, Point target){
        boolean valid = false;
        // if it's a rook
        if(piece.getType().equals("Rook")) {
            valid = checkRookPathClean(piece, target);
        }
        else if (piece.getType().equals("King"))
            valid = true;
        else if(piece.getType().equals("Bishop"))
            valid = checkBishopPathClean(piece, target);
        else if(piece.getType().equals("Queen"))
            valid = checkQueenPathClean(piece, target);
        else if(piece.getType().equals("Knight"))
            valid = true;
        else if(piece.getType().equals("Pawn")){
            return true;
        }
        return valid;
    }

    boolean checkRookPathClean(Piece piece, Point target){
        Point position = piece.getPosition();
        int min=0, max=0;
        if(position.x == target.x){
            min = Math.min(target.y, position.y);
            max = Math.max(target.y, position.y);
            for(int i = min+1;i<max;i++){
                if(whoIsHere(new Point(position.x, i)) != 0)
                    return false;
            }
        }else{
            min = Math.min(target.x, position.x);
            max = Math.max(target.x, position.x);
            for(int i = min+1;i<max;i++){
                if(whoIsHere(new Point(i, position.y)) != 0)
                    return false;
            }
        }
        return true;
    }

    boolean checkBishopPathClean(Piece piece, Point target){
        Point position = piece.getPosition();
        int y = target.y + 1;
        // check left top
        if(target.x < position.x && target.y < position.y){
            for(int i = target.x + 1; i < position.x; i++){
                if(whoIsHere(new Point(i, y++)) != 0)
                    return false;
            }
        }
        // right top
        else if (target.x < position.x && target.y > position.y ){
            y = position.y;
            for(int i= position.x + 1; i<target.x;i++){
                if(whoIsHere(new Point(i, y++)) != 0)
                    return false;
            }
        }
        // left bottom
        else if(target.x > position.x && target.y < position.y){
            for(int i= target.x -1; i < position.x; i--){
                if(whoIsHere(new Point(i, y++)) != 0)
                    return false;
            }
        }
        // right bottom
        else{
            y = position.y;
            for(int i = position.x +1; i< target.x; i++){
                if(whoIsHere(new Point(i, y++)) != 0)
                    return false;
            }
        }
        return true;
    }

    boolean checkQueenPathClean(Piece piece, Point target){
        if(checkRookPathClean(piece, target)) return true;
        if(checkBishopPathClean(piece, target)) return true;
        return false;
    }

    boolean checkPawnMoveAndCapture(Piece piece, Point target ){
        Point position = piece.getPosition();
        // confirm it's moving forward

        // A pawn captures diagonally forward one square to the left or right, must move forward
        if(Math.abs(target.x - position.x)==1 && Math.abs(target.y - position.y) == 1
                && whoIsHere(new Point(target.x, target.y)) != 0
                && whoIsHere(new Point(target.x, target.y)) != piece.getColor()
                )
            return true;

        //Pawn's passant capture. if the enemy pawn is the first move.
        Pawn pawn = (Pawn) board[target.x - piece.getForward()][target.y];
        System.out.println(pawn.getName());
        if(Math.abs(target.x - position.x)==1 && Math.abs(target.y - position.y) == 1
                && pawn.getColor() != 0
                && pawn.getColor() != piece.getColor()
                ){
            if(pawn.getMoveCount() == 1) {
                pawnPassantCapture(pawn);
                return true;
            }
        }

        return false;
    }

    void pawnPassantCapture(Pawn pawn){
        System.out.println("Pawn's special move, passant move, "+ pawn.getName()+" is captured.");
        curPlayer.next.pieces.remove(pawn.getName());
        board[pawn.getPosition().x][pawn.getPosition().y] = null;
        pawn = null;

    }

    boolean castling(King king, Rook rook){
        if(castlingValid(king, rook)) {
            isCastling = true;
            doCastling(king, rook);
            return true;
        }else{
            return false;
        }
    }
    boolean castlingValid(King king, Rook rook){
        if(king.getMoveCount() != 0 || rook.getMoveCount() != 0)
            return false;
        if(isInCheck(king))
            return false;

        int miny = Math.min(rook.getPosition().y, king.getPosition().y);
        int maxy = Math.max(rook.getPosition().y, king.getPosition().y);
        int x = king.getPosition().x;

        // check the path between king and rook is clean
        for(int i = miny + 1; i< maxy; i++) {
            if(whoIsHere(new Point(x, i)) != 0)
                return false;
        }

        // check the path is not in check
        for(int i = miny + 1; i< maxy; i++){
            King temp = new King("test", king.getColor(), new Point(x, i));
            if(isInCheck(temp)) {
                temp = null;
                return false;
            }
            temp = null;
        }

        return true;
    }

    void doCastling(King king, Rook rook){
        int x = king.getPosition().x;
        int ky = king.getPosition().y;
        int ry = rook.getPosition().y;
        if(rook.getPosition().y == 0){
            doMove(king, new Point(x, ky - 2));
            doMove(rook, new Point(x, ry + 3));
        }else{
            doMove(king, new Point(x, ky + 2));
            doMove(rook, new Point(x, ry - 2));
        }
    }

    boolean isInCheck(King king){
        // if in check by rook
        if(checkByRook(king))
            return true;
        else if(checkByBishop(king))
            return true;
        else if(checkByKnight(king))
            return true;
        else if(checkByPawn(king))
            return true;
        return false;
    }

    // in check list
    boolean checkByRook(King king){
        // when reach a point that is not null,
        // check if it's ally
        boolean inCheck = false;
        //up
        int x = king.getPosition().x;
        int y = king.getPosition().y;
        for(int i =x-1; i>= 0; i--){
            if(board[i][y] == null) continue;
            if(!board[i][y].getType().equals("Rook") && !board[i][y].getType().equals("Queen")) continue;
            if(whoIsHere(new Point(i, y)) != 0) {
                if (whoIsHere(new Point(i, y)) != king.getColor()) {
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
            if(board[x][i] == null) continue;
            if(!board[x][i].getType().equals("Rook") && !board[x][i].getType().equals("Queen")) continue;
            if(whoIsHere(new Point(x, i)) !=0  ) {
                if (whoIsHere(new Point(x, i)) != king.getColor()){
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
        }
        //down
        for(int i =x+1; i< line; i++){
            if(board[i][y] == null) continue;
            if(!board[i][y].getType().equals("Rook") && !board[i][y].getType().equals("Queen")) continue;
            if(whoIsHere(new Point(i, y)) != 0) {
                if (whoIsHere(new Point(i, y)) != king.getColor()) {
                    return true;
                }else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
        }
        //bottom
        for(int i = y+1; i< line; i++){
            if(board[x][i] == null) continue;
            if(!board[x][i].getType().equals("Rook") && !board[x][i].getType().equals("Queen")) continue;
            if(whoIsHere(new Point(x, i)) !=0  ) {
                if (whoIsHere(new Point(x, i)) != king.getColor()){
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

    boolean checkByBishop(King king){
        boolean inCheck = false;
        // left top
        int x = king.getPosition().x;
        int y = king.getPosition().y;
        x -= 1;
        y -= 1;
        while( x >= 0 && y >= 0){
            if (whoIsHere(new Point(x, y)) != 0
                    && (board[x][y].getType().equals("Bishop") || board[x][y].getType().equals("Queen"))) {
                if (whoIsHere(new Point(x, y)) != king.getColor()) {
                    return true;
                } else {
                    inCheck = false;
                    break;
                }
            }
            inCheck = false;
            x--;
            y--;
        }
        // right top
        x = king.getPosition().x;
        y = king.getPosition().y;
        x -= 1;
        y += 1;
        while( x >= 0 && y< line){
            if(whoIsHere(new Point(x, y)) !=0
                    && (board[x][y].getType().equals("Bishop") || board[x][y].getType().equals("Queen"))) {
                if (whoIsHere(new Point(x, y)) != king.getColor()){
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
        x = king.getPosition().x;
        y = king.getPosition().y;
        x += 1;
        y -= 1;
        while( x < line && y >= 0){
            if(whoIsHere(new Point(x, y)) !=0
                    && (board[x][y].getType().equals("Bishop") || board[x][y].getType().equals("Queen") )) {
                if (whoIsHere(new Point(x, y)) != king.getColor()){
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
        x = king.getPosition().x;
        y = king.getPosition().y;
        x += 1;
        y += 1;
        while( x < line && y < line){
            if(whoIsHere(new Point(x, y)) !=0
                    && (board[x][y].getType().equals("Bishop") || board[x][y].getType().equals("Queen") )) {
                if (whoIsHere(new Point(x, y)) != king.getColor()){
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

    boolean checkByKnight(King king){
        int x = king.getPosition().x;
        int y = king.getPosition().y;

        // actually only 4 points for each side
        if(x == 0){ // up side
            // x+2, y+1     x+1, y+2
            // x+2, y-1     x+1, y-2
            if(  (whoIsHere(new Point(x+2, y+1)) == -king.getColor() && board[x+2][y+1].getType().equals("Knight"))
                || (whoIsHere(new Point(x+1, y+2)) == -king.getColor() && board[x+1][y+2].getType().equals("Knight"))
                || (whoIsHere(new Point(x+2, y-1)) == -king.getColor() && board[x+2][y-1].getType().equals("Knight"))
                || (whoIsHere(new Point(x+1, y-2)) == -king.getColor() && board[x+1][y-2].getType().equals("Knight"))
                    )
                return true;
        }else{
            // x-2, y-1     x-1, y-2
            // x-2, y+1     x-1, y+2
            if( (whoIsHere(new Point(x-2, y-1)) == -king.getColor() && board[x-2][y-1].getType().equals("Knight"))
                    || (whoIsHere(new Point(x-1, y-2)) == -king.getColor() && board[x-1][y-2].getType().equals("Knight"))
                    || (whoIsHere(new Point(x-2, y+1)) == -king.getColor() && board[x-2][y+1].getType().equals("Knight"))
                    || (whoIsHere(new Point(x-1, y+2)) == -king.getColor() && board[x-1][y+2].getType().equals("Knight"))
                    )
                return true;
        }

        return false;
    }

    boolean checkByQueen(King king){
        // rook and bishop cover queen

        return false;
    }

    boolean checkByPawn(King king){
        // only 2 points for each side
        int x = king.getPosition().x;
        int y = king.getPosition().y;

        if(x == 0) { // up side
            // x+1, y-1     x+1, y+1
            if((whoIsHere(new Point(x+1, y-1)) == -king.getColor() && board[x+1][y-1].getType().equals("Pawn"))
                || (whoIsHere(new Point(x+1, y+1)) == -king.getColor() && board[x+1][y+1].getType().equals("Pawn"))
                    )
                return true;

        }else{
            // x-1, y-1     x-1, y+1
            if((whoIsHere(new Point(x-1, y-1)) == -king.getColor() && board[x-1][y-1].getType().equals("Pawn"))
                || (whoIsHere(new Point(x-1, y+1)) == -king.getColor() && board[x-1][y+1].getType().equals("Pawn"))
                    )
                return true;
        }
        return false;
    }

    boolean isOnboard(Point point){
        if(point.x >= 0 && point.x < line && point.y >= 0 && point.y < line)
            return true;
        return false;
    }

    int whoIsHere(Point point){
        if(!isOnboard(point)) return 0;
        if(board[point.x][point.y] == null)
            return 0;
        return board[point.x][point.y].getColor();
    }

    void updateBoard(Piece piece, Point target){
        board[piece.getPosition().x][piece.getPosition().y] = null;
        capture(piece,target);
        board[target.x][target.y] = piece;
    }

    void capture(Piece piece, Point target){
        Piece p = board[target.x][target.y];
        if(p != null){
            curPlayer.next.pieces.remove(p.getName());
            System.out.println(curPlayer.next.name+"'s "+ p.getName()+" is captured by "+ curPlayer.name+"'s "+piece.getName());
            String t = p.getType();
            p = null;
            if(t.equals("King")){
                end = true;
            }
        }
    }

    public void initiatePlayers(){
        ChessPlayer p1 = new ChessPlayer("P1", -1);
        ChessPlayer p2 = new ChessPlayer("P2", 1);
        p1.next = p2;
        p2.next = p1;
        curPlayer = p1;
    }
    void initiateBoard(){
        while (!curPlayer.initiated) {
            for (Piece piece : curPlayer.pieces.values()) {
                Point point = piece.getPosition();
                board[point.x][point.y] = piece;
            }
            curPlayer.initiated = true;
            curPlayer = curPlayer.next;
        }
    }

    void printPlayers(ChessPlayer player){

    }
    void printBoard(){
        int count = 0;
        ChessPlayer cp = curPlayer;
        while(count < 2){
            for (Piece piece : cp.pieces.values()) {
                Point point = piece.getPosition();
                board[point.x][point.y] = piece;
            }
            cp = cp.next;
            count ++;
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if(board[i][j] == null) {
                    System.out.printf("%4s ", "__");
                }else {
                    if(board[i][j].getColor() == -1)
                        System.out.printf("%4s ", board[i][j].getSign()+"1" );
                    else
                        System.out.printf("%4s ", board[i][j].getSign()+"2" );
                }
            }
            System.out.println();
        }
    }
    void printPosition(Piece piece){
        System.out.println(piece.getPosition().x+" "+piece.getPosition().y);
    }

    public static void main(String[] args) {
        new Chess().newGame();
    }


}
