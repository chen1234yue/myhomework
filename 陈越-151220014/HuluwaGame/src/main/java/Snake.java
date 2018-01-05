//package main.java;

import java.io.FileWriter;
import java.net.URL;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Snake extends Player {
    public Snake(int x, int y, Field field) {
        super(x, y, field);
        blood = 12;
        direction = Direction.LEFT;
        URL loc = this.getClass().getClassLoader().getResource("蛇精1.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
        loc = this.getClass().getClassLoader().getResource("蛇精die.png");
        iia = new ImageIcon(loc);
        image = iia.getImage();
        this.setDieImage(image);
    }
    public void  writedown()
    {
        try {
            this.field.fw.write(2+" ");
            super.writedown();
            this.field.fw.write("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void printdie()
    {
        System.out.println("蛇精die");
    }
}
