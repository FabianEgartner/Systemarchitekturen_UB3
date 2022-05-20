package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.PullFilter;
import at.fhv.sysarch.lab3.pipeline.api.PushFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pair;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.obj.PipelineData;
import javafx.scene.paint.Color;

public class ColorFilter implements PullFilter<Pair<Face, Color>, Face>, PushFilter<Face, Pair<Face, Color>> {

    private Pipe<Face> predecessor;
    private Pipe<Pair<Face, Color>> successor;
    private final PipelineData pd;

    public ColorFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {
        Face input = predecessor.read();

        if (null == input) {
            return null;
        }

        return process(input);
    }

    @Override
    public void write(Face input) {
        Pair<Face, Color> result = process(input);
        this.successor.write(result);
    }

    @Override
    public Pair<Face, Color> process(Face input) {
        return new Pair<>(input, pd.getModelColor());
    }

    @Override
    public void setPipePredecessor(Pipe<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
