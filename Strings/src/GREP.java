/**
 * Created by pmg on 2015/10/10.
 */
public class GREP
{
    public static void main(String[] args)
    {
        StdOut.print("Input a regular expression: ");
        String regexp = "(.*" + StdIn.readLine() + ".*)";
        NFA nfa = new NFA(regexp);
        StdOut.print("Input a filename: ");
        In in = new In(StdIn.readLine());
        while (in.hasNextLine()) {
            String txt = in.readLine();
            if (nfa.recognizes(txt)) StdOut.println(txt);
        }
    }
}
