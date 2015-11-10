import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by pmg on 2015/11/10.
 */

public class Outcast {
    // constructor takes a WordNet object
    private WordNet wordnet;
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int max = -1;
        int curr;
        String outcast = "";
        for (int i = 0; i < nouns.length; i++) {
            curr = 0;
            for (int j = 0; j < nouns.length; j++) {
                int distance = wordnet.distance(nouns[i], nouns[j]);
                if (distance != -1)   // -1 not added to the answer.
                    curr += distance;
            }
            if (curr > max) {
                max = curr;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
        String path = "hw6/src/wordnet/";
        WordNet wordnet = new WordNet(path+"synsets.txt", path+"hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);

        while (StdIn.hasNextLine()) {
            String filename = StdIn.readLine();
            In in = new In(path+filename);
            String[] nouns = in.readAllStrings();
            in.close();
            StdOut.println(outcast.outcast(nouns));
        }
    }
}


