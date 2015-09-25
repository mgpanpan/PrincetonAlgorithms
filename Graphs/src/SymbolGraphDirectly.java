/**
 * Created by pmg on 2015/9/14.
 */
public class SymbolGraphDirectly
{
    private int V;
    private int E;
    private ST<String, SET<String>> adj;

    public SymbolGraphDirectly(String filename, String delim)
    {
        E = 0;
        adj = new ST<String, SET<String>>();
        In in = new In(filename);
        while (in.hasNextLine())
        {
            String[] line = in.readLine().split(delim);
            for (int i = 1; i < line.length; i++)
            {
                addEdge(line[0], line[i]);
                E++;
            }
        }
        V = adj.size();
    }

    public void addEdge(String v, String w)
    {
        if (!adj.contains(v)) adj.put(v, new SET<String>());
        if (!adj.contains(w)) adj.put(w, new SET<String>());
        adj.get(v).add(w);
        adj.get(w).add(v);
    }

    public int V()
    { return V; }

    public int E()
    {return E; }

    public Iterable<String> adj(String v)
    { return adj.get(v); }

    public static void main(String[] args)
    {
        StdOut.print("Input a filename and its delimiter: ");
        String filename = StdIn.readLine();
        String delimiter = StdIn.readLine();
        SymbolGraphDirectly sg = new SymbolGraphDirectly(filename, delimiter);
        StdOut.print("Input name of an element: ");
        while (StdIn.hasNextLine())
        {
            String name = StdIn.readLine();
            StdOut.println("Elements that are adjacent to " + name + ":");
            for (String w : sg.adj(name))
                StdOut.println("    " + w);
        }
    }

}
