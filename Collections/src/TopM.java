/**
 * Created by pmg on 2015/7/14.
 */
public class TopM
{
    public static void main(String[] args)
    {
        int M = StdIn.readInt();
        String filename = StdIn.readString();
        In in = new In(filename);
        MinPQGeneric<Transaction> pq = new MinPQGeneric<Transaction>(M+1); // need an addition position
        while (in.hasNextLine())
        {
            pq.insert(new Transaction(in.readLine()));
            if (pq.size() > M) pq.delMin();
        }
        Stack<Transaction> s = new Stack<Transaction>();
        while (!pq.isEmpty()) s.push(pq.delMin());
        // while (!s.isEmpty()) StdOut.println(s.pop());
        for (Transaction t : s) StdOut.println(t);
    }
}
