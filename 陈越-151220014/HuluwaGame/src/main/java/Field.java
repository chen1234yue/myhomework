//package main.java;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;

import static java.lang.Thread.sleep;

public class Field extends JPanel {

    private final int OFFSET = 240;
    private final int SPACE = 60;
    private ArrayList tiles = new ArrayList();
    //private Player player;
    private ArrayList huluwaTeam = new ArrayList();
    private ArrayList snakeTeam = new ArrayList();
    private int w = 0;
    private int h = 0;
    private Image startImage;
    private Image gameImage;
    private Image huluwinImage;
    private Image snakewinImage;
    public boolean completed = false;
    public boolean started = false;
    PlayBack pb;
    FileWriter fw;
    FileReader fr;
    int win;
    private String level =
             "................\n" +
             ".1..........00..\n" +
             ".2.........00...\n" +
             ".3.......x00....\n" +
             ".4.8.....s00....\n" +
             ".5.......000....\n" +
             ".6.........00...\n" +
             ".7..........00..\n" +
             "................\n" ;
    //+"....................\n";

    public Field() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        URL loc = this.getClass().getClassLoader().getResource("back2.jpg");
        ImageIcon iia = new ImageIcon(loc);
        startImage = iia.getImage();
        loc = this.getClass().getClassLoader().getResource("back1.png");
        iia = new ImageIcon(loc);
        gameImage = iia.getImage();
        loc = this.getClass().getClassLoader().getResource("huluWin.png");
        iia = new ImageIcon(loc);
        huluwinImage = iia.getImage();
        loc = this.getClass().getClassLoader().getResource("snakeWin.png");
        iia = new ImageIcon(loc);
        snakewinImage = iia.getImage();
        initWorld();
        pb = new PlayBack(this);
    }
    public ArrayList getHuluwaTeam()
    {
        return huluwaTeam;
    }
    public ArrayList getSnakeTeam()
    {
        return snakeTeam;
    }
    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    public final void initWorld() {

 //       int x = OFFSET;
 //       int y = OFFSET;
        int x = 0;
        int y = 0;
        Tile a;
        Player b;
        int lolono = 0;
        for (int i = 0; i < level.length(); i++) {

            char item = level.charAt(i);

            if (item == '\n') {
                y += SPACE;
                if (this.w < x) {
                    this.w = x;
                }

                x = 0;
            } else if (item == '.') {
                a = new Tile(x, y);
                tiles.add(a);
                x += SPACE;
            } else if (item >= '1'&&item<='7') {
                //System.out.println(1);
                a = new Tile(x, y);
                tiles.add(a);
                b = new Huluwa(x,y,this,item-'0');
                huluwaTeam.add(b);
                //player = new Player(x, y, this);
                x += SPACE;
            }
            else if (item == '8') {
                //System.out.println(1);
                a = new Tile(x, y);
                tiles.add(a);
                b = new HuluGrandfather(x,y,this);
                huluwaTeam.add(b);
                //player = new Player(x, y, this);
                x += SPACE;
            }
            else if (item =='s') {
                //System.out.println(1);
                a = new Tile(x, y);
                tiles.add(a);
                b = new Snake(x,y,this);
                snakeTeam.add(b);
                //player = new Player(x, y, this);
                x += SPACE;
            }else if (item =='x') {
                //System.out.println(1);
                a = new Tile(x, y);
                tiles.add(a);
                b = new Scorpion(x,y,this);
                snakeTeam.add(b);
                //player = new Player(x, y, this);
                x += SPACE;
            }else if (item =='0') {
                //System.out.println(1);
                a = new Tile(x, y);
                tiles.add(a);
                b = new Littlell(x,y,this,lolono);
                lolono++;
                snakeTeam.add(b);
                //player = new Player(x, y, this);
                x += SPACE;
            }
            else if (item == ' ') {
                x += SPACE;
            }

            h = y;
        }
        for (int i = 0; i < huluwaTeam.size(); i++)
            ((Player)huluwaTeam.get(i)).setEnemyFriend(snakeTeam,huluwaTeam);
        for (int i = 0; i < snakeTeam.size(); i++)
            ((Player)snakeTeam.get(i)).setEnemyFriend(huluwaTeam,snakeTeam);
        //player = new Player(0+ OFFSET,0+OFFSET, this);

    }
    public Player playerAction(String s)
    {
        Scanner sn = new Scanner(s);
        int x,y,die;
        int type = sn.nextInt();
        Player pl=null;
        switch (type)
        {
            case 0:pl = findhulu(sn.nextInt());break;
            case 1:pl = findgf();break;
            case 2:pl = findsnake();break;
            case 3:pl = findsc();break;
            case 4:pl = findll(sn.nextInt());break;
            default:break;
        }
      //  System.out.println("type:"+type);
        x = sn.nextInt();
       // System.out.println("X:"+x);
        y=sn.nextInt();
        //System.out.println("y:"+y);
        die=sn.nextInt();
        //System.out.println("die"+die);
       // stop=sn.nextInt();
        //System.out.println("stop"+stop);
      //  System.out.print(pl.x()+","+pl.y()+" ");
        if(pl!=null)
        {

            if(die==1)
            {
                pl.setDie();
            }
           /* if(stop==1)
            {
                pl.setStop();
            }*/
            pl.moveto(x,y);
        }
        return pl;
       // System.out.println(pl.x()+","+pl.y()+" ");
    }
    public Player findhulu(int no)
    {
        for (int i = 0; i < huluwaTeam.size(); i++) {
            if (huluwaTeam.get(i) instanceof Huluwa) {
                Huluwa temp = (Huluwa) huluwaTeam.get(i);
                if (temp.no == no)
                    return temp;
            }
        }
        return null;
    }
    public Player findgf()
    {
        for (int i = 0; i < huluwaTeam.size(); i++) {
            if (huluwaTeam.get(i) instanceof HuluGrandfather) {
                return (Player) huluwaTeam.get(i);
            }
        }
        return null;
    }
    public Player findll(int no)
    {
        for (int i = 0; i < snakeTeam.size(); i++) {
            if (snakeTeam.get(i) instanceof Littlell) {
                Littlell temp = (Littlell) snakeTeam.get(i);
                if (temp.no == no)
                    return temp;
            }
        }
        return null;
    }
    public Player findsnake()
    {
        for (int i = 0; i < snakeTeam.size(); i++) {
            if (snakeTeam.get(i) instanceof Snake) {
                return (Player) snakeTeam.get(i);
            }
        }
        return null;
    }
    public Player findsc()
    {
        for (int i = 0; i < snakeTeam.size(); i++) {
            if (snakeTeam.get(i) instanceof Scorpion) {
                return (Player) snakeTeam.get(i);
            }
        }
        return null;
    }
    public void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));

       /* if(started==false)
        {
            g.fillRect(0, 0, 1000, 600);
            g.drawImage(startImage,0,0,this);
            return;
        }*/
        g.fillRect(0, 0, this.getWidth(), this.getHeight()+60+OFFSET);
        g.drawImage(gameImage,0,0,this);
        ArrayList world = new ArrayList();
       // world.addAll(tiles);


        world.addAll(huluwaTeam);
        world.addAll(snakeTeam);

        for (int i = 0; i < world.size(); i++) {

            Thing2D item = (Thing2D) world.get(i);

            if (item instanceof Player) {
                if(((Player) item).die)
                    g.drawImage(item.getDieImage(), item.x() + 2, item.y()+OFFSET + 2, this);
            }
         //   System.out.print(item.x()+","+item.y()+" ");

        }
        for (int i = 0; i < world.size(); i++) {

            Thing2D item = (Thing2D) world.get(i);

            if (item instanceof Player) {
                if(!((Player) item).die)
                    g.drawImage(item.getImage(), item.x() + 2, item.y()+OFFSET + 2, this);
            }
            //   System.out.print(item.x()+","+item.y()+" ");

        }
        if (completed) {
            g.setColor(new Color(0, 0, 0));
            g.drawString("Completed", 25, 20);
            if(win == 1)
            {
               g.drawImage(snakewinImage,200,300,this);
            }
            else
            {
                g.drawImage(huluwinImage,150,300,this);
            }
        }
       // System.out.println();
    }
    public void updateField()
    {
        boolean com = true;
        for(int i=0;i<huluwaTeam.size();i++) {
            Player hulu = (Player) huluwaTeam.get(i);
            if (!hulu.die) {
                com = false;
                break;
            }
        }
        if(com==false) {
            com = true;
            for (int j = 0; j < snakeTeam.size(); j++) {

                Player sn = (Player) snakeTeam.get(j);
                if (!sn.die) {
                    com = false;
                    break;
                }
            }
            if(com ==true)
                win = 2;
        }
        else
            win = 1;
        completed = com;
        if(completed)
        {
            System.out.println("completed");
            try {
                fw.close();
            } catch (IOException t1) {
                //TODO 自动生成的 catch 块
                //t.printStackTrace();
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }
    public void run()
    {
        if(completed==false)
        {
            restartLevel();
        }
        started = true;
        File f2=new File("FightResult.txt");
        try {
            fw = new FileWriter(f2);
        }
        catch (IOException t)
        {
            t.printStackTrace();
        }
               /*for (int i = 0; i < huluwaTeam.size(); i++)
                ((Player)huluwaTeam.get(i)).writedown();
                for (int i = 0; i < snakeTeam.size(); i++)
                    ((Player)snakeTeam.get(i)).writedown();*/
        //ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < huluwaTeam.size(); i++) {
            Thread t = new Thread((Player)huluwaTeam.get(i));
            t.start();

            //exec.execute((Player) huluwaTeam.get(i));
        }
        for (int i = 0; i < snakeTeam.size(); i++) {
            Thread t = new Thread((Player)snakeTeam.get(i));
            t.start();
            // exec.execute((Player) snakeTeam.get(i));
        }
        //exec.shutdown();
        //completed = boolCompleted();
    }
    public void playback()
    {
        int b;

        if(completed==true||started==false) {
            restartLevel();
            File f2=null;

            JFileChooser jfc=new JFileChooser(".");
            jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
            if(jfc.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
                f2 = jfc.getSelectedFile();
            }
            if(f2!=null) {
                try {
                    fr = new FileReader(f2);
                } catch (IOException x2) {
                    x2.printStackTrace();
                }
                new Thread(pb).start();
            }
        }
    }
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();


            if (key == KeyEvent.VK_LEFT) {


                //player.move(-SPACE, 0);

            } else if (key == KeyEvent.VK_RIGHT) {


                //player.move(SPACE, 0);

            } else if (key == KeyEvent.VK_UP) {


                //player.move(0, -SPACE);

            } else if (key == KeyEvent.VK_DOWN) {


                //player.move(0, SPACE);

            } else if (key == KeyEvent.VK_SPACE) {


                    run();


            } else if (key == KeyEvent.VK_R) {
                restartLevel();
            }
            else if (key == KeyEvent.VK_L) {
                playback();
            }

            repaint();
        }
    }

    public void restartLevel() {

        tiles.clear();
        completed=false;
        started=true;
        huluwaTeam.clear();
        snakeTeam.clear();
        initWorld();
        if (completed) {
            completed = false;
        }
    }
}