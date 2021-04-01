package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Oper{
    int x, d, k;

    Oper(int x, int d, int k){
        this.x = x;
        this.d = d;
        this.k = k;
    }
}

public class Main {
    private static int N, M, T;
    private static int[][] circle;
    private static int[] topIndex;
    private static int sum = 0;
    private static int count = 0;
    private static boolean[][] isVisited;
    private static final Queue<Oper> operQueue = new LinkedList<>();
    private static final int[] DIR = {-1, +1};
    private static final int[] DR = {0, 0, -1, +1};
    private static final int[] DC = {-1, +1, 0, 0};


    public static void main(String[] args) throws IOException {
        setInput();

        while(!operQueue.isEmpty()){
            Oper oper = operQueue.poll();

            processOper(oper);

//            show();
        }

        System.out.println(sum);
    }

    private static void show() {
        System.out.println();
        for(int r=0; r<N; r++){
            for(int c=0; c<M; c++){
                System.out.printf("%d ", get(r, c));
            }
            System.out.println();
        }
    }

    private static void processOper(Oper oper) {
        int x = oper.x;
        int d = oper.d;
        int k = oper.k;

        spinAll(x, d, k);
//        show();
        isVisited = new boolean[N][M];
        if(!hasSameAdjacent()){
            flatten();
        }
    }

    private static void flatten() {
        int newSum = sum;

        for(int r=0; r<N; r++){
            for(int c=0; c<M; c++){
                int d = circle[r][c];

                if(d == 0) continue;
                if(d * count > sum){
                    circle[r][c]--;
                    newSum--;
                }else if(d * count < sum){
                    circle[r][c]++;
                    newSum++;
                }
            }
        }

        sum = newSum;
    }


    private static boolean hasSameAdjacent() {
        boolean hasSameAdjacent = false;

        for(int r=0; r<N; r++){
            for(int c=0; c<M; c++){
                if(isVisited(r, c)) continue;
                int num = get(r, c);

                if(num != 0){
                    if(findSameAdjacent(r, c, num) > 1) {
                        remove(r, c);
                        hasSameAdjacent = true;
                    }
                }
            }
        }

        return hasSameAdjacent;
    }

    private static int realC(int r, int c){
        int rc = (topIndex[r] + c) % M;
        if(rc < 0) rc += M;

        return rc;
    }

    private static int get(int r, int c) {
        if(r < 0 || r >= N) return 0;

        int rc = realC(r, c);

        return circle[r][rc];
    }

    private static int findSameAdjacent(int r, int c, int num) {
        int sameAdjacentCount = 1;
        setVisited(r, c);

        for(int i=0; i<4; i++){
            int nr = r + DR[i];
            int nc = c + DC[i];

            if(!isVisited(nr, nc) && get(nr, nc) == num){
                sameAdjacentCount += findSameAdjacent(nr, nc, num);
                remove(nr, nc);
            }
        }

        return sameAdjacentCount;
    }

    private static void setVisited(int r, int c) {
        int rc = realC(r, c);
        isVisited[r][rc] = true;
    }

    private static boolean isVisited(int r, int c) {
        if(r < 0 || r >= N) return true;

        return isVisited[r][realC(r, c)];
    }

    private static void remove(int r, int c){
        int rc = realC(r, c);

        sum -= circle[r][rc];
        circle[r][rc] = 0;
        count--;
    }

    private static void spinAll(int x, int d, int k) {
        for(int r=x-1; r<N; r+=x){
            int i = (topIndex[r] + DIR[d] * k) % M;
            if(i < 0) i += M;

            topIndex[r] = i;
        }
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        circle = new int[N][M];
        topIndex = new int[N];
        for(int r=0; r<N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<M; c++){
                int d = Integer.parseInt(st.nextToken());
                circle[r][c] = d;
                sum += d;
                count++;
            }
        }

        for(int t=0; t<T; t++){
            st = new StringTokenizer(bf.readLine());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            operQueue.add(new Oper(x, d, k));
        }
    }
}
