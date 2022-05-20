package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.api.PullFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import com.hackoeur.jglm.Vec4;

public class DataSource implements PullFilter<Face, Face> {

    private final Model model;
    private int currentFaceIndex;

    public DataSource(Model model) {
        this.model = model;
    }

    @Override
    public Face read() {
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

    @Override
    public void setPipePredecessor(Pipe<Face> predecessor) {

    }

    @Override
    public Face process(Face input) {
        return input;
    }

}