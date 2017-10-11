package play.cheses;

import play.cheses.pieces.*;
import common.Point;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by jieluo on 2017-04-10.
 */
public class ChessPlayer {
    String name;
    int color; // -1 - black, 1 - white
    HashMap<String, Piece> pieces = new HashMap<>();
    ChessPlayer next = null;
    boolean initiated = false;

    public ChessPlayer(String name, int color){
        this.name = name;
        this.color = color;
        initiatePiece();
//        initiatePiece("test");
    }

    public Piece pointPiece(){
        Scanner in = new Scanner(System.in);
        Piece piece = null;
        while(piece == null) {
            System.out.println("Please point the piece: (with name)");
            String s = in.nextLine();
            // the name format is username+"_"+king
            String pieceName = s.trim();
            piece = pieces.get(pieceName);
        }
        return piece;
    }

    public Point getTarget(){
        Scanner in = new Scanner(System.in);
        int x=0, y=0;
        System.out.println("Target Point: ");
        String n = in.nextLine();
        String[] strInput = n.split(",");
        x = Integer.valueOf(strInput[0].trim());
        y = Integer.valueOf(strInput[1].trim());
        return new Point(x, y);

    }

    public void move(Piece piece, Point target){
        piece.move(target);
    }

    void initiatePiece(){
        int boardLineth = 7;
        int line;
        int pline;
        if(color == -1) {
            line =7;
            pline = 6;
        }else{
            line =0;
            pline = 1;
        }
        Piece king = new King(this.name + "_king", this.color, new Point(line, 4));
        Piece queen = new Queen(this.name + "_queen", this.color, new Point(line, 3));
        Piece bishop1 = new Bishop(this.name + "_bishop1", this.color, new Point(line, 2));
        Piece bishop2 = new Bishop(this.name + "_bishop2", this.color, new Point(line, boardLineth - 2));
        Piece knight1 = new Knight(this.name + "_knight1", this.color, new Point(line, 1));
        Piece knight2 = new Knight(this.name + "_knight2", this.color, new Point(line, boardLineth - 1));
        Piece rook1 = new Rook(this.name + "_rook1", this.color, new Point(line, 0));
        Piece rook2 = new Rook(this.name + "_rook2", this.color, new Point(line, boardLineth));
        for(int i=0;i< 8; i++){
            Piece pawn = new Pawn(this.name + "_pawn"+(i+1), this.color, new Point(pline, i));
            pieces.put(pawn.getName(), pawn);
        }
        pieces.put(king.getName(), king);
        pieces.put(queen.getName(), queen);
        pieces.put(bishop1.getName(), bishop1);
        pieces.put(bishop2.getName(), bishop2);
        pieces.put(knight1.getName(), knight1);
        pieces.put(knight2.getName(), knight2);
        pieces.put(rook1.getName(), rook1);
        pieces.put(rook2.getName(), rook2);
    }

    void initiatePiece(String test){
        int boardLineth = 7;
        int line;
        int pline;
        if(color == -1) {
            line =7;
            pline = 6;
        }else{
            line =0;
            pline = 1;
        }
        Piece king = new King(this.name + "_king", this.color, new Point(line, 4));
        Piece rook1 = new Rook(this.name + "_rook1", this.color, new Point(line, 0));
        Piece rook2 = new Rook(this.name + "_rook2", this.color, new Point(line, boardLineth));
        Piece bishop2 = new Bishop(this.name + "_bishop2", this.color, new Point(line, boardLineth - 2));

        pieces.put(bishop2.getName(), bishop2);
        pieces.put(king.getName(), king);
        pieces.put(rook1.getName(), rook1);
        pieces.put(rook2.getName(), rook2);
    }

    public void doPawnPromotion(Piece pawn){
        if(!pawn.getType().equals("Pawn")) return;
        Pawn p = (Pawn) pawn;
        if(!p.isPromotionTime()) return;
        System.out.println("Please choose the promotion type: bishop, queen, rook, knight");
        Scanner in = new Scanner(System.in);
        Piece piece = null;
        while(piece == null) {
            String type = in.nextLine();
            if (type.equals("bishop"))
                piece = new Bishop(this.name + "_bishop3", this.color, pawn.getPosition());
            else if (type.equals("queen"))
                piece = new Queen(this.name + "_queen3", this.color, pawn.getPosition());
            else if (type.equals("rook"))
                piece = new Rook(this.name + "_rook3", this.color, pawn.getPosition());
            else if (type.equals("knight"))
                piece = new Knight(this.name + "_knight2", this.color, pawn.getPosition());
        }
        pieces.remove(pawn.getName());
        pieces.put(piece.getName(), piece);
    }

}
