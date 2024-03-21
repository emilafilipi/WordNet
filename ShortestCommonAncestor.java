package Laboratore.Lab5;

import edu.princeton.cs.algs4.*;

public class ShortestCommonAncestor {
    private Digraph G;

    //constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G){
        if(G == null)
            throw new IllegalArgumentException("Argument to the constructor cannot be null");
        DirectedCycle cycleFinder = new DirectedCycle(G);
        if (cycleFinder.hasCycle()) {
            throw new IllegalArgumentException("Input is not a rooted DAG");
        }
        this.G = G;
    }

    //length of shortest ancestral path between v and w
    public int length(int v, int w){
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++){
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)){
                int dist = bfsV.distTo(i) + bfsW.distTo(i);
                if (dist < minDist)
                    minDist = dist;
            }
        }
        return minDist;
    }

    //a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w){
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int ancestor = -1;
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++){
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)){
                int dist = bfsV.distTo(i) + bfsW.distTo(i);
                if (dist < minDist) {
                    ancestor = i;
                    minDist = dist;
                }
            }
        }
        return ancestor;
    }

    //length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        int minDist = Integer.MAX_VALUE;
        for (int v : subsetA){
            for (int w : subsetB){
                int dist = length(v, w);
                if (dist < minDist)
                    minDist = dist;
            }
        }
        return minDist;
    }

    //a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        int ancestor = -1;
        int minDist = Integer.MAX_VALUE;
        for(int v : subsetA){
            for (int w : subsetB){
                int dist = length(v, w);
                if (dist < minDist){
                    minDist = dist;
                    ancestor = ancestor(v, w);
                }
            }
        }
        return ancestor;
    }

    //unit testing (required)
    public static void main(String[] args){
        In in = new In("C:\\Users\\User\\IdeaProjects\\Algoritmike\\src\\Laboratore\\Lab5\\digraph1.txt");
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()){
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
