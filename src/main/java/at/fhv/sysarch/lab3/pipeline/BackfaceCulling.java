package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

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
        if (face.getV1().dot(face.getN1()) > 0) {
            return null;
        }
        return face;
    }

    @Override
    public void setPipeSuccessor(Pipe successor) {
        this.successor = successor;
    }
}
