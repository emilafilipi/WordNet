package Laboratore.Lab5;
import edu.princeton.cs.algs4.*;
import java.util.*;

public class WordNet {

    //map ku celesi eshte ID e synset dhe value eshte seti i emrave te nje synseti
    private final Map<Integer, HashSet<String>> idToNouns;

    //map ku celesi eshte nje emer qe ben pjese ne nje synset dhe value eshte seti i ID te synseteve ku ky emer ben pjese
    private final Map<String, HashSet<Integer>> nounToIds;

    //map ku celesi eshte ID e synset dhe vlera eshte synset
    private final Map<Integer, String> idToSynset;

    //perdoret per te gjetur sca dhe distance
    private final Digraph G;

    public WordNet(String synsets, String hypernyms){

        if(synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Arguments cannot be null");

        //synsets
        In in = new In(synsets);
        idToNouns = new HashMap<>();
        nounToIds = new HashMap<>();
        idToSynset = new HashMap<>();

        while (in.hasNextLine()){
            String[] line = in.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            idToSynset.put(id, line[1]);
            String[] words = line[1].split(" ");
            HashSet<String> set = new HashSet<>();
            for (String word : words){
                set.add(word);
                if(!nounToIds.containsKey(word))
                    nounToIds.put(word, new HashSet<>());
                nounToIds.get(word).add(id);
            }
            idToNouns.put(id, set);
        }


        //hypernyms
        G = new Digraph(idToSynset.size());

        in = new In(hypernyms);

        while (in.hasNextLine()){
            String[] line = in.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++){
                G.addEdge(v, Integer.parseInt(line[i]));
            }
        }

    }


    //the set of all WordNet nouns
    public Iterable<String> nouns(){
        return nounToIds.keySet();
    }

    //is the word a WordNet noun?
    public boolean isNoun(String word){
        if (word == null)
            throw new IllegalArgumentException("Argument cannot be null");
        return nounToIds.containsKey(word);
    }

    //a synset that is the shortest common ancestor of noun1 and noun2, The methods distance() and sca() must make
    // exactly one call to the lengthSubset() and ancestorSubset() methods in ShortestCommonAncestor, respectively.
    public String sca(String noun1, String noun2){
        if(noun1 == null || noun2 == null)
            throw new IllegalArgumentException("Arguments cannot be null");
        if(!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException("Arguments must be WordNet nouns");
        HashSet<Integer> synsetsNouns1 = nounToIds.get(noun1);
        HashSet<Integer> synsetsNouns2 = nounToIds.get(noun2);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        int ancestor = sca.ancestorSubset(synsetsNouns1, synsetsNouns2);
        return idToSynset.get(ancestor);
    }

    //distance between noun1 and noun2, The methods distance() and sca() must make exactly one call to the lengthSubset() and
    //ancestorSubset() methods in ShortestCommonAncestor, respectively.
    public int distance(String noun1, String noun2){
        if(noun1 == null || noun2 == null)
            throw new IllegalArgumentException("Arguments cannot be null");
        if(!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException("Arguments must be WordNet nouns");
        HashSet<Integer> synsetsName1 = nounToIds.get(noun1);
        HashSet<Integer> synsetsName2 = nounToIds.get(noun2);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        return sca.lengthSubset(synsetsName1, synsetsName2);
    }

    //unit testing
    public static void main(String[] args){
        WordNet wordNet = new WordNet("C:\\Users\\User\\IdeaProjects\\Algoritmike\\src\\Laboratore\\Lab5\\synsets.txt", "C:\\Users\\User\\IdeaProjects\\Algoritmike\\src\\Laboratore\\Lab5\\hypernyms.txt");
        System.out.println(wordNet.isNoun("gate"));
        System.out.println(wordNet.isNoun("nonexistent"));

        System.out.println(wordNet.sca("AND_circuit", "AND_gate"));
        System.out.println(wordNet.distance("AND_circuit", "AND_gate"));
    }

}
