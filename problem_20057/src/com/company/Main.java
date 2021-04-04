package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] A;
    static final int[] DR = {0, +1, 0, -1};
    static final int[] DC = {-1, 0, +1, 0};

    public static void main(String[] args) throws IOException {
        setInput();

        int r = (N-1)/2;
        int c = (N-1)/2;
        int speed = 1;
        int moveCount = 0;
        int dir = 0;
        int blownSand = 0;
        while(r != 0 || c != 0){
            moveCount++;

            int nr = r + DR[dir];
            int nc = c + DC[dir];
            blownSand += blowSand(nr, nc, dir);

            r = nr;
            c = nc;
            if(moveCount % speed == 0){
                dir = dir == 3 ? 0 : dir+1;

                if(moveCount == speed * 2){
                    speed++;
                    moveCount = 0;
                }
            }
        }

        System.out.println(blownSand);
    }

    private static int blowSand(int nr, int nc, int dir) {
        int sand = A[nr][nc];
        int p1 = (int) Math.floor(sand * 0.01f);
        int p2 = (int) Math.floor(sand * 0.02f);
        int p5 = (int) Math.floor(sand * 0.05f);
        int p7 = (int) Math.floor(sand * 0.07f);
        int p10 = (int) Math.floor(sand * 0.1f);
        int a = sand - 2*p1 - 2*p2 - 2*p7 - 2*p10 - p5;
        int blownSand = 0;

        if(dir == 0){
            blownSand += updateSand(nr-1, nc+1, p1);
            blownSand += updateSand(nr+1, nc+1, p1);
            blownSand += updateSand(nr-2, nc, p2);
            blownSand += updateSand(nr+2, nc, p2);
            blownSand += updateSand(nr-1, nc, p7);
            blownSand += updateSand(nr+1, nc, p7);
            blownSand += updateSand(nr-1, nc-1, p10);
            blownSand += updateSand(nr+1, nc-1, p10);
            blownSand += updateSand(nr, nc-2, p5);
            blownSand += updateSand(nr, nc-1, a);
        }else if(dir == 1){
            blownSand += updateSand(nr-1, nc-1, p1);
            blownSand += updateSand(nr-1, nc+1, p1);
            blownSand += updateSand(nr, nc-2, p2);
            blownSand += updateSand(nr, nc+2, p2);
            blownSand += updateSand(nr, nc-1, p7);
            blownSand += updateSand(nr, nc+1, p7);
            blownSand += updateSand(nr+1, nc-1, p10);
            blownSand += updateSand(nr+1, nc+1, p10);
            blownSand += updateSand(nr+2, nc, p5);
            blownSand += updateSand(nr+1, nc, a);
        }else if(dir == 2){
            blownSand += updateSand(nr-1, nc-1, p1);
            blownSand += updateSand(nr+1, nc-1, p1);
            blownSand += updateSand(nr-2, nc, p2);
            blownSand += updateSand(nr+2, nc, p2);
            blownSand += updateSand(nr-1, nc, p7);
            blownSand += updateSand(nr+1, nc, p7);
            blownSand += updateSand(nr-1, nc+1, p10);
            blownSand += updateSand(nr+1, nc+1, p10);
            blownSand += updateSand(nr, nc+2, p5);
            blownSand += updateSand(nr, nc+1, a);
        }else{
            blownSand += updateSand(nr+1, nc-1, p1);
            blownSand += updateSand(nr+1, nc+1, p1);
            blownSand += updateSand(nr, nc-2, p2);
            blownSand += updateSand(nr, nc+2, p2);
            blownSand += updateSand(nr, nc-1, p7);
            blownSand += updateSand(nr, nc+1, p7);
            blownSand += updateSand(nr-1, nc-1, p10);
            blownSand += updateSand(nr-1, nc+1, p10);
            blownSand += updateSand(nr-2, nc, p5);
            blownSand += updateSand(nr-1, nc, a);
        }

        return blownSand;
    }

    private static int updateSand(int r, int c, int sand) {
        if(isInboud(r, c)){
            A[r][c] += sand;
            return 0;
        }

        return sand;
    }

    private static boolean isInboud(int r, int c){
        return 0<=r && r<N && 0<=c && c<N;
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());

        A = new int[N][N];
        for(int r=0; r<N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<N; c++){
                A[r][c] = Integer.parseInt(st.nextToken());
            }
        }
    }
}
