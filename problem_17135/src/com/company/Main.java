package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Unit{
    int row, col;

    Unit(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int dist(Unit unit){
        return Math.abs(this.row - unit.row) + Math.abs(this.col - unit.col);
    }
}

class Enemy extends Unit{
    int N;

    Enemy(int row, int col, int N) {
        super(row, col);
        this.N = N;
    }

    public Enemy clone(){
        return new Enemy(row, col, N);
    }

    public void advance(){
        row+=1;
    }

    public boolean isEnd(){
        return row >= N;
    }

    public void show(){
        System.out.printf("Enemy (r, c) = (%d, %d)\n", this.row, this.col);
    }
}

class Archer extends Unit{
    int D;
    int M;

    Archer(int row, int col, int D, int M) {
        super(row, col);
        this.D = D;
        this.M = M;
    }

    public void show(){
        System.out.printf("Archer col: %d\n", this.col);
    }


    public Enemy attack(ArrayList<Enemy> enemyList){
        int leftCol = M;
        int closest = D+1;
        Enemy targetEnemy = null;

        for(Enemy enemy : enemyList){
            int dist = this.dist(enemy);
            if(dist <= D){
                if(dist < closest){
                    targetEnemy = enemy;
                    leftCol = enemy.col;
                    closest = dist;
                }else if(dist == closest){
                    if(enemy.col <= leftCol){
                        targetEnemy = enemy;
                        leftCol = enemy.col;
                        closest = dist;
                    }
                }
            }
        }

        return targetEnemy;
    }
}

class Simulation{
    int N, M, D;
    int score = 0;
    ArrayList<Enemy> enemyList = new ArrayList<>();
    ArrayList<Archer> archerList;

    Simulation(int N, int M, int D, ArrayList<Enemy> enemyList, ArrayList<Archer> archerList){
        this.N = N;
        this.M = M;
        this.D = D;
        for(Enemy enemy : enemyList){
            this.enemyList.add(enemy.clone());
        }
        this.archerList = archerList;
    }

    public void show(){
        for(Enemy enemy : enemyList) enemy.show();
        for(Archer archer : archerList) archer.show();
    }

    public int run(){
//        show();
        while(!enemyList.isEmpty()){
            Set<Enemy> removeEnemySet = new HashSet<>();

            // 공격
            for(Archer archer : archerList){
                Enemy killedEnemy = archer.attack(enemyList);
                if(killedEnemy != null) removeEnemySet.add(killedEnemy);
            }

            // 죽임
            for(Enemy enemy : removeEnemySet){
                enemyList.remove(enemy);
                score+=1;
            }

            // 전진
            for(Enemy enemy : enemyList){
                enemy.advance();
                if(enemy.isEnd()) removeEnemySet.add(enemy);
            }

            // 제거
            for(Enemy enemy : removeEnemySet){
                enemyList.remove(enemy);
            }
//            show();
        }

        return score;
    }

}

public class Main {

    public static void main(String[] args) throws IOException {
        int N, M, D;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        ArrayList<Enemy> enemyList = new ArrayList<>();
        for(int r=0; r<N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<M; c++){
                if(Integer.parseInt(st.nextToken()) == 1){
                    enemyList.add(new Enemy(r, c, N));
                }
            }
        }

        ArrayList<ArrayList<Archer>> archerCombination = new ArrayList<>();
        ArrayList<int[]> archerPosList = combination3(M);
        for(int[] archerPos : archerPosList){
            ArrayList<Archer> archerList = new ArrayList<>();
            archerList.add(new Archer(N, archerPos[0], D, M));
            archerList.add(new Archer(N, archerPos[1], D, M));
            archerList.add(new Archer(N, archerPos[2], D, M));
            archerCombination.add(archerList);
        }

        ArrayList<Simulation> simulations = new ArrayList<>();
        for(ArrayList<Archer> archerList : archerCombination){
            simulations.add(new Simulation(N, M, D, enemyList, archerList));
        }

        int maxScore = 0;
        for(Simulation simulation : simulations){
//            System.out.printf("\n시뮬 시작\n");
            int score = simulation.run();
            if(score >= maxScore) maxScore = score;
//            System.out.printf("시뮬종료 score: %d\n", score);
        }

        System.out.println(maxScore);
    }

    public static ArrayList<int[]> combination3(int M){
        ArrayList<int[]> arrayList = new ArrayList<>();

        for(int a=0; a<M; a++){
            for(int b=a+1; b<M; b++){
                for(int c=b+1; c<M; c++){
                    arrayList.add(new int[]{a, b, c});
                }
            }
        }

        return arrayList;
    }
}
