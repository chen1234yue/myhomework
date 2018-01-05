//package main.java;


import javax.swing.JFrame;


public final class Ground extends JFrame {

    private final int OFFSET = 240;


    public Ground() {
        InitUI();
    }

    public void InitUI() {
        Field field = new Field();
        add(field);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth(),
                field.getBoardHeight() + 2* OFFSET-60);
        setLocationRelativeTo(null);
        setTitle("Ground");
    }



}