// IN THE NAME OF GOD
// ALIREZA Binesh
package minesweeper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Minesweeperserver {

    public static ArrayList<Gamerpackage> ourgamer = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket sock = new ServerSocket(1234);
            int k = 1;
            while (true) {
                System.out.println("wating to accept");
                Socket mygamer = sock.accept();
                System.out.println(mygamer.getInputStream());
                Gamerpackage gamer = new Gamerpackage();
                gamer.setDoutc(new ObjectOutputStream(mygamer.getOutputStream()));
                gamer.setDinc(new ObjectInputStream(mygamer.getInputStream()));
                gamer.setSock(mygamer);
                gamer.setId(k);
                ourgamer.add(gamer);
                new GamerHandler(gamer).start();
                k++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Minesweeperserver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class Gamerpackage {

    int id;
    Socket sock;
    ObjectInputStream dinc;
    ObjectOutputStream doutc;

    public void setId(int id) {
        this.id = id;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public void setDinc(ObjectInputStream dinc) {
        this.dinc = dinc;
    }

    public void setDoutc(ObjectOutputStream doutc) {
        this.doutc = doutc;
    }

    public Socket getSock() {
        return sock;
    }

    public ObjectOutputStream getDoutc() {
        return doutc;
    }

    public ObjectInputStream getDinc() {
        return dinc;
    }

}

class GamerHandler extends Thread {

    Gamerpackage gamer;
    ObjectOutputStream doutserver;
    ObjectInputStream dinserver;

    public GamerHandler(Gamerpackage gamer) {
        this.gamer = gamer;
    }

    @Override
    public void run() {
        try {
            System.out.println("hello");
            dinserver = gamer.getDinc();
            doutserver = gamer.getDoutc();

            while (true) {

                System.out.println(dinserver);
                Message tables = (Message) dinserver.readObject();
                System.out.println(tables);

                if (tables.i == -1) {
                    if (tables.gameover == Gameover.WIN) {
                        tables.gameover = Gameover.LOSE;
                        send_to_all(tables, gamer);
                    } else if (tables.gameover == Gameover.LOSE && Minesweeperserver.ourgamer.size() == 2) {
                        tables.gameover = Gameover.WIN;
                        send_to_all(tables, gamer);
                    } else{
                        Minesweeperserver.ourgamer.remove(gamer);
                    }
                }

                
            }

        } catch (IOException ex) {
            Logger.getLogger(GamerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GamerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Minesweeperserver.ourgamer.remove(gamer);
        }
    }
    
    public void send_to_all(Message tables , Gamerpackage gamer){
        for (Gamerpackage ourgamer : Minesweeperserver.ourgamer) {
            try {
                if (gamer.id == ourgamer.id) {
                    continue;
                }
                doutserver = ourgamer.getDoutc();
                doutserver.writeObject(tables);
                doutserver.flush();
            } catch (IOException ex) {
                Logger.getLogger(GamerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
    }

}
