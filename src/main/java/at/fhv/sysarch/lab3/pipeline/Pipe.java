package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

// TODO: how can pipes be used for different data types?
public class Pipe<T> {

    private Filter successor;

    public void setSuccessor (Filter successor) {
        this.successor = successor;
    }

    public void write(T data) {
        this.successor.write(data);
    }
}
