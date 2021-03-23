package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Plague{
    int i, j;

    Plague(int i, int j){
        this.i = i;
        this.j = j;
    }

    public Plague clone(){
        return new Plague(i, j);
    }

    public void show(){
        System.out.printf("Plague (%d, %d)\n", i, j);
    }
}

class EmptyArea{
    int i, j;

    EmptyArea(int i, int j){
        this.i = i;
        this.j = j;
    }
}

class Lab{
    int N, M;
    int[][] map;
    int safeArea = 0;
    Queue<Plague> plagueQueue = new LinkedList<>();

    Lab(int N, int M, int[][] map, Queue<Plague> plagueQueue){
        this.N = N;
        this.M = M;
        this.map = new int[N][M];
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                int d = map[i][j];
                this.map[i][j] = d;
                if(d == 0) safeArea++;
            }
        }

        for(Plague plague : plagueQueue){
            this.plagueQueue.add(plague.clone());
        }
    }

    public void show(){
        System.out.printf("N: %d, M: %d\n", N, M);
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.printf("safeArea: %d\n", safeArea);
        for(Plague plague : plagueQueue){
            plague.show();
        }
    }

    public Lab clone(){
        return new Lab(N, M, map, plagueQueue);
    }

    public void makeWall(EmptyArea emptyArea){
        map[emptyArea.i][emptyArea.j] = 1;
        safeArea--;
    }

    private void spreadPlague(Plague plague){
        int p_i = plague.i;
        int p_j = plague.j;
        int[] pi = {p_i-1,  p_i,    p_i+1,  p_i};
        int[] pj = {p_j,    p_j+1,  p_j,    p_j-1};

        for(int dir=0; dir<4; dir++){
            if(isInbound(pi[dir], pj[dir]) && isEmpty(pi[dir], pj[dir])){
                map[pi[dir]][pj[dir]] = 2;
                safeArea--;
                plagueQueue.add(new Plague(pi[dir], pj[dir]));
            }
        }

    }

    private boolean isInbound(int i, int j){
        return (0 <= i && i < N && 0 <= j && j < M);
    }

    private boolean isEmpty(int i, int j){
        return map[i][j] == 0;
    }

    public int test(){
        while(!plagueQueue.isEmpty()){
            Plague plague = plagueQueue.poll();
            this.spreadPlague(plague);
        }

        return safeArea;
    }
}

public class Main {
    static int N, M;
    static int[][] map;
    static Queue<Plague> plagueQueue = new LinkedList<>();
    static ArrayList<EmptyArea> emptyAreas = new ArrayList<>();
    static Lab originLab;

    public static void main(String[] args) throws IOException {
        getInput();

        int emptyAreaSize = emptyAreas.size();
        int maxSafeArea = 0;

        for(int a=0; a<emptyAreaSize; a++){
            for(int b=a+1; b<emptyAreaSize; b++){
                for(int c=b+1; c<emptyAreaSize; c++){
                    Lab newLab = originLab.clone();

                    newLab.makeWall(emptyAreas.get(a));
                    newLab.makeWall(emptyAreas.get(b));
                    newLab.makeWall(emptyAreas.get(c));

                    int currSafeArea = newLab.test();
                    maxSafeArea = Math.max(maxSafeArea, currSafeArea);
                }
            }
        }

        System.out.println(maxSafeArea);
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(bf.readLine());
            for(int j=0; j<M; j++){
                int d = Integer.parseInt(st.nextToken());
                map[i][j] = d;
                if(d == 2) plagueQueue.add(new Plague(i, j));
                else if(d == 0) emptyAreas.add(new EmptyArea(i, j));
            }
        }

        originLab = new Lab(N, M, map, plagueQueue);
    }
}
