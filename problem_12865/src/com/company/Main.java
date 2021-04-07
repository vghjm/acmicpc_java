package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Stuff{
    int w, v;

    Stuff(int w, int v){
        this.w = w;
        this.v = v;
    }
}

public class Main {
    static int N, K;
    static Stuff[] stuffs;

    public static void main(String[] args) throws IOException {
        setInput();

        int[][] valueSum = new int[N][100001];
        for(int i=0; i<N; i++) {
            for(int j=0; j<100001; j++){
                valueSum[i][j] = -1;
            }
        }
        int maxValueSum = dfs(valueSum, 0, 0);

        System.out.println(maxValueSum);
    }

    private static int dfs(int[][] valueSum, int index, int weightSum) {
        if(index >= N) return 0;
        if(valueSum[index][weightSum] == -1){
            int v1 = 0, v2 = 0;

            if(weightSum + stuffs[index].w <= K){
                v1 += stuffs[index].v + dfs(valueSum, index + 1, weightSum + stuffs[index].w);
            }
            v2 += dfs(valueSum, index + 1, weightSum);

            valueSum[index][weightSum] = Math.max(v1, v2);
        }

        return valueSum[index][weightSum];
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        stuffs = new Stuff[N];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(bf.readLine());

            int w = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            stuffs[i] = new Stuff(w, v);
        }
    }
}
