package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.api.PullFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import com.hackoeur.jglm.Vec4;

public class DataSource implements PullFilter<Face, Face> {

    private Pipe<Face> successor;
    private Model model;
    private int currentFaceIndex;

    public DataSource() {}

    public DataSource(Model model) {
        this.model = model;
    }

    @Override
    public Face read() {
        if (currentFaceIndex >= model.getFaces().size()) {

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

    public void write() {
        for (Face face : model.getFaces())
            successor.write(face);
    }

    @Override
    public void setPipePredecessor(Pipe<Face> predecessor) {
        // no predecessor
    }

    public void setPipeSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setCurrentFaceIndex(int currentFaceIndex) {
        this.currentFaceIndex = currentFaceIndex;
    }

    @Override
    public Face process(Face input) {
        return null;
    }
}