package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

abstract class Camera{
    int type;
    int px, py;
    int dir;
    final static int[] dx = {-1, 0, +1, 0};
    final static int[] dy = {0, +1, 0, -1};

    Camera(int type, int px, int py){
        this.type = type;
        this.px = px;
        this.py = py;
        dir = 0;
    }

    abstract public int viewing(int[][] map, int N, int M);
}

class CameraType1 extends Camera{
    CameraType1(int px, int py) {
        super(1, px, py);
    }

    @Override
    public int viewing(int[][] map, int N, int M) {
        int viewCount = 0;
        int x, y;

        x = px + dx[dir];
        y = py + dy[dir];
        while(0<=x && x<N && 0<=y && y<M && map[x][y] != 6){
            if(map[x][y] == 0){
                map[x][y] = -1;
                viewCount++;
            }

            x += dx[dir];
            y += dy[dir];
        }

        return viewCount;
    }
}
class CameraType2 extends Camera{
    CameraType2(int px, int py) {
        super(2, px, py);
    }

    @Override
    public int viewing(int[][] map, int N, int M) {
        int viewCount = 0;
        int x, y;

        x = px + dx[dir];
        y = py + dy[dir];
        while(0<=x && x<N && 0<=y && y<M && map[x][y] != 6){
            if(map[x][y] == 0){
                map[x][y] = -1;
                viewCount++;
            }

            x += dx[dir];
            y += dy[dir];
        }

        int reverseDir = (dir+2)%4;
        x = px + dx[reverseDir];
        y = py + dy[reverseDir];
        while(0<=x && x<N && 0<=y && y<M && map[x][y] != 6){
            if(map[x][y] == 0){
                map[x][y] = -1;
                viewCount++;
            }

            x += dx[reverseDir];
            y += dy[reverseDir];
        }

        return viewCount;
    }
}
class CameraType3 extends Camera{
    CameraType3(int px, int py) {
        super(3, px, py);
    }

    @Override
    public int viewing(int[][] map, int N, int M) {
        int viewCount = 0;
        int x, y;

        x = px + dx[dir];
        y = py + dy[dir];
        while(0<=x && x<N && 0<=y && y<M && map[x][y] != 6){
            if(map[x][y] == 0){
                map[x][y] = -1;
                viewCount++;
            }

            x += dx[dir];
            y += dy[dir];
        }

        int rightDir = (dir+1)%4;
        x = px + dx[rightDir];
        y = py + dy[rightDir];
        while(0<=x && x<N && 0<=y && y<M && map[x][y] != 6){
            if(map[x][y] == 0){
                map[x][y] = -1;
                viewCount++;
            }

            x += dx[rightDir];
            y += dy[rightDir];
        }

        return viewCount;
    }
}
class CameraType4 extends Camera{
    CameraType4(int px, int py) {
        super(4, px, py);
    }

    @Override
    public int viewing(int[][] map, int N, int M) {
        int viewCount = 0;
        int x, y;


        for(int i=0; i<4; i++){
            if(i==dir) continue;

            x = px + dx[i];
            y = py + dy[i];
            while(0<=x && x<N && 0<=y && y<M && map[x][y] != 6){
                if(map[x][y] == 0){
                    map[x][y] = -1;
                    viewCount++;
                }

                x += dx[i];
                y += dy[i];
            }
        }

        return viewCount;
    }
}
class CameraType5 extends Camera{
    CameraType5(int px, int py) {
        super(5, px, py);
    }

    @Override
    public int viewing(int[][] map, int N, int M) {
        int viewCount = 0;
        int x, y;

        for(int i=0; i<4; i++){
            x = px + dx[i];
            y = py + dy[i];
            while(0<=x && x<N && 0<=y && y<M && map[x][y] != 6){
                if(map[x][y] == 0){
                    map[x][y] = -1;
                    viewCount++;
                }

                x += dx[i];
                y += dy[i];
            }
        }

        return viewCount;
    }
}

class Simulation{
    int N, M;
    int[][] map;
    ArrayList<Camera> cameras;

    Simulation(int N, int M, int[][] map, ArrayList<Camera> cameras){
        this.N = N;
        this.M = M;
        this.map = new int[N][M];
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                this.map[i][j] = map[i][j];
            }
        }
        this.cameras = cameras;
    }

    public int test(){
        int maxViewSpot = 0;

        for(Camera camera : cameras){
            maxViewSpot += camera.viewing(map, N, M);
        }

        return maxViewSpot;
    }
}

public class Main {
    static int N, M;
    static int[][] map;
    static int emptyCount = 0;
    static ArrayList<Camera> cameras = new ArrayList<>();
    static int minBlindSpot = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        getInput();

        dfs(0);

        System.out.println(minBlindSpot);
    }

    private static void dfs(int i){
        if(i == cameras.size()){
            Simulation simulation = new Simulation(N, M, map, cameras);
            int maxViewSpot = simulation.test();
            minBlindSpot = Math.min(minBlindSpot, emptyCount - maxViewSpot);
            return;
        }

        for(int dir=0; dir<4; dir++){
            cameras.get(i).dir = dir;
            dfs(i+1);
        }
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
                switch (d){
                    case 0:
                        emptyCount++;
                        break;
                    case 1:
                        cameras.add(new CameraType1(i, j));
                        break;
                    case 2:
                        cameras.add(new CameraType2(i, j));
                        break;
                    case 3:
                        cameras.add(new CameraType3(i, j));
                        break;
                    case 4:
                        cameras.add(new CameraType4(i, j));
                        break;
                    case 5:
                        cameras.add(new CameraType5(i, j));
                        break;
                }
            }
        }
    }
}
