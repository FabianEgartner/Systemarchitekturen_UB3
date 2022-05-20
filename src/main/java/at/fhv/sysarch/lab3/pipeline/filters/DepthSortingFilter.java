package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.PullFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.utils.PipeLineUtils;

import java.util.PriorityQueue;
import java.util.Queue;

public class DepthSortingFilter implements PullFilter<Face, Face> {

    private final Queue<Face> processQueue;
    private boolean bufferingMode;
    private Pipe<Face> predecessor;

    public DepthSortingFilter() {
        bufferingMode = true;
        processQueue = new PriorityQueue<>((face1, face2) -> {
            // First face
            float firstZ1 = face1.getV1().getZ();
            float firstZ2 = face1.getV2().getZ();
            float firstZ3 = face1.getV3().getZ();

            float firstFaceAverageZ = (firstZ1 + firstZ2 + firstZ3) / 3;

            // Second face
            float secondZ1 = face2.getV1().getZ();
            float secondZ2 = face2.getV2().getZ();
            float secondZ3 = face2.getV3().getZ();

            float secondFaceAverageZ = (secondZ1 + secondZ2 + secondZ3) / 3;

            // Computed value is too small for rounding -> value * 100
            // value + 0.5 to round without using, for example, Math.round()
            return (int) ((firstFaceAverageZ - secondFaceAverageZ) * 100 + 0.5);
        });
    }

    @Override
    public Face read() {
        while (bufferingMode) {
            Face input = predecessor.read();

            if (null == input) {
                continue;
            }

            if (PipeLineUtils.isFaceMakingEnd(input)) {
                bufferingMode = false;
            }

            processQueue.add(input);
        }

        if (processQueue.size() <= 1) {
            bufferingMode = true;
        }

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
