/**
 * Created by pmg on 2015/10/9.
 */
public class NFA
{
    private char[] re;        // match transitions
    private Digraph G;        // epsilon transitions
    private int M;            // number of states

    public NFA(String regexp)
    {
        // use stack to keep track of the positions of '('and '|'
        // issue : or的优先级最低
        Stack<Integer> ops = new Stack<Integer>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M+1);

        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|')
                ops.push(i);
            else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or+1);
                    G.addEdge(or, i);
                }
                else lp = or;   // 此时是'(', 在后面跟的是*时会用到
            }
            if (i < M - 1 && re[i+1] == '*') {
                // look ahead
                G.addEdge(lp, i+1);
                G.addEdge(i+1, lp);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == ')')
                G.addEdge(i, i+1);
        }
    }

    public boolean recognizes(String txt)
    {
        Bag<Integer> pc = new Bag<Integer>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++)
            if (dfs.marked(v)) pc.add(v);

        for (int i = 0; i < txt.length(); i++) {
            Bag<Integer> match = new Bag<Integer>();
            for (int v : pc)
                if (v < M)
                    if (re[v] == txt.charAt(i) || re[v] == '.')
                        match.add(v+1);
            dfs = new DirectedDFS(G, match);
            pc = new Bag<Integer>();
            for (int v = 0; v < G.V(); v++)
                if (dfs.marked(v)) pc.add(v);
        }

        for (int v : pc) if (v == M) return true;
        return false;
    }

    public static void main(String[] args)
    {
        StdOut.print("Input a regular expression: ");
        String regexp = "(" + StdIn.readLine() + ")";
        while (StdIn.hasNextLine()) {
            String txt = StdIn.readLine();
            if (txt.indexOf('|') >= 0) {
                throw new IllegalArgumentException("| character in text is not supported");
            }
            NFA nfa = new NFA(regexp);
            StdOut.println(nfa.recognizes(txt));
        }
    }
}
