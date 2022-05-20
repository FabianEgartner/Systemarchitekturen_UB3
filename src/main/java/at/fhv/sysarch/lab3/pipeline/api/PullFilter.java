package at.fhv.sysarch.lab3.pipeline.api;

import at.fhv.sysarch.lab3.pipeline.obj.Pipe;

public interface PullFilter<I, O> {

    I read();

    void setPipePredecessor(Pipe<O> predecessor);

    I process(O input);

}