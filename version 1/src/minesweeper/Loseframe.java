// IN THE NAME OF GOD
// ALIREZA Binesh
package minesweeper;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loseframe extends javax.swing.JFrame {

    FileReader fileReader;
    BufferedReader bufferedReader;
    Label pm = new Label("Better luck next time", Label.CENTER);
    Label time = new Label();
    Label besttime = new Label();
    Label gameplayed = new Label();
    Label gamewon = new Label();
    Button playagain = new Button(" play again !");
    Button exit = new Button(" EXIT ");
    Font font = new Font("Ebrima", 0, 35);
    int winnumber, playednumber, thistime, mybesttime;
    Gamemode gamemode;

    public Loseframe(Gamemode gamemode ,Frame frame) {

        this.gamemode = gamemode;
        if (gamemode == Gamemode.singleplayer) {
            try {
                winnumber = 0;
                playednumber = 0;
                thistime = 0;
                mybesttime = 0;
                init();
                fileReader = new FileReader("record.txt");
                bufferedReader = new BufferedReader(fileReader);
                String a = bufferedReader.readLine();
                while (a != null) {
                    playednumber++;
                    if (a.split(";")[0].equals("win") && mybesttime == 0) {
                        mybesttime = Integer.parseInt(a.split(";")[1]);
                    }
                    if (a.split(";")[0].equals("win")) {
                        winnumber++;
                    }
                    if (Integer.parseInt(a.split(";")[1]) < mybesttime && a.split(";")[0].equals("win")) {
                        mybesttime = Integer.parseInt(a.split(";")[1]);
                    }
                    thistime = Integer.parseInt(a.split(";")[1]);
                    a = bufferedReader.readLine();
                }
                time.setText(" time : " + thistime);
                gameplayed.setText(" game played : " + playednumber);
                gamewon.setText(" game won : " + winnumber);
                if (mybesttime == 0) {
                    besttime.setText(" best time : ----");
                } else {
                    besttime.setText(" best time : " + mybesttime);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Winframe.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Winframe.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            this.setLayout(null);
            this.setSize(400, 300);
            this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
            this.setVisible(true);
            pm.setBounds(0, 20, 380, 150);
            pm.setFont(font);
            playagain.setBounds(200, 180, 150, 50);
            playagain.setBackground(Color.WHITE);
            exit.setBounds(20, 180, 150, 50);
            exit.setBackground(Color.WHITE);
            this.add(exit);
            this.add(playagain);
            this.add(pm);
        }
        playagain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new Frame();
                    dispose();
                } catch (IOException ex) {
                    Logger.getLogger(Winframe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        exit.addActionListener((ActionEvent e) -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

    }

    public void init() {
        this.setLayout(null);
        this.setSize(400, 350);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);
        pm.setBounds(0, 20, 380, 50);
        pm.setFont(font);
        time.setBounds(20, 100, 200, 10);
        besttime.setBounds(20, 130, 200, 10);
        gameplayed.setBounds(20, 160, 200, 15);
        gamewon.setBounds(20, 190, 200, 15);
        playagain.setBounds(200, 230, 150, 50);
        playagain.setBackground(Color.WHITE);
        exit.setBounds(20, 230, 150, 50);
        exit.setBackground(Color.WHITE);
        this.add(exit);
        this.add(playagain);
        this.add(gamewon);
        this.add(gameplayed);
        this.add(besttime);
        this.add(time);
        this.add(pm);
    }

}
