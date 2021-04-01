package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] population;
    static int[][] pattern;

    public static void main(String[] args) throws IOException {
        setInput();

        int minDiff = Integer.MAX_VALUE;
        for(int d1=1; d1<=N; d1++){
            for(int d2=1; d2<=N; d2++){
                for(int x=1; x<=N-d1-d2; x++){
                    for(int y=1+d1; y<=N-d2; y++){
//        int x=2, y=4, d1=2, d2=2;
                        int[] populationSum = new int[6];

                        for(int r=1; r<=N; r++){
                            for(int c=1; c<=N; c++){
                                int index;

                                if(1 <= r && r < x+d1 && 1 <= c && c <= y){
                                    if(c - y < -(r - x)){
                                        index = 1;
                                    }else{
                                        index = 5;
                                    }
                                }else if(1 <= r && r <= x+d2 && y < c && c <= N){
                                    if(c - y > r - x){
                                        index = 2;
                                    }else{
                                        index = 5;
                                    }
                                }else if(x+d1 <= r && r <= N && 1 <= c && c < y-d1+d2){
                                    if(c - (y + d2 - d1) < r - (x + d1 + d2)){
                                        index = 3;
                                    }else{
                                        index = 5;
                                    }
                                }else if(x+d2 < r && r<=N && y-d1+d2 <= c && c <= N){
                                    if(c - (y + d2 - d1) > -(r - (x + d1 + d2))){
                                        index = 4;
                                    }else{
                                        index = 5;
                                    }
                                }else{
                                    index = 5;
                                }

                                populationSum[index] += population[r][c];
                                pattern[r][c] = index;
                            }
                        }

                        int min = populationSum[1];
                        int max = populationSum[1];
                        for(int i=2; i<=5; i++){
                            min = Math.min(min, populationSum[i]);
                            max = Math.max(max, populationSum[i]);
                        }

                        minDiff = Math.min(minDiff, max - min);


//                        System.out.println("\n 출력");
//                        for(int i=1; i<=5; i++){
//                            System.out.printf("%d ", populationSum[i]);
//                        }
//
//                        System.out.println("\n");
//                        for(int r=1; r<=N; r++){
//                            for(int c=1; c<=N; c++){
//                                System.out.printf("%d ", pattern[r][c]);
//                            }
//                            System.out.println();
//                        }
                    }
                }
            }
        }

//        System.out.println();
        System.out.println(minDiff);
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf. readLine());

        N = Integer.parseInt(st.nextToken());
        population = new int[N+1][N+1];
        pattern = new int[N+1][N+1];

        for(int r=1; r<=N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=1; c<=N; c++){
                population[r][c] = Integer.parseInt(st.nextToken());
            }
        }
    }
}
