package com.cuizhanming.template.dsa.base;

public class QueueOnArray {
    private int[] array;
    private int headIndex;
    private int tailIndex;

    public QueueOnArray(int size) {
        this.array = new int[size];
    }

    public QueueOnArray enqueue(int value) {
        if (headIndex == (tailIndex + 1) % (array.length)) {
            throw new ArrayIndexOutOfBoundsException("Queue is full");
        }
        array[tailIndex] = value;
        tailIndex = (tailIndex + 1) % (array.length);
        return this;
    }

    public int dequeue() {
        if (headIndex == tailIndex) {
            throw new ArrayIndexOutOfBoundsException("Queue is empty");
        }
        int value = array[headIndex];
        array[headIndex] = 0;
        headIndex = (headIndex + 1) % array.length;
        return value;
    }

    public void output() {
        for (int j : array) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        QueueOnArray queueOnArray = new QueueOnArray(5);
        queueOnArray.enqueue(1).enqueue(2).enqueue(3).enqueue(4).output();

        queueOnArray.dequeue();
        queueOnArray.dequeue();
        queueOnArray.dequeue();
        queueOnArray.enqueue(6).enqueue(7).output();
        queueOnArray.dequeue();
        queueOnArray.dequeue();
        queueOnArray.dequeue();
        queueOnArray.dequeue();
    }
}
