package com.meki.nopepert;

import com.meki.nopepert.inference.ImageLoader;
import com.meki.nopepert.inference.NoPepeModel;
import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class MainTest extends TestCase {

    private NoPepeModel nopepe;

    @Override
    protected void setUp() throws Exception {
        // load model
        nopepe = new NoPepeModel();

        URL res = Main.class.getClassLoader().getResource("nopepe.onnx");
        assert res != null;
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        nopepe.load_model(absolutePath);
    }

    private boolean testImage(String resourceFilename) throws Exception {
        URL res = MainTest.class.getClassLoader().getResource(resourceFilename);
        assert res != null;
        File file = Paths.get(res.toURI()).toFile();

        BufferedImage inputImage = ImageIO.read(file);
        float[] inputData = ImageLoader.imageToRGBFloatBuffer(inputImage);
        float score = nopepe.pepeScore(inputData);

        System.out.println(resourceFilename + " score=" + score);

        return score >= nopepe.getThreshold();
    }

    public void testPepeNegativeSamples() throws Exception {
        assertFalse(testImage("notpepe/np1.png"));
        assertFalse(testImage("notpepe/np2.png"));
        assertFalse(testImage("notpepe/np3.png"));
        assertFalse(testImage("notpepe/np4.png"));
    }

    public void testPepePositiveSamples() throws Exception {
        assertTrue(testImage("yespepe/yp1.png"));
        assertTrue(testImage("yespepe/yp2.png"));
        assertTrue(testImage("yespepe/yp3.png"));
        assertTrue(testImage("yespepe/yp4.png"));
    }

}
