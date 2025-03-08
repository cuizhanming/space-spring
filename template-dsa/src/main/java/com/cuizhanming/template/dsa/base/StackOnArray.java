package com.cuizhanming.template.dsa.base;

public class StackOnArray {

    private int[] array;
    private int size;
    private int index = 0;

    public StackOnArray(int size) {
        this.array = new int[size];
        this.size = size;
    }

    public StackOnArray push(int value) {
        if (size == index) {
            throw new ArrayIndexOutOfBoundsException("Stack is full");
        }
        array[index++] = value;
        return this;
    }

    public int pop() {
        if (index <= 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("index: " + index + ", size: " + array.length);
        }
        int top = array[index-1];
        array[index-1] = 0;
        index--;
        return top;
    }

    public void output() {
        for(int i = 0; i < index; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        StackOnArray stackOnArray = new StackOnArray(5);
        stackOnArray.push(1)
                .push(2)
                .push(3)
                .push(4)
                .output();

        System.out.println(stackOnArray.pop());
        System.out.println(stackOnArray.pop());
        System.out.println(stackOnArray.pop());
        System.out.println(stackOnArray.pop());


        stackOnArray.push(1)
                .push(2)
                .push(3)
                .push(4)
                .output();
    }

}
