package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Logic {
    BColor bColor;
    ColoredPrinter coloredPrinter = new ColoredPrinter.Builder(1,false).build();

    public void convert(String white, String black, URL img){
        try {
            BufferedImage buf = ImageIO.read(img);
            for (int i = 0; i < buf.getHeight(); i++) {
                for (int j = 0; j < buf.getWidth(); j++) {
                    Color color = new Color(buf.getRGB(j, i));
                    int green = color.getGreen();
                    if (green == 0) {
                        bColor = Ansi.BColor.valueOf(white);
                    } else {
                        bColor = Ansi.BColor.valueOf(black);
                    }
                    coloredPrinter.print(" ", Attribute.NONE, FColor.NONE, bColor);
                }
                coloredPrinter.println("", Attribute.NONE, FColor.NONE, BColor.NONE);
            }
    } catch (IllegalArgumentException e) {
            coloredPrinter.println("", Attribute.NONE, FColor.NONE, BColor.NONE);
            System.err.println("Error! Bad Color!");
        } catch (IOException e) {
            System.out.println("Error! Bad image Source!");
            System.exit(-1);
        }
    }
}
