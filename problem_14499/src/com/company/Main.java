package com.company;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Dice{
    int up, down, left, right, front, back;
    static final int ROLL_RIGHT = 1;
    static final int ROLL_LEFT = 2;
    static final int ROLL_UP = 3;
    static final int ROLL_DOWN = 4;

    Dice(int up, int down, int left, int right, int front, int back){
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.front = front;
        this.back = back;
    }

    public void roll(int dir){
        int savedFront = front;

        switch (dir){
            case ROLL_RIGHT:
                front = left;
                left = back;
                back = right;
                right = savedFront;
                break;
            case ROLL_LEFT:
                front = right;
                right = back;
                back = left;
                left = savedFront;
                break;
            case ROLL_UP:
                front = down;
                down = back;
                back = up;
                up = savedFront;
                break;
            case ROLL_DOWN:
                front = up;
                up = back;
                back = down;
                down = savedFront;
                break;
        }
    }
}

class Map{
    int n, m;
    int[][] map;
    int diceX, diceY;
    Dice dice;
    static final int[] DIR_X = {0, 0, -1, 1};
    static final int[] DIR_Y = {1, -1, 0, 0};

    Map(int N, int M, int X, int Y, int[][] initMap){
        n = N;
        m = M;
        diceX = X;
        diceY = Y;
        map = initMap;
        dice = new Dice(0 , 0, 0 ,0, 0, 0);
    }

    public void processOrder(int order){
        int nextX = diceX + DIR_X[order-1];
        int nextY = diceY + DIR_Y[order-1];

        if(!this.isInbound(nextX, nextY)) return;
        diceX = nextX;
        diceY = nextY;

        dice.roll(order);
        if(map[diceX][diceY] == 0){
            map[diceX][diceY] = dice.back;
        }else{
            dice.back = map[diceX][diceY];
            map[diceX][diceY] = 0;
        }

        System.out.println(dice.front);

    }

    boolean isInbound(int nextX, int nextY){
        return 0 <= nextX && nextX < n && 0 <= nextY && nextY < m;
    }
}


public class Main {
    static int N, M, X, Y, K;
    static int[][] initMap;
    static Queue<Integer> orderQueue;
    static Map map;

    public static void main(String[] args) throws IOException {
        getInput();

        while(!orderQueue.isEmpty()){
            map.processOrder(orderQueue.poll());
        }

    }

    static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        initMap = new int[N][M];
        for(int x=0; x<N; x++){
            st = new StringTokenizer(bf.readLine());
            for(int y=0; y<M; y++){
                initMap[x][y] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(bf.readLine());
        orderQueue = new LinkedList<Integer>();
        for(int k=0; k<K; k++){
            orderQueue.add(Integer.parseInt(st.nextToken()));
        }

        map = new Map(N, M, X, Y, initMap);
    }
}
