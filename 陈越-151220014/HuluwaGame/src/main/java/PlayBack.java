//package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class PlayBack implements Runnable{
    Field field;

    public PlayBack(Field t)
    {
        field = t;


    }
    public void run()
    {
        BufferedReader br = new BufferedReader(field.fr);
        try {
            String pl = br.readLine();
            while (pl!=null) {
                // System.out.println(pl + ' ');
                synchronized (field) {
                    field.playerAction(pl);
                    field.repaint();
                }
                sleep(100);
                pl = br.readLine();
            }
            field.updateField();
            //field.completed = true;
            field.repaint();
        }
        catch(Exception x1)
        {

        }


    }
}
