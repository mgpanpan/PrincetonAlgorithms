public class QuickFindUF
{
    private int[] id;

    private int cost = 0;  // count the number of array access.

    public int getCost()
    { return cost; }
    public void setCost(int cost)
    { this.cost = cost; }

    public QuickFindUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    public void union(int p, int q)
    {
        int p_id = id[p];
        int q_id = id[q];
        if (p_id == q_id) return;
        for (int i = 0; i < id.length; i++)
        {
            if (id[i] == p_id)
            {
                id[i] = q_id; cost++;
            }
            cost++;
        }
    }

    public int find(int i)
    { cost++; return id[i]; }

    public boolean connected(int p, int q)
    { return find(p) == find(q); }
    
    public static void main(String[] args)
    {
        In in = new In("mediumUF.txt");
        int N = in.readInt();
        QuickFindUF uf = new QuickFindUF(N);
        VisualAccumulator va = new VisualAccumulator(900,1300); // 900 is the number of connects in file MediumUF.txt
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
