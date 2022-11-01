package com.company;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

class CircularArrayQueue{
    private int front;
    private int rear;
    private int[] numArray;

    public CircularArrayQueue(int initSize){
        this.front = -1;
        this.rear = -1;
        this.numArray = new int[initSize];
    }

    public void enqueue(int num){
        if(isEmpty()){
            front++;
        }
        rear = (rear + 1) % numArray.length;
        numArray[rear] = num;
    }

    public int dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int temp = numArray[front];
        if(front == rear){
            front = -1;
            rear = -1;
        }else{
            numArray[front] = 0;
            front = (front + 1) % numArray.length;
        }
        return temp;
    }

    public int size(){
        return ((numArray.length - front) + rear + 1) % numArray.length;
    }

    public int front(){
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return numArray[front];
    }

    public boolean isEmpty(){
        return front == -1;
    }

    public String toString(){
        String s = "";
        for(int i : numArray){
            s = s + i + " ";
        }
        return s;
    }
}

public class Part2_B {
    public static void main(String[] args) throws IOException {
        CircularArrayQueue q1 = new CircularArrayQueue(10);
        q1.enqueue(4);
        q1.enqueue(8);
        q1.enqueue(5);
        q1.enqueue(2);
        q1.enqueue(3);
        q1.enqueue(5);
        q1.enqueue(1);
        q1.enqueue(6);
        q1.enqueue(4);
        q1.enqueue(0);

        System.out.println(q1 + "\n");

        CircularArrayQueue q2 = new CircularArrayQueue(10);

        moveRight(q1,q2,q1.front());

        //System.out.println("Q1 after move right: " + q1);
        System.out.println("Q1 after move right: " + q1);
        System.out.println("Q2 after move right: " + q2);

        System.out.println("Q1.front(): " + q1.front());
        System.out.println("Q1.size(): " + q1.size());
        System.out.println("Q2.size(): " + q2.size());

        moveRight(q1,q2,q1.front());

        System.out.println();

        System.out.println("Q1 after move right: " + q1);
        System.out.println("Q2 after move right: " + q2);

        System.out.println("Q1.size(): " + q1.size());
        System.out.println("Q1.front(): " + q1.front());
        System.out.println("Q2.size(): " + q2.size());

        System.out.println("------------------------------------------------");

        moveLeft(q1,q2);
        System.out.println("Q1: " + q1);
        System.out.println("Q2: " + q2);

        System.out.println("------------------------------------------------");

        moveRight(q1, q2, q1.size());
        System.out.println("Q1: " + q1);
        System.out.println("Q2: " + q2);
    }

    public static void moveRight(CircularArrayQueue q1, CircularArrayQueue q2, int times){
        for(int i = 0; i < times; i++){
            int temp = q1.dequeue();
            q2.enqueue(temp);
        }
    }

    public static void moveLeft(CircularArrayQueue q1, CircularArrayQueue q2){
        int temp1 = q1.size();
        int temp2 = q1.front();
        int temp3 = q2.size();

        for(int i = 0; i < temp1; i++){
            q2.enqueue(q1.dequeue());
        }
        for(int i = 0; i < temp3; i++){
            q1.enqueue(q2.dequeue());
        }
        for(int i = 0; i < temp1; i++){
            q1.enqueue(q2.dequeue());
        }
        for(int i = 0; i < temp3 - temp2; i++){
            q2.enqueue(q1.dequeue());
        }
    }
}
