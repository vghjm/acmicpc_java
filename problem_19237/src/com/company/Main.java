package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

class Shark implements Comparable<Shark>{
    Point pos;
    int num, dir;
    int[][] priorityDir = new int[5][5];
    static final int[] DX = {0, -1, +1, 0, 0};
    static final int[] DY = {0, 0, 0, -1, +1};

    Shark(Point pos, int num){
        this.pos = (Point) pos.clone();
        this.num = num;
    }

    Shark(Point pos, int num, int dir, int[][] priorityDir) {
        this.pos = (Point) pos.clone();
        this.num = num;
        this.dir = dir;
        for(int i=1; i<=4; i++){
            System.arraycopy(priorityDir[i], 1, this.priorityDir[i], 1, 4);
        }
    }

    public Shark clone() {
        return new Shark(pos, num, dir, priorityDir);
    }

    @Override
    public int compareTo(Shark o) {
        return Integer.compare(this.num, o.num);
    }


    public Point getNextPos(int priority){
        int pdir = priorityDir[dir][priority];
        return new Point(pos.x + DX[pdir], pos.y + DY[pdir]);
    }

    public void changeDir(int priority){
        dir = priorityDir[dir][priority];
    }

    public void changePos(Point nextPos){
        this.pos = (Point) nextPos.clone();
    }
}

class Area {
    Shark shark;
    int smellNumber = NO_SMELL, smellRemainTime = 0, maxSmellTime;
    private static final int NO_SMELL = -1;

    Area(Shark shark, int maxSmellTime) {
        this.shark = shark;
        this.maxSmellTime = maxSmellTime;
        if(shark != null){
            smellNumber = shark.num;
            smellRemainTime = maxSmellTime;
        }
    }

    Area(int smellNumber, int smellRemainTime, int maxSmellTime) {
        this.shark = null;
        this.smellNumber = smellNumber;
        this.smellRemainTime = smellRemainTime;
        this.maxSmellTime = maxSmellTime;
    }

    public Area cloneWithoutShark(){
        return new Area(smellNumber, smellRemainTime, maxSmellTime);
    }

    public boolean isEmpty(){
        return smellNumber == NO_SMELL;
    }

    public boolean isSameSmell(Shark shark){
        return smellNumber == shark.num;
    }

    public boolean addShark(Shark shark){
        if(this.shark == null){
            this.shark = shark;
            this.smellNumber = shark.num;
            this.smellRemainTime = maxSmellTime;
            return true;
        }

        return false;
    }

    public void smellBlow() {
        if(shark == null && smellRemainTime != 0) {
            smellRemainTime--;
            if(smellRemainTime == 0) smellNumber = NO_SMELL;
        }

    }
}

public class Main {
    static int N, M, K;
    static Area[][] map;
    static ArrayList<Shark> sharkList = new ArrayList<>();
    static final int[] DX = {0, 0, -1, +1};
    static final int[] DY = {-1, +1, 0, 0};

    public static void main(String[] args) throws IOException {
        setInput();

        int time = 0;

        while(sharkList.size() != 1){
            time++;

            Area[][] newMap = copyWithoutShark(map);
            ArrayList<Shark> newSharkList = new ArrayList<>();
            for(Shark shark : sharkList){
                if(hasEmptySpace(shark.pos)){
                    for(int priority=1; priority<=4; priority++){
                        Point nextPos = shark.getNextPos(priority);
                        int x = nextPos.x, y = nextPos.y;

                        if(isInbound(x, y) && map[x][y].isEmpty()){
                            shark.changeDir(priority);
                            shark.changePos(nextPos);
                            if(newMap[x][y].addShark(shark)){
                                newSharkList.add(shark);
                            }

                            break;
                        }
                    }
                }else{
                    for(int priority=1; priority<=4; priority++){
                        Point nextPos = shark.getNextPos(priority);
                        int x = nextPos.x, y = nextPos.y;

                        if(isInbound(x, y) && map[x][y].isSameSmell(shark)){
                            shark.changeDir(priority);
                            shark.changePos(nextPos);
                            if(newMap[x][y].addShark(shark)){
                                newSharkList.add(shark);
                            }

                            break;
                        }
                    }
                }
            }

            map = newMap;
            sharkList = newSharkList;

            for(int r=0; r<N; r++){
                for(int c=0; c<N; c++){
                    map[r][c].smellBlow();
                }
            }

            if(time > 1000){
                time = -1;
                break;
            }
        }

        System.out.println(time);
    }

    private static boolean hasEmptySpace(Point pos) {
        int x = pos.x, y = pos.y;

        for(int dir=0; dir<4; dir++){
            int nx = x + DX[dir];
            int ny = y + DY[dir];

            if(isEmpty(nx, ny)){
                return true;
            }
        }

        return false;
    }

    private static boolean isEmpty(int x, int y) {
        return isInbound(x, y) && map[x][y].isEmpty();
    }

    private static boolean isInbound(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < N;
    }

    private static Area[][] copyWithoutShark(Area[][] map) {
        Area[][] newMap = new Area[N][N];

        for(int r=0; r<N; r++){
            for(int c=0; c<N; c++){
                newMap[r][c] = map[r][c].cloneWithoutShark();
            }
        }

        return newMap;
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new Area[N][N];
        for(int r=0; r<N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<N; c++){
                int num = Integer.parseInt(st.nextToken());
                Shark shark = null;

                if(num != 0) {
                    shark = new Shark(new Point(r, c), num - 1);
                    sharkList.add(shark);
                }

                map[r][c] = new Area(shark, K);
            }
        }

        Collections.sort(sharkList);

        st = new StringTokenizer(bf.readLine());
        for(Shark shark : sharkList){
            shark.dir = Integer.parseInt(st.nextToken());
        }

        for(Shark shark : sharkList){
            for(int i=1; i<=4; i++){
                st = new StringTokenizer(bf.readLine());
                for(int j=1; j<=4; j++){
                    shark.priorityDir[i][j] = Integer.parseInt(st.nextToken());
                }
            }
        }
    }
}
