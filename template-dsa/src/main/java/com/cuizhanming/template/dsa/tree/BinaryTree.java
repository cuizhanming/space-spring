package com.cuizhanming.template.dsa.tree;


import java.util.*;

public class BinaryTree {


    public static class TreeNode {
        public int value;
        public TreeNode leftChild;
        public TreeNode rightChild;
        public TreeNode(int value) {
            this.value = value;
        }
        @Override
        public String toString() {
            return "[(" + value + "), leftChild=" + leftChild + ", rightChild=" + rightChild + "]\n";
        }
    }

    /**
     [(3),
         leftChild=
         [(2),
                leftChild=[(9), leftChild=null, rightChild=null]
                rightChild=[(10), leftChild=null, rightChild=null]
         ]
         rightChild=
         [(8),
                leftChild=null,
                rightChild=[(4), leftChild=null, rightChild=null]
         ]
     ]

     */

    private static TreeNode createBinaryTree(LinkedList<Integer> inputList) {
        TreeNode node = null;
        if (inputList == null || inputList.isEmpty()) {
            return node;
        }
        Integer data = inputList.removeFirst();
        if (data != null) {
            node = new TreeNode(data);
            node.leftChild = createBinaryTree(inputList);
            node.rightChild = createBinaryTree(inputList);
        }
        return node;
    }

    public static List<Integer> preOrderTraversalResult = new ArrayList<>();
    public static void preOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        preOrderTraversalResult.add(root.value);
        preOrderTraversal(root.leftChild);
        preOrderTraversal(root.rightChild);
    }

    public static void preOrderTraveralWithStack(TreeNode root) {
        if (root == null) {
            return;
        }
        preOrderTraversalResult.clear();

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                preOrderTraversalResult.add(node.value);
                stack.push(node);
                node = node.leftChild;
            }

            if (!stack.isEmpty()) {
                node = stack.pop();
                node = node.rightChild;
            }
        }
    }

    public static List<Integer> inOrderTraversalResult = new ArrayList<>();
    public static void inOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrderTraversal(root.leftChild);
        inOrderTraversalResult.add(root.value);
        inOrderTraversal(root.rightChild);
    }

    public static List<Integer> postOrderTraversalResult = new ArrayList<>();
    public static void postOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrderTraversal(root.leftChild);
        postOrderTraversal(root.rightChild);
        postOrderTraversalResult.add(root.value);
    }


    public static void main(String[] args) {
        /*
              3
            2    8
           9 10    4
         */

        Integer[] inputArray = new Integer[]{3, 2, 9, null, null, 10, null, null, 8, null, 4};
        LinkedList<Integer> inputList = new LinkedList<>(Arrays.asList(inputArray));
        TreeNode treeNode = createBinaryTree(inputList);

        preOrderTraversal(treeNode);
        System.out.println(preOrderTraversalResult);
        // [3, 2, 9, 10, 8, 4]

        preOrderTraveralWithStack(treeNode);
        System.out.println(preOrderTraversalResult);

        inOrderTraversal(treeNode);
        System.out.println(inOrderTraversalResult);
        // [9, 2, 10, 3, 8, 4]

        postOrderTraversal(treeNode);
        System.out.println(postOrderTraversalResult);
        // [9, 10, 2, 4, 8, 3]

    }
}
