package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.api.Filter;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;

public class DataSource<I extends Model> implements Filter<I, Face> {

    private Pipe<Face> successor;

    @Override
    public void write(I model) {

        for (Face face : model.getFaces()) {
            successor.write(face);
        }
    }

    @Override
    public Face process(I model) {
        return null;
    }

    @Override
    public void setPipeSuccessor(Pipe<Face> pipe) {this.successor = pipe; }
}