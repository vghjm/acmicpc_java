package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, K, upPos = 0;
    static Belt[] belts;
    static Queue<Robot> robotQueue = new LinkedList<>();

    static class Belt{
        int duration;
        boolean hasRobot = false;

        Belt(int duration){
            this.duration = duration;
        }
    }

    static class Robot{
        int pos;

        Robot(int pos){
            this.pos = pos;
        }
    }

    public static void main(String[] args) throws IOException {
        setInput();

        int step = 0;
        do {
            step++;

            spinBelts();
            moveRobots();
            putUpRobot();
        } while (!isFinish());

        System.out.println(step);
    }

    private static boolean isFinish() {
        int durationZeroCount = 0;
        for(int i=0; i<2*N; i++){
            if(belts[i].duration == 0) durationZeroCount++;
        }

        return durationZeroCount >= K;
    }

    private static boolean isRaiseable(int pos){
        return !belts[pos].hasRobot && belts[pos].duration > 0;
    }

    private static void putUpRobot() {
        if(isRaiseable(upPos)){
            belts[upPos].hasRobot = true;
            belts[upPos].duration--;
            robotQueue.add(new Robot(upPos));
        }
    }

    private static void moveRobots() {
        int downPos = getDownPos();

        int liftDownCount = 0;
        for(Robot robot : robotQueue){
            int currPos = robot.pos;
            int nextPos = (currPos + 1) % (2*N);

            if(currPos == downPos){
                liftDownCount++;
                belts[currPos].hasRobot = false;
            }else if(isRaiseable(nextPos)){
                belts[currPos].hasRobot = false;
                belts[nextPos].hasRobot = true;
                belts[nextPos].duration--;
                robot.pos = nextPos;
            }
        }

        for(int i=0; i<liftDownCount; i++){
            robotQueue.poll();
        }
    }

    private static int getDownPos() {
        return (upPos + N - 1) % (2*N);
    }

    private static void spinBelts() {
        int downPos = getDownPos();
        if((robotQueue.peek() != null ? robotQueue.peek().pos : -1) == downPos) {
            robotQueue.poll();
            belts[downPos].hasRobot = false;
        }

        if(--upPos < 0) upPos = 2*N - 1;
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(bf.readLine());
        belts = new Belt[2*N];
        for(int n=0; n<2*N; n++){
            belts[n] = new Belt(Integer.parseInt(st.nextToken()));
        }
    }





}
