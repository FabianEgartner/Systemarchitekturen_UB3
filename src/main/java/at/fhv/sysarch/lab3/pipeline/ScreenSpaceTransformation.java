package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class ScreenSpaceTransformation<I extends Face> implements Filter<Pair<Face, Color>>  {

    private Pipe<Pair<Face, Color>> successor;
    private final PipelineData pd;

    public ScreenSpaceTransformation(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Pair<Face, Color> input) {
        Pair<Face, Color> result = process(input);

        if (null != this.successor) {
            this.successor.write(result);
        }
    }

    public Pair<Face, Color> process(Pair<Face, Color> input) {
        Face face = input.fst();

        Face dividedFace = new Face(
                face.getV1().multiply((1.0f / face.getV1().getW())),
                face.getV2().multiply((1.0f / face.getV2().getW())),
                face.getV3().multiply((1.0f / face.getV3().getW())),
                face
        );

        Face transformedFace = new Face(
                pd.getViewportTransform().multiply(dividedFace.getV1()),
                pd.getViewportTransform().multiply(dividedFace.getV2()),
                pd.getViewportTransform().multiply(dividedFace.getV3()),
                dividedFace
        );

        return new Pair<>(transformedFace, input.snd());
    }

    @Override
    public void setPipeSuccessor(Pipe successor) {
        this.successor = successor;
    }
}
