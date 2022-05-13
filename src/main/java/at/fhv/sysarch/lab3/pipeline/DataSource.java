package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class DataSource {

    private Pipe successor;

    public void setPipeSuccessor(Pipe pipe) {this.successor = pipe; }

    public void write(I model) {
        for(Face face : model.getFaces()) {
            System.out.println("DataSource extracted face");
            successor.write(face);
        }
    }
}