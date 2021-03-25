package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Tree implements Comparable<Tree>{
    int k;

    Tree(int k){
        this.k = k;
    }

    @Override
    public int compareTo(Tree t) {
        if(this.k > t.k) return -1;
        else if(this.k < t.k) return 1;
        else return 0;
    }
}

class Ground{
    ArrayList<Tree> trees = new ArrayList<>();
    ArrayList<Tree> deadTrees = new ArrayList<>();
    ArrayList<Ground> nearGround = new ArrayList<>();
    int treeCount = 0;
    int food = 5;

    public void addNearGround(Ground ground){
        nearGround.add(ground);
    }

    public void addTree(Tree tree){
        trees.add(tree);
        treeCount++;
    }

    public void spring(){
        deadTrees.clear();

        for(int i=treeCount-1; i>=0; i--){
            Tree tree = trees.get(i);
            if(food >= tree.k){
                food -= tree.k;
                tree.k++;
            }else{
                deadTrees.add(tree);
                trees.remove(i);
            }
        }
    }

    public void summer(){
        for(Tree tree : deadTrees){
            food += Math.floorDiv(tree.k, 2);
            treeCount--;
        }
    }

    public void fall(){
        for(Tree tree : trees){
            if(tree.k % 5 == 0){
                for(Ground ground : nearGround){
                    ground.addTree(new Tree(1));
                }
            }
        }
    }

    public void winter(int A){
        food += A;
    }
}

public class Main {
    static int N, M, K;
    static int[][] A;
    static Ground[][] ground;


    public static void main(String[] args) throws IOException {
        getInput();

        for(int r=0; r<N; r++){
            for(int c=0; c<N; c++){
                Collections.sort(ground[r][c].trees);
            }
        }

        for(int k=1; k<=K; k++){
            for(int r=0; r<N; r++){
                for(int c=0; c<N; c++){
                    ground[r][c].spring();
                }
            }

            for(int r=0; r<N; r++){
                for(int c=0; c<N; c++){
                    ground[r][c].summer();
                }
            }

            for(int r=0; r<N; r++){
                for(int c=0; c<N; c++){
                    ground[r][c].fall();
                }
            }

            for(int r=0; r<N; r++){
                for(int c=0; c<N; c++){
                    ground[r][c].winter(A[r][c]);
                }
            }
        }

        int treeCount = 0;
        for(int r=0; r<N; r++){
            for(int c=0; c<N; c++){
                treeCount += ground[r][c].treeCount;
            }
        }

        System.out.println(treeCount);
    }


    private static boolean isAvailable(int r, int c){
        return 0 <= r && r < N && 0 <= c && c < N;
    }

    private static void getInput() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        A = new int[N][N];
        ground = new Ground[N][N];
        for(int r=0; r<N; r++){
            st = new StringTokenizer(bf.readLine());
            for(int c=0; c<N; c++){
                A[r][c] = Integer.parseInt(st.nextToken());
                ground[r][c] = new Ground();
            }
        }

        for(int r=0; r<N; r++){
            for(int c=0; c<N; c++){
                if(isAvailable(r-1, c-1)) ground[r][c].addNearGround(ground[r-1][c-1]);
                if(isAvailable(r-1, c)) ground[r][c].addNearGround(ground[r-1][c]);
                if(isAvailable(r-1, c+1)) ground[r][c].addNearGround(ground[r-1][c+1]);
                if(isAvailable(r, c-1)) ground[r][c].addNearGround(ground[r][c-1]);
                if(isAvailable(r, c+1)) ground[r][c].addNearGround(ground[r][c+1]);
                if(isAvailable(r+1, c-1)) ground[r][c].addNearGround(ground[r+1][c-1]);
                if(isAvailable(r+1, c)) ground[r][c].addNearGround(ground[r+1][c]);
                if(isAvailable(r+1, c+1)) ground[r][c].addNearGround(ground[r+1][c+1]);
            }
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(bf.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int k = Integer.parseInt(st.nextToken());

            ground[r][c].addTree(new Tree(k));
        }
    }
}
