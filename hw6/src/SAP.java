import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

/**
 * Created by pmg on 2015/11/10.
 */

public class SAP {
    private final Digraph G;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths vbfp = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbfp = new BreadthFirstDirectedPaths(G, w);
        int shortest = -1;
        for (int i = 0; i < G.V(); i++)
            if (vbfp.hasPathTo(i) && wbfp.hasPathTo(i)) {
                int len = vbfp.distTo(i) + wbfp.distTo(i);
                if (shortest == -1 || len < shortest) shortest = len;
            }
        return shortest;
    }
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths vbfp = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbfp = new BreadthFirstDirectedPaths(G, w);
        int shortest = -1;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++)
            if (vbfp.hasPathTo(i) && wbfp.hasPathTo(i)) {
                int len = vbfp.distTo(i) + wbfp.distTo(i);
                if (shortest == -1 || len < shortest)
                { shortest = len; ancestor = i; }
            }
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths vbfp = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbfp = new BreadthFirstDirectedPaths(G, w);
        int shortest = -1;
        for (int i = 0; i < G.V(); i++)
            if (vbfp.hasPathTo(i) && wbfp.hasPathTo(i)) {
                int len = vbfp.distTo(i) + wbfp.distTo(i);
                if (shortest == -1 || len < shortest) shortest = len;
            }
        return shortest;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths vbfp = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbfp = new BreadthFirstDirectedPaths(G, w);
        int shortest = -1;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++)
            if (vbfp.hasPathTo(i) && wbfp.hasPathTo(i)) {
                int len = vbfp.distTo(i) + wbfp.distTo(i);
                if (shortest == -1 || len < shortest)
                { shortest = len; ancestor = i; }
            }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("hw6\\src\\wordnet\\digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
