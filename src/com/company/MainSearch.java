package com.company;

import com.company.Methods.AutoSearches;
import com.company.Methods.ManualSearches;

import java.util.Arrays;
import java.util.Scanner;

public class MainSearch {
    public static void main(String[] args) {
        int[][] initPositions = {{6, 0, 8}, {5, 2, 1}, {4, 3, 7}};
        int[][] targetPositions = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        Node<int[][]> initNode = new Node<>(initPositions);
        Node<int[][]> targetNode = new Node<>(targetPositions);
        System.out.println("Init state is " + Arrays.deepToString(initPositions));
        System.out.println("Target state is " + Arrays.deepToString(targetPositions));
        System.out.println("Please select method to find solution:");
        System.out.println("Type 1 for Deep First Search");
        System.out.println("Type 2 for Two Directional Search");
        System.out.println("Type 3 for Heuristic Search by 'number of knuckles not in target place'");
        System.out.println("Type 4 for Heuristic Search by Manhattan method");
        Scanner in = new Scanner(System.in);
        int decision = in.nextInt();
        switch (decision) {
            case 1: {
                System.out.println("Please select type of solution");
                System.out.println("Type 1 for automatically running solver");
                System.out.println("Type 2 for manual running solver");
                decision = in.nextInt();
                switch (decision) {
                    case 1: {
                        AutoSearches search = new AutoSearches();
                        search.autoDFS(initNode, targetNode);
                        break;
                    }
                    case 2: {
                        ManualSearches search = new ManualSearches();
                        search.manualDFS(initNode, targetNode);
                        break;
                    }
                }
                break;
            }
            case 2: {
                System.out.println("Please select type of solution");
                System.out.println("Type 1 for automatically running solver");
                System.out.println("Type 2 for manual running solver");
                decision = in.nextInt();
                switch (decision) {
                    case 1: {
                        AutoSearches search = new AutoSearches();
                        search.autoTDS(initNode, targetNode);
                        break;
                    }
                    case 2: {
                        ManualSearches search = new ManualSearches();
                        search.manualTDS(initNode, targetNode);
                        break;
                    }
                }
                break;
            }
            case 3: {
                System.out.println("Please select type of solution");
                System.out.println("Type 1 for automatically running solver");
                System.out.println("Type 2 for manual running solver");
                decision = in.nextInt();
                switch (decision) {
                    case 1: {
                        AutoSearches search = new AutoSearches();
                        search.autoNIPS(initNode, targetNode);
                        break;
                    }
                    case 2: {
                        ManualSearches search = new ManualSearches();
                        search.manualNIPS(initNode, targetNode);
                        break;
                    }
                }
                break;
            }
            case 4: {
                System.out.println("Please select type of solution");
                System.out.println("Type 1 for automatically running solver");
                System.out.println("Type 2 for manual running solver");
                decision = in.nextInt();
                switch (decision) {
                    case 1: {
                        AutoSearches search = new AutoSearches();
                        search.autoMMS(initNode, targetNode);
                        break;
                    }
                    case 2: {
                        ManualSearches search = new ManualSearches();
                        search.manualMMS(initNode, targetNode);
                        break;
                    }
                }
                break;
            }
        }
        in.close();
    }
}
