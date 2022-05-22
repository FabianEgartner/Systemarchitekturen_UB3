package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import com.hackoeur.jglm.Vec4;

public class DataSource {

    private Pipe<Face> successor;
    private int currentFaceIndex;

    public Face read(Model model) {
        if (currentFaceIndex >= model.getFaces().size()) {
            // Special marker face to indicate end of data
            return new Face(
                    Vec4.VEC4_ZERO,
                    Vec4.VEC4_ZERO,
                    Vec4.VEC4_ZERO,
                    Vec4.VEC4_ZERO,
                    Vec4.VEC4_ZERO,
                    Vec4.VEC4_ZERO
            );
        }

        return model.getFaces().get(currentFaceIndex++);
    }

    public void write(Model model) {
        for (Face face : model.getFaces()) {
            successor.write(face);
        }
    }

    public void setPipeSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}