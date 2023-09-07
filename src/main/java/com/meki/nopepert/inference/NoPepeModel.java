package com.meki.nopepert.inference;

import ai.onnxruntime.*;
import com.meki.nopepert.inference.exceptions.InferenceException;
import com.meki.nopepert.inference.exceptions.ModelLoadFailureException;

import java.util.HashMap;
import java.util.Map;

public class NoPepeModel {
    private static final long[] INPUT_SHAPE = {1, 128, 128, 3};
    private static final String INPUT_NODE_NAME = "image_tensor";
    private static final float DEFAULT_THRESHOLD = 0.4f;

    private OrtEnvironment env;
    private OrtSession session;
    private final float threshold;

    public float getThreshold() {
        return threshold;
    }

    public NoPepeModel(float threshold) {
        this.threshold = threshold;
    }

    public NoPepeModel() {
        this.threshold = DEFAULT_THRESHOLD;
    }


    public void load_model(String modelPath) throws ModelLoadFailureException {
        try {
            env = OrtEnvironment.getEnvironment();
            session = env.createSession(modelPath ,new OrtSession.SessionOptions());
        } catch (OrtException e) {
            throw new ModelLoadFailureException(e);
        }
    }

    public boolean isPepe(float[] inputImageData) throws InferenceException {
        return pepeScore(inputImageData) >= threshold;
    }

    public float pepeScore(float[] inputImageData) throws InferenceException {

        try {
            // create inputs container
            Map<String, OnnxTensor> inputs = new HashMap<>();

            // reshape float array to desired tensor shape
            Object tensorData = OrtUtil.reshape(inputImageData, INPUT_SHAPE);

            // create input tensor
            OnnxTensor inputTensor = OnnxTensor.createTensor(env, tensorData);

            // put the input tensor into inputs container, assigned to the only input we have
            inputs.put(INPUT_NODE_NAME, inputTensor);

            // finally run the process
            OrtSession.Result results = session.run(inputs);

            // extract result from the output tensor (batch 0, output 0)
            return ((OnnxTensor) results.get(0)).getFloatBuffer().get(0);

        } catch (OrtException e) {
            throw new InferenceException(e);
        }
    }
}
