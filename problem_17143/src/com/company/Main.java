package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

class Shark implements Comparable<Shark>{
    int r, c, speed, dir, size;

    Shark(int r, int c, int speed, int dir, int size){
        this.r = r;
        this.c = c;
        this.speed = speed;
        this.dir = dir;
        this.size = size;
    }

    @Override
    public int compareTo(Shark shark) {
        return Integer.compare(shark.size, this.size);
    }

    public String show(){
        return "("+r+", "+c+") size: " + size;
    }
}

public class Main {
    static int R, C, M;
    static ArrayList<Shark> sharks = new ArrayList<>();
    static Shark[] targetSharkList;
    static int killedSharkSizeSum = 0;

    public static void main(String[] args) throws IOException {
        getInput();


        for(int c=1; c<=C; c++){
            killShark();

            moveShark(c + 1);
        }

        System.out.println(killedSharkSizeSum);
    }

    private static void show(int index){
        System.out.println(index + " 번");
        for(Shark s : sharks){
            System.out.println(s.show());
        }
        System.out.println();
    }

    private static void sharkPosChange(Shark shark){
        int dir = shark.dir;
        int speed = shark.speed;

        int nextR = shark.r, nextC = shark.c;
        if(dir <= 2){
            speed %= 2 * (R - 1);
            if(dir == 1) nextR -= speed;
            else nextR += speed;

            while(nextR < 1 || nextR > R){
                dir = 3 - dir;
                if(nextR < 1) nextR = 2 - nextR;
                else nextR = R - (nextR - R);
            }
        }else{
            speed %= 2 * (C - 1);
            if(dir == 3) nextC += speed;
            else nextC -= speed;

            while(nextC < 1 || nextC > C){
                dir = 7 - dir;
                if(nextC < 1) nextC = 2 - nextC;
                else nextC = C - (nextC - C);
            }
        }

        shark.r = nextR;
        shark.c = nextC;
        shark.dir = dir;
    }

    private static void moveShark(int targetC){
        Shark[][] sharkMap = new Shark[R+1][C+1];
        ArrayList<Shark> movedShark = new ArrayList<>();

        targetSharkList = new Shark[R + 1];
        for(Shark shark : sharks){
            sharkPosChange(shark);

            int r = shark.r;
            int c = shark.c;
            if(sharkMap[r][c] == null){
                sharkMap[r][c] = shark;
                movedShark.add(shark);
                if(c == targetC) targetSharkList[r] = shark;
            }
        }

        sharks = movedShark;
    }

    private static void killShark(){
        for(int r=1; r<=R; r++){
            if(targetSharkList[r] != null){
                Shark killedShark = targetSharkList[r];
                killedSharkSizeSum += killedShark.size;
                sharks.remove(killedShark);

                return;
            }
        }
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        targetSharkList = new Shark[R + 1];
        int r, c, s, d, z;
        for(int m=0; m<M; m++){
            st = new StringTokenizer(bf.readLine());
            r = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            s = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            z = Integer.parseInt(st.nextToken());

            Shark shark = new Shark(r, c, s, d, z);
            sharks.add(shark);

            if(shark.c == 1) targetSharkList[shark.r] = shark;
        }

        Collections.sort(sharks);
    }
}
