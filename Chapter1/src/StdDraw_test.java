/**
 * Created by pmg on 2015/6/30.
 */
import java.util.Arrays;
public class StdDraw_test
{
    public static void main(String[] args)
    {
        Draw d1 = new Draw();
        int N = 100;
        d1.setXscale(0, N);
        d1.setYscale(0, N * N);
        d1.setPenRadius(0.01);
        for (int i = 1; i <= N; i++)
        {
            d1.point(i, i);
            d1.point(i, i * i);
            d1.point(i, i * Math.log(i));
        }

        Draw d2 = new Draw();
        N = 50;
        double[] a = new double[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.random();
        for (int i = 0; i < N; i++)
        {
            double x = 1.0 * i / N;
            double y = a[i] / 2.0;
            double rw = 0.3 / N;
            double rh = a[i] / 2.0;
            d2.filledRectangle(x, y, rw, rh);
        }
        Arrays.sort(a);

        Draw d3 = new Draw();
        for (int i = 0; i < N; i++)
        {
            double x = 1.0 * i / N;
            double y = a[i] / 2.0;
            double rw = 0.3 / N;
            double rh = a[i] / 2.0;
            d3.filledRectangle(x, y, rw, rh);
        }
    }
}
