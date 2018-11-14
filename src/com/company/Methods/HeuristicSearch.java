package com.company.Methods;

import com.company.Node;

import java.util.*;
import java.util.stream.Collectors;

public class HeuristicSearch {

    private static List<Node<int[][]>> visitedNodes = new ArrayList<>();

    public static void main(String[] args) {
        int[][] initPositions = {{6, 0, 8}, {5, 2, 1}, {4, 3, 7}};
        int[][] targetPositions = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        Node<int[][]> initNode = new Node<>(initPositions);
        Node<int[][]> target = new Node<>(targetPositions);
        solutionNotInPosition(initNode, target);

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

    private static void solutionNotInPosition(Node<int[][]> initState, Node<int[][]> targetState){
        Comparator<Node<int[][]>> comparator = new Comparator<Node<int[][]>>() {
            @Override
            public int compare(Node<int[][]> o1, Node<int[][]> o2) {
                if (o1.getKnucklesNotInPlace() < o2.getKnucklesNotInPlace()) {
                    return -1;
                }
                else if (o1.getKnucklesNotInPlace() > o2.getKnucklesNotInPlace()){
                    return 1;
                }
                return 0;
            }
        };
        PriorityQueue<Node<int[][]>> queue = new PriorityQueue<>(5, comparator);
        initState.setKnucklesNotInPlace(knucklesNotInPlace(initState, targetState));
        queue.add(initState);
        while (!queue.isEmpty()){
            Node<int[][]> node = queue.remove();
            if (!node.equals(targetState)) {
                visitedNodes.add(node);
                List<Node<int[][]>> siblings = getSiblings(node.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                siblings.forEach(x -> x.setKnucklesNotInPlace(knucklesNotInPlace(x, targetState)));
                for (int i = 0; i < siblings.size(); i++) {
                    if (!contains(visitedNodes, siblings.get(i))) {
                        queue.add(siblings.get(i));
                    }
                }
            } else {
                System.out.println("Steps count is:" + String.valueOf(visitedNodes.size()));
                System.out.println("Solution is founded");
                break;
            }
        }

    }

    private static void solutionManhetten(Node<int[][]> initState, Node<int[][]> targetState){
        Comparator<Node<int[][]>> comparator = new Comparator<Node<int[][]>>() {
            @Override
            public int compare(Node<int[][]> o1, Node<int[][]> o2) {
                if (o1.getManhatten() < o2.getManhatten()) {
                    return -1;
                }
                else if (o1.getManhatten() > o2.getManhatten()){
                    return 1;
                }
                return 0;
            }
        };
        PriorityQueue<Node<int[][]>> queue = new PriorityQueue<>(5, comparator);
        initState.setManhatten(knucklesNotInPlace(initState, targetState));
        queue.add(initState);
        while (!queue.isEmpty()){
            Node<int[][]> node = queue.remove();
            if (!node.equals(targetState)) {
                visitedNodes.add(node);
                List<Node<int[][]>> siblings = getSiblings(node.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                siblings.forEach(x -> x.setManhatten(knucklesNotInPlace(x, targetState)));
                for (int i = 0; i < siblings.size(); i++) {
                    if (!contains(visitedNodes, siblings.get(i))) {
                        queue.add(siblings.get(i));
                    }
                }
            } else {
                System.out.println("Steps count is:" + String.valueOf(visitedNodes.size()));
                System.out.println("Solution is founded");
                break;
            }
        }

    }

    private static int knucklesNotInPlace(Node<int[][]> state, Node<int[][]> targetState){
        int knucklesNotInPlace = 0;
        for (int i = 0; i < state.getData().length; i++) {
            for (int j = 0; j < state.getData()[i].length; j++) {
                if (state.getData()[i][j] != targetState.getData()[i][j]){
                    knucklesNotInPlace++;
                }
            }
        }
        return knucklesNotInPlace;
    }

    private static int manhatten(Node<int[][]> state, Node<int[][]> targetState){
        int manhatten = 0;
        return manhatten;
    }

}
