package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class LightingFilter<I extends Face> implements Filter<Pair<Face, Color>>{

    private Pipe<Pair<Face, Color>> successor;
    private final PipelineData pd;

    public LightingFilter(PipelineData pipelineData) {
        this.pd = pipelineData;
    }

    @Override
    public void write(Pair<Face, Color> input) {
        Pair<Face, Color> result = process(input);
        if (null == result) {
            return;
        }

        if (this.successor != null) {
            this.successor.write(result);
        }
    }

    public Pair<Face, Color> process(Pair<Face, Color> input) {
        Face face = input.fst();

        float dotProduct = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());

        if (dotProduct <= 0) {
            return new Pair<>(face, Color.BLACK);
        }

        return new Pair<>(face, input.snd().deriveColor(0, 1, dotProduct, 1));
    }

    @Override
    public void setPipeSuccessor(Pipe successor) {
        this.successor = successor;
    }
}
