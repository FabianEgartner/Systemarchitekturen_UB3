package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Model;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import javafx.animation.AnimationTimer;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // TODO: the connection of filters and pipes requires a lot of boilerplate code. Think about options how this can be minimized

        // TODO: push from the source (model)
        Filter dataSource = new DataSource<>();
        ModelViewTransformation modelViewFilter = new ModelViewTransformation(pd);
        BackfaceCulling backfaceCullingFilter = new BackfaceCulling();
        ColorFilter colorFilter = new ColorFilter<>(pd);
        LightingFilter lightingFilter = new LightingFilter<>(pd);
        PerspectiveProjection perspectiveProjection = new PerspectiveProjection<>(pd);
        Filter dataSink = new DataSink<>(pd);

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        Pipe toModelViewFilter = new Pipe();
        dataSource.setPipeSuccessor(toModelViewFilter);
        toModelViewFilter.setSuccessor(modelViewFilter);

        // TODO 2. perform backface culling in VIEW SPACE
        Pipe toBackfaceCullingFilter = new Pipe();
        modelViewFilter.setPipeSuccessor(toBackfaceCullingFilter);
        toBackfaceCullingFilter.setSuccessor(backfaceCullingFilter);

        // TODO 3. perform depth sorting in VIEW SPACE
        // Not possible (without a hack) in the push pipeline

        // TODO 4. add coloring (space unimportant)
        Pipe toColorFilter = new Pipe();
        backfaceCullingFilter.setPipeSuccessor(toColorFilter);
        toColorFilter.setSuccessor(colorFilter);

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. perform lighting in VIEW SPACE
            Pipe toLightingFilter = new Pipe();
            colorFilter.setPipeSuccessor(toLightingFilter);
            toLightingFilter.setSuccessor(lightingFilter);

            // 5. perform projection transformation on VIEW SPACE coordinates
            Pipe lightingtoPerspectivePipe = new Pipe();
            lightingFilter.setPipeSuccessor(lightingtoPerspectivePipe);
            lightingtoPerspectivePipe.setSuccessor(perspectiveProjection);
        } else {
            // 5. perform projection transformation
            Pipe colorToPerspectivePipe = new Pipe();
            colorFilter.setPipeSuccessor(colorToPerspectivePipe);
            colorToPerspectivePipe.setSuccessor(perspectiveProjection);
        }

        // TODO 6. perform perspective division to screen coordinates

        // TODO 7. feed into the sink (renderer)
        Pipe toSink = new Pipe();
        perspectiveProjection.setPipeSuccessor(toSink);
        toSink.setSuccessor(dataSink);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {

            // rotation variable goes in here
            float rotation = 0f;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {

                // compute rotation in radians
                rotation += fraction;
                double radiant = rotation % (2 * Math.PI); // 2 PI = 360°

                // create new model rotation matrix using pd.modelRotAxis
                Vec3 modelRotAxis = pd.getModelRotAxis();

                // compute updated model-view tranformation
                Mat4 rotationMatrix = Matrices.rotate((float) radiant, modelRotAxis);

                // update model-view filter
                modelViewFilter.setRotationMatrix(rotationMatrix);

                // trigger rendering of the pipeline
                dataSource.write(model);

                // render static line
//                pd.getGraphicsContext().setStroke(Color.RED);
//                pd.getGraphicsContext().strokeLine(150,150,250,250);

                // render single face of model
//                pd.getGraphicsContext().setStroke(Color.YELLOW);
//                Face face = model.getFaces().get(0);
//                pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());
/*
                List<Face> faces = model.getFaces();
                final int GROW = 100;

                // render wireframes
                if (pd.getRenderingMode().equals(RenderingMode.WIREFRAME)) {
                    pd.getGraphicsContext().setStroke(pd.getModelColor());

                    for (Face face : faces) {
                        String fx1 = Float.toString(face.getV1().getX() * GROW);
                        double dx1 = Double.parseDouble(fx1);

                        String fx2 = Float.toString(face.getV2().getX() * GROW);
                        double dx2 = Double.parseDouble(fx2);

                        String fx3 = Float.toString(face.getV3().getX() * GROW);
                        double dx3 = Double.parseDouble(fx3);

                        double[] x = {dx1, dx2, dx3};


                        String fy1 = Float.toString(face.getV1().getY() * GROW);
                        double dy1 = Double.parseDouble(fy1);

                        String fy2 = Float.toString(face.getV2().getY() * GROW);
                        double dy2 = Double.parseDouble(fy2);

                        String fy3 = Float.toString(face.getV3().getY() * GROW);
                        double dy3 = Double.parseDouble(fy3);

                        double[] y = {dy1, dy2, dy3};

                        pd.getGraphicsContext().strokePolygon(x, y, 3);
                    }
                }

                // render filled
                else if (pd.getRenderingMode().equals(RenderingMode.FILLED)) {
                    pd.getGraphicsContext().setFill(pd.getModelColor());

                    for (Face face : faces) {
                        String fx1 = Float.toString(face.getV1().getX() * GROW);
                        double dx1 = Double.parseDouble(fx1);

                        String fx2 = Float.toString(face.getV2().getX() * GROW);
                        double dx2 = Double.parseDouble(fx2);

                        String fx3 = Float.toString(face.getV3().getX() * GROW);
                        double dx3 = Double.parseDouble(fx3);

                        double[] x = {dx1, dx2, dx3};


                        String fy1 = Float.toString(face.getV1().getY() * GROW);
                        double dy1 = Double.parseDouble(fy1);

                        String fy2 = Float.toString(face.getV2().getY() * GROW);
                        double dy2 = Double.parseDouble(fy2);

                        String fy3 = Float.toString(face.getV3().getY() * GROW);
                        double dy3 = Double.parseDouble(fy3);

                        double[] y = {dy1, dy2, dy3};

                        pd.getGraphicsContext().fillPolygon(x, y, 3);
                    }
                }

                // render points
                else {
                    pd.getGraphicsContext().setStroke(pd.getModelColor());

                    for (Face face : faces) {
                        pd.getGraphicsContext().strokeLine(face.getV1().getX() * GROW, face.getV1().getY() * GROW, face.getV2().getX() * GROW, face.getV2().getY() * GROW);
                    }
                }
*/
            }
        };
    }
}