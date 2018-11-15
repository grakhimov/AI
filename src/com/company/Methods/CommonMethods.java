package com.company.Methods;

import com.company.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonMethods {

    boolean contains(List<Node<int[][]>> l, Node<int[][]> node) {
        for (Node<int[][]> n : l) {
            if (Arrays.deepEquals(n.getData(), node.getData())) {
                return true;
            }
        }
        return false;
    }

    public List<int[][]> getSiblings(int[][] initState) {
        List<int[][]> queue = new ArrayList<>();
        int zeroPosX = getPositionOfKnuckle(0, initState)[0];
        int zeroPosY = getPositionOfKnuckle(0, initState)[1];
        System.out.println("zeroPosX is " + String.valueOf(zeroPosX) + " and zeroPosY is " + String.valueOf(zeroPosY));

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

    private int[][] deepCopy(int[][] original) {
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

    public int knucklesNotInPlace(Node<int[][]> state, Node<int[][]> targetState) {
        int knucklesNotInPlace = 0;
        for (int i = 0; i < state.getData().length; i++) {
            for (int j = 0; j < state.getData()[i].length; j++) {
                if (state.getData()[i][j] != targetState.getData()[i][j]) {
                    knucklesNotInPlace++;
                }
            }
        }
        return knucklesNotInPlace;
    }

    public int manhattan(Node<int[][]> state, Node<int[][]> targetState) {
        int manhattan = 0;
        for (int i = 0; i < 8; i++) {
            int currentPositionXOfI = getPositionOfKnuckle(i, state.getData())[0];
            int currentPositionYOfI = getPositionOfKnuckle(i, state.getData())[1];
            int targetPositionXOfI = getPositionOfKnuckle(i, targetState.getData())[0];
            int targetPositionYOfI = getPositionOfKnuckle(i, targetState.getData())[1];
            manhattan = manhattan + Math.abs(targetPositionXOfI - currentPositionXOfI) + Math.abs(targetPositionYOfI - currentPositionYOfI);
        }
        return manhattan;
    }

    private int[] getPositionOfKnuckle(int knuckle, int[][] array) {
        int posX = 0;
        int posY = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == knuckle) {
                    posX = i;
                    posY = j;
                }
            }
        }
        return new int[]{posX, posY};
    }
}
