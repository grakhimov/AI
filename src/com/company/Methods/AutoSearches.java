package com.company.Methods;

import com.company.Node;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AutoSearches {

    public void autoDFS(Node<int[][]> initNode, Node<int[][]> target) {
        long startTime = System.currentTimeMillis();
        CommonMethods methods = new CommonMethods();
        List<Node<int[][]>> visitedNodes = new ArrayList<>();
        Stack<Node<int[][]>> stack = new Stack<>();
        stack.add(initNode);
        while (!stack.isEmpty()) {
            Node<int[][]> element = stack.pop();
            if (!element.equals(target)) {
                System.out.println("State to open: " + Arrays.deepToString(element.getData()));
                List<Node<int[][]>> neighbours = methods.getSiblings(element.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                element.addChildren(neighbours);
                neighbours.forEach(x -> System.out.println("Neighbour " + Arrays.deepToString(x.getData()) + " is added for opened state"));
                visitedNodes.add(element);
                for (int i = 0; i < neighbours.size(); i++) {
                    Node<int[][]> n = neighbours.get(i);
                    if (n != null && !methods.contains(visitedNodes, n)) {
                        stack.push(n);
                    } else if (n != null && methods.contains(visitedNodes, n)) {
                        System.out.println("This node is already visited: " + Arrays.deepToString(n.getData()));
                    }
                }
            } else {
                System.out.println("Steps count is:" + String.valueOf(visitedNodes.size()));
                long endTime = System.currentTimeMillis();
                System.out.println("Solution is founded. Time spent is " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(endTime - startTime),
                        TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime - startTime))
                ));
                break;
            }
        }
    }

    public void autoTDS(Node<int[][]> initNode, Node<int[][]> targetNode) {
        long startTime = System.currentTimeMillis();
        CommonMethods methods = new CommonMethods();
        Stack<Node<int[][]>> stackFromInit = new Stack<>();
        Stack<Node<int[][]>> stackFromTarget = new Stack<>();
        List<Node<int[][]>> visitedNodesFromInit = new ArrayList<>();
        List<Node<int[][]>> visitedNodesFromTarget = new ArrayList<>();
        stackFromInit.add(initNode);
        stackFromTarget.add(targetNode);
        while (!stackFromInit.isEmpty() && !stackFromTarget.isEmpty()) {
            Node<int[][]> elementFromInit = stackFromInit.pop();
            Node<int[][]> elementFromTarget = stackFromTarget.pop();
            if (visitedNodesFromTarget.stream().noneMatch(x -> x.equals(elementFromInit))) {
                System.out.println("State to open from initState: " + Arrays.deepToString(elementFromInit.getData()));
                List<Node<int[][]>> neighboursFromInit = methods.getSiblings(elementFromInit.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                elementFromInit.addChildren(neighboursFromInit);
                neighboursFromInit.forEach(x -> System.out.println("Neighbour " + Arrays.deepToString(x.getData()) + " is added for opened state"));
                visitedNodesFromInit.add(elementFromInit);
                for (int i = 0; i < neighboursFromInit.size(); i++) {
                    Node<int[][]> n = neighboursFromInit.get(i);
                    if (n != null && !methods.contains(visitedNodesFromInit, n)) {
                        stackFromInit.add(n);
                    } else if (n != null && methods.contains(visitedNodesFromInit, n)) {
                        System.out.println("This node is already visited: " + Arrays.deepToString(n.getData()));
                    }
                }
            } else {
                System.out.println("Steps count is:" + String.valueOf(visitedNodesFromInit.size() + visitedNodesFromTarget.size()));
                long endTime = System.currentTimeMillis();
                System.out.println("Solution is founded. Time spent is " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(endTime - startTime),
                        TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime - startTime))
                ));
                break;
            }
            if (visitedNodesFromInit.stream().noneMatch(x -> x.equals(elementFromTarget))) {
                System.out.println("State to open from targetState: " + Arrays.deepToString(elementFromTarget.getData()));
                List<Node<int[][]>> neighboursFromTarget = methods.getSiblings(elementFromTarget.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                elementFromTarget.addChildren(neighboursFromTarget);
                neighboursFromTarget.forEach(x -> System.out.println("Neighbour " + Arrays.deepToString(x.getData()) + " is added for opened state"));
                visitedNodesFromTarget.add(elementFromTarget);
                for (int i = 0; i < neighboursFromTarget.size(); i++) {
                    Node<int[][]> n = neighboursFromTarget.get(i);
                    if (n != null && !methods.contains(visitedNodesFromTarget, n)) {
                        stackFromTarget.add(n);
                    } else if (n != null && methods.contains(visitedNodesFromTarget, n)) {
                        System.out.println("This node is already visited: " + Arrays.deepToString(n.getData()));
                    }
                }
            } else {
                System.out.println("Steps count is:" + String.valueOf(visitedNodesFromInit.size() + visitedNodesFromTarget.size()));
                long endTime = System.currentTimeMillis();
                System.out.println("Solution is founded. Time spent is " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(endTime - startTime),
                        TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime - startTime))
                ));
                break;
            }
        }
    }

    public void autoNIPS(Node<int[][]> initState, Node<int[][]> targetState) {
        long startTime = System.currentTimeMillis();
        CommonMethods methods = new CommonMethods();
        List<Node<int[][]>> visitedNodes = new ArrayList<>();
        Comparator<Node<int[][]>> comparator = new Comparator<Node<int[][]>>() {
            @Override
            public int compare(Node<int[][]> o1, Node<int[][]> o2) {
                if (o1.getKnucklesNotInPlace() < o2.getKnucklesNotInPlace()) {
                    return -1;
                } else if (o1.getKnucklesNotInPlace() > o2.getKnucklesNotInPlace()) {
                    return 1;
                }
                return 0;
            }
        };
        PriorityQueue<Node<int[][]>> queue = new PriorityQueue<>(5, comparator);
        initState.setKnucklesNotInPlace(methods.knucklesNotInPlace(initState, targetState));
        queue.add(initState);
        while (!queue.isEmpty()) {
            Node<int[][]> node = queue.remove();
            System.out.println("State to open: " + Arrays.deepToString(node.getData()));
            if (!node.equals(targetState)) {
                visitedNodes.add(node);
                List<Node<int[][]>> siblings = methods.getSiblings(node.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                siblings.forEach(x -> System.out.println("Neighbour " + Arrays.deepToString(x.getData()) + " is added for opened state"));
                siblings.forEach(x -> x.setKnucklesNotInPlace(methods.knucklesNotInPlace(x, targetState)));
                for (int i = 0; i < siblings.size(); i++) {
                    if (!methods.contains(visitedNodes, siblings.get(i))) {
                        queue.add(siblings.get(i));
                    } else {
                        System.out.println("This node is already visited: " + Arrays.deepToString(siblings.get(i).getData()));
                    }
                }
            } else {
                long endTime = System.currentTimeMillis();
                System.out.println("Steps count is:" + String.valueOf(visitedNodes.size()));
                System.out.println("Solution is founded. Time spent is " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(endTime - startTime),
                        TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime - startTime))
                ));
                break;
            }
        }

    }

    public void autoMMS(Node<int[][]> initState, Node<int[][]> targetState) {
        long startTime = System.currentTimeMillis();
        CommonMethods methods = new CommonMethods();
        List<Node<int[][]>> visitedNodes = new ArrayList<>();
        Comparator<Node<int[][]>> comparator = new Comparator<Node<int[][]>>() {
            @Override
            public int compare(Node<int[][]> o1, Node<int[][]> o2) {
                if (o1.getManhatten() < o2.getManhatten()) {
                    return -1;
                } else if (o1.getManhatten() > o2.getManhatten()) {
                    return 1;
                }
                return 0;
            }
        };
        PriorityQueue<Node<int[][]>> queue = new PriorityQueue<>(5, comparator);
        initState.setManhatten(methods.manhattan(initState, targetState));
        queue.add(initState);
        while (!queue.isEmpty()) {
            Node<int[][]> node = queue.remove();
            System.out.println("State to open: " + Arrays.deepToString(node.getData()));
            if (!node.equals(targetState)) {
                visitedNodes.add(node);
                List<Node<int[][]>> siblings = methods.getSiblings(node.getData()).stream().<Node<int[][]>>map(Node::new).collect(Collectors.toList());
                siblings.forEach(x -> System.out.println("Neighbour " + Arrays.deepToString(x.getData()) + " is added for opened state"));
                siblings.forEach(x -> x.setManhatten(methods.manhattan(x, targetState)));
                for (int i = 0; i < siblings.size(); i++) {
                    if (!methods.contains(visitedNodes, siblings.get(i))) {
                        queue.add(siblings.get(i));
                    } else {
                        System.out.println("This node is already visited: " + Arrays.deepToString(siblings.get(i).getData()));
                    }
                }
            } else {
                long endTime = System.currentTimeMillis();
                System.out.println("Steps count is:" + String.valueOf(visitedNodes.size()));
                System.out.println("Solution is founded. Time spent is " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(endTime - startTime),
                        TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime - startTime))
                ));
                break;
            }
        }
    }

}
