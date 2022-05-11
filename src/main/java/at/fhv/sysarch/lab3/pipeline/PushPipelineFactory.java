package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.util.List;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // TODO: push from the source (model)

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates

        // TODO 2. perform backface culling in VIEW SPACE

        // TODO 3. perform depth sorting in VIEW SPACE

        // TODO 4. add coloring (space unimportant)

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            // 5. TODO perform projection transformation
        }

        // TODO 6. perform perspective division to screen coordinates

        // TODO 7. feed into the sink (renderer)

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {

            // TODO rotation variable goes in here

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {

                // render static line
//                pd.getGraphicsContext().setStroke(Color.RED);
//                pd.getGraphicsContext().strokeLine(150,150,250,250);

                // render single face of model
//                pd.getGraphicsContext().setStroke(Color.YELLOW);
//                Face face = model.getFaces().get(0);
//                pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());

                // render all faces of model
//                pd.getGraphicsContext().setStroke(Color.YELLOW);
                pd.getGraphicsContext().setStroke(pd.getModelColor());
                List<Face> faces = model.getFaces();

                final int GROW = 100;

                for (Face face : faces) {
                    pd.getGraphicsContext().strokeLine(face.getV1().getX()*GROW, face.getV1().getY()*GROW, face.getV2().getX()*GROW, face.getV2().getY()*GROW);
                    pd.getGraphicsContext().strokeLine(face.getN1().getX()*GROW, face.getN1().getY()*GROW, face.getN2().getX()*GROW, face.getN2().getY()*GROW);
                }

                // TODO compute rotation in radians

                // TODO create new model rotation matrix using pd.modelRotAxis

                // TODO compute updated model-view tranformation

                // TODO update model-view filter

                // TODO trigger rendering of the pipeline
            }
        };
    }
}