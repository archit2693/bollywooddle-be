package com.example.springbootbackend.util;

import org.springframework.context.annotation.Bean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class PixelateImage {
    int PIX_SIZE = 10;

    public byte[] pixelateImage(File image, int resolution) throws IOException {
        PIX_SIZE = resolution;
        BufferedImage img = resizeImage(ImageIO.read(image), 600, 800);
        Raster src = img.getData();
        WritableRaster dest = src.createCompatibleWritableRaster();
        for (int y = 0; y < src.getHeight(); y += PIX_SIZE) {
            for (int x = 0; x < src.getWidth(); x += PIX_SIZE) {
                double[] pixel = new double[3];
                pixel = src.getPixel(x, y, pixel);
                for (int yd = y; (yd < y + PIX_SIZE) && (yd < dest.getHeight()); yd++) {
                    for (int xd = x; (xd < x + PIX_SIZE) && (xd < dest.getWidth()); xd++) {
                        dest.setPixel(xd, yd, pixel);
                    }
                }
            }
        }
        img.setData(dest);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", output);
        byte[] bytes = output.toByteArray();
        return bytes;
    }

    public void renameFilesInDirectory(){
        File folder = new File("src/main/resources/images");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                String newFileName = fileName.replace(" ", "_").toLowerCase();
                file.renameTo(new File("src/main/resources/images/" + newFileName));
            }
        }
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
