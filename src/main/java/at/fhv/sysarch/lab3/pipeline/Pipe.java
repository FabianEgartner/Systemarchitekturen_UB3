package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

public class Pipe {

    private Filter successor;

    public void setSuccessor (Filter successor) {
        this.successor = successor;
    }

    public void write(Face face) {
        System.out.println("Pipe got Face");
        this.successor.write(face);
    }
}
