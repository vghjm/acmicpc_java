package com.company;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Block{
    int t, x, y;

    Block(int t, int x, int y){
        this.t = t;
        this.x = x;
        this.y = y;
    }

    public Block spin90(){
        int nt = 1, nx = y, ny = 3 - x;

        if(t == 3) {
            nt = 2;
            ny--;
        }else if(t == 2) nt = 3;

        return new Block(nt, nx, ny);
    }
}

class Board{
    boolean[][] isFilled = new boolean[6][4];
    int blockCount = 0;
    int score = 0;

    public void addBlock(Block block){
        int t = block.t;
        int x = 2, y = block.y;

        if(t == 1){
            for(; x<6; x++) if(isFilled[x][y]) break;
            isFilled[x-1][y] = true;
            blockCount++;
        }else if(t == 2){
            int y2 = y + 1;

            for(; x<6; x++) if(isFilled[x][y] || isFilled[x][y2]) break;
            isFilled[x-1][y] = isFilled[x-1][y2] = true;
            blockCount += 2;
        }else if(t == 3){
            for(; x<6; x++) if(isFilled[x][y]) break;
            isFilled[x-1][y] = isFilled[x-2][y] = true;
            blockCount += 2;
        }

        if(getScore(x - 1)){
            getScore(x - 1);
        }else getScore(x - 2);

        if(hasBlock(1)){
            hasBlock(1);
        }
    }

    private boolean hasBlock(int x) {
        if(isFilled[x][0] || isFilled[x][1] || isFilled[x][2] || isFilled[x][3]){
            for(int y=0; y<4; y++){
                if(isFilled[5][y]) blockCount--;
            }
            fallDown(5);

            return true;
        }

        return false;
    }

    private boolean getScore(int x){
        if(isFilled[x][0] && isFilled[x][1] && isFilled[x][2] && isFilled[x][3]){
            score++;
            blockCount -= 4;
            fallDown(x);

            return true;
        }

        return false;
    }

    private void fallDown(int fromX){
        for(int x=fromX; x>0; x--){
            isFilled[x] = isFilled[x-1];
        }
        isFilled[0] = new boolean[4];
    }
}

public class Main {
    static int N;
    static Board greenBoard = new Board();
    static Board blueBoard = new Board();
    static ArrayList<Block> blockList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        setInput();

        for(Block block : blockList){
            greenBoard.addBlock(block);
            blueBoard.addBlock(block.spin90());
        }

        System.out.println(greenBoard.score + blueBoard.score);
        System.out.println(greenBoard.blockCount + blueBoard.blockCount);
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());

        for(int n=0; n<N; n++){
            st = new StringTokenizer(bf.readLine());
            int t = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            blockList.add(new Block(t, x, y));
        }
    }
}
