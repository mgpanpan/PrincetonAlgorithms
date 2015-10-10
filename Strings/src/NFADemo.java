/**
 * Created by pmg on 2015/10/9.
 * Plot the NFA
 */
public class NFADemo
{
    private char[] re;        // match transitions
    private Digraph G;        // epsilon transitions
    private int M;            // number of states

    public NFADemo(String regexp)
    {
        // use stack to keep track of the positions of '('and '|'
        // issue : or的优先级最低
        Stack<Integer> ops = new Stack<Integer>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M+1);

        // 第一次遍历，确定层次，仅为作图用
        int[] levels = new int[M];    // levels仅在 * 和 | 处非零
        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|')
                ops.push(i);
            else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    levels[or] = maxOfArr(levels, lp, i) + 1;
                }
                else lp = or;
            }
            if (i < M - 1 && re[i+1] == '*')
                levels[i+1] = maxOfArr(levels, lp, i+1) + 1;
        }
        int maxLevel = maxOfArr(levels, 0, M);

        StdDraw.setCanvasSize(800, 800 * 2 * maxLevel / M);
        StdDraw.setXscale(0, M);
        StdDraw.setYscale(-maxLevel, maxLevel);

        double r = 0.2;
        for (int v = 0; v < G.V(); v++) {
            if (v < M) {
                StdDraw.circle((double) v, 0.0, r);
                StdDraw.text((double) v, 0.0, (new Character(re[v]).toString()));
            }
            else StdDraw.filledCircle((double) v, 0.0, r);
        }

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.005);
        ops = new Stack<Integer>();
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

                    // connect (lp, or+1), (or, i)
                    double level = (double) levels[or];
                    StdDraw.line(or, r, or, level);
                    StdDraw.line(or, level, i, level);
                    StdDraw.arrow(i, level, i, r);
                    StdDraw.line(lp, -r, lp, -level);
                    StdDraw.line(lp, -level, or + 1, -level);
                    StdDraw.filledArrow(or + 1, -level, or + 1, -r);
                }
                else lp = or;   // 此时是'(', 在后面跟的是*时会用到
            }
            if (i < M - 1 && re[i+1] == '*') {
                // look ahead
                G.addEdge(lp, i+1);
                G.addEdge(i+1, lp);

                // connect (lp, i+1), (i+1, lp)
                double level = (double) levels[i+1];
                StdDraw.line(i+1, r, i+1, level);
                StdDraw.line(i+1, level, lp, level);
                StdDraw.filledArrow(lp, level, lp, r);
                StdDraw.line(lp, -r, lp, -level);
                StdDraw.line(lp, -level, i+1, -level);
                StdDraw.filledArrow(i+1, -level, i+1, -r);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G.addEdge(i, i + 1);
                StdDraw.filledArrow(i + r, 0.0, i + 1 - r, 0.0);
            } else if (re[i] != '|') { // match transitions
                StdDraw.setPenColor();
                StdDraw.filledArrow(i + r, 0.0, i + 1 - r, 0.0);
                StdDraw.setPenColor(StdDraw.RED);
            }
        }
    }

    private int maxOfArr(int[] arr, int lo, int hi) // [lo, hi)
    {
        // arr elements are >= 0
        int max = 0;
        for (int i = lo; i < hi; i++)
            if (arr[i] > max) max = arr[i];
        return max;
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

            // optimization if no states reachable
            if (pc.size() == 0) return false;
        }

        for (int v : pc) if (v == M) return true;
        return false;
    }

    public static void main(String[] args)
    {
        // StdOut.print("Input a regular expression: ");
        // String regexp = "(" + StdIn.readLine() + ")";
        // String regexp = "(" + "(A*B|AC)D" + ")";
        String regexp = "(" + ".*AB((C|D*E)F)*G" + ")";
        while (StdIn.hasNextLine()) {
            String txt = StdIn.readLine();
            if (txt.indexOf('|') >= 0) {
                throw new IllegalArgumentException("| character in text is not supported");
            }
            NFADemo nfa = new NFADemo(regexp);
            StdOut.println(nfa.recognizes(txt));
        }
    }
}
