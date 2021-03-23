package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] T;
    static int[] P;
    static int maxIncome = 0;

    public static void main(String[] args) throws IOException {
	    getInput();

	    startFrom(1, 0);

	    System.out.println(maxIncome);
    }

    private static void startFrom(int day, int totalIncome){
        if(day > N+1) return;
        if(day == N+1) {
            maxIncome = Math.max(maxIncome, totalIncome);
            return;
        }

        // do work today
        startFrom(day + T[day], totalIncome + P[day]);

        // no work today
        startFrom(day+1, totalIncome);
    }


    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        T = new int[N+1];
        P = new int[N+1];
        for(int i=1; i<=N; i++){
            st = new StringTokenizer(bf.readLine());
            T[i] = Integer.parseInt(st.nextToken());
            P[i] = Integer.parseInt(st.nextToken());
        }
    }
}
