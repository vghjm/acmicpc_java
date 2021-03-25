package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class AirConditioner{
    int myR, myC;
    int R, C;
    static final int[] dr = {0, -1, 0, +1};
    static final int[] dc = {+1, 0, -1, 0};

    AirConditioner(int myR, int myC, int R, int C){
        this.myR = myR;
        this.myC = myC;
        this.R = R;
        this.C = C;
    }

    public void work(int[][] dustMap, int spinDir) {
        int pushedDust = 0;
        int temp;

        int r = myR;
        int c = myC;
        int dir = 0;
        do{
            if(!isInbound(r+dr[dir], c+dc[dir])){
                dir += spinDir;
                if(dir<0) dir = 3;
                continue;
            }

            r += dr[dir];
            c += dc[dir];
            if(r == myR && c == myC) break;

            temp = dustMap[r][c];
            dustMap[r][c] = pushedDust;
            pushedDust = temp;
        }while(true);
    }

    private boolean isInbound(int r, int c){
        return 0 <= r && r < R && 0 <= c && c < C;
    }
}

public class Main {
    static int R, C, T;
    static int[][] A;
    static int[][] A_next;
    static ArrayList<AirConditioner> airConditioners = new ArrayList<>();
    static final int[] dr = {0, -1, 0, +1};
    static final int[] dc = {+1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        getInput();

        for(int t=1; t<=T; t++){
            // 확산
            A_next = new int[R][C];
            for(int r=0; r<R; r++){
                for(int c=0; c<C; c++){
                    int dust = A[r][c];
                    if(dust <= 0) continue;

                    int dividedDust = Math.floorDiv(dust, 5);
                    int dividedCount = 0;
                    for(int dir=0; dir<4; dir++){
                        int r_next = r + dr[dir];
                        int c_next = c + dc[dir];
                        if(isAvailable(r_next, c_next)){
                            A_next[r_next][c_next] += dividedDust;
                            dividedCount++;
                        }
                    }
                    A_next[r][c] -= dividedCount * dividedDust;
                }
            }

            for(int r=0; r<R; r++){
                for(int c=0; c<C; c++){
                    A[r][c] += A_next[r][c];
                }
            }

            // 에어컨
            airConditioners.get(0).work(A, +1);
            airConditioners.get(1).work(A, -1);
        }

        // 먼지 총합
        int dustTotal = 2;
        for(int r=0; r<R; r++){
            for(int c=0; c<C; c++){
                dustTotal += A[r][c];
            }
        }
        System.out.println(dustTotal);
    }

    private static boolean isAvailable(int r, int c){
        return 0 <= r && r < R && 0 <= c && c < C && A[r][c] != -1;
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        A = new int[R][C];
        for(int r=0; r<R; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<C; c++){
                int d = Integer.parseInt(st.nextToken());

                if(d == -1) airConditioners.add(new AirConditioner(r, c, R, C));

                A[r][c] = d;
            }
        }
    }
}
