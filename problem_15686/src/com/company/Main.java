package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.*;

class Building{
    int r, c;

    Building(int r, int c){
        this.r = r;
        this.c = c;
    }

    public int dist(Building building){
        return Math.abs(building.r - r) + Math.abs(building.c - c);
    }
}

class ChickenBuilding extends Building{

    ChickenBuilding(int r, int c) {
        super(r, c);
    }
}

class HouseBuilding extends Building{

    HouseBuilding(int r, int c) {
        super(r, c);
    }
}

public class Main {
    static int N, M;
    static int[][] distMap;
    static int houseCount, chickenCount;
    static int minChickenDist = Integer.MAX_VALUE;


    public static void main(String[] args) throws IOException {
        getInput();

        dfs(0, new HashSet<>(), 0);

        System.out.println(minChickenDist);
    }

    private static void dfs(int chickenBuildingCount, Set<Integer> chickenSet, int i_s){
        if(chickenBuildingCount == M){
            int chickenDistSum = 0;

            for(int house=0; house<houseCount; house++){
                int chickenDist = Integer.MAX_VALUE;
                for(int chicken=0; chicken<chickenCount; chicken++){
                    if(chickenSet.contains(chicken)){
                        chickenDist = Math.min(chickenDist, distMap[house][chicken]);
                    }
                }
                chickenDistSum += chickenDist;
            }

            minChickenDist = Math.min(minChickenDist, chickenDistSum);
            return;
        }

        for(int i=i_s; i<chickenCount; i++){
            if(!chickenSet.contains(i)){
                chickenSet.add(i);
                dfs(chickenBuildingCount+1, chickenSet, i + 1);
                chickenSet.remove(i);
            }
        }
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        ArrayList<HouseBuilding> houseBuildingList = new ArrayList<>();
        ArrayList<ChickenBuilding> chickenBuildingList = new ArrayList<>();
        for(int r=0; r<N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<N; c++){
                int buildType = Integer.parseInt(st.nextToken());

                switch (buildType){
                    case 0: break;
                    case 1:
                        houseBuildingList.add(new HouseBuilding(r, c));
                        break;
                    case 2:
                        chickenBuildingList.add(new ChickenBuilding(r, c));
                        break;
                }
            }
        }

        houseCount = houseBuildingList.size();
        chickenCount = chickenBuildingList.size();
        distMap = new int[houseCount][chickenCount];

        for(int h=0; h<houseCount; h++){
            HouseBuilding houseBuilding = houseBuildingList.get(h);
            for(int c=0; c<chickenCount; c++){
                distMap[h][c] = houseBuilding.dist(chickenBuildingList.get(c));
            }
        }

    }
}
