package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main {
    static int N, M;
    static int emptyCount = 0;
    static ArrayList<Point> virusList = new ArrayList<>();
    static boolean[] isActivate = new boolean[10];
    static int[][] map;
    static int minTime = Integer.MAX_VALUE;
    final static int[] dx = {0, 0, +1, -1};
    final static int[] dy = {-1, +1, 0, 0};

    public static void main(String[] args) throws IOException {
        setInput();

        dfs(0, 0);

        if(minTime == Integer.MAX_VALUE) minTime = -1;
        System.out.println(minTime);
    }

    private static void dfs(int activeCount, int start) {
        if(activeCount == M){
            int[][] time = new int[N][N];

            for(int i=0; i<N; i++){
                for(int j=0; j<N; j++){
                    time[i][j] = -1;
                }
            }

            spread(time);
        }else{
            for(int i=start; i< virusList.size(); i++){
                isActivate[i] = true;
                dfs(activeCount + 1, i + 1);
                isActivate[i] = false;
            }
        }
    }

    private static void spread(int[][] time) {
        int spreadTime = 0;
        int infectedPlace = 0;
        Queue<Point> virusQueue = new LinkedList<>();

        for(int i=0; i< virusList.size(); i++)
            if(isActivate[i]) {
                Point virus = virusList.get(i);
                virusQueue.add(virus);
                time[virus.x][virus.y] = 0;
            }

        while(!virusQueue.isEmpty()){
            Point virus = virusQueue.poll();
            int x = virus.x;
            int y = virus.y;
            int beforeTime = time[x][y];

            for(int dir=0; dir<4; dir++){
                int nx = x + dx[dir];
                int ny = y +dy[dir];

                if(0 <= nx && nx < N && 0 <= ny && ny < N){
                    if(map[nx][ny] != 1 && time[nx][ny] == -1){
                        virusQueue.add(new Point(nx, ny));
                        time[nx][ny] = beforeTime + 1;

                        if(map[nx][ny] == 0) {
                            infectedPlace++;
                            spreadTime = time[nx][ny];
                        }
                    }
                }
            }
        }

        if(infectedPlace == emptyCount) minTime = Math.min(minTime, spreadTime);
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        for(int r=0; r<N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<N; c++){
                int d = Integer.parseInt(st.nextToken());

                if(d == 0) emptyCount++;
                else if(d == 2) virusList.add(new Point(r, c));
                map[r][c] = d;
            }
        }
    }


}
