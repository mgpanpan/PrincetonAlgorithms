/**
 * Created by pmg on 2015/9/26.
 */
public class DepthFirstOrder
{
    private Queue<Integer> pre;
    private Queue<Integer> post;
    private Stack<Integer> reversePost;
    private boolean[] marked;
    public DepthFirstOrder(Digraph G)
    {
        pre = new Queue<Integer>();
        post = new Queue<Integer>();
        reversePost = new Stack<Integer>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v)
    {
        marked[v] = true;
        pre.enqueue(v);
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
        post.enqueue(v);
        reversePost.push(v);
    }

    public Iterable<Integer> pre()
    { return pre; }

    public Iterable<Integer> post()
    { return post; }

    public Iterable<Integer> reversePost()
    { return reversePost; }

    public static void main(String[] args)
    {
        // 注意对同一个DAG，这几个输出都不唯一，和adj表中元素的先后有关（即图建立过程中addEdge的顺序有关）
        Digraph G = new Digraph(new In("tinyDAG.txt"));
        DepthFirstOrder DFOrder = new DepthFirstOrder(G);
        StdOut.println("Pre order: ");
        for (int v : DFOrder.pre())
            StdOut.print(v + " ");
        StdOut.println();
        StdOut.println("Post order: ");
        for (int v : DFOrder.post())
            StdOut.print(v + " ");
        StdOut.println();
        StdOut.println("Reversed Post order: ");
        for (int v : DFOrder.reversePost())
            StdOut.print(v + " ");
        StdOut.println();
    }

}
