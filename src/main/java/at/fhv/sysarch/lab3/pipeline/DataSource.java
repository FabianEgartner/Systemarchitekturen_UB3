package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Model;

public class DataSource {

    public PipelineData write(Filter filter, PipelineData pd) {
        Pipe pipe = new Pipe(filter);
        return pipe.write(pd);
    }
}
