//package main.java;

import java.io.FileWriter;
import java.net.URL;
import java.awt.Image;
import javax.swing.ImageIcon;

public class HuluGrandfather extends Player{
    public HuluGrandfather(int x, int y, Field field) {
        super(x, y, field);
        blood = 1;
        direction = Direction.RIGHT;
        URL loc = this.getClass().getClassLoader().getResource("11.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
        loc = this.getClass().getClassLoader().getResource("爷爷die.png");
        iia = new ImageIcon(loc);
        image = iia.getImage();
        this.setDieImage(image);
    }
    public void writedown()
    {
        try {
            super.field.fw.write(1+" ");
            super.writedown();
            super.field.fw.write("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void printdie()
    {
        System.out.println("爷爷die");

    }
}
