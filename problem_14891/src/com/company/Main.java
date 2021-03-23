package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Gear{
    int gearSize;
    int[] gearPole;
    int topPos;
    private static final int POLE_N = 0;
    private static final int POLE_S = 1;
    private static final int CLOCKWISE = 1;
    private static final int ANTI_CLOCKWISE = -1;

    Gear(String gearStr){
        gearSize = gearStr.length();
        gearPole = new int[gearSize];

        int i=0;
        for(char c : gearStr.toCharArray()){
            if(c == '0') gearPole[i++] = POLE_N;
            else gearPole[i++] = POLE_S;
        }

        topPos = 0;
    }

    public String show(){
        return topPos + " < ";
    }

    public int getLeftPole(){
        return gearPole[(topPos+6) % gearSize];
    }

    public int getRightPole(){
        return gearPole[(topPos+2) % gearSize];
    }

    public int getTopPole(){
        return gearPole[topPos % gearSize];
    }

    public void spin(int dir){
        topPos = (topPos - dir) % gearSize;
        if(topPos == -1) topPos = gearSize-1;
    }
}

class Oper{
    int gearNumber;
    int spinDirection;

    Oper(int gearNumber, int spinDirection){
        this.gearNumber = gearNumber;
        this.spinDirection = spinDirection;
    }
}

class Gears{
    int gearSize = 0;
    ArrayList<Gear> gearArrayList = new ArrayList<>();

    public void addGear(Gear gear){
        gearArrayList.add(gear);
        gearSize++;
    }

    public void runOper(Oper oper){
        int gearNumber = oper.gearNumber;
        int spinDir = oper.spinDirection;

        leftGearActivate(gearNumber-1, -1 * spinDir);
        rightGearActivate(gearNumber+1, -1 * spinDir);
        gearArrayList.get(gearNumber).spin(spinDir);
    }

    private void leftGearActivate(int gearNumber, int spinDir){
        if(gearNumber >= 0 && gearArrayList.get(gearNumber).getRightPole() != gearArrayList.get(gearNumber+1).getLeftPole()){
            leftGearActivate(gearNumber - 1, -1 * spinDir);
            gearArrayList.get(gearNumber).spin(spinDir);
        }
    }

    private void rightGearActivate(int gearNumber, int spinDir){
        if(gearNumber < gearSize && gearArrayList.get(gearNumber).getLeftPole() != gearArrayList.get(gearNumber-1).getRightPole()){
            rightGearActivate(gearNumber + 1, -1 * spinDir);
            gearArrayList.get(gearNumber).spin(spinDir);
        }
    }

    public  int getTopValueSum(int[][] weight){
        int sum = 0;

        for(int i=0; i<gearSize; i++){
            sum += weight[gearArrayList.get(i).getTopPole()][i];
        }

        return sum;
    }

    public void show(){
        for(Gear gear : gearArrayList){
            System.out.println(gear.show());
        }
    }
}

public class Main {
    private static Gears gears = new Gears();
    private static int K;
    private static ArrayList<Oper> opers = new ArrayList<>();
    private static final int[][] GEAR_WEIGHT = {
            {0, 0, 0, 0}, // N pole
            {1, 2, 4, 8}  // S pole
    };

    public static void main(String[] args) throws IOException {
        getInput();

        for(Oper oper : opers){
            gears.runOper(oper);
        }


        System.out.println(gears.getTopValueSum(GEAR_WEIGHT));
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int i=0; i<4; i++){
            gears.addGear(new Gear(bf.readLine()));
        }

        K = Integer.parseInt(bf.readLine());
        for(int i=0; i<K; i++){
            st = new StringTokenizer(bf.readLine());

            opers.add(new Oper(
                    Integer.parseInt(st.nextToken()) - 1,
                    Integer.parseInt(st.nextToken())
            ));
        }
    }
}
