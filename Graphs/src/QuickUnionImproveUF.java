public class QuickUnionImproveUF
{
    private int[] id;
    private int[] sz;
    private int count;
    private int cost = 0;  // count the number of array access.

    public int getCost()
    { return cost; }
    public void setCost(int cost)
    { this.cost = cost; }
    
    public QuickUnionImproveUF(int N)
    {
        id = new int[N];
        sz = new int[N];
        count = N;
        for (int i = 0; i < N; i++)
            id[i] = i;
        for (int i = 0; i < N; i++)
            sz[i] = 1;
    }

    private int find(int i)
    {
        while (id[i] != i)
        {
            // id[i] = id[id[i]];  // flatten the tree (improvement 2).
            i = id[i];
            cost++;
        }
        return i;
    }
    
    public void union(int p, int q)
    {
        int p_root = find(p);
        int q_root = find(q);

        if (p_root == q_root) return;
        
        if (sz[p_root] < sz[q_root])
        {
            id[p_root] = q_root;
            sz[q_root] += sz[p_root];
            cost += 4;
        }
        else
        {
            id[q_root] = p_root;
            sz[p_root] += sz[q_root];
            cost += 4;
        }
        count--;
     }

    public boolean connected(int p, int q)
    {
        return find(p) == find(q);
    }

    public int count()
    { return count; }

    public static void main(String[] args)
    {
        In in = new In("mediumUF.txt");
        int N = in.readInt();
        QuickUnionImproveUF uf = new QuickUnionImproveUF(N);
        VisualAccumulator va = new VisualAccumulator(900, 20); // 900 is the number of connects in file MediumUF.txt
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
