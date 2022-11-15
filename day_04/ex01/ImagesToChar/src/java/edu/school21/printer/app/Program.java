package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Error! Read README.txt!");
            System.exit(-1);
        }
        char white = args[0].charAt(0);
        char anotherColor = args[1].charAt(0);

        BufferedImage img = null;
        URL path;
        path = Program.class.getResource("/resources/it.bmp");
        try {
            assert path != null;
            img = ImageIO.read(path);
        } catch (IOException e) {
            System.err.println("Error! Read README.txt!");
            System.exit(-1);
        }
        Logic.convert(img, white, anotherColor);
    }
}
