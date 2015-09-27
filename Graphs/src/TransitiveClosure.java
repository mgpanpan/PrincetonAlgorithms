/**
 * Created by pmg on 2015/9/27.
 */
public class TransitiveClosure
{
    private DirectedDFS[] all;
    public TransitiveClosure(Digraph G)
    {
        all = new DirectedDFS[G.V()];
        for (int v = 0; v < G.V(); v++)
            all[v] = new DirectedDFS(G, v);
    }

    boolean reachable(int v, int w)
    { return all[v].marked(w); }

    public static void main(String[] args)
    {
        Digraph G = new Digraph(new In("tinyDG.txt"));
        TransitiveClosure tc = new TransitiveClosure(G);
        StdOut.println("Enter vertex pairs, separated by comma");
        while (StdIn.hasNextLine())
        {
            String line = StdIn.readLine();
            String[] vertex = line.split(", ");
            StdOut.print(vertex[0] + " -> " + vertex[1] + " :");
            if (tc.reachable(Integer.parseInt(vertex[0]), Integer.parseInt(vertex[1])))
                StdOut.println("Reachable");
            else StdOut.println("Unreachable");
        }
    }

}
