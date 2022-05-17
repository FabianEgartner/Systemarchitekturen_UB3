package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Filter;
import at.fhv.sysarch.lab3.pipeline.Pipe;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class ColorFilter<I extends Face> implements Filter<I, Pair<Face, Color>> {

    private Pipe<Pair<Face, Color>> successor;
    private final PipelineData pd;

    public ColorFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(I input) {
        Pair<Face, Color> result = process(input);
        this.successor.write(result);
    }

    @Override
    public Pair<Face, Color> process(I input) {
        return new Pair<>(input, pd.getModelColor());
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
