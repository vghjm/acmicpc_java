package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class Main {
    static int N, M;
    static int maxValue = -1;
    static int currValue = 0;
    static int[][] map;
    static boolean[][] isOccupied;
    static final int MAX_COUNT = 4;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        isOccupied = new boolean[N][M];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(bf.readLine());
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                isOccupied[i][j] = false;
            }
        }

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                startFrom(i, j, 1);
                check_exshape(i, j);
            }
        }

        System.out.println(maxValue);
    }

    private static void check_exshape(int i, int j){

        int sumValue = 0;
        // 1. ㅜ
        if(0 <= i && i+1 < N && 0 <= j && j+2 < M){
            sumValue = map[i][j] + map[i][j+1] + map[i][j+2] + map[i+1][j+1];
            maxValue = max(maxValue, sumValue);
        }

        // 2. ㅏ
        if(0 <= i && i+2 < N && 0 <= j && j+1 < M){
            sumValue = map[i][j] + map[i+1][j] + map[i+2][j] + map[i+1][j+1];
            maxValue = max(maxValue, sumValue);
        }

        // 3. ㅗ
        if(0 <= i-1 && i < N && 0 <= j && j+2 < M){
            sumValue = map[i][j] + map[i][j+1] + map[i][j+2] + map[i-1][j+1];
            maxValue = max(maxValue, sumValue);
        }

        // 4. ㅓ
        if(0 <= i-1 && i+1 < N && 0 <= j && j+1 < M){
            sumValue = map[i][j] + map[i][j+1] + map[i-1][j+1] + map[i+1][j+1];
            maxValue = max(maxValue, sumValue);
        }
    }

    private static void startFrom(int i, int j, int count){
        if(!isInBound(i, j)) return;

        occupy(i, j);
        if(count == MAX_COUNT){
            maxValue = max(maxValue, currValue);
            vacant(i, j);
            return;
        }else if(count == 2){
            if(isInBound(i-1, j)){
                occupy(i-1, j);
                startFrom(i, j+1, count+2);
                startFrom(i+1, j, count+2);
                startFrom(i, j-1, count+2);
                vacant(i-1, j);
            }else if(isInBound(i, j+1)){
                occupy(i, j+1);
                startFrom(i-1, j, count+2);
                startFrom(i+1, j, count+2);
                startFrom(i, j-1, count+2);
                vacant(i, j+1);
            }else if(isInBound(i+1, j)){
                occupy(i+1, j);
                startFrom(i-1, j, count+2);
                startFrom(i, j+1, count+2);
                startFrom(i, j-1, count+2);
                vacant(i+1, j);
            }else if(isInBound(i, j-1)){
                occupy(i, j-1);
                startFrom(i, j+1, count+2);
                startFrom(i+1, j, count+2);
                startFrom(i-1, j, count+2);
                vacant(i, j-1);
            }
        }

        startFrom(i, j-1, count+1);
        startFrom(i, j+1, count+1);
        startFrom(i+1, j, count+1);
        startFrom(i-1, j, count+1);

        vacant(i, j);
    }

    private static void occupy(int i, int j){
        currValue += map[i][j];
        isOccupied[i][j] = true;
    }

    private static void vacant(int i, int j){
        currValue -= map[i][j];
        isOccupied[i][j] = false;
    }

    private static boolean isInBound(int i, int j){
        return (0 <= i && i < N && 0 <= j && j < M && !isOccupied[i][j]);
    }
}
