package at.fhv.sysarch.lab3.pipeline;

public interface Filter {
    PipelineData write(PipelineData pd);
}