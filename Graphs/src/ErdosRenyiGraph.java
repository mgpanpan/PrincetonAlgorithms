/**
 * Created by pmg on 2015/9/20.
 */
public class ErdosRenyiGraph
{
    public static Graph randomGraphOut(int V, int E)
    {
        Graph G = new Graph(V);
        for (int e = 0; e < E; e++)
            G.addEdge(StdRandom.uniform(0, V), StdRandom.uniform(0, V));
        return G;
    }

    public static void main(String[] args)
    {
        StdOut.print("Input Graph vertex number and edge number: ");
        int V = StdIn.readInt();
        int E = StdIn.readInt();
        StdOut.println(randomGraphOut(V, E));
    }

}
