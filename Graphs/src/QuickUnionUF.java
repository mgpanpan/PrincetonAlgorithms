public class QuickUnionUF
{
    private int[] id;

    private int cost = 0;  // count the number of array access.

    public int getCost()
    { return cost; }
    public void setCost(int cost)
    { this.cost = cost; }

    public QuickUnionUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    private int find(int i)
    {
        while (id[i] != i)
        { i = id[i]; cost++; }
        return i;
    }
    
    public void union(int p, int q)
    {
        int p_root = find(p);
        int q_root = find(q);

        id[p_root] = q_root;
        cost++;
    }

    public boolean connected(int p, int q)
    {
        return find(p) == find(q);
    }
    
    public static void main(String[] args)
    {
        In in = new In("mediumUF.txt");
        int N = in.readInt();
        QuickUnionUF uf = new QuickUnionUF(N);
        VisualAccumulator va = new VisualAccumulator(900, 100); // 900 is the number of connects in file MediumUF.txt
        while (!in.isEmpty())
        {
            uf.setCost(0);
            int p = in.readInt();
            int q = in.readInt();
            if (!uf.connected(p, q))
            {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
            va.addDataValue(uf.getCost());
        }
        StdOut.println(va);
    }
}
