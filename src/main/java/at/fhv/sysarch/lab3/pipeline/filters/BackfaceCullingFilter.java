package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.Filter;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.utils.PipeLineUtils;
import com.hackoeur.jglm.Vec4;

public class BackfaceCullingFilter<I extends Face> implements Filter<I, Face> {

    private Pipe<Face> predecessor;
    private Pipe<Face> successor;

    @Override
    public I read() {
        Face input = predecessor.read();

        if (null == input) {
            return null;
        } else if (PipeLineUtils.isFaceMakingEnd(input)) {
            return (I) input;
        }

        return (I) process(input);
    }

    @Override
    public void write(I input) {
        Face result = process(input);

        if (null == result) {
            return;
        }

        if (null != this.successor) {
            this.successor.write(result);
        }
    }

    @Override
    public Face process(Face face) {
        Vec4 v1 = face.getV1();
        Vec4 n1 = face.getN1();
        float dotProduct = v1.dot(n1);

        if (dotProduct > 0) {
            return null;
        }

        return face;
    }

    @Override
    public void setPipePredecessor(Pipe<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public void setPipeSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}
