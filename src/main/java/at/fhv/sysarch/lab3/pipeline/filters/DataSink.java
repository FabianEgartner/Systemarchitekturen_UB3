package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.PushFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pair;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.obj.PipelineData;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DataSink implements PushFilter<Pair<Face, Color>, Void> {

    private final PipelineData pd;
    private final int factor = 1;

    public DataSink(PipelineData pd) {this.pd = pd; }

    @Override
    public void setPipeSuccessor(Pipe<Void> pipe) {}

    @Override
    public Void process(Pair<Face, Color> input) {
        return null;
    }

    @Override
    public void write(Pair<Face, Color> pair) {

        if (pd.getRenderingMode().equals(RenderingMode.WIREFRAME)) {
            renderWireframe(pd.getGraphicsContext(), pair);
        }
        else if (pd.getRenderingMode().equals(RenderingMode.FILLED)) {
            renderFilled(pd.getGraphicsContext(), pair);
        }
        else {
            renderPoint(pd.getGraphicsContext(), pair);
        }
    }

    private void renderWireframe(GraphicsContext gc, Pair<Face, Color> pair) {
        Face face = pair.fst();
        Color color = pair.snd();

        gc.setStroke(color);

        double dx1 = face.getV1().getX() * factor;
        double dx2 = face.getV2().getX() * factor;
        double dx3 = face.getV3().getX() * factor;
        double[] x = {dx1, dx2, dx3};

        double dy1 = face.getV1().getY() * factor;
        double dy2 = face.getV2().getY() * factor;
        double dy3 = face.getV3().getY() * factor;
        double[] y = {dy1, dy2, dy3};

        gc.strokePolygon(x, y, 3);
    }

    private void renderFilled(GraphicsContext gc, Pair<Face, Color> pair) {
        Face face = pair.fst();
        Color color = pair.snd();

        gc.setFill(color);

        double dx1 = face.getV1().getX() * factor;
        double dx2 = face.getV2().getX() * factor;
        double dx3 = face.getV3().getX() * factor;
        double[] x = {dx1, dx2, dx3};

        double dy1 = face.getV1().getY() * factor;
        double dy2 = face.getV2().getY() * factor;
        double dy3 = face.getV3().getY() * factor;
        double[] y = {dy1, dy2, dy3};

        gc.fillPolygon(x, y, 3);
    }

    private void renderPoint(GraphicsContext gc, Pair<Face, Color> pair) {
        Face face = pair.fst();
        Color color = pair.snd();

        gc.setFill(color);

        gc.fillOval(face.getV1().getX()*factor, face.getV1().getY()*factor, 3, 3);
    }
}
