//package main.java;

import java.io.FileWriter;
import java.net.URL;
import java.awt.Image;
import javax.swing.ImageIcon;


public class Littlell extends Player{
    int no;
    public Littlell(int x, int y, Field field,int n) {
        super(x, y, field);
        blood=1;
        no = n;
        direction = Direction.LEFT;
        URL loc = this.getClass().getClassLoader().getResource("小喽啰1.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
        loc = this.getClass().getClassLoader().getResource("小喽啰die.png");
        iia = new ImageIcon(loc);
        image = iia.getImage();
        this.setDieImage(image);
    }
    public void  writedown()
    {
        try {
            this.field.fw.write(4+" ");
            this.field.fw.write(no+" ");
            super.writedown();
            this.field.fw.write("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void printdie()
    {
        System.out.println("小喽啰die");
    }
}
