package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static int N, Q, SIZE, iceSum = 0;
    static int[][] A;
    static int[] L;
    static final int[] DR = {-1, +1, 0, 0};
    static final int[] DC = {0, 0, -1, +1};

    public static void main(String[] args) throws IOException {
	    setInput();

        for(int l : L){
            int size = (int) Math.pow(2, l);

            for(int r=0; r<SIZE; r+=size){
                for(int c=0; c<SIZE; c+=size){
                    fireStrom(r, c, size);
                }
            }

            ArrayList<Point> melt = new ArrayList<>();
            for(int r=0; r<SIZE; r++){
                for(int c=0; c<SIZE; c++){
                    if(A[r][c]!=0 && meltDown(r, c)){
                        melt.add(new Point(r, c));
                    }
                }
            }

            for(Point point : melt){
                A[point.x][point.y]--;
                iceSum--;
            }
        }

        int maxIceSize = 0;
        boolean[][] isVisit = new boolean[SIZE][SIZE];
        for(int r=0; r<SIZE; r++){
            for(int c=0; c<SIZE; c++){
                if(!isVisit[r][c] && A[r][c] != 0){
                    int iceSize = findIceSize(r, c, isVisit);

                    maxIceSize = Math.max(maxIceSize, iceSize);
                }
            }
        }


        System.out.println(iceSum);
        System.out.println(maxIceSize);
    }

    private static void show() {
        System.out.println();
        for(int r=0; r<SIZE; r++){
            System.out.println();
            for(int c=0; c<SIZE; c++){
                System.out.printf("%d ", A[r][c]);
            }
        }
        System.out.println();
    }


    private static int findIceSize(int r, int c, boolean[][] isVisit) {
        int iceSize = 1;
        isVisit[r][c] = true;

        for(int dir=0; dir<4; dir++){
            int nr = r + DR[dir];
            int nc = c + DC[dir];

            if(isInbound(nr, nc) && !isVisit[nr][nc] && A[nr][nc] != 0){
                iceSize += findIceSize(nr, nc, isVisit);
            }
        }

        return iceSize;
    }

    private static boolean meltDown(int r, int c) {
        int nearIceCount = 0;

        for(int dir=0; dir<4; dir++){
            int nr = r + DR[dir];
            int nc = c + DC[dir];

            if(isInbound(nr, nc) && A[nr][nc] != 0){
                nearIceCount++;
            }
        }

        return nearIceCount < 3;
    }

    private static boolean isInbound(int r, int c) {
        return 0<=r && r<SIZE && 0<=c && c<SIZE;
    }

    private static void fireStrom(int r, int c, int size) {
        int[][] rotateArr = new int[size][size];

        for(int j=0; j<size; j++){
            for(int i=0; i<size; i++){
                rotateArr[i][j] = A[r+size-1-j][c+i];
            }
        }

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                A[r + i][c + j] = rotateArr[i][j];
            }
        }
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        SIZE = (int) Math.pow(2, N);
        A = new int[SIZE][SIZE];
        for(int r=0; r<SIZE; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<SIZE; c++){
                int ice = Integer.parseInt(st.nextToken());
                A[r][c] = ice;
                iceSum += ice;
            }
        }

        L = new int[Q];
        st = new StringTokenizer(bf.readLine());
        for(int i=0; i<Q; i++){
            L[i] = Integer.parseInt(st.nextToken());
        }
    }
}
