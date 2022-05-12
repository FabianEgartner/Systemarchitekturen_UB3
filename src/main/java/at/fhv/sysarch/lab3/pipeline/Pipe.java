package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.List;

public class Pipe {

    private Filter filter;

    public Pipe(Filter filter) {
        this.filter = filter;
    }

    public PipelineData write(PipelineData pd) {
        System.out.println("Pipe got PipeLineData");
        return this.filter.write(pd);
    }
}
