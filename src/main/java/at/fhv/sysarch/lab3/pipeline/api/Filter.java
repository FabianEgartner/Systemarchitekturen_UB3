package at.fhv.sysarch.lab3.pipeline.api;

import at.fhv.sysarch.lab3.pipeline.obj.Pipe;

// TODO: Think about how generics can be applied in this context
// TODO: The current solution is JUST an illustration and not sufficient for the example. It only shows how generics may be used.
// TODO: Can you use one interface for both implementations (push and pull)? Or do they require specific interfaces?
public interface Filter<I, O> {

    I read();

    void write(I data);

    void setPipePredecessor(Pipe<O> predecessor);

    void setPipeSuccessor(Pipe<O> successor);

    O process(I input);
}