package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] A;
    static int[] C = {0, 0, 0, 0};
    static int maxValue = Integer.MIN_VALUE;
    static int minValue = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        getInput();


        dfs(1, A[0]);

        System.out.println(maxValue);
        System.out.println(minValue);
    }

    private static void dfs(int i, int sum){
        if(i == N){
            check(sum);
        }

        if(C[0] > 0){
            C[0]--;
            dfs(i+1, sum + A[i]);
            C[0]++;
        }

        if(C[1] > 0){
            C[1]--;
            dfs(i+1, sum - A[i]);
            C[1]++;
        }

        if(C[2] > 0){
            C[2]--;
            dfs(i+1, sum * A[i]);
            C[2]++;
        }

        if(C[3] > 0){
            C[3]--;
            dfs(i+1, sum / A[i]);
            C[3]++;
        }
    }

    private static void check(int sum){
        maxValue = Math.max(maxValue, sum);
        minValue = Math.min(minValue, sum);
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        A = new int[N];
        for(int i=0; i<N; i++){
            A[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(bf.readLine());
        for(int i=0; i<4; i++){
            C[i] = Integer.parseInt(st.nextToken());
        }
    }
}
