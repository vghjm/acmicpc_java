package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    private static int[] piece = new int[4];
    private static int[] dice = new int[10];
    private static int maxScore = 0;
    private static int[][] node = {
            {0, 1, 2, 3, 4, 5}, // 0, 시작
            {2, 2, 3, 4, 5, 6}, // 1
            {4, 3, 4, 5, 6, 7}, // 2
            {6, 4, 5, 6, 7, 8}, // 3
            {8, 5, 6, 7, 8, 9}, // 4
            {10, 21, 22, 23, 29, 30}, // 5
            {12, 7, 8, 9, 10, 11}, // 6
            {14, 8, 9, 10, 11, 12}, // 7
            {16, 9, 10, 11, 12, 13}, // 8
            {18, 10, 11, 12, 13, 14}, // 9
            {20, 24, 25, 29, 30, 31}, // 10
            {22, 12, 13, 14, 15, 16}, // 11
            {24, 13, 14, 15, 16, 17}, // 12
            {26, 14, 15, 16, 17, 18}, // 13
            {28, 15, 16, 17, 18, 19}, // 14
            {30, 26, 27, 28, 29, 30}, // 15
            {32, 17, 18, 19, 20, 32}, // 16
            {34, 18, 19, 20, 32, 32}, // 17
            {36, 19, 20, 32, 32, 32}, // 18
            {38, 20, 32, 32, 32, 32}, // 19
            {40, 32, 32, 32, 32, 32}, // 20
            {13, 22, 23, 29, 30, 31}, // 21
            {16, 23, 29, 30, 31, 20}, // 22
            {19, 29, 30, 31, 20, 32}, // 23
            {22, 25, 29, 30, 31, 20}, // 24
            {24, 29, 30, 31, 20, 32}, // 25
            {28, 27, 28, 29, 30, 31}, // 26
            {27, 28, 29, 30, 31, 20}, // 27
            {26, 29, 30, 31, 20, 32}, // 28
            {25, 30, 31, 20, 32, 32}, // 29
            {30, 31, 20, 32, 32, 32}, // 30
            {35, 20, 32, 32, 32, 32}, // 31
            {0, 32, 32, 32, 32, 32}, // 32, 끝
    };
    private static boolean[] isUsed = new boolean[33];

    
    public static void main(String[] args) throws IOException {
        setInput();
    
        dfs(0, 0);

        System.out.println(maxScore);
    }

    private static void dfs(int time, int sum){
        if(time == 10){
            maxScore = Math.max(maxScore, sum);
        }else{
            int moveCount = dice[time];

            for(int i=0; i<4; i++){
                int currPos = piece[i];
                if(currPos == 32) continue;

                int nextPos = node[currPos][moveCount];
                if(nextPos == 32 || !isUsed[nextPos]) {
                    isUsed[currPos] = false;
                    piece[i] = nextPos;
                    isUsed[nextPos] = true;

                    dfs(time + 1, sum + node[nextPos][0]);

                    isUsed[nextPos] = false;
                    piece[i] = currPos;
                    isUsed[currPos] = true;
                }
            }
        }
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        for(int i=0; i<10; i++){
            dice[i] = Integer.parseInt(st.nextToken());
        }
    }

}
