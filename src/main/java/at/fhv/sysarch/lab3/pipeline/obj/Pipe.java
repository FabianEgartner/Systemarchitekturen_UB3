package at.fhv.sysarch.lab3.pipeline.obj;

import at.fhv.sysarch.lab3.pipeline.api.Filter;

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
