package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.PullFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.utils.PipeLineUtils;

import java.util.PriorityQueue;
import java.util.Queue;

public class DepthSortingFilter implements PullFilter<Face, Face> {

    private Pipe<Face> predecessor;
    private final Queue<Face> processQueue;
    private boolean buffMode;

    public DepthSortingFilter() {
        buffMode = true;
        processQueue = new PriorityQueue<>((face1, face2) -> {

            float fstZ1 = face1.getV1().getZ();
            float fstZ2 = face1.getV2().getZ();
            float fstZ3 = face1.getV3().getZ();

            float fstFaceAvgZ = (fstZ1 + fstZ2 + fstZ3) / 3;

            float sndZ1 = face2.getV1().getZ();
            float sndZ2 = face2.getV2().getZ();
            float sndZ3 = face2.getV3().getZ();

            float sndFaceAvgZ = (sndZ1 + sndZ2 + sndZ3) / 3;

            return Math.round((int) ((fstFaceAvgZ - sndFaceAvgZ) * 100));
        });
    }

    @Override
    public Face read() {

        while (buffMode) {
            Face input = predecessor.read();

            if (null == input)
                continue;

            if (PipeLineUtils.isFaceEnd(input))
                buffMode = false;

            processQueue.add(input);
        }

        if (processQueue.size() <= 1)
            buffMode = true;

        return this.processQueue.poll();
    }

    @Override
    public void setPipePredecessor(Pipe<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Face process(Face input) {
        return input;
    }
}
