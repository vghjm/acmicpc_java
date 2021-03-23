package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static int N, L;
    static int[][] map;
    static int crossableRoadCount = 0;

    public static void main(String[] args) throws IOException {
	    getInput();

	    for(int n=0; n<N; n++){
            ArrayList<Integer> rowList = new ArrayList<>();
            ArrayList<Integer> columnList = new ArrayList<>();

            for(int i=0; i<N; i++){
                rowList.add(map[i][n]);
                columnList.add(map[n][i]);
            }

            if(isCrossable(rowList)) {
                crossableRoadCount++;
            }
            if(isCrossable(columnList)) {
                crossableRoadCount++;
            }
        }

	    System.out.println(crossableRoadCount);
    }
    
    private static boolean isCrossable(ArrayList<Integer> heightList){
        boolean[] isUsed = new boolean[heightList.size()];
        int lastHeight = heightList.get(0);

        
        for(int i=1; i<heightList.size(); i++){
            int height = heightList.get(i);

            if(lastHeight == height){
                continue;
            }else if(lastHeight == height - 1){
                if(!backCheck(heightList, i-L, i-1, isUsed)){
                    return false;
                }
            }else if(lastHeight ==  height + 1){
                if(!backCheck(heightList, i, i+L-1, isUsed)){
                    return false;
                }
            }else{
                return false;
            }

            lastHeight = height;
        }
        
        return true;
    }

    private static boolean backCheck(ArrayList<Integer> array, int s, int e, boolean[] isUsed){
        if(s < 0 ||  array.size() <= e) return false;
        int origin = array.get(s);

        for(int i=s; i<=e; i++){
            if(array.get(i) != origin || isUsed[i]) return false;
            isUsed[i] = true;
        }

        return true;
    }


    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(bf.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}
