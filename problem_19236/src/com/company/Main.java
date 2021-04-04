package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

class Fish implements Comparable<Fish>{
    int size, dir;
    Point pos;
    boolean isActive;
    static final int[] DX = {0, -1, -1, 0, +1, +1, +1, 0, -1};
    static final int[] DY = {0, 0, -1, -1, -1, 0, +1, +1, +1};

    Fish(int size, int dir, Point pos, boolean isActive){
        this.size = size;
        this.dir = dir;
        this.pos = (Point)pos.clone();
        this.isActive = isActive;
    }

    @Override
    public int compareTo(Fish f) {
        return Integer.compare(this.size, f.size);
    }

    public Fish clone() {
        return new Fish(size, dir, pos, isActive);
    }

    public Point getNextPos(int speed) {
        return new Point(pos.x + speed * DX[dir], pos.y + speed * DY[dir]);
    }

    public void spin() {
        dir++;
        if(dir > 8) dir = 1;
    }
}

class Shark extends Fish{
    int score;

    Shark(Point pos, int dir, int score, boolean isActive){
        super(100, dir, pos, isActive);
        this.score = score;
    }

    public Shark clone(){
        return new Shark(pos, dir, score, isActive);
    }
}

class Sea{
    Fish[][] area = new Fish[4][4];
    ArrayList<Fish> fishList = new ArrayList<>();
    Shark shark;

    Sea(ArrayList<Fish> fishList, Shark shark){
        for(Fish fish : fishList){
            Fish newFish = fish.clone();

            this.fishList.add(newFish);
            area[fish.pos.x][fish.pos.y] = newFish;
        }

        if(shark == null) this.shark = new Shark(new Point(0, 0), 0, 0, true);
        else this.shark = shark.clone();
    }

    public Sea clone(){
        return new Sea(fishList, shark);
    }

    public void moveShark(Point pos) {
        int x = pos.x, y = pos.y;

        Fish fish = area[x][y];
        fish.isActive = false;

        shark.score += fish.size;
        shark.dir = fish.dir;
        shark.pos = new Point(x, y);
    }

    public int getScore() {
        return shark.score;
    }

    public void moveFish() {
        for(Fish currentFish : fishList){
            if(!currentFish.isActive) continue;

            for(int i=0; i<8; i++){
                Point nextPos = currentFish.getNextPos(1);

                if(isAvailFishMove(nextPos)){
                    fishSwap(currentFish.pos, nextPos);
                    break;
                }

                currentFish.spin();
            }
        }
    }

    private void fishSwap(Point pos, Point nextPos) {
        int x = pos.x, y = pos.y, nx = nextPos.x, ny = nextPos.y;

        Fish temp = area[x][y];
        area[x][y] = area[nx][ny];
        area[nx][ny] = temp;

        area[x][y].pos = new Point(x, y);
        area[nx][ny].pos = new Point(nx, ny);
    }

    private boolean isAvailFishMove(Point pos) {
        return isInbound(pos) && !pos.equals(shark.pos);
    }

    public boolean isAvailSharkMove(Point pos) {
        return isInbound(pos) && area[pos.x][pos.y].isActive;
    }

    public boolean isInbound(Point pos){
        return 0 <= pos.x && pos.x < 4 && 0 <= pos.y && pos.y < 4;
    }
}

public class Main {
    static int maxScore = 0;

    public static void main(String[] args) throws IOException {
        ArrayList<Fish> fishList = new ArrayList<>();
        setInput(fishList);

        Sea sea = new Sea(fishList, null);
        sea.moveShark(new Point(0, 0));

        dfs(sea);

        System.out.println(maxScore);
    }

    private static void dfs(Sea sea) {
        maxScore = Math.max(maxScore, sea.getScore());

        sea.moveFish();

        for(int speed=1; speed<=4; speed++){
            Point nextSharkPos = sea.shark.getNextPos(speed);

            if(sea.isAvailSharkMove(nextSharkPos)){
                Sea newSea = sea.clone();
                newSea.moveShark(nextSharkPos);
                dfs(newSea);
            }
        }
    }


    private static void setInput(ArrayList<Fish> fishList) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int r=0; r<4; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<4; c++){
                int size = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());

                fishList.add(new Fish(size, dir, new Point(r, c), true));
            }
        }

        Collections.sort(fishList);
    }
}