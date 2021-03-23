package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] S;
    static int minValue = Integer.MAX_VALUE;
    static int startTeamPower = 0;
    static int linkTeamPower = 0;
    static int[] membersTeamNumber;
    final static int START_TEAM = 1;
    final static int LINK_TEAM = 2;

    public static void main(String[] args) throws IOException {
	    getInput();

        dfs(0);

        System.out.println(minValue);
    }

    private static void dfs(int n){
        if(n == N){
            int memberCountDiff = 0;
            for(int team : membersTeamNumber){
                if(team == START_TEAM) memberCountDiff++;
                else memberCountDiff--;
            }

            if(memberCountDiff == 0) minValue = Math.min(minValue, Math.abs(startTeamPower-linkTeamPower));
            return;
        }

        startTeamPower += addTeam(n, START_TEAM);
        dfs(n+1);
        startTeamPower += removeTeam(n, START_TEAM);

        linkTeamPower += addTeam(n, LINK_TEAM);
        dfs(n+1);
        linkTeamPower += removeTeam(n, LINK_TEAM);
    }

    private static int addTeam(int n, int team){
        int powerChanged = 0;
        membersTeamNumber[n] = team;

        for(int i=0; i<n; i++){
            if(membersTeamNumber[i] == team){
                powerChanged += S[i][n] + S[n][i];
            }
        }

        return powerChanged;
    }

    private static int removeTeam(int n, int team){
        int powerChanged = 0;
        membersTeamNumber[n] = 0;

        for(int i=0; i<n; i++){
            if(membersTeamNumber[i] == team){
                powerChanged -= S[i][n] + S[n][i];
            }
        }

        return powerChanged;
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        S = new int[N][N];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(bf.readLine());
            for(int j=0; j<N; j++){
                S[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        membersTeamNumber = new int[N];
    }
}
