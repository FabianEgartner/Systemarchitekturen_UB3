package at.fhv.sysarch.lab3.pipeline.obj;

import at.fhv.sysarch.lab3.pipeline.api.Filter;

// TODO: how can pipes be used for different data types?
public class Pipe<I> {

    private Filter<I, ?> predecessor;
    private Filter<I, ?> successor;

    public I read() {
        return this.predecessor.read();
    }

    public void write(I data) {
        this.successor.write(data);
    }

    public void setPredecessor(Filter<I, ?> predecessor) {
        this.predecessor = predecessor;
    }

    public void setSuccessor (Filter<I, ?> successor) {
        this.successor = successor;
    }
}
