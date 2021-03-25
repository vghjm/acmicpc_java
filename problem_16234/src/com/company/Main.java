package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Pos{
    int i, j;

    Pos(int i, int j){
        this.i = i;
        this.j = j;
    }
}

public class Main {
    static int N, L, R;
    static int[][] country;
    static boolean[][] isJoined;
    static ArrayList<ArrayList<Pos>> unions;

    public static void main(String[] args) throws IOException {
        getInput();

        int populationMovement = 0;
        while(true){
            int unionNumber = 0;

            isJoined = new boolean[N][N];
            unions = new ArrayList<>();
            for(int i=0; i<N; i++){
                for(int j=0; j<N; j++){
                    if(!isJoined[i][j]) {
                        unions.add(new ArrayList<>());
                        joinUnion(i, j, unionNumber);
                        unionNumber++;
                    }
                }
            }

            if(unionNumber == N*N) break;
            populationMovement++;

            for(ArrayList<Pos> union : unions){
                if(union.size() != 1){


                    int populationSum = 0;
                    for(Pos pos : union){
                        populationSum += country[pos.i][pos.j];
                    }

                    int newPopulation = Math.floorDiv(populationSum, union.size());
                    for(Pos pos : union){
                        country[pos.i][pos.j] = newPopulation;
                    }

                    // show();
                }
            }
        }

        System.out.println(populationMovement);
    }

    public static void show(){
        System.out.println();
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                System.out.printf("%d ", country[i][j]);
            }
            System.out.println();
        }
    }

    private static void joinUnion(int i, int j, int unionNumber){
        isJoined[i][j] = true;
        unions.get(unionNumber).add(new Pos(i, j));

        int population = country[i][j];
        if(isAlliable(i-1, j, population)) joinUnion(i-1, j, unionNumber);
        if(isAlliable(i, j-1, population)) joinUnion(i, j-1, unionNumber);
        if(isAlliable(i+1, j, population)) joinUnion(i+1, j, unionNumber);
        if(isAlliable(i, j+1, population)) joinUnion(i, j+1, unionNumber);
    }

    private static boolean isAlliable(int i, int j, int population){
        return 0 <= i && i < N && 0 <= j && j < N && !isJoined[i][j] && L <= Math.abs(country[i][j] - population) && Math.abs(country[i][j] - population) <= R;
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        country = new int[N][N];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(bf.readLine());
            for(int j=0; j<N; j++){
                country[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}
