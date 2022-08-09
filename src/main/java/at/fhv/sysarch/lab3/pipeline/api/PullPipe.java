package at.fhv.sysarch.lab3.pipeline.api;

public interface PullPipe<I> {

    I read();

    void setPredecessor(PullFilter<I, ?> predecessor);
}
