package com.cuizhanming.template.dsa.base;

public class LinkNode {

    private static class Node {
        public int value;
        public Node next;
        public Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;


    public LinkNode insert(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        Node node = new Node(value);

        if (size == 0) {
            head = node;
            tail = node;
        } else if (index == 0) {
            node.next = head;
            head = node;
        } else if (index == size) {
            tail.next = node;
            tail = node;
        } else {
            Node prev = get(index - 1);
            node.next = prev.next;
            prev.next = node;
        }
        size++;
        return this;
    }

    public LinkNode remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        if (index == 0) {
            head = head.next;
        } else if (index == size - 1) {
            Node prev = get(index - 1);
            prev.next = null;
            tail = prev;
        } else {
            Node prev = get(index - 1);
            Node next = prev.next.next;
            prev.next = next;
        }
        size--;
        return this;
    }

    public Node get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    @Override
    public String toString() {
        Node temp = head;
        StringBuilder linkNode = new StringBuilder();
        while (temp != null) {
            linkNode.append(temp.value).append(" ");
            temp = temp.next;
        }
        return linkNode.toString();
    }

    public static void main(String[] args) {
        LinkNode linkNode = new LinkNode();
        linkNode.insert(0, 1)
                .insert(0, 2)
                .insert(0, 3)
                .insert(0, 4);
        System.out.println(linkNode);

        linkNode.insert(3, 5)
                .insert(4, 6)
                .insert(2, 7);
        System.out.println(linkNode);

        linkNode.remove(2);
        System.out.println(linkNode);
    }
}
