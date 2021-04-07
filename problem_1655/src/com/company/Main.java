package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {


    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder()), minHeap = new PriorityQueue<>();
        int N = Integer.parseInt(bf.readLine());

        for(int i=0; i<N; i++){
            int d = Integer.parseInt(bf.readLine());

            if(maxHeap.size() == minHeap.size()) maxHeap.offer(d);
            else minHeap.offer(d);

            if(!minHeap.isEmpty() && maxHeap.peek() > minHeap.peek()){
                int t = maxHeap.poll();
                maxHeap.offer(minHeap.poll());
                minHeap.offer(t);
            }

            sb.append(maxHeap.peek()).append("\n");
        }

        System.out.println(sb);
    }
}
