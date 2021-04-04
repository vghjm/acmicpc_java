package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M, K;
    static ArrayList<FireBall> fireBalls = new ArrayList<>();
    static Queue<FireBall>[][] map;
    static final int[] DR = {-1, -1, 0, +1, +1, +1, 0, -1};
    static final int[] DC = {0, +1, +1, +1, 0, -1, -1, -1};

    static class FireBall{
        int r, c, mass, speed, dir;

        FireBall(int r, int c, int m, int s, int d){
            this.r = r;
            this.c = c;
            this.mass = m;
            this.speed = s;
            this.dir = d;
        }

        public void merge(FireBall fireBall) {
            this.mass += fireBall.mass;
            this.speed += fireBall.speed;
        }
    }

    public static void main(String[] args) throws IOException {
        setInput();

        for(int k=0; k<K; k++){
            moveAllFireBalls();
            mergeAndDivide();
        }

        int massSum = 0;
        for(FireBall fireBall : fireBalls){
            massSum += fireBall.mass;
        }

        System.out.println(massSum);
    }

    private static void mergeAndDivide() {
        for(int r=0; r<N; r++){
            for(int c=0; c<N; c++){
                Queue<FireBall> fireBallQueue = map[r][c];

                if(fireBallQueue.size() >= 2){
                    FireBall mergeFireball = new FireBall(r, c, 0, 0, 0);

                    int divideType = -1;
                    int mergeCount = 0;
                    while (!fireBallQueue.isEmpty()){
                        mergeCount++;
                        FireBall fireBall = fireBallQueue.poll();
                        fireBalls.remove(fireBall);

                        mergeFireball.merge(fireBall);

                        switch (divideType){
                            case -1:
                                divideType = fireBall.dir % 2; break;
                            case 0:
                                if(fireBall.dir % 2 != 0) divideType = 3;
                                break;
                            case 1:
                                if(fireBall.dir % 2 != 1) divideType = 3;
                                break;
                        }
                    }

                    int dir;
                    if(divideType == 3){
                        // 대각방향
                        dir = 1;
                    }else{
                        // 십자방향
                        dir = 0;
                    }

                    int mass = Math.floorDiv(mergeFireball.mass, 5);
                    int speed = Math.floorDiv(mergeFireball.speed, mergeCount);
                    if(mass != 0){
                        for(int i=0; i<4; i++){
                            FireBall dividedFireBall = new FireBall(r, c, mass, speed, dir);
                            fireBallQueue.add(dividedFireBall);
                            fireBalls.add(dividedFireBall);
                            dir += 2;
                        }
                    }
                }
            }
        }
    }

    private static void moveAllFireBalls() {
        for(FireBall fireBall : fireBalls){
            int r = fireBall.r;
            int c = fireBall.c;
            int nr = (r + fireBall.speed * DR[fireBall.dir]) % N;
            int nc = (c + fireBall.speed * DC[fireBall.dir]) % N;

            if(nr < 0) nr += N;
            if(nc < 0) nc += N;

            fireBall.r = nr;
            fireBall.c = nc;
            map[nr][nc].add(map[r][c].poll());
        }
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new Queue[N][N];
        for(int r=0; r<N; r++){
            for(int c=0; c<N; c++){
                map[r][c] = new LinkedList<>();
            }
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(bf.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            FireBall fireBall = new FireBall(r, c, m, s, d);
            map[r][c].add(fireBall);
            fireBalls.add(fireBall);
        }
    }
}
