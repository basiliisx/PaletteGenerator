/**
 * File: RandomImage.java
 *
 * Description:
 * Create a random color image.
 *
 * @author Yusuf Shakeel
 * Date: 01-04-2014 tue
 */
package randomimage;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;

public class RandomImage {

    //dimension y parametros de la imagen
    int width = 640;
    int height = 320;
    int a, r, g, b;
    double h, s, l;
    //tratado de la imagen mediante un buffer
    BufferedImage img;

    /**
     * llena en una imagen una barra horizontal de pixeles desde start hasta end
     *
     * @param start altura inicial
     * @param end altura final
     */
    public void fillBand(int start, int end) {
        for (int y = start; y < end; y++) {
            for (int x = 0; x < width; x++) {

                int p = (a << 24) | (r << 16) | (g << 8) | b; //pixel

                img.setRGB(x, y, p);
            }
        }
    }

    /**
     * genera un color aleatorio en RGB
     */
    public void randomColor() {
        r = (int) (Math.random() * 256); //red
        g = (int) (Math.random() * 256); //green
        b = (int) (Math.random() * 256); //blue
    }

    public void RGBtoHSL() {
        double min, max;
        double auxr, auxg, auxb;
        auxr = r;
        auxr /= 255;
        auxg = g;
        auxg /= 255;
        auxb = b;
        auxb /= 255;
        max = Math.max(auxr, Math.max(auxg, auxb));
        min = Math.min(auxr, Math.min(auxg, auxb));

        l = (max + min) / 2;
        l = (double) Math.round(l * 100d) / 100d;
        if (max == min) {
            s = 0;
            h = 0;
            return;
        }

        if (l <= 0.5) {
            s = (max - min) / (2 * l);
        } else {
            s = (max - min) / (2 - 2 * l);
        }
        s = (double) Math.round(s * 100d) / 100d;

        if (auxr == max) {
            h = (60 * (auxg - auxb) / (max - min)) % 360;
        } else if (auxg == max) {
            h = (60 * (auxb - auxr) / (max - min)) + 120;
        } else {
            h = (60 * (auxr - auxg) / (max - min)) + 240;
        }
        h = (double) Math.round(h * 1d) / 1d;
        if (h < 0) {
            h += 360;
        }
    }

    public double f(int n) {
        double k = (n + h / 30) % 12;
        double a = s * Math.min(l, 1 - l);
        return l - a * Math.max(Math.min(k - 3, Math.min(9 - k, 1)), -1);
    }

    public void HSLtoRGB() {
        r = (int) (f(0) * 255);
        g = (int) (f(8) * 255);
        b = (int) (f(4) * 255);
    }

    public void generateAnal() {
        Random rng = new Random();
        randomColor();
        for (int i = 0; i < 5; i++) {
            fillBand(height * i / 5, height * (i + 1) / 5);
            int aux = rng.nextInt(15);
            RGBtoHSL();
            h = (h + aux) % 360;
            HSLtoRGB();
        }
    }

    public void generatePastelAnal() {
        Random rng = new Random();
        randomColor();
        for (int i = 0; i < 5; i++) {
            RGBtoHSL();
            s = 0.9 + rng.nextDouble() * 0.1;
            l = 0.75 + rng.nextDouble() * 0.25;
            HSLtoRGB();
            fillBand(height * i / 5, height * (i + 1) / 5);
            int aux = rng.nextInt(15);
            h = (h + aux) % 360;
        }
    }

    public void generatePastel() {
        Random rng = new Random();
        for (int i = 0; i < 5; i++) {
            randomColor();
            RGBtoHSL();
            s = 0.9 + rng.nextDouble() * 0.1;
            l = 0.75 + rng.nextDouble() * 0.25;
            HSLtoRGB();
            fillBand(height * i / 5, height * (i + 1) / 5);
        }
    }

    public void generateRandom() {
        for (int i = 0; i < 5; i++) {
            randomColor();
            fillBand(height * i / 5, height * (i + 1) / 5);
        }
    }

    public void program() {

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //file object
        File f;
        //create random image pixel by pixel
        a = 255; //alpha
        Random rng = new Random();
        generatePastelAnal();
        //write image
        try {
            int i = 1;
            f = new File("C:\\Image\\Output"+i+".png");
            while(f.exists()){
                i++;
                f = new File("C:\\Image\\Output"+i+".png");
            }
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String args[]) throws IOException {
        RandomImage a = new RandomImage();
        a.program();
    }//main() ends here
}//class ends here
