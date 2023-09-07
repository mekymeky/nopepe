package com.meki.nopepert;

import com.meki.nopepert.inference.ImageLoader;
import com.meki.nopepert.inference.NoPepeModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        NoPepeModel nopepe = new NoPepeModel();

        String pngImagePath = "";

        if (pngImagePath == null) {
            throw new RuntimeException("You need to put your image in there you silly goose!");
        }

        try {
            // load model
            URL res = Main.class.getClassLoader().getResource("nopepe.onnx");
            assert res != null;
            File file = Paths.get(res.toURI()).toFile();
            String absolutePath = file.getAbsolutePath();
            nopepe.load_model(absolutePath);

            // load image and do stuff
            BufferedImage inputImage = ImageIO.read(new File(pngImagePath));
            float[] inputData = ImageLoader.imageToRGBFloatBuffer(inputImage);
            System.out.println(nopepe.isPepe(inputData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
