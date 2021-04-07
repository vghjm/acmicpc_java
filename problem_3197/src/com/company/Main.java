package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main {
    static int R, C;
    static boolean[][] hasIce;
    static boolean[][] isVisitedIce;
    static boolean[][] isAccessible;
    static boolean[][] isMapped;
    static Point start, end;
    static Queue<Point> adjacentIceList = new LinkedList<>();
    static Queue<Point> reachablePos = new LinkedList<>();
    static final int[] DR = {-1, +1, 0, 0};
    static final int[] DC = {0, 0, -1, +1};

    public static void main(String[] args) throws IOException {
        setInput();

        for(int r=0; r<R; r++){
            for(int c=0; c<C; c++){
                if(hasIce[r][c] && isAdjacentToWater(r, c)) {
                    adjacentIceList.add(new Point(r, c));
                    isVisitedIce[r][c] = true;
                }
            }
        }

        int day = 0;
        reachablePos.add(start);
        do{
            Queue<Point> nextReachablePos = new LinkedList<>();
            while (!reachablePos.isEmpty()){
                mapped(reachablePos.poll(), nextReachablePos);
            }
            reachablePos = nextReachablePos;

            if(isConnectedWith()) break;

            day++;
            Queue<Point> newAdjacentIceList = new LinkedList<>();
            while (!adjacentIceList.isEmpty()){
                melt(adjacentIceList.poll(), newAdjacentIceList);
            }
            adjacentIceList = newAdjacentIceList;

        }while(true);

        System.out.println(day);


    }

    private static void melt(Point icePos, Queue<Point> newAdjacentIceList) {
        int r = icePos.x;
        int c = icePos.y;
        hasIce[r][c] = false;

        for(int dir=0; dir<4; dir++){
            int nr = r + DR[dir];
            int nc = c + DC[dir];

            if(isInbound(nr, nc) && hasIce[nr][nc] && !isVisitedIce[nr][nc]){
                isVisitedIce[nr][nc] = true;
                newAdjacentIceList.add(new Point(nr, nc));
            }
        }
    }

    private static void mapped(Point pos, Queue<Point> nextReachablePos) {
        int r = pos.x;
        int c = pos.y;
        isAccessible[r][c] = true;

        for(int dir=0; dir<4; dir++){
            int nr = r + DR[dir];
            int nc = c + DC[dir];

            if(isInbound(nr, nc)){
                if(hasIce[nr][nc]){
                    if(!isMapped[nr][nc]) {
                        isMapped[nr][nc] = true;
                        nextReachablePos.add(new Point(nr, nc));
                    }
                }else if(!isAccessible[nr][nc]){
                    mapped(new Point(nr, nc), nextReachablePos);
                }
            }
        }
    }

    private static boolean isConnectedWith() {
        return isAccessible[end.x][end.y];
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        hasIce = new boolean[R][C];
        isVisitedIce = new boolean[R][C];
        isAccessible = new boolean[R][C];
        isMapped = new boolean[R][C];

        for(int r=0; r<R; r++){
            char[] str = bf.readLine().toCharArray();

            for(int c=0; c<C; c++){
                char _c = str[c];

                if(_c == 'X') {
                    hasIce[r][c] = true;
                }else if(_c == 'L'){
                    if(start == null) start = new Point(r, c);
                    else end = new Point(r, c);
                }
            }
        }
    }

    private static boolean isAdjacentToWater(int r, int c) {
        for(int dir=0; dir<4; dir++){
            int nr = r + DR[dir];
            int nc = c + DC[dir];

            if(isInbound(nr, nc) && !hasIce[nr][nc]) return true;
        }

        return false;
    }

    private static void show(int[][] visitDay) {
        System.out.println();
        for(int r=0; r<R; r++){
            for(int c=0; c<C; c++){
                if(hasIce[r][c]) System.out.printf("%d", 1);
                else System.out.printf("%d", 0);
            }
            System.out.print("\t");
            for(int c=0; c<C; c++){
                System.out.printf("%2d ", visitDay[r][c]);
            }
            System.out.println();
        }
    }

    private static boolean isInbound(int r, int c) {
        return 0<=r && r<R && 0<=c && c<C;
    }
}
