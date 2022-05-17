package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Filter;
import at.fhv.sysarch.lab3.pipeline.Pipe;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PerspectiveProjection implements Filter<Pair<Face, Color>, Pair<Face, Color>> {

    private Pipe<Pair<Face, Color>> successor;
    private final PipelineData pd;

    public PerspectiveProjection(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Pair<Face, Color> pair) {
        Pair<Face, Color> result = process(pair);

        if (null != this.successor) {
            this.successor.write(result);
        }
    }

    @Override
    public Pair<Face, Color> process(Pair<Face, Color> pair) {
        Mat4 projectionTransform = pd.getProjTransform();
        Face face = pair.fst();

        Face projectedFace = new Face(
                projectionTransform.multiply(face.getV1()),
                projectionTransform.multiply(face.getV2()),
                projectionTransform.multiply(face.getV3()),
                face
        );

        return new Pair<>(projectedFace, pair.snd());
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
