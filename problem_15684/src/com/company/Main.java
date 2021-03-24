package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, H, M;
    static boolean[][] hasLadder;
    static int answer = -1;
    static int testLadderCount;
    static boolean isComplete = false;

    public static void main(String[] args) throws IOException {
        getInput();

        for(int i=0; i<=3; i++){
            if(isComplete) break;

            testLadderCount = i;
            dfs(0, 1);
        }

        System.out.println(answer);
    }

    private static void dfs(int addedLadderCount, int _a){
        if(isComplete) return;
        if(addedLadderCount == testLadderCount) {
            if(ladderTest()){
                isComplete = true;
                answer = addedLadderCount;
            }
            return;
        }

        for(int a=_a; a<=H; a++){
            for(int b=1; b<N; b++){
                if(canHaveLadder(a, b)){
                    hasLadder[a][b] = true;
                    dfs(addedLadderCount + 1, a);
                    hasLadder[a][b] = false;
                }
            }
        }
    }

    private static boolean canHaveLadder(int a, int b){
        return !hasLadder[a][b - 1] && !hasLadder[a][b] && !hasLadder[a][b + 1];
    }

    private static boolean ladderTest(){
        for(int line=1; line<=N; line++){
            int currLine = line;
            for(int h=1; h<=H; h++){
                if(hasLadder[h][currLine-1]) currLine--;
                else if(hasLadder[h][currLine]) currLine++;
            }

            if(currLine != line) return false;
        }

        return true;
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        hasLadder = new boolean[H+1][N+1];
        int a, b;
        for(int i=0; i<M; i++){
            st = new StringTokenizer(bf.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            hasLadder[a][b] = true;
        }
    }
}
