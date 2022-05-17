package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DataSink<I extends Face> implements Filter<I>{

    private final PipelineData pd;
    private final int factor = 100;

    public DataSink(PipelineData pd) {this.pd = pd; }

    @Override
    public void setPipeSuccessor(Pipe pipe) {
        // TODO: think about how you organize your interfaces
    }

    @Override
    public void write(I face) {

        if (pd.getRenderingMode().equals(RenderingMode.WIREFRAME)) {
            renderWireframe(pd.getGraphicsContext(), pd.getModelColor(), face);
        }
        else if (pd.getRenderingMode().equals(RenderingMode.FILLED)) {
            renderFilled(pd.getGraphicsContext(), pd.getModelColor(), face);
        }
        else {
            renderPoint(pd.getGraphicsContext(), pd.getModelColor(), face);
        }
    }

    private void renderWireframe(GraphicsContext gc, Color color, I face) {
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

    private void renderFilled(GraphicsContext gc, Color color, I face) {
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

    private void renderPoint(GraphicsContext gc, Color color, I face) {
        gc.setFill(color);
        gc.fillOval(face.getV1().getX()*factor, face.getV1().getY()*factor, 3, 3);
    }
}
