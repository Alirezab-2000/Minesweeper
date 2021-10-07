// IN THE NAME OF GOD
// ALIREZA Binesh
package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static minesweeper.Core.mineframe;
import sun.audio.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Frame extends javax.swing.JFrame implements MouseListener, Serializable {

    static int numberofhouse = 0;
    FileWriter fileWriter;
    PrintWriter printWriter;
    ObjectInputStream input;
    ObjectOutputStream output;
    Gameover winlose = Gameover.NONE;
    Gamemode gamemode = Gamemode.multiplayer;
    Random rand = new Random();
    Font clockandbombtext = new Font("Ebrima", 0, 35);
    int numberofbombs = 10;
    JLabel mainlabel = new JLabel();
    JLabel clockicon = new JLabel();
    JLabel bombicon = new JLabel();
    JLabel bomb_text = new JLabel();
    JLabel clock_text = new JLabel();
    JLabel on_clock = new JLabel("0", JLabel.CENTER);
    JLabel on_bomb = new JLabel(String.valueOf(numberofbombs), JLabel.CENTER);
    Time mytime = new Time(on_clock);
    int[][] table = new int[9][9];
    House[][] house = new House[9][9];
    Icon border = new ImageIcon(getClass().getResource("/sprites/border.png"));
    Icon bomb_icon = new ImageIcon(getClass().getResource("/sprites/bomb_icon.png"));
    Icon bomb_text_holder = new ImageIcon(getClass().getResource("/sprites/bomb_text_holder.png"));
    Icon clock_icon = new ImageIcon(getClass().getResource("/sprites/clock_icon.png"));
    Icon clock_text_holder = new ImageIcon(getClass().getResource("/sprites/clock_text_holder.png"));
    Icon idle = new ImageIcon(getClass().getResource("/sprites/house_idle.png"));
    Icon hover = new ImageIcon(getClass().getResource("/sprites/house_hover.png"));
    Icon flag = new ImageIcon(getClass().getResource("/sprites/flag_idle.png"));
    Icon bomb = new ImageIcon(getClass().getResource("/sprites/unflagged_bomb.png"));
    Icon clicked_bomb = new ImageIcon(getClass().getResource("/sprites/misclicked_bomb.png"));
    Icon flaged_bomb = new ImageIcon(getClass().getResource("/sprites/flagged_bomb.png"));
    Icon flag_hover = new ImageIcon(getClass().getResource("/sprites/flag_hover.png"));
    Icon question = new ImageIcon(getClass().getResource("/sprites/question_mark_idle.png"));
    Icon question_hover = new ImageIcon(getClass().getResource("/sprites/question_mark_hover.png"));
    Icon numzero = new ImageIcon(getClass().getResource("/sprites/house_empty.png"));
    Icon numone = new ImageIcon(getClass().getResource("/sprites/number_1.png"));
    Icon numtwo = new ImageIcon(getClass().getResource("/sprites/number_2.png"));
    Icon numthree = new ImageIcon(getClass().getResource("/sprites/number_3.png"));
    Icon numfour = new ImageIcon(getClass().getResource("/sprites/number_4.png"));
    Icon numfive = new ImageIcon(getClass().getResource("/sprites/number_5.png"));
    Icon numsix = new ImageIcon(getClass().getResource("/sprites/number_6.png"));
    Icon numseven = new ImageIcon(getClass().getResource("/sprites/number_7.png"));
    Icon numeight = new ImageIcon(getClass().getResource("/sprites/number_8.png"));
    final String AUDIO_FILE_PATH_WOW_NICE = "src/music/wow_nice.wav";
    final String AUDIO_FILE_PATH_CLICK = "src/music/click.wav";
    final String AUDIO_FILE_PATH_GAME_START = "src/music/game_start.wav";
    final String AUDIO_FILE_PATH_BOMB = "src/music/bomb.wav";
    final String AUDIO_FILE_PATH_WON = "src/music/game_won.wav";
    final String AUDIO_FILE_PATH_FLAG = "src/music/flag.wav";
    final String AUDIO_FILE_PATH_QUESTION_MARK = "src/music/question_mark.wav";
    final String AUDIO_FILE_PATH_WTF = "src/music/wtf.wav";
    final String AUDIO_FILE_PATH_DOUBLE_CLICK = "src/music/double_click.wav";
    final String AUDIO_FILE_PATH_NOT_ENOUGH_FLAG = "src/music/not_enough_flags.wav";
    AudioStream currentlyPlayingAudio = null;

    /* AudioStream clic = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\Click.wav"));
    AudioStream janamijan = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\Janamijan.wav"));
    AudioStream shoro = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\Shorobazi.wav"));
    AudioStream bombe = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\Bombe.wav"));
    AudioStream toyebarandei = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\Toyebarandei.wav"));
    AudioStream parcham = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\Parcham.wav"));
    AudioStream nemidonichei = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\Nemidini.wav"));
    AudioStream fazetcheiayzam = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\What the faz.wav"));
    AudioStream dobleclic = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\doublclic.wav"));
    AudioStream bdonparcham = new AudioStream(new FileInputStream("D:\\PROJAVA\\EXAMPLE\\MineSweeper\\music\\Pasparcamashco.wav"));*/
    public Frame() throws IOException {
        playmusic(AUDIO_FILE_PATH_GAME_START);
        fileWriter = new FileWriter("record.txt", true);
        printWriter = new PrintWriter(fileWriter);
        try {
            if ((Minesweeperserver.gamestart)) {
                throw new Exception(" you cant connected");
            }
            System.out.println(Minesweeperserver.gamestart);
            Socket gamer = new Socket("127.0.0.1", 1234);
            System.out.println("client coneccted");
            input = new ObjectInputStream(gamer.getInputStream());
            output = new ObjectOutputStream(gamer.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        Message start = (Message) input.readObject();
                        if (start.i != -1) {
                            table = start.house;
                            filltable(start.i, start.j);
                            click(start.i, start.j);
                            showtable();
                        } else {
                            winlose = start.gameover;
                            if (winlose == Gameover.LOSE) {
                                new Loseframe(gamemode, this);
                            } else if (winlose == Gameover.WIN) {
                                new Winframe(gamemode, this);
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        } catch (Exception e) {
            System.out.println("single pleyer");
            gamemode = Gamemode.singleplayer;
        }
        this.setLayout(null);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                house[i][j] = new House(new JLabel(), Status.IDLE, i, j);
                house[i][j].jlabel.setBounds(85 + (50 * j), 85 + (50 * i), 50, 50);
                house[i][j].jlabel.setIcon(idle);
                this.add(house[i][j].jlabel);
                house[i][j].jlabel.addMouseListener(this);
            }
        }
        init();
    }

    public void click(int i, int j) {
        if (house[i][j].getNumber() == -1 && !(house[i][j].getStatus().equals(Status.FLAGGED))) {
            System.out.println(" you lose ");
            showbomb(i, j);
            winlose = Gameover.LOSE;
            int recordtime = mytime.firsttime;
            mytime.stop();
            if (gamemode != Gamemode.multiplayer) {
                printWriter.println("lose" + ";" + recordtime);
                printWriter.close();
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                output.writeObject(new Message(winlose));
            } catch (Exception e) {
                System.out.println("single player");
            }
            new Loseframe(gamemode, this);
            System.out.println("writed");
        }

        if (house[i][j].getNumber() != -1 && house[i][j].getNumber() != 0 && !(house[i][j].getStatus().equals(Status.FLAGGED))) {
            switch (house[i][j].getNumber()) {
                case 1:
                    house[i][j].status = Status.NUMBER;
                    numberofhouse++;
                    break;
                case 2:
                    house[i][j].status = Status.NUMBER;
                    numberofhouse++;
                    break;
                case 3:
                    house[i][j].status = Status.NUMBER;
                    numberofhouse++;
                    break;
                case 4:
                    house[i][j].status = Status.NUMBER;
                    numberofhouse++;
                    break;
                case 5:
                    house[i][j].status = Status.NUMBER;
                    numberofhouse++;
                    break;
                case 6:
                    house[i][j].status = Status.NUMBER;
                    numberofhouse++;
                    break;
                case 7:
                    house[i][j].status = Status.NUMBER;
                    numberofhouse++;
                    break;
                case 8:
                    house[i][j].status = Status.NUMBER;
                    numberofhouse++;
                    break;
                default:
                    break;
            }
        }

        if (house[i][j].getNumber() == 0) {
            house[i][j].status = Status.NUMBER;
            numberofhouse++;

            for (int k = i - 1; k <= i + 1; k++) {
                for (int l = j - 1; l <= j + 1; l++) {
                    try {
                        if (house[k][l].status == Status.NUMBER) {
                            continue;
                        }
                        click(k, l);

                    } catch (Exception e) {
                    }
                }
            }
        }
        if (checkwin()) {
            winlose = Gameover.WIN;
            try {
                System.out.println("heelo");
                output.writeObject(new Message(winlose));
            } catch (Exception e) {
                System.out.println("single player");
            }
            int recordtime = mytime.firsttime;
            if (gamemode != Gamemode.multiplayer) {
                printWriter.println("win" + ";" + recordtime);
                printWriter.println("lose" + ";" + recordtime);
                printWriter.close();
            }
            mytime.stop();
            new Winframe(gamemode, this);
        }
    }

    public void showtable() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (house[i][j].status == Status.NUMBER) {
                    switch (house[i][j].getNumber()) {
                        case 0:
                            house[i][j].jlabel.setIcon(numzero);
                            break;
                        case 1:
                            house[i][j].jlabel.setIcon(numone);
                            break;
                        case 2:
                            house[i][j].jlabel.setIcon(numtwo);
                            break;
                        case 3:
                            house[i][j].jlabel.setIcon(numthree);
                            break;
                        case 4:
                            house[i][j].jlabel.setIcon(numfour);
                            break;
                        case 5:
                            house[i][j].jlabel.setIcon(numfive);
                            break;
                        case 6:
                            house[i][j].jlabel.setIcon(numsix);
                            break;
                        case 7:
                            house[i][j].jlabel.setIcon(numseven);
                            break;
                        case 8:
                            house[i][j].jlabel.setIcon(numeight);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public boolean isempty(int[][] a) {
        boolean issempty = true;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] != 0) {
                    issempty = false;
                }
            }
        }
        return issempty;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (winlose == Gameover.LOSE || winlose == Gameover.WIN) {
            e.consume();
            return;
        }
        int i = -1;
        int j = -1;
        JLabel clickedlabel = (JLabel) e.getComponent();
        for (House[] myrow : house) {
            for (House myfield : myrow) {
                if (myfield.jlabel.equals(clickedlabel)) {
                    i = myfield.getI();
                    j = myfield.getJ();
                }
            }
        }
        if (e.getButton() == 1) {
            if (!(mytime.isAlive())) {
                mytime.start();
            }
            System.out.println(" i: " + i + " j: " + j);
            if (e.getClickCount() == 2) {
                int numberofflag = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        try {
                            if (house[k][l].getStatus().equals(Status.FLAGGED)) {
                                numberofflag++;
                            }
                        } catch (Exception q) {
                        }
                    }
                }
                if (numberofflag == house[i][j].getNumber()) {
                    playmusic(AUDIO_FILE_PATH_DOUBLE_CLICK);
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            try {
                                if (house[k][l].status == Status.NUMBER) {
                                    continue;
                                }
                                click(k, l);
                            } catch (Exception q) {
                            }
                        }
                    }
                    if (numberofhouse >= 9) {
                        playmusic(AUDIO_FILE_PATH_WOW_NICE);
                        numberofhouse = 0;
                    }
                } else {
                    playmusic(AUDIO_FILE_PATH_NOT_ENOUGH_FLAG);
                }
                showtable();
            }
            if (isempty(table)) {
                filltable(i, j);
                System.out.println();
                System.out.println();
                showArray();

                try {
                    output.writeObject(new Message(table, i, j));
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex2) {
                    System.out.println("single player");
                }

            }
            Status mysituation = house[i][j].getStatus();
            click(i, j);
            if (winlose == Gameover.WIN) {
                playmusic(AUDIO_FILE_PATH_WON);
            } else if (winlose == Gameover.LOSE) {
                playmusic(AUDIO_FILE_PATH_BOMB);
            } else if (numberofhouse >= 9) {
                playmusic(AUDIO_FILE_PATH_WOW_NICE);
                numberofhouse = 0;
            } else if (mysituation != Status.NUMBER) {
                playmusic(AUDIO_FILE_PATH_CLICK);
                numberofhouse = 0;
            }
            showtable();
        } else if (e.getButton() == 3) {

            if (house[i][j].getStatus().equals(Status.IDLE)) {
                playmusic(AUDIO_FILE_PATH_FLAG);
                house[i][j].jlabel.setIcon(flag);
                house[i][j].status = Status.FLAGGED;
                numberofbombs--;
                on_bomb.setText(String.valueOf(numberofbombs));
            } else if (house[i][j].getStatus().equals(Status.FLAGGED)) {
                playmusic(AUDIO_FILE_PATH_QUESTION_MARK);
                house[i][j].jlabel.setIcon(question);
                house[i][j].status = Status.UNKNOWN;
                numberofbombs++;
                on_bomb.setText(String.valueOf(numberofbombs));
            } else if (house[i][j].getStatus().equals(Status.UNKNOWN)) {
                playmusic(AUDIO_FILE_PATH_WTF);
                house[i][j].jlabel.setIcon(idle);
                house[i][j].status = Status.IDLE;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel j = (JLabel) e.getComponent();
        if (j.getIcon().equals(idle)) {
            j.setIcon(hover);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel j = (JLabel) e.getComponent();
        if (j.getIcon().equals(hover)) {
            j.setIcon(idle);
        }
    }

    public void filltable(int i, int j) {
        if (isempty(table)) {
            for (int k = 0; k < 10; k++) {
                int ibomb = rand.nextInt(9);
                int jbomb = rand.nextInt(9);

                if (((ibomb <= i + 1) && (ibomb >= i - 1) && (jbomb <= j + 1) && (jbomb >= j - 1)) || (table[ibomb][jbomb] == -1)) {
                    k--;
                } else {
                    table[ibomb][jbomb] = -1;
                    System.out.println(" ibomb :" + ibomb + " jbomb :" + jbomb);

                }

            }
            for (int ii = 0; ii < 9; ii++) {
                for (int jj = 0; jj < 9; jj++) {
                    if (table[ii][jj] == -1) {
                        continue;
                    }

                    int number = 0;
                    for (int k = ii - 1; k <= ii + 1; k++) {
                        for (int l = jj - 1; l <= jj + 1; l++) {
                            try {
                                if (table[k][l] == -1) {
                                    number++;
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    table[ii][jj] = number;

                }
            }
        }
        for (int r = 0; r < 9; r++) {
            for (int o = 0; o < 9; o++) {
                house[r][o].setNumber(table[r][o]);
            }
        }
    }

    public void showbomb(int x, int y) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (house[i][j].getNumber() == -1) {
                    if (house[i][j].getStatus().equals(Status.FLAGGED)) {
                        house[i][j].jlabel.setIcon(flaged_bomb);
                    } else if (i == x && j == y) {
                        house[i][j].jlabel.setIcon(clicked_bomb);
                    } else {
                        house[i][j].jlabel.setIcon(bomb);
                    }
                }
            }
        }
    }

    public boolean checkwin() {
        boolean win = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (house[i][j].status != Status.NUMBER && house[i][j].getNumber() != -1) {
                    win = false;
                }
            }
        }
        return win;
    }

    public void showArray() {
        for (int[] tablerow : table) {
            System.out.println(Arrays.toString(tablerow));
        }
    }

    public void init() {
        this.setSize(625, 665);
        this.setVisible(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        mainlabel.setBounds(0, 0, border.getIconWidth(), border.getIconHeight());
        mainlabel.setIcon(border);
        bombicon.setBounds(470, 550, bomb_icon.getIconWidth(), bomb_icon.getIconHeight());
        bombicon.setIcon(bomb_icon);
        clockicon.setBounds(85, 550, clock_icon.getIconWidth(), clock_icon.getIconHeight());
        clockicon.setIcon(clock_icon);
        bomb_text.setBounds(360, 560, bomb_text_holder.getIconWidth(), bomb_text_holder.getIconHeight());
        bomb_text.setIcon(bomb_text_holder);
        clock_text.setBounds(160, 560, clock_text_holder.getIconWidth(), clock_text_holder.getIconHeight());
        clock_text.setIcon(clock_text_holder);
        on_clock.setBounds(160, 560, clock_text_holder.getIconWidth(), clock_text_holder.getIconHeight());
        on_clock.setFont(clockandbombtext);
        on_clock.setForeground(Color.WHITE);
        on_bomb.setBounds(360, 560, bomb_text_holder.getIconWidth(), bomb_text_holder.getIconHeight());
        on_bomb.setFont(clockandbombtext);
        on_bomb.setForeground(Color.WHITE);
        this.add(on_bomb);
        this.add(on_clock);
        this.add(clock_text);
        this.add(bomb_text);
        this.add(clockicon);
        this.add(bombicon);
        this.add(mainlabel);
    }

    public void playmusic(String name) {

        try {
            if (currentlyPlayingAudio != null) {
                AudioPlayer.player.stop(currentlyPlayingAudio);
            }

            currentlyPlayingAudio = new AudioStream(new FileInputStream(name));

            AudioPlayer.player.start(currentlyPlayingAudio);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class Time extends Thread {

    JLabel timeshow;
    int firsttime = 0;

    public Time(JLabel timeshow) {
        this.timeshow = timeshow;
    }

    @Override
    public void run() {

        while (true) {
            try {
                firsttime++;
                if (firsttime < 1000) {
                    timeshow.setText(String.format(" %d ", firsttime));
                }
                Thread.sleep(999);
            } catch (InterruptedException ex) {
                Logger.getLogger(Time.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

enum Gameover {
    WIN,
    LOSE,
    NONE;
}

enum Gamemode {
    singleplayer,
    multiplayer;
}
