import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by pmg on 2015/11/10.
 */
public class WordNet
{
    private ST<String, Bag<Integer>> index;
    private ST<Integer, String> synset;
    private SAP sap;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        index = new ST<String, Bag<Integer>>();
        In in = new In(synsets);
        synset = new ST<Integer, String>();
        int V = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] lineElement = line.split(",");
            synset.put(V++, lineElement[1]);
            String[] words = lineElement[1].split(" ");
            for (int i = 0; i < words.length; i++) {
                if (!index.contains(words[i])) index.put(words[i], new Bag<Integer>());
                index.get(words[i]).add(Integer.parseInt(lineElement[0]));
            }
        }
        in.close();

        Digraph G = new Digraph(V);
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] lineElement = line.split(",");
            for (int i = 1; i < lineElement.length; i++)
                G.addEdge(Integer.parseInt(lineElement[0]),
                        Integer.parseInt(lineElement[i]));
        }
        in.close();

        // Determine whether correct Graph is a rooted DAG
        DirectedCycle DC = new DirectedCycle(G);
        if (DC.hasCycle()) throw new IllegalArgumentException("Input graph can not have a cycle.");
        // the root of a rooted DAG has no out edge.
        int rootCnt = 0;
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) == 0) rootCnt++;
        }
        if (rootCnt == 1) { sap = new SAP(G); return; }
        else
            throw new IllegalArgumentException("Input graph does not have a root.");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return index.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return index.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA))
            throw new IllegalArgumentException(nounA + " is not a WordNet noun");
        if (!isNoun(nounB))
            throw new IllegalArgumentException(nounB + " is not a WordNet noun");
        return sap.length(index.get(nounA), index.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA))
            throw new IllegalArgumentException(nounA + " is not a WordNet noun");
        if (!isNoun(nounB))
            throw new IllegalArgumentException(nounB + " is not a WordNet noun");
        int ancestor = sap.ancestor(index.get(nounA), index.get(nounB));
        return synset.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String path = "hw6/src/wordnet/";
        // WordNet wordnet = new WordNet(path+"synsets.txt", path+"hypernyms.txt");
        WordNet wordnet = new WordNet(path+"synsets8.txt", path+"hypernyms3InvalidTwoRoots.txt"); // exception test
        while (StdIn.hasNextLine()) {
            String[] line = StdIn.readLine().split(",");
            StdOut.println(wordnet.sap(line[0], line[1]) + ", " + wordnet.distance(line[0], line[1]));
        }
    }
}
