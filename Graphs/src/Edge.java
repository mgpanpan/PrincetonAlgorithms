/**
 * Created by pmg on 2015/11/5.
 */
public class Edge
{
    private final int v;
    private final int w;
    private final double weight;
    public Edge(int v, int w, double weight)
    {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight()
    { return weight; }

    public int either()
    { return v; }

    public int other(int vertex)
    {
        if (vertex == v)       return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    public int compareTo(Edge that)
    {
        if      (this.weight < that.weight) return -1;
        else if (this.weight > that.weight) return +1;
        else                                return 0;
    }

    public String toString()
    { return String.format("%d-%d %.5f", v , w, weight); }

    public static void main(String[] args)
    {
        Edge e = new Edge(12, 13, 3.14);
        StdOut.println(e);
    }

}
