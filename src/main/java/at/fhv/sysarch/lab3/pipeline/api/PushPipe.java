package at.fhv.sysarch.lab3.pipeline.api;

public interface PushPipe<I> {

    void write(I input);

    void setSuccessor(PushFilter<I, ?> successor);
}
