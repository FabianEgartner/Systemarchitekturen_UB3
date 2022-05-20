package at.fhv.sysarch.lab3.pipeline.utils;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec4;

public class PipeLineUtils {

    public static boolean isFaceMakingEnd(Face face) {
        if (null == face) {
            return false;
        }

        return face.getV1().equals(Vec4.VEC4_ZERO) &&
                face.getV2().equals(Vec4.VEC4_ZERO) &&
                face.getV3().equals(Vec4.VEC4_ZERO) &&
                face.getN1().equals(Vec4.VEC4_ZERO) &&
                face.getN2().equals(Vec4.VEC4_ZERO) &&
                face.getN3().equals(Vec4.VEC4_ZERO);
    }

}