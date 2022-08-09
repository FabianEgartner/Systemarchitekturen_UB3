package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.PullFilter;
import at.fhv.sysarch.lab3.pipeline.api.PushFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.obj.PipelineData;
import at.fhv.sysarch.lab3.pipeline.utils.PipeLineUtils;
import com.hackoeur.jglm.Mat4;

public class ModelViewTransformationFilter implements PullFilter<Face, Face>, PushFilter<Face, Face> {

    private Pipe<Face> predecessor;
    private Pipe<Face> successor;
    private final PipelineData pd;
    private Mat4 rotationMatrix;

    public ModelViewTransformationFilter(PipelineData pd) {this.pd = pd; }

    @Override
    public Face read() {
        Face input = predecessor.read();

        if (null == input)
            return null;
        else if (PipeLineUtils.isFaceEnd(input))
            return input;

        return process(input);
    }

    @Override
    public void write(Face input) {
        Face result = process(input);

        if (null == result)
            return;

        if (this.successor != null)
            this.successor.write(result);
    }

    @Override
    public Face process(Face face) {

        Mat4 modelTranslation = pd.getModelTranslation();
        Mat4 viewTransform = pd.getViewTransform();

        Mat4 updatedTransformation = viewTransform.multiply(modelTranslation).multiply(rotationMatrix);

        return new Face(
                updatedTransformation.multiply(face.getV1()),
                updatedTransformation.multiply(face.getV2()),
                updatedTransformation.multiply(face.getV3()),
                updatedTransformation.multiply(face.getN1()),
                updatedTransformation.multiply(face.getN2()),
                updatedTransformation.multiply(face.getN3())
        );
    }

    @Override
    public void setPipePredecessor(Pipe<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public void setPipeSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }
}