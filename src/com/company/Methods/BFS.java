package com.company.Methods;

import com.company.Node;

import java.util.*;
import java.util.stream.Collectors;

public class BFS {

    private static List<Node<int[][]>> visitedNodes = new ArrayList<>();

    public static void main(String[] args) {
        int[][] initPositions = {{6, 0, 8}, {5, 2, 1}, {4, 3, 7}};
        int[][] targetPositions = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        Node<int[][]> initNode = new Node<>(initPositions);
        Node<int[][]> target = new Node<>(targetPositions);
        System.out.println("Init state is " + Arrays.deepToString(initPositions));
        System.out.println("Target state is " + Arrays.deepToString(targetPositions));
        System.out.println("Would you like to automatically get autoSolution? Type 'yes' for it and 'no' to step by step method");
        Scanner in = new Scanner(System.in);
        String decision = in.nextLine();
        if (decision.equalsIgnoreCase("yes")) {
            autoSolution(initNode, target);
        } else if (decision.equalsIgnoreCase("no")) {
            manualSolution(initNode, target);
        } else {
            System.out.println("Method is not selected, program is stopping now");
        }
        in.close();

    }

    public static void autoSolution(Node<int[][]> initNode, Node<int[][]> target) {
        Stack<Node<int[][]>> stack = new Stack<>();
        stack.add(initNode);
        while (!stack.isEmpty()) {
            Node<int[][]> element = stack.pop();
            if (!element.equals(target)) {
                System.out.println("State to open: " + Arrays.deepToString(element.getData()));
                List<Node<int[][]>> neighbours = getSiblings(element.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                element.addChildren(neighbours);
                neighbours.forEach(x -> System.out.println("Neighbour " + Arrays.deepToString(x.getData()) + " is added for opened state"));
                visitedNodes.add(element);
                for (int i = 0; i < neighbours.size(); i++) {
                    Node<int[][]> n = neighbours.get(i);
                    if (n != null && !contains(visitedNodes, n)) {
                        stack.add(n);
                        if (stack.search(target) != -1) {
                            System.out.println("Steps count until target will be reached is " + stack.search(target));
                        }
                    } else if (n != null && contains(visitedNodes, n)) {
                        System.out.println("This node is already visited: " + Arrays.deepToString(n.getData()));
                    }
                }
            } else {
                System.out.println("Solution is founded:");
                System.out.println(Arrays.deepToString(element.getData()));
                while (element.hasParent()) {
                    element = element.getParent();
                    System.out.println(Arrays.deepToString(element.getData()));
                }
                break;
            }
        }
    }

    public static void manualSolution(Node<int[][]> initNode, Node<int[][]> target) {
        Stack<Node<int[][]>> stack = new Stack<>();
        stack.add(initNode);
        while (!stack.isEmpty()) {
            Node<int[][]> element = stack.pop();
            if (!element.equals(target)) {
                System.out.println("State to open: " + Arrays.deepToString(element.getData()));
                System.out.println("Press Enter Key To Continue...");
                new java.util.Scanner(System.in).nextLine();
                List<Node<int[][]>> neighbours = getSiblings(element.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                element.addChildren(neighbours);
                neighbours.forEach(x -> System.out.println("Neighbour " + Arrays.deepToString(x.getData()) + " is added for opened state"));
                System.out.println("Press Enter Key To Continue...");
                new java.util.Scanner(System.in).nextLine();
                visitedNodes.add(element);
                for (int i = 0; i < neighbours.size(); i++) {
                    Node<int[][]> n = neighbours.get(i);
                    if (n != null && !contains(visitedNodes, n)) {
                        stack.add(n);
                        if (stack.search(target) != -1) {
                            System.out.println("Steps count until target will be reached is " + stack.search(target));
                            System.out.println("Press Enter Key To Continue...");
                            new java.util.Scanner(System.in).nextLine();
                        }
                    } else if (n != null && contains(visitedNodes, n)) {
                        System.out.println("This node is already visited: " + Arrays.deepToString(n.getData()));
                        System.out.println("Press Enter Key To Continue...");
                        new java.util.Scanner(System.in).nextLine();
                    }
                }
            } else {
                System.out.println("Solution is founded:");
                System.out.println(Arrays.deepToString(element.getData()));
                while (element.hasParent()) {
                    element = element.getParent();
                    System.out.println(Arrays.deepToString(element.getData()));
                }
                break;
            }
        }
    }

    static boolean contains(List<Node<int[][]>> l, Node<int[][]> node) {
        for (Node<int[][]> n : l) {
            if (Arrays.deepEquals(n.getData(), node.getData())) {
                return true;
            }
        }
        return false;
    }

    public static List<int[][]> getSiblings(int[][] initState) {
        List<int[][]> queue = new ArrayList<>();
        int zeroPosX = 0;
        int zeroPosY = 0;
        for (int i = 0; i < initState.length; i++) {
            for (int j = 0; j < initState[i].length; j++) {
                if (initState[i][j] == 0) {
                    zeroPosX = i;
                    zeroPosY = j;
                    System.out.println("zeroPosX is " + String.valueOf(zeroPosX) + " and zeroPosY is " + String.valueOf(zeroPosY));
                }
            }
        }
        if (zeroPosX > 0 && zeroPosX <= 2) {
            int[][] newState = deepCopy(initState);
            newState[zeroPosX][zeroPosY] = initState[zeroPosX - 1][zeroPosY];
            newState[zeroPosX - 1][zeroPosY] = 0;
            System.out.println("zeroPosX > 0 && zeroPosX <= 2 -->" + Arrays.deepToString(newState));
            queue.add(0, newState);
        }
        if (zeroPosX < 2) {
            int[][] newState = deepCopy(initState);
            newState[zeroPosX][zeroPosY] = initState[zeroPosX + 1][zeroPosY];
            newState[zeroPosX + 1][zeroPosY] = 0;
            System.out.println("zeroPosX < 2 -->" + Arrays.deepToString(newState));
            queue.add(0, newState);
        }
        if (zeroPosY > 0 && zeroPosY <= 2) {
            int[][] newState = deepCopy(initState);
            newState[zeroPosX][zeroPosY] = initState[zeroPosX][zeroPosY - 1];
            newState[zeroPosX][zeroPosY - 1] = 0;
            System.out.println("zeroPosY > 0 && zeroPosY <= 2 -->" + Arrays.deepToString(newState));
            queue.add(0, newState);
        }
        if (zeroPosY < 2) {
            int[][] newState = deepCopy(initState);
            newState[zeroPosX][zeroPosY] = initState[zeroPosX][zeroPosY + 1];
            newState[zeroPosX][zeroPosY + 1] = 0;
            System.out.println("zeroPosY < 2 -->" + Arrays.deepToString(newState));
            queue.add(0, newState);
        }
        return queue;
    }

    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }
        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = new int[original[i].length];
            for (int j = 0; j < original[i].length; j++) {
                result[i][j] = original[i][j];
            }
        }
        return result;
    }
}
