/**
 * Created by pmg on 2015/9/14.
 */

/**
 * Test Client
 * Input a filename and its delimiter: tinyEWG_forSymbol.txt
 *  (space)
 * Input name of an element: 0
 * Elements that are adjacent to 0:
 *  6
 *  2
 *  4
 *  7
 * Elements that are adjacent to 1:
 *  3
 *  2
 *  7
 *  5
 * Elements that are adjacent to 2:
 *  6
 *  7
 *  1
 *  0
 *  3
 * Elements that are adjacent to 7:
 *  2
 *  1
 *  0
 *  5
 *  4
 */

public class SymbolEdgeWeightedGraph
{
    private ST<String, Integer> index;
    private String[] name;
    private EdgeWeightedGraph G;

    // SymbolEdgeWeightedGraph 对应的文件规定，一行只能有一个Edge，且由delim分成三部分
    // 分别代表两个vertex以及weight 两个vertex可以是任意String weight必须是double
    public SymbolEdgeWeightedGraph(String filename, String delim)
    {
        index = new ST<String, Integer>();
        In in = new In(filename);
        int k = 0;
        while (in.hasNextLine())
        {
            String[] line = in.readLine().split(delim);
            if (!index.contains(line[0]))
            {
                index.put(line[0], k);// use trim to delete the space character at the start or the end of the string.???
                k++;
            }
            if (!index.contains(line[1]))
            {
                index.put(line[1], k);
                k++;
            }
        }
        name = new String[index.size()];
        for (String key : index.keys())
            name[index.get(key)] = key;

        in = new In(filename);
        G = new EdgeWeightedGraph(index.size());
        while (in.hasNextLine())
        {
            String[] line = in.readLine().split(delim);
            G.addEdge(new Edge(index.get(line[0]), index.get(line[1]), Double.parseDouble(line[2])));
        }
    }

    public String name(int index)
    { return name[index]; }

    public int index(String name)
    { return index.get(name); }

    public EdgeWeightedGraph G()
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
        SymbolEdgeWeightedGraph sg = new SymbolEdgeWeightedGraph(filename, delimiter);
        StdOut.print("Input name of an element: ");
        while (StdIn.hasNextLine())
        {
            String name = StdIn.readLine();
            StdOut.println("Elements that are adjacent to " + name + ":");
            EdgeWeightedGraph G = sg.G();
            for (Edge edge : G.adj(sg.index(name)))
                StdOut.println("    " + sg.name(edge.other(sg.index(name))));
        }
    }


}
