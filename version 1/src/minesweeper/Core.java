// IN THE NAME OF GOD
// ALIREZA Binesh
package minesweeper;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Core implements Serializable {

   static Frame mineframe;

    public static void main(String[] args) throws IOException {

        //     new Winframe();
        mineframe = new Frame();
   /*     mineframe.setSize(625, 665);
        mineframe.setVisible(true);
        mineframe.setLayout(null);
        mineframe.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mineframe.setLocation(dim.width / 2 - mineframe.getSize().width / 2, dim.height / 2 - mineframe.getSize().height / 2);*/
    }

   

}
