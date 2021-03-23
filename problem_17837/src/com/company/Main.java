package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Piece{
    int id, row, column, dir;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    private static final int UP = 3;
    private static final int DOWN = 4;

    Piece(int id, int row, int column, int dir){
        this.id = id;
        this.row = row;
        this.column = column;
        this.dir = dir;
    }

    public String show(){
        String str = String.format("ID: %d, (%d, %d) DIR:", id+1, row, column);

        if(dir == RIGHT){
            str += "->";
        }else if(dir == LEFT){
            str += "<-";
        }else if(dir == UP){
            str += "ㅅ";
        }else if(dir == DOWN){
            str += "V";
        }

        return str;
    }
}

class Tile{
    int tileSet, pieceCount;
    Stack<Piece> pieceStack = new Stack<>();
    private static final int WHITE_TILE = 0;
    private static final int RED_TILE = 1;
    private static final int BLUE_TILE = 2;

    Tile(int tileSet, int pieceCount){
        this.tileSet = tileSet;
        this.pieceCount = pieceCount;
    }

    public void addPiece(Piece piece){
        pieceStack.push(piece);
        pieceCount++;
    }

    public MoveInfo getMoveInfoFromPiece(Piece piece){
        ArrayList<Piece> movePieceList = new ArrayList<>();

        Piece removedPiece;
        do{
            pieceCount--;
            removedPiece = pieceStack.peek();
            pieceStack.pop();
            movePieceList.add(removedPiece);
        }while(!piece.equals(removedPiece));

        return new MoveInfo(movePieceList, piece);
    }

    public int applyMoveInfo(MoveInfo moveInfo){
        int movedPieceCount = moveInfo.pieces.size();

        if(moveInfo.reverseFlag == false){
            for(int i=movedPieceCount-1; i>=0; i--){
                Piece piece = moveInfo.pieces.get(i);
                piece.row = moveInfo.nextRow;
                piece.column = moveInfo.nextColumn;

                pieceStack.push(piece);
            }
        }else{
            for(int i=0; i<movedPieceCount; i++){
                Piece piece = moveInfo.pieces.get(i);
                piece.row = moveInfo.nextRow;
                piece.column = moveInfo.nextColumn;

                pieceStack.push(piece);
            }
        }

        pieceCount += movedPieceCount;

        return pieceCount;
    }

    public boolean isBlueTile(){
        if(tileSet == BLUE_TILE) return true;

        return false;
    }

    public boolean isRedTile(){
        if(tileSet == RED_TILE) return true;

        return false;
    }

    public boolean isWhiteTile(){
        if(tileSet == WHITE_TILE) return true;

        return false;
    }

    public String show(){
        String str = "";
        if(this.isWhiteTile()){
            str = "W";
        }else if(this.isRedTile()){
            str = "R";
        }else if(this.isBlueTile()){
            str = "B";
        }

        return str + pieceStack.size();
    }
}

class MoveInfo{
    ArrayList<Piece> pieces;
    Piece nowPiece;
    int nextRow, nextColumn, dir;
    boolean reverseFlag = false;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    private static final int UP = 3;
    private static final int DOWN = 4;

    MoveInfo(ArrayList<Piece> pieces, Piece nowPiece){
        this.pieces = pieces;
        this.nowPiece = nowPiece;
        this.nextRow = nowPiece.row;
        this.nextColumn = nowPiece.column;

        this.dir = nowPiece.dir;

        switch (this.dir){
            case RIGHT:
                this.nextColumn++;
                break;
            case LEFT:
                this.nextColumn--;
                break;
            case UP:
                this.nextRow--;
                break;
            case DOWN:
                this.nextRow++;
                break;
        }
    }

    public void meetBlueTileFirst(){
        switch (this.dir){
            case RIGHT:
                this.nextColumn-=2;
                this.dir = LEFT;
                break;
            case LEFT:
                this.nextColumn+=2;
                this.dir = RIGHT;
                break;
            case UP:
                this.nextRow+=2;
                this.dir = DOWN;
                break;
            case DOWN:
                this.nextRow-=2;
                this.dir = UP;
                break;
        }
        nowPiece.dir = this.dir;
    }

    public void meetBlueTileSecond(){
        switch (this.dir){
            case RIGHT:
                this.nextColumn--;
                break;
            case LEFT:
                this.nextColumn++;
                break;
            case UP:
                this.nextRow++;
                break;
            case DOWN:
                this.nextRow--;
                break;
        }
    }

    public void meetRedTile(){
        this.reverseFlag = true;
    }
}

class Board{
    int N, K;
    Tile[][] tiles;
    Piece[] pieces;
    private static final int BLUE_TILE = 2;

    public void initBoard() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        tiles = new Tile[N+2][N+2];
        for(int r=0; r<=N+1; r++){
            if(r >= 1 && r <= N){
                st = new StringTokenizer(bf.readLine());
            }
            for(int c=0; c<=N+1; c++){
                if(r==0 || c==0 || r==N+1 || c==N+1){
                    tiles[r][c] = new Tile(BLUE_TILE, 0);
                }else{
                    int tileSet = Integer.parseInt(st.nextToken());
                    int pieceCount = 0;

                    tiles[r][c] = new Tile(tileSet, pieceCount);
                }
            }
        }

        pieces = new Piece[K];
        for(int id=0; id<K; id++){
            st = new StringTokenizer(bf.readLine());
            int row = Integer.parseInt(st.nextToken());
            int column = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            Piece piece = new Piece(id, row, column, dir);

            pieces[id] = piece;
            tiles[row][column].addPiece(piece);
        }
    }

    public boolean isEnd(){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(tiles[i][j].pieceCount >= 4 ){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean doNextTurn(){
        for(int id=0; id<K; id++){
            Piece piece = pieces[id];

            MoveInfo moveInfo = tiles[piece.row][piece.column].getMoveInfoFromPiece(piece);
            Tile nextTile = tiles[moveInfo.nextRow][moveInfo.nextColumn];
            if(nextTile.isBlueTile()){
                moveInfo.meetBlueTileFirst(); // 방향 전환
                nextTile = tiles[moveInfo.nextRow][moveInfo.nextColumn];

                if(nextTile.isBlueTile()){
                    moveInfo.meetBlueTileSecond(); // 가만히
                }else if(nextTile.isRedTile()){
                    moveInfo.meetRedTile();
                }else if(nextTile.isWhiteTile()){
                    // do nothing
                }
            }else if(nextTile.isRedTile()){
                moveInfo.meetRedTile();
            }else if(nextTile.isWhiteTile()){
                // do nothing
            }

            if(tiles[moveInfo.nextRow][moveInfo.nextColumn].applyMoveInfo(moveInfo) >= 4){
                return false;
            }

        }

        return true;
    }

    public void show(int turnCount){
        System.out.println("show board");
        System.out.printf("turnCount: %d, N: %d, K: %d\n", turnCount, N, K);
        for(int r=0; r<=N+1; r++){
            for(int c=0; c<=N+1; c++){
                System.out.printf("%s ", tiles[r][c].show());
            }
            System.out.println();
        }
        for(int id=0; id<K; id++){
            System.out.println(pieces[id].show());
        }
        System.out.println("end show\n");
    }
}

public class Main {
    public static final boolean DEBUG = false;
    public static void main(String[] args) throws IOException {
        Board gameBoard = new Board();

        gameBoard.initBoard();

        int turnCount = 0;
        while(gameBoard.isEnd() == false){
            turnCount++;
            if(gameBoard.doNextTurn() == false){
                break;
            }
            if(DEBUG){
                gameBoard.show(turnCount);
            }
            if(turnCount > 1000){
                turnCount = -1;

                break;
            }
        }
        System.out.println(turnCount);
    }
}
