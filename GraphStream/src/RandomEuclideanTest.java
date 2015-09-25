import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.*;

/**
 * Created by pmg on 2015/9/21.
 */


public class RandomEuclideanTest
{
    public static void main(String[] args)
    {
        Graph graph = new SingleGraph("random euclidean");
        // RandomEuclideanGenerator gen = new RandomEuclideanGenerator(2);
        // gen.setThreshold(0.06);
        GridGenerator gen = new GridGenerator(false, false);
        gen.addSink(graph);
        gen.begin();
        int N = 10;
        for (int i = 0; i < N; i++)
            gen.nextEvents();
        gen.end();
        graph.display();
    }

}
