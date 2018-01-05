//package main.java;


import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

import static java.lang.Thread.currentThread;

public abstract class Player extends Thing2D implements Runnable {
    protected Field field;
    public enum Direction{LEFT,RIGHT,UP,DOWN};
    Direction direction;
    boolean die;
   // boolean stop;
    int blood;
    ArrayList enemy;
    ArrayList friend;
    public Player(int x, int y, Field field) {
        super(x, y);
        die = false;
       // stop=false;
        this.field = field;
        enemy = null;
        friend=null;
        //writedown();
        /*URL loc = this.getClass().getClassLoader().getResource("resources/红娃1.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);*/
    }
    public boolean isDie()
    {
        return  die;
    }
    public void writedown()
    {
        super.writedown(this.field.fw);
        try {
        if(die)
            this.field.fw.write(1+" ");
        else
            this.field.fw.write(0+" ");
   /*     if(stop)
            this.field.fw.write(1+" ");
        else
            this.field.fw.write(0+" ");*/
    } catch (Exception e) {
        System.out.println(e.toString());
    }
    }
    abstract void printdie();
    public void setEnemyFriend(ArrayList e,ArrayList fed)
    {
        enemy = e;
        friend = fed;
    }
    public void move(Direction d) {
        int bex = x();
        int bey = y();
        int nx;
        int ny;
        int x=60;
        if(d==Direction.LEFT) {
            nx = this.x() - x;
            if (nx < 0) {
                direction = Direction.RIGHT;
                return;

            }
            this.setX(nx);
        }
        else if(d==Direction.RIGHT){
            nx = this.x() + x;
            if(nx+60>field.getBoardWidth())
            {
                direction = Direction.LEFT;
                return;
            }
            this.setX(nx);
        }
        else if(d ==Direction.UP)
        {
            ny = this.y() - x;
            if(ny<0)
            {
                return;
            }
            this.setY(ny);
        }
        else
        {
            ny = this.y() + x;
            if(ny+60>field.getBoardHeight())
            {
                return;
            }
            this.setY(ny);
        }
        if(enemy!=null) {
            for (int i = 0; i < enemy.size(); i++) {
                if (this.samePlace((Thing2D) enemy.get(i)) && !((Player) enemy.get(i)).die) {
                    this.setY(bey);
                    this.setX(bex);
                    break;
                }
            }
        }
        if(friend!=null) {
            for (int i = 0; i < friend.size(); i++) {
                if (this != friend.get(i) && this.samePlace((Thing2D) friend.get(i)) && !((Player) friend.get(i)).die) {
                    this.setY(bey);
                    this.setX(bex);
                    break;
                }
            }
        }
        //int ny = this.y() + y;
        //this.setX(nx);
        //this.setY(ny);
    }
    public void moveto(int x,int y)
    {
        this.setX(x);
        this.setY(y);
        this.field.repaint();
    }
    public void setDie()
    {
        die = true;
    }
    /*public void setStop()
    {
        stop = true;
    }*/
    public void fight(Player en)
    {
        Random rand = new Random();
        if(die||en.die)
            return;
         int temp = blood - en.blood;
         if(temp>0)
         {
             blood = blood-rand.nextInt(en.blood);
             blood = temp;
             en.blood=0;
             en.die = true;
         }
         else if(temp<0){

             en.blood = en.blood-rand.nextInt(blood);
             blood = 0;
             die = true;
         }
         else
         {
             blood = 0;
             die = true;
             en.blood=0;
             en.die = true;
         }
    }
    public void updatePlayer()
    {
        for(int i=0;i<enemy.size();i++)
        {
            Player e = (Player)enemy.get(i);
            if(!e.die) {
                if (ishit(e))
                {
                    fight(e);
                    e.writedown();
                    break;
                }
            }
        }
        writedown();
    }
    public Direction getdirection()
    {
        Random rand = new Random();
        Player min=null;
        int dmin=1<<30;
        for(int i=0;i<enemy.size();i++)
        {
            Player e = (Player)enemy.get(i);
            if(e!=null) {
                int dtemp = (x() - e.x()) * (x() - e.x()) + (y() - e.y()) * (y() - e.y());
                if (dtemp < dmin) {
                    min = e;
                    dmin = dtemp;
                }
            }
        }
        Direction choose1;
        Direction choose2;
        if(min!=null)
        {
            if(min.x()>x())
                choose1=Direction.RIGHT;
            else
                choose1=Direction.LEFT;
            if(min.y()>y())
                choose2=Direction.DOWN;
            else
                choose2=Direction.UP;

            int i = rand.nextInt(2);
            if(i==1)
            {
                return choose1;
            }
            else
                return choose2;
        }
        int t = rand.nextInt(4);
        switch (t) {
            case 0:
                return Direction.LEFT;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.UP;
            case 3:
                return Direction.DOWN;
            default:
                break;
        }
        return direction;
    }
    public void run() {
        while ((!Thread.interrupted())&&!die&&!field.completed) {
            Random rand = new Random();

                //System.out.println(t);
            synchronized (Player.class) {
                  /*  int t = rand.nextInt(4);
                    switch (t) {
                        case 0:
                            this.move(Direction.LEFT);
                            break;
                        case 1:
                            this.move(Direction.RIGHT);
                            break;
                        case 2:
                            this.move(Direction.UP);
                            break;
                        case 3:
                            this.move(Direction.DOWN);
                            break;
                        default:
                            break;
                    }*/
                  this.move(getdirection());
                    updatePlayer();

                  this.field.updateField();
            }
            try {

                Thread.sleep(rand.nextInt(1000) + 1000);
                this.field.repaint();

            } catch (Exception e) {

            }
        }
       /* Thread.interrupted();
        try {

            Thread.sleep(1000);

        } catch (Exception e) {

        }*/
    }
}