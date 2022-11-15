package edu.school21.spring;

public class RendererStandartImpl implements Renderer{
    public PreProcessor preProcessor;

    public RendererStandartImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void printText(String text) {
        System.out.println(preProcessor.preProcess(text));
    }
}
