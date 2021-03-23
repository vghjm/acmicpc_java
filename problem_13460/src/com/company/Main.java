package com.company;

import java.util.*;

class Simulation {
    int n, m;
    char[][] board;
    int tiltCount;
    int Rx, Ry;
    int Bx, By;
    boolean redGoal, blueGoal;

    Simulation(char[][] initBoard, int N, int M, int tiltCount){
        n = N;
        m = M;
        board = new char[N][M];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                board[i][j] = initBoard[i][j];
                if(board[i][j] == 'R'){
                    Rx = i;
                    Ry = j;
                }else if(board[i][j] == 'B'){
                    Bx = i;
                    By = j;
                }
            }
        }
        this.tiltCount = tiltCount;
        redGoal = false;
        blueGoal = false;
    }

    public void show(String str){
        System.out.println("show simulation");
        System.out.printf("desc: %s\n", str);
        System.out.printf("n: %d, m: %d\n", n, m);
        System.out.printf("tiltCount: %d\n", tiltCount);
        System.out.printf("Rx: %d, Ry: %d\n", Rx, Ry);
        System.out.printf("Bx: %d, By: %d\n", Bx, By);
        System.out.printf("redGoal: %b, blueGoal: %b\n", redGoal, blueGoal);
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                System.out.printf("%c ", board[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.println("end simulation\n");
    }

    public Simulation tiltUp(){
        Simulation sim = new Simulation(board, n, m, tiltCount+1);

        if(sim.Rx < sim.Bx){
            // red first
            sim.board[sim.Rx][sim.Ry] = '.';
            while(sim.Rx > 0){
                char c = sim.board[sim.Rx-1][sim.Ry];
                if(c == '.'){
                    sim.Rx--;
                }else if(c == 'B' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Rx--;
                    sim.redGoal = true;
                    break;
                }
            }
            if(!sim.redGoal) sim.board[sim.Rx][sim.Ry] = 'R';

            sim.board[sim.Bx][sim.By] = '.';
            while(sim.Bx > 0){
                char c = sim.board[sim.Bx-1][sim.By];
                if(c == '.'){
                    sim.Bx--;
                }else if(c == 'R' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Bx--;
                    sim.blueGoal = true;
                    break;
                }
            }
            if(!sim.blueGoal) sim.board[sim.Bx][sim.By] = 'B';
        }else{
            // blue first
            sim.board[sim.Bx][sim.By] = '.';
            while(sim.Bx > 0){
                char c = sim.board[sim.Bx-1][sim.By];
                if(c == '.'){
                    sim.Bx--;
                }else if(c == 'R' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Bx--;
                    sim.blueGoal = true;
                    break;
                }
            }
            if(!sim.blueGoal) sim.board[sim.Bx][sim.By] = 'B';

            sim.board[sim.Rx][sim.Ry] = '.';
            while(sim.Rx > 0){
                char c = sim.board[sim.Rx-1][sim.Ry];
                if(c == '.'){
                    sim.Rx--;
                }else if(c == 'B' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Rx--;
                    sim.redGoal = true;
                    break;
                }
            }
            if(!sim.redGoal) sim.board[sim.Rx][sim.Ry] = 'R';
        }

        return sim;
    }

    public Simulation tiltDown(){
        Simulation sim = new Simulation(board, n, m, tiltCount+1);

        if(sim.Rx > sim.Bx){
            // red first
            sim.board[sim.Rx][sim.Ry] = '.';
            while(sim.Rx < n-1){
                char c = sim.board[sim.Rx+1][sim.Ry];
                if(c == '.'){
                    sim.Rx++;
                }else if(c == 'B' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Rx++;
                    sim.redGoal = true;
                    break;
                }
            }
            if(!sim.redGoal) sim.board[sim.Rx][sim.Ry] = 'R';

            sim.board[sim.Bx][sim.By] = '.';
            while(sim.Bx < n-1){
                char c = sim.board[sim.Bx+1][sim.By];
                if(c == '.'){
                    sim.Bx++;
                }else if(c == 'R' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Bx++;
                    sim.blueGoal = true;
                    break;
                }
            }
            if(!sim.blueGoal) sim.board[sim.Bx][sim.By] = 'B';
        }else{
            // blue first
            sim.board[sim.Bx][sim.By] = '.';
            while(sim.Bx < n-1){
                char c = sim.board[sim.Bx+1][sim.By];
                if(c == '.'){
                    sim.Bx++;
                }else if(c == 'R' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Bx++;
                    sim.blueGoal = true;
                    break;
                }
            }
            if(!sim.blueGoal) sim.board[sim.Bx][sim.By] = 'B';

            sim.board[sim.Rx][sim.Ry] = '.';
            while(sim.Rx < n-1){
                char c = sim.board[sim.Rx+1][sim.Ry];
                if(c == '.'){
                    sim.Rx++;
                }else if(c == 'B' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Rx++;
                    sim.redGoal = true;
                    break;
                }
            }
            if(!sim.redGoal) sim.board[sim.Rx][sim.Ry] = 'R';
        }

        return sim;
    }

    public Simulation tiltleft(){
        Simulation sim = new Simulation(board, n, m, tiltCount+1);

        if(sim.Ry < sim.By){
            // red first
            sim.board[sim.Rx][sim.Ry] = '.';
            while(sim.Ry > 0){
                char c = sim.board[sim.Rx][sim.Ry-1];
                if(c == '.'){
                    sim.Ry--;
                }else if(c == 'B' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Ry--;
                    sim.redGoal = true;
                    break;
                }
            }
            if(!sim.redGoal) sim.board[sim.Rx][sim.Ry] = 'R';

            sim.board[sim.Bx][sim.By] = '.';
            while(sim.By > 0){
                char c = sim.board[sim.Bx][sim.By-1];
                if(c == '.'){
                    sim.By--;
                }else if(c == 'R' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.By--;
                    sim.blueGoal = true;
                    break;
                }
            }
            if(!sim.blueGoal) sim.board[sim.Bx][sim.By] = 'B';
        }else{
            // blue first
            sim.board[sim.Bx][sim.By] = '.';
            while(sim.By > 0){
                char c = sim.board[sim.Bx][sim.By-1];
                if(c == '.'){
                    sim.By--;
                }else if(c == 'R' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.By--;
                    sim.blueGoal = true;
                    break;
                }
            }
            if(!sim.blueGoal) sim.board[sim.Bx][sim.By] = 'B';

            sim.board[sim.Rx][sim.Ry] = '.';
            while(sim.Ry > 0){
                char c = sim.board[sim.Rx][sim.Ry-1];
                if(c == '.'){
                    sim.Ry--;
                }else if(c == 'B' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Ry--;
                    sim.redGoal = true;
                    break;
                }
            }
            if(!sim.redGoal) sim.board[sim.Rx][sim.Ry] = 'R';
        }

        return sim;
    }

    public Simulation tiltRight(){
        Simulation sim = new Simulation(board, n, m, tiltCount+1);

        if(sim.Ry > sim.By){
            // red first
            sim.board[sim.Rx][sim.Ry] = '.';
            while(sim.Ry < m-1){
                char c = sim.board[sim.Rx][sim.Ry+1];
                if(c == '.'){
                    sim.Ry++;
                }else if(c == 'B' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Ry++;
                    sim.redGoal = true;
                    break;
                }
            }
            if(!sim.redGoal) sim.board[sim.Rx][sim.Ry] = 'R';

            sim.board[sim.Bx][sim.By] = '.';
            while(sim.By < m-1){
                char c = sim.board[sim.Bx][sim.By+1];
                if(c == '.'){
                    sim.By++;
                }else if(c == 'R' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.By++;
                    sim.blueGoal = true;
                    break;
                }
            }
            if(!sim.blueGoal) sim.board[sim.Bx][sim.By] = 'B';
        }else{
            // blue first
            sim.board[sim.Bx][sim.By] = '.';
            while(sim.By < m-1){
                char c = sim.board[sim.Bx][sim.By+1];
                if(c == '.'){
                    sim.By++;
                }else if(c == 'R' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.By++;
                    sim.blueGoal = true;
                    break;
                }
            }
            if(!sim.blueGoal) sim.board[sim.Bx][sim.By] = 'B';

            sim.board[sim.Rx][sim.Ry] = '.';
            while(sim.Ry < m-1){
                char c = sim.board[sim.Rx][sim.Ry+1];
                if(c == '.'){
                    sim.Ry++;
                }else if(c == 'B' || c == '#'){
                    break;
                }else if(c == 'O'){
                    sim.Ry++;
                    sim.redGoal = true;
                    break;
                }
            }
            if(!sim.redGoal) sim.board[sim.Rx][sim.Ry] = 'R';
        }

        return sim;
    }

    public boolean isChanged(Simulation sim){
        if(Rx == sim.Rx && Ry == sim.Ry && Bx == sim.Bx && By == sim.By) return false;
        return true;
    }
}

public class Main {

    public static void main(String[] args) {
        int N, M;
        char[][] initBoard;

//        입력 받기
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        initBoard = new char[N][M];

        for(int i=0; i<N; i++){
            String s = sc.next();
            for(int j=0; j<M; j++){
                initBoard[i][j] = s.charAt(j);
            }
        }

//        너비탐색
        Simulation sim = new Simulation(initBoard, N, M, 0);
//        sim.show("origin");
//        sim.tiltUp().show("tilt up");
//        sim.tiltDown().show("tilt down");
//        sim.tiltleft().show("tilt left");
//        sim.tiltRight().show("tilt right");

        Queue<Simulation> q = new LinkedList<>();
        q.add(sim);
        int answer = -1;
        while(!q.isEmpty()){
            Simulation originSim = q.poll();

            if(originSim.tiltCount > 10) continue;
            if(originSim.redGoal == true || originSim.blueGoal == true){
                if(originSim.blueGoal == true) continue;

                answer = originSim.tiltCount;
                break;
            }

            Simulation upSim = originSim.tiltUp();
            Simulation downSim = originSim.tiltDown();
            Simulation leftSim = originSim.tiltleft();
            Simulation rightSim = originSim.tiltRight();
            if(upSim.isChanged(originSim)) q.add(upSim);
            if(downSim.isChanged(originSim)) q.add(downSim);
            if(leftSim.isChanged(originSim)) q.add(leftSim);
            if(rightSim.isChanged(originSim)) q.add(rightSim);
        }

        System.out.println(answer);
    }
}
