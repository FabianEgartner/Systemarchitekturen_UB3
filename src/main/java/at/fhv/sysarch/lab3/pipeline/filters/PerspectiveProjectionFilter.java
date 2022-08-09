package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.PullFilter;
import at.fhv.sysarch.lab3.pipeline.api.PushFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pair;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.obj.PipelineData;
import at.fhv.sysarch.lab3.pipeline.utils.PipeLineUtils;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PerspectiveProjectionFilter implements PullFilter<Pair<Face, Color>, Pair<Face, Color>>, PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private Pipe<Pair<Face, Color>> predecessor;
    private Pipe<Pair<Face, Color>> successor;
    private final PipelineData pd;

    public PerspectiveProjectionFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {
        Pair<Face, Color> input = predecessor.read();

        if (null == input)
            return null;
        else if (PipeLineUtils.isFaceEnd(input.fst()))
            return input;

        return process(input);
    }

    @Override
    public void write(Pair<Face, Color> pair) {
        Pair<Face, Color> result = process(pair);

        if (null != this.successor)
            this.successor.write(result);
    }

    @Override
    public Pair<Face, Color> process(Pair<Face, Color> pair) {
        Mat4 projTransform = pd.getProjTransform();
        Face face = pair.fst();

        Face projectedFace = new Face(
                projTransform.multiply(face.getV1()),
                projTransform.multiply(face.getV2()),
                projTransform.multiply(face.getV3()),
                face
        );

        return new Pair<>(projectedFace, pair.snd());
    }

    @Override
    public void setPipePredecessor(Pipe<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
