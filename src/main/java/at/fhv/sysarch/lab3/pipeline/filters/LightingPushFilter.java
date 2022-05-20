package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.PushFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pair;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.obj.PipelineData;
import javafx.scene.paint.Color;

public class LightingPushFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private Pipe<Pair<Face, Color>> successor;
    private final PipelineData pd;

    public LightingPushFilter(PipelineData pipelineData) {
        this.pd = pipelineData;
    }

    @Override
    public void write(Pair<Face, Color> pair) {
        Pair<Face, Color> result = process(pair);

        if (null == result) {
            return;
        }

        if (this.successor != null) {
            this.successor.write(result);
        }
    }

    @Override
    public Pair<Face, Color> process(Pair<Face, Color> pair) {
        Face face = pair.fst();

        float dotProduct = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());

        if (dotProduct <= 0) {
            return new Pair<>(face, Color.BLACK);
        }

        return new Pair<>(face, pair.snd().deriveColor(0, 1, dotProduct, 1));
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
