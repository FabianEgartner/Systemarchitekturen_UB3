package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.api.PullFilter;
import at.fhv.sysarch.lab3.pipeline.api.PushFilter;
import at.fhv.sysarch.lab3.pipeline.obj.Pair;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.obj.PipelineData;
import at.fhv.sysarch.lab3.pipeline.utils.PipeLineUtils;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DataSink implements PullFilter<Pair<Face, Color>, Pair<Face, Color>>, PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private Pipe<Pair<Face, Color>> predecessor;
    private final PipelineData pd;

    public DataSink(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {
        while (true) {
            Pair<Face, Color> input = predecessor.read();

            if (null == input)
                continue;
            else if (PipeLineUtils.isFaceEnd(input.fst()))
                break;

            process(input);
        }

        return null;
    }

    @Override
    public void write(Pair<Face, Color> pair) {

        if (pd.getRenderingMode().equals(RenderingMode.WIREFRAME))
            renderWireframe(pd.getGraphicsContext(), pair);
        else if (pd.getRenderingMode().equals(RenderingMode.FILLED))
            renderFilled(pd.getGraphicsContext(), pair);
        else
            renderPoint(pd.getGraphicsContext(), pair);
    }

    @Override
    public void setPipePredecessor(Pipe<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face, Color>> successor) {
        // no successor
    }

    @Override
    public Pair<Face, Color> process(Pair<Face, Color> input) {
        Face face = input.fst();
        Color color = input.snd();

        double[] x = new double[]{
                face.getV1().getX(),
                face.getV2().getX(),
                face.getV3().getX()
        };
        double[] y = new double[]{
                face.getV1().getY(),
                face.getV2().getY(),
                face.getV3().getY()
        };

        pd.getGraphicsContext().setStroke(color);
        pd.getGraphicsContext().setFill(color);
        switch (pd.getRenderingMode()) {
            case POINT:
                pd.getGraphicsContext().fillOval(x[0], y[0], 3, 3);
                break;
            case WIREFRAME:
                pd.getGraphicsContext().strokePolygon(x, y, x.length);
                break;
            case FILLED:
                pd.getGraphicsContext().fillPolygon(x, y, x.length);
                pd.getGraphicsContext().strokePolygon(x, y, x.length);
                break;
        }

        return null;
    }

    private void renderWireframe(GraphicsContext gc, Pair<Face, Color> pair) {
        Face face = pair.fst();
        Color color = pair.snd();

        gc.setStroke(color);

        double dx1 = face.getV1().getX();
        double dx2 = face.getV2().getX();
        double dx3 = face.getV3().getX();
        double[] x = {dx1, dx2, dx3};

        double dy1 = face.getV1().getY();
        double dy2 = face.getV2().getY();
        double dy3 = face.getV3().getY();
        double[] y = {dy1, dy2, dy3};

        gc.strokePolygon(x, y, 3);
    }

    private void renderFilled(GraphicsContext gc, Pair<Face, Color> pair) {
        Face face = pair.fst();
        Color color = pair.snd();

        gc.setFill(color);

        double dx1 = face.getV1().getX();
        double dx2 = face.getV2().getX();
        double dx3 = face.getV3().getX();
        double[] x = {dx1, dx2, dx3};

        double dy1 = face.getV1().getY();
        double dy2 = face.getV2().getY();
        double dy3 = face.getV3().getY();
        double[] y = {dy1, dy2, dy3};

        gc.fillPolygon(x, y, 3);
    }

    private void renderPoint(GraphicsContext gc, Pair<Face, Color> pair) {
        Face face = pair.fst();
        Color color = pair.snd();

        gc.setFill(color);

        gc.fillOval(face.getV1().getX(), face.getV1().getY(), 3, 3);
    }
}
