package at.fhv.sysarch.lab3.pipeline.factories;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filters.*;
import at.fhv.sysarch.lab3.pipeline.obj.Pair;
import at.fhv.sysarch.lab3.pipeline.obj.Pipe;
import at.fhv.sysarch.lab3.pipeline.obj.PipelineData;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.awt.*;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // pull from the source (model)
        DataSource dataSource = new DataSource();
        dataSource.setModel(pd.getModel());

        // 1. perform model-view transformation from model to VIEW SPACE coordinates (see C.2 of Lecture Notes)
        ModelViewTransformationFilter modelViewFilter = new ModelViewTransformationFilter(pd);
        Pipe<Face> toModelViewFilter = new Pipe<>();
        modelViewFilter.setPipePredecessor(toModelViewFilter);
        toModelViewFilter.setPredecessor(dataSource);

        // 2. perform backface culling in VIEW SPACE (see C.4 of Lecture Notes)
        BackfaceCullingFilter backfaceCullingFilter = new BackfaceCullingFilter();
        Pipe<Face> toBackfacePipeFilter = new Pipe<>();
        backfaceCullingFilter.setPipePredecessor(toBackfacePipeFilter);
        toBackfacePipeFilter.setPredecessor(modelViewFilter);

        // 3. perform depth sorting in VIEW SPACE (see C.3 of Lecture Notes)
        DepthSortingFilter depthSortingFilter = new DepthSortingFilter();
        Pipe<Face> toDepthSortingFilter = new Pipe<>();
        depthSortingFilter.setPipePredecessor(toDepthSortingFilter);
        toDepthSortingFilter.setPredecessor(backfaceCullingFilter);

        // 4. add coloring (space unimportant - see C.5 of Lecture Notes)
        ColorFilter colorFilter = new ColorFilter(pd);
        Pipe<Face> toColorFilter = new Pipe<>();
        colorFilter.setPipePredecessor(toColorFilter);
        toColorFilter.setPredecessor(depthSortingFilter);

        // lighting can be switched on/off
        PerspectiveProjectionFilter perspectiveProjectionFilter = new PerspectiveProjectionFilter(pd);

        if (pd.isPerformLighting()) {
            // 4a. perform lighting in VIEW SPACE
            LightingFilter lightingFilter = new LightingFilter(pd);
            Pipe<Pair<Face, Color>> toLightingFilter = new Pipe<>();
            lightingFilter.setPipePredecessor(toLightingFilter);
            toLightingFilter.setPredecessor(colorFilter);

            // 5. perform projection transformation on VIEW SPACE coordinates
            Pipe<Pair<Face, Color>> toPerspectivePipe = new Pipe<>();
            perspectiveProjectionFilter.setPipePredecessor(toPerspectivePipe);
            toPerspectivePipe.setPredecessor(lightingFilter);
        } else {
            // 5. perform projection transformation
            Pipe<Pair<Face, Color>> toPerspectivePipe = new Pipe<>();
            perspectiveProjectionFilter.setPipePredecessor(toPerspectivePipe);
            toPerspectivePipe.setPredecessor(colorFilter);
        }

        // 6. perform perspective division to screen coordinates
        ScreenSpaceTransformationFilter screenSpaceTransformationFilter = new ScreenSpaceTransformationFilter(pd);
        Pipe<Pair<Face, Color>> toScreenSpaceTransformationFilter = new Pipe<>();
        screenSpaceTransformationFilter.setPipePredecessor(toScreenSpaceTransformationFilter);
        toScreenSpaceTransformationFilter.setPredecessor(perspectiveProjectionFilter);

        // 7. feed into the sink (renderer)
        DataSink dataSink = new DataSink(pd);
        Pipe<Pair<Face, Color>> toSink = new Pipe<>();
        dataSink.setPipePredecessor(toSink);
        toSink.setPredecessor(screenSpaceTransformationFilter);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the fraction
        return new AnimationRenderer(pd) {
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
                Mat4 rotationMatrix = Matrices.rotate(
                        (float) radiant,
                        pd.getModelRotAxis() // Rotation axis is a Vec3 with y=1 and x/z=0
                );

                // update model-view filter
                modelViewFilter.setRotationMatrix(rotationMatrix);

                // trigger rendering of the pipeline
                dataSource.setModel(model);
                dataSink.read();
            }
        };
    }
}