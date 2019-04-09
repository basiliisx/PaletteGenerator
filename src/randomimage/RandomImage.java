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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RandomImage {

    //dimension y parametros de la 
    String path;
    JFrame frame;
    JPanel pane;
    JButton bAnal, bAnalPastel, bPastel, bComp, bRandom;
    int width = 640;
    int height = 320;
    int a, r, g, b;
    double h, s, l;
    //tratado de la imagen mediante un buffer
    BufferedImage img;

    public void init() {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        frame = new JFrame("Palette Generator");
        frame.setResizable(false);
        frame.setSize(560, 75);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane = new JPanel();
        frame.add(pane);

        bAnal = new JButton("Analogous");
        pane.add(bAnal);
        bAnal.setForeground(Color.BLACK);
        bAnal.setBackground(Color.WHITE);
        bAnal.setBorderPainted(true);
        bAnal.setFocusPainted(false);
        bAnal.setContentAreaFilled(false);
        bAnal.setRolloverEnabled(false);

        bAnal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                generateAnal();
                savePalette();
                printImg();
            }
        });

        bAnalPastel = new JButton("Analogous Pastel");
        pane.add(bAnalPastel);
        bAnalPastel.setForeground(Color.BLACK);
        bAnalPastel.setBackground(Color.WHITE);
        bAnalPastel.setBorderPainted(true);
        bAnalPastel.setFocusPainted(false);
        bAnalPastel.setContentAreaFilled(false);
        bAnalPastel.setRolloverEnabled(false);

        bAnalPastel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                generatePastelAnal();
                savePalette();
                printImg();
            }
        });
        bPastel = new JButton("Pastel");
        pane.add(bPastel);
        bPastel.setForeground(Color.BLACK);
        bPastel.setBackground(Color.WHITE);
        bPastel.setBorderPainted(true);
        bPastel.setFocusPainted(false);
        bPastel.setContentAreaFilled(false);
        bPastel.setRolloverEnabled(false);

        bPastel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                generatePastel();
                savePalette();
                printImg();
            }
        });

        bComp = new JButton("Complementary");
        pane.add(bComp);
        bComp.setForeground(Color.BLACK);
        bComp.setBackground(Color.WHITE);
        bComp.setBorderPainted(true);
        bComp.setFocusPainted(false);
        bComp.setContentAreaFilled(false);
        bComp.setRolloverEnabled(false);

        bComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                generateComp();
                savePalette();
                printImg();
            }
        });
        
        bRandom = new JButton("Random");
        pane.add(bRandom);
        bRandom.setForeground(Color.BLACK);
        bRandom.setBackground(Color.WHITE);
        bRandom.setBorderPainted(true);
        bRandom.setFocusPainted(false);
        bRandom.setContentAreaFilled(false);
        bRandom.setRolloverEnabled(false);

        bRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                generateRandom();
                savePalette();
                printImg();
            }
        });
    }

    public void printImg() {
        try {
            String fileName = path;
            String[] commands = {"cmd.exe", "/c", "start", "\"DummyTitle\"",
                "\"" + fileName + "\""};
            Process p = Runtime.getRuntime().exec(commands);
            p.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(RandomImage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RandomImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    public void generateComp() {
        a = 255;
        Random rng = new Random();
        randomColor();
        for (int i = 0; i < 5; i++) {
            fillBand(height * i / 5, height * (i + 1) / 5);
            RGBtoHSL();
            if (i == 1 || i == 2) {
                h = (h + 90) % 360;
            }
            s = 0.4 * rng.nextDouble() + 0.4;
            l = 0.4 * rng.nextDouble() + 0.4;
            HSLtoRGB();
        }
    }

    public void generateAnal() {
        a = 255;
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
        a = 255;
        Random rng = new Random();
        randomColor();
        for (int i = 0; i < 5; i++) {
            RGBtoHSL();
            s = 0.9 + rng.nextDouble() * 0.1;
            l = 0.75 + rng.nextDouble() * 0.15;
            HSLtoRGB();
            fillBand(height * i / 5, height * (i + 1) / 5);
            int aux = rng.nextInt(30);
            h = (h + aux) % 360;
        }
    }

    public void generatePastel() {
        a = 255;
        Random rng = new Random();
        for (int i = 0; i < 5; i++) {
            randomColor();
            RGBtoHSL();
            s = 0.9 + rng.nextDouble() * 0.1;
            l = 0.75 + rng.nextDouble() * 0.15;
            HSLtoRGB();
            fillBand(height * i / 5, height * (i + 1) / 5);
        }
    }

    public void generateRandom() {
        a = 255;
        for (int i = 0; i < 5; i++) {
            randomColor();
            fillBand(height * i / 5, height * (i + 1) / 5);
        }
    }

    public void savePalette() {

        //file object
        File f;
        //write image
        try {
            int i = 1;
            new File("C:\\Image").mkdirs();
            path = "C:\\Image\\Output" + i + ".png";
            f = new File(path);
            while (f.exists()) {
                i++;
                path = "C:\\Image\\Output" + i + ".png";
                f = new File(path);
            }
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String args[]) throws IOException {
        RandomImage a = new RandomImage();
        a.init();
    }//main() ends here
}//class ends here
