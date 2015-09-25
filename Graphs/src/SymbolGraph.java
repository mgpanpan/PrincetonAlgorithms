/**
 * Created by pmg on 2015/9/14.
 */
public class SymbolGraph
{
    private ST<String, Integer> index;
    private String[] name;
    private Graph G;

    public SymbolGraph(String filename, String delim)
    {
        index = new ST<String, Integer>();
        In in = new In(filename);
        int k = 0;
        while (in.hasNextLine())
        {
            String[] line = in.readLine().split(delim);
            for (int i = 0; i < line.length; i++)
                if (!index.contains(line[i]))
                {
                    index.put(line[i], k);// use trim to delete the space character at the start or the end of the string.???
                    k++;
                }
            name = new String[index.size()];
            for (String key : index.keys())
                name[index.get(key)] = key;
        }

        in = new In(filename);
        G = new Graph(index.size());
        while (in.hasNextLine())
        {
            String[] line = in.readLine().split(delim);
            for (int i = 1; i < line.length; i++)
                G.addEdge(index.get(line[0]), index.get(line[i]));
        }
    }

    public String name(int index)
    { return name[index]; }

    public int index(String name)
    { return index.get(name); }

    public Graph G()
    { return G; }

    public boolean contains(String name)
    { return index.contains(name); }

    // to add an edge to a SymbolGraph object, do the addEdge operation to the return value
    // of G() method, which reference the underlying Graph object.

    public static void main(String[] args)
    {
        StdOut.print("Input a filename and its delimiter: ");
        String filename = StdIn.readLine();
        String delimiter = StdIn.readLine();
        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        StdOut.print("Input name of an element: ");
        while (StdIn.hasNextLine())
        {
            String name = StdIn.readLine();
            StdOut.println("Elements that are adjacent to " + name + ":");
            Graph G = sg.G();
            for (int w : G.adj(sg.index(name)))
                StdOut.println("    " + sg.name(w));
        }
    }


}
