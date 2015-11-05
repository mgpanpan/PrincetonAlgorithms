/**
 * Created by pmg on 2015/11/5.
 */
import java.util.regex.*;

public class TestRegex
{
    public static void main(String[] args)
    {
        String regex1 = "\n p";
        String txt1 = "\n p";

        String regex2 = "\\n p";
        String txt2 = "\n p";

        String regex3 = "\b p";
        String txt3 = "\b p";

        String regex4 = "\\b p";
        String txt4 = "\b p";

        StdOut.println(Pattern.matches(regex1, txt1));
        StdOut.println(Pattern.matches(regex2, txt2));
        StdOut.println(Pattern.matches(regex3, txt3));
        StdOut.println(Pattern.matches(regex4, txt4));

        // Pattern p = Pattern.compile("\\y x");

    }
}
