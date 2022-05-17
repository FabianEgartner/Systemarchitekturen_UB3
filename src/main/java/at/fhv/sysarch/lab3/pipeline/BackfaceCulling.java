package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec4;

public class BackfaceCulling<I extends Face> implements Filter<I> {

    private Pipe successor;

    @Override
    public void write(Face input) {
        Face result = process(input);

        if (null == result) {
            return;
        }

        if (null != this.successor) {
            this.successor.write(result);
        }
    }

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
    public void setPipeSuccessor(Pipe successor) {
        this.successor = successor;
    }
}
