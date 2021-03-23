package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Simulation{
    private int[][] board;
    private int n;
    public int maxValue;
    public int step;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    Simulation(int[][] initBoard, int n, int step){
        board = new int[n][n];
        maxValue = 0;
        this.n = n;
        this.step = step;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                // copy board
                board[i][j] = initBoard[i][j];

                // update max value
                if(board[i][j] > maxValue) maxValue = board[i][j];
            }
        }
    }

    private static void compress(int[] arr, int dir, int n){
        int savePos;
        boolean isColliedJustBefore = false;
        boolean isFirstTry = true;

        if(dir == LEFT){
            savePos = 0; // leftmost

            for(int i=0; i<n; i++){
                if(arr[i] == 0) continue; // skip 0 value
                int pickedUpValue = arr[i];
                arr[i] = 0; // make empty

                if(isFirstTry){
                    isFirstTry = false;
                }else{
                    if(isColliedJustBefore == false && arr[savePos] == pickedUpValue){
                        isColliedJustBefore = true; // collide occur
                    }else{
                        isColliedJustBefore = false;
                        savePos++;
                    }
                }

                arr[savePos] += pickedUpValue;
            }
        }else if(dir == RIGHT){
            savePos = n-1; // rightmost

            for(int i=n-1; i>=0; i--){
                if(arr[i] == 0) continue; // skip 0 value
                int pickedUpValue = arr[i];
                arr[i] = 0; // make empty

                if(isFirstTry){
                    isFirstTry = false;
                }else{
                    if(isColliedJustBefore == false && arr[savePos] == pickedUpValue){
                        isColliedJustBefore = true; // collide occur
                    }else{
                        isColliedJustBefore = false;
                        savePos--;
                    }
                }

                arr[savePos] += pickedUpValue;
            }
        }
    }

    public void show(String str){
        // debug only
        System.out.println("show start");
        System.out.printf("desc: %s\n", str);
        System.out.printf("step: %d, maxValue: %d\n", step, maxValue);
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.printf("%d ", board[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.println("show end\n");
    }

    public Simulation swipeUp(){
        int[][] newBoard = new int[n][n];
        int[] arr = new int[n];

        for(int j=0; j<n; j++){
            for(int i=0; i<n; i++){
                arr[i] = board[i][j];
            }

            compress(arr, LEFT, n);

            for(int i=0; i<n; i++){
                newBoard[i][j] = arr[i];
            }
        }

        return new Simulation(newBoard, n, step+1);
    }

    public Simulation swipeDown(){
        int[][] newBoard = new int[n][n];
        int[] arr = new int[n];

        for(int j=0; j<n; j++){
            for(int i=0; i<n; i++){
                arr[i] = board[i][j];
            }

            compress(arr, RIGHT, n);

            for(int i=0; i<n; i++){
                newBoard[i][j] = arr[i];
            }
        }

        return new Simulation(newBoard, n, step+1);
    }

    public Simulation swipeLeft(){
        int[][] newBoard = new int[n][n];
        int[] arr = new int[n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                arr[j] = board[i][j];
            }

            compress(arr, LEFT, n);

            for(int j=0; j<n; j++){
                newBoard[i][j] = arr[j];
            }
        }

        return new Simulation(newBoard, n, step+1);
    }

    public Simulation swipeRight(){
        int[][] newBoard = new int[n][n];
        int[] arr = new int[n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                arr[j] = board[i][j];
            }

            compress(arr, RIGHT, n);

            for(int j=0; j<n; j++){
                newBoard[i][j] = arr[j];
            }
        }

        return new Simulation(newBoard, n, step+1);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());

        int[][] initBoard = new int[N][N];
        for(int i=0; i<N; i++){
            StringTokenizer st = new StringTokenizer(bf.readLine());

            for(int j=0; j<N; j++){
                initBoard[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Queue<Simulation> q = new LinkedList<>();
        Simulation initSim = new Simulation(initBoard, N, 0);

        int maxValue = 0;
        q.add(initSim);
        while(!q.isEmpty()){
            Simulation sim = q.poll();
            if(sim.step > 5) continue;
            if(sim.maxValue > maxValue) maxValue = sim.maxValue;

            q.add(sim.swipeLeft());
            q.add(sim.swipeRight());
            q.add(sim.swipeUp());
            q.add(sim.swipeDown());
        }

        System.out.println(maxValue);
    }
}
