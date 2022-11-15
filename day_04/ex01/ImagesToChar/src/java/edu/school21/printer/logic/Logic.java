package edu.school21.printer.logic;

import java.awt.image.BufferedImage;

public class Logic {
    public static void convert(BufferedImage img, char white, char anotherColor) {
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                if (img.getRGB(j, i) == -1) {
                    System.out.print(white);
                }
                else {
                    System.out.print(anotherColor);
                }
            }
            System.out.println();
        }
    }
}
