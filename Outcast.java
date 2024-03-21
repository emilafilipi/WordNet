package Laboratore.Lab5;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;

    //constructor takes a WordNet object
    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }

    //given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        int maxDistance = -1;
        String outcast = null;
        for(String nounA : nouns){
            int distance = 0;
            for (String nounB : nouns)
                if (!nounA.equals(nounB))
                    distance += wordnet.distance(nounA, nounB);
            if(distance > maxDistance){
                maxDistance = distance;
                outcast = nounA;
            }
        }
        return outcast;
    }

    //test client (see below)
    public static void main(String[] args){
        WordNet wordnet = new WordNet("C:\\Users\\User\\IdeaProjects\\Algoritmike\\src\\Laboratore\\Lab5\\synsets.txt", "C:\\Users\\User\\IdeaProjects\\Algoritmike\\src\\Laboratore\\Lab5\\hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);

        In in = new In("C:\\Users\\User\\IdeaProjects\\Algoritmike\\src\\Laboratore\\Lab5\\outcast5.txt");
        String[] nouns = in.readAllStrings();
        StdOut.println("outcast5.txt" + ": " + outcast.outcast(nouns));
    }

}
