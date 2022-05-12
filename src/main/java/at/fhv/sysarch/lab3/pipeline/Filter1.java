package at.fhv.sysarch.lab3.pipeline;

public class Filter1 implements Filter{

    @Override
    public PipelineData write(PipelineData pd) {
        System.out.println("Filter1 doing stuff");
        return pd;
    }
}
