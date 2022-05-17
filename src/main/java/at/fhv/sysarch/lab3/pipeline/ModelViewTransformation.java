package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import jdk.jshell.execution.Util;

public class ModelViewTransformation<I extends Face> implements Filter<I> {

    private PipelineData pd;
    private Pipe successor;
    private Mat4 rotationMatrix;

    public ModelViewTransformation(PipelineData pd) {this.pd = pd; }

    public void write(I input) {
        Face result = process(input);
        if (null == result) {
            return;
        }

        if (this.successor != null) {
            this.successor.write(result);
        }
    }

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
    public void setPipeSuccessor(Pipe successor) {
        this.successor = successor;
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }
}