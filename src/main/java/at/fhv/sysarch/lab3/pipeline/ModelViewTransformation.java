package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

public class ModelViewTransformation<I extends Face> implements Filter<I> {

    private Pipe successor;

    @Override
    public void setPipeSuccessor(Pipe successor) {
        this.successor = successor;
    }

    @Override
    public void write(I input) {
        System.out.println("ModelViewTransformation received Input");
        this.successor.write(input);
    }
}