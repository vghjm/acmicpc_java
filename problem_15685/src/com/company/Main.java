package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Pos {
    int x, y;
    public static final int[] DX = {+1, 0, -1, 0};
    public static final int[] DY = {0, -1, 0, +1};

    Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    Pos(int x, int y, int dir){
        this.x = x + DX[dir];
        this.y = y + DY[dir];
    }

    public Pos spin(Pos pos){
        int ordinalX = x - pos.x;
        int ordinalY = y - pos.y;

        return new Pos(-ordinalY + pos.x, ordinalX + pos.y);
    }
}

class DragonCurve{
    ArrayList<Pos> posList = new ArrayList<>();

    DragonCurve(int x, int y, int d, int g){
        posList.add(new Pos(x, y));
        posList.add(new Pos(x, y, d));

        for(int i=1; i<=g; i++){
            int posListSize = posList.size();
            Pos end = posList.get(posListSize - 1);

            for(int j=posListSize - 2; j>=0; j--){
                posList.add(posList.get(j).spin(end));
            }
        }
    }

    public void mapping(int[][] map){
        for(Pos pos : posList){
            map[pos.y][pos.x] = 1;
        }
    }
}

public class Main {
    final static int MAP_SIZE = 101;
    static int[][] map = new int[MAP_SIZE][MAP_SIZE];
    static ArrayList<DragonCurve> dragonCurves = new ArrayList<>();
    static int N;

    public static void main(String[] args) throws IOException {
        getInput();

        for(DragonCurve dragonCurve : dragonCurves){
            dragonCurve.mapping(map);
        }

        int answer = 0;
        for(int i=0; i<MAP_SIZE-1; i++){
            for(int j=0; j<MAP_SIZE-1; j++){
                if(map[i][j] == 1 && map[i][j+1] == 1 && map[i+1][j] == 1 && map[i+1][j+1] == 1){
                    answer++;
                }
            }
        }

        System.out.println(answer);
    }

    static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(bf.readLine());
        for(int i=0; i<N; i++){
            int x, y, d, g;
            st = new StringTokenizer(bf.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());

            dragonCurves.add(new DragonCurve(x, y, d, g));
        }
    }
}
