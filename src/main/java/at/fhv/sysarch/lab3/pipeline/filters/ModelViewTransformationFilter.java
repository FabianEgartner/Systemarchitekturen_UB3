package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Filter;
import at.fhv.sysarch.lab3.pipeline.Pipe;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;

public class ModelViewTransformationFilter<I extends Face> implements Filter<I, Face> {

    private final PipelineData pd;
    private Pipe<Face> successor;
    private Mat4 rotationMatrix;

    public ModelViewTransformationFilter(PipelineData pd) {this.pd = pd; }

    @Override
    public void write(I input) {
        Face result = process(input);

        if (null == result) {
            return;
        }

        if (this.successor != null) {
            this.successor.write(result);
        }
    }

    @Override
    public Face process(Face face) {

        // compute updated model-view transformation
        Mat4 modelTranslation = pd.getModelTranslation();
        Mat4 viewTransformation = pd.getViewTransform();

        Mat4 updatedTransformation = viewTransformation.multiply(modelTranslation).multiply(rotationMatrix);

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
    public void setPipeSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }
}