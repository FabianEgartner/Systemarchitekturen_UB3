package at.fhv.sysarch.lab3.pipeline.api;

import at.fhv.sysarch.lab3.pipeline.obj.Pipe;

public interface PushFilter<I, O> {

    void write(I data);

    void setPipeSuccessor(Pipe<O> successor);

    O process(I input);

}