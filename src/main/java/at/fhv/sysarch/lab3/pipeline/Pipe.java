package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

// TODO: how can pipes be used for different data types?
public class Pipe<I> {

    private Filter<I, ?> successor;

    public void setSuccessor (Filter<I, ?> successor) {
        this.successor = successor;
    }

    public void write(I data) {
        this.successor.write(data);
    }
}
