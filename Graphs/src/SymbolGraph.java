/**
 * Created by pmg on 2015/9/14.
 * Input a filename and its delimiter: movies.txt
 * /
 * Input name of an element: Pacino, Al
 *     Two for the Money (2005)
 *         Serpico (1973)
 *         Sea of Love (1989)
 *         Scent of a Woman (1992)
 *         Scarface (1983)
 *         S1m0ne (2002)
 *         Recruit, The (2003)
 *         People I Know (2002)
 *         Merchant of Venice, The (2004)
 *         Madonna: Truth or Dare (1991)
 *         Looking for Richard (1996)
 *         Insomnia (2002 I)
 *         Insider, The (1999)
 *         Heat (1995)
 *         Godfather: Part III, The (1990)
 *         Godfather: Part II, The (1974)
 *         Godfather, The (1972)
 *         Glengarry Glen Ross (1992)
 *         Gigli (2003)
 *         Frankie and Johnny (1991)
 *         Donnie Brasco (1997)
 *         Dog Day Afternoon (1975)
 *         Dick Tracy (1990)
 *         Devil's Advocate, The (1997)
 *         City Hall (1996)
 *         Carlito's Way (1993)
 *         Any Given Sunday (1999)
 *         ...And Justice for All (1979)
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
        }

        name = new String[index.size()];
        for (String key : index.keys())
            name[index.get(key)] = key;

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
