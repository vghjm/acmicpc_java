package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

class Number implements Comparable<Number> {
    int num, count;

    Number(int num, int count){
        this.num = num;
        this.count = count;
    }

    @Override
    public int compareTo(Number number) {
        if(this.count > number.count){
            return 1;
        }else if(this.count < number.count){
            return -1;
        }else if(this.num > number.num){
            return 1;
        }else {
            return -1;
        }
    }
}

public class Main {
    private static final int MAX_NUMBER = 100;
    static int R, C, K;
    static int[][] A = new int[MAX_NUMBER + 1][MAX_NUMBER + 1];

    public static void main(String[] args) throws IOException {
        setInput();

        int operCount = 0;
        int maxRow = 3;
        int maxColum = 3;
        ArrayList<Integer> beforeSort = new ArrayList<>();
        while(A[R][C] != K){
            operCount++;
            if(operCount > 100){
                operCount = -1;
                break;
            }

            ArrayList<Integer> afterSort;
            if(maxRow >= maxColum){
                int newMaxC = 0;
                // R 연산
                for(int r=1; r<=maxRow; r++){
                    beforeSort.clear();

                    for(int c=1; c<=maxColum; c++){
                        if(A[r][c] == 0) continue;

                        beforeSort.add(A[r][c]);
                        A[r][c] = 0;
                    }

                    afterSort = sort(beforeSort);
                    for(int c=1; c<=afterSort.size(); c++){
                        A[r][c] = afterSort.get(c - 1);
                    }

                    newMaxC = Math.max(newMaxC, afterSort.size());
                }

                maxColum = newMaxC;
            }else{
                int newMaxR = 0;
                // C 연산
                for(int c=1; c<=maxColum; c++){
                    beforeSort.clear();

                    for(int r=1; r<=maxRow; r++){
                        if(A[r][c] == 0) continue;

                        beforeSort.add(A[r][c]);
                        A[r][c] = 0;
                    }

                    afterSort = sort(beforeSort);
                    for(int r=1; r<=afterSort.size(); r++){
                        A[r][c] = afterSort.get(r - 1);
                    }

                    newMaxR = Math.max(newMaxR, afterSort.size());
                }

                maxRow = newMaxR;
            }

//            System.out.printf("\n\nr:%d, c:%d\n", maxRow, maxColum);
//            for(int i=1; i<=maxRow; i++){
//                for(int j=1; j<=maxColum; j++){
//                   System.out.printf("%d ", A[i][j]);
//                }
//                System.out.println();
//            }
        }

        System.out.println(operCount);
    }

    private static ArrayList<Integer> sort(ArrayList<Integer> beforeSort) {
        int[] set = new int[MAX_NUMBER + 1];
        ArrayList<Integer> afterSort = new ArrayList<>();
        ArrayList<Number> numbers = new ArrayList<>();

        for(int d : beforeSort){
            set[d]++;
        }

        for(int i=1; i<=MAX_NUMBER; i++){
            if(set[i] == 0) continue;
            numbers.add(new Number(i, set[i]));
        }

        Collections.sort(numbers);
        for(Number number : numbers){
            if(afterSort.size() >= 100) break;

            afterSort.add(number.num);
            afterSort.add(number.count);
        }

        return afterSort;
    }

    private static void setInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for(int r=1; r<=3; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=1; c<=3; c++){
                A[r][c] = Integer.parseInt(st.nextToken());
            }
        }
    }
}
