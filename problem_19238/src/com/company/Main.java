package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Customer implements Comparable<Customer>{
    Point start, end;
    int startDist, endDist;

    Customer(Point start, Point end){
        this.start = (Point) start.clone();
        this.end = (Point) end.clone();
    }

    public void setDist(int[][] distMap) {
        startDist = distMap[start.x][start.y];
        endDist = distMap[end.x][end.y];
    }

    @Override
    public int compareTo(Customer o) {
        if(startDist < o.startDist) return -1;
        else if(startDist > o.startDist) return  1;
        else if(start.x < o.start.x) return -1;
        else if(start.x > o.start.x) return 1;
        else return Integer.compare(start.y, o.start.y);
    }
}

class Car{
    Point pos;
    int state, fuel, moveDist;
    static final int STATE_FIND_CUSTOMER = 0;
    static final int STATE_FIND_ARRIVAL = 1;

    Car(Point pos, int fuel){
        this.pos = (Point) pos.clone();
        state = STATE_FIND_CUSTOMER;
        this.fuel = fuel;
    }

    public boolean canPickUp(Customer customer) {
        int dist = customer.startDist;

        if(dist == -1 || fuel < dist) return false;

        fuel -= dist;
        pos = (Point) customer.start.clone();

//        System.out.println("pick up " + (pos.x+1) + " " + (pos.y+1)   + " use : " + dist +  " remain fuel: " + fuel);

        return true;
    }

    public boolean canPickDown(Customer customer) {
        int dist = customer.endDist;

        if(dist == -1 || fuel < dist) return false;

        fuel -= dist;
        moveDist = dist;
        pos = (Point) customer.end.clone();

//        System.out.println("pick down " + (pos.x+1) + " " + (pos.y+1)  + " use : " + dist + " remain fuel: " + fuel);

        return true;
    }

    public void reFill() {
        fuel += moveDist * 2;
    }
}

class Dist{
    int x, y, d;

    Dist(int x, int y, int d){
        this.x = x;
        this.y = y;
        this.d = d;
    }
}

public class Main {
    static int N, M, initFuel;
    static boolean[][] isMovable;
    static Car car;
    static ArrayList<Customer> customers = new ArrayList<>();
    static final int[] DX = {0, 0, -1, +1};
    static final int[] DY = {-1, +1, 0, 0};

    public static void main(String[] args) throws IOException {
        setInput();

        while(!customers.isEmpty()){
            int[][] distMap = makeDistMap();
            for(Customer customer : customers) customer.setDist(distMap);

            if(car.state == Car.STATE_FIND_CUSTOMER){
                Collections.sort(customers);
                if(car.canPickUp(customers.get(0))){
                    car.state = Car.STATE_FIND_ARRIVAL;
                }else{
                    car.fuel = -1;
                    break;
                }
            }else{
                if(car.canPickDown(customers.get(0))){
                    car.state = Car.STATE_FIND_CUSTOMER;
                    car.reFill();
                    customers.remove(0);
                }else{
                    car.fuel = -1;
                    break;
                }
            }

        }

        System.out.println(car.fuel);
    }


    private static int[][] makeDistMap() {
        int[][] distMap = new int[N][N];
        Queue<Dist> q = new LinkedList<>();

        for(int r=0; r<N; r++)
            for(int c=0; c<N; c++)
                distMap[r][c] = -1;

        int sx = car.pos.x;
        int sy = car.pos.y;
        distMap[sx][sy] = 0;
        q.add(new Dist(sx, sy, 0));
        while (!q.isEmpty()){
            Dist dist = q.poll();
            int x = dist.x;
            int y = dist.y;
            int d = dist.d;

            for(int dir=0; dir<4; dir++){
                int nx = x + DX[dir];
                int ny = y + DY[dir];

                if(isInbound(nx, ny) && isMovable[nx][ny] && distMap[nx][ny] == -1){
                    distMap[nx][ny] = d + 1;
                    q.add(new Dist(nx, ny, d + 1));
                }
            }

        }

        return distMap;
    }

    public static boolean isInbound(int x, int y){
        return 0 <= x && x < N && 0 <= y && y < N;
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        initFuel = Integer.parseInt(st.nextToken());
        isMovable = new boolean[N][N];

        for(int r=0; r<N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<N; c++){
                if(Integer.parseInt(st.nextToken()) == 0){
                    isMovable[r][c] = true;
                }
            }
        }

        st = new StringTokenizer(bf.readLine());
        car = new Car(new Point(
                Integer.parseInt(st.nextToken()) - 1,
                Integer.parseInt(st.nextToken()) - 1
        ), initFuel);

        for(int m=0; m<M; m++){
            st = new StringTokenizer(bf.readLine());
            customers.add(new Customer(
                    new Point(
                            Integer.parseInt(st.nextToken()) - 1,
                            Integer.parseInt(st.nextToken()) - 1
                    ),
                    new Point(
                            Integer.parseInt(st.nextToken()) - 1,
                            Integer.parseInt(st.nextToken()) - 1
                    )
            ));
        }

    }
}
