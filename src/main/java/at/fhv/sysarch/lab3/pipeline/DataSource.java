package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class DataSource<I extends Model> implements Filter<I> {

    private Pipe successor;

    public void setPipeSuccessor(Pipe pipe) {this.successor = pipe; }

    public void write(I model) {

        for(Face face : model.getFaces()) {
            // TODO: write face to next filter
            successor.write(face);
        }
    }
}