package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class ColorFilter<I extends Face> implements Filter<I> {

    private Pipe<Pair<Face, Color>> successor;
    private final PipelineData pipelineData;

    public ColorFilter(PipelineData pipelineData) {
        this.pipelineData = pipelineData;
    }

    @Override
    public void write(Face face) {
        this.successor.write(new Pair<>(face, pipelineData.getModelColor()));
    }

    @Override
    public void setPipeSuccessor(Pipe successor) {
        this.successor = successor;
    }
}
