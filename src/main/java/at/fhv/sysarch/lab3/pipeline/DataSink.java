package at.fhv.sysarch.lab3.pipeline;

public class DataSink implements Filter{

    @Override
    public PipelineData write(PipelineData pd) {
        System.out.println("DataSink doing stuff");
        System.out.println("Rendering...");

        return pd;
    }
}
