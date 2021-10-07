// IN THE NAME OF GOD
// ALIREZA Binesh
package minesweeper;

import java.io.Serializable;
import javax.swing.JLabel;

public class House implements Serializable{

    public JLabel jlabel;
    public Status status;
    public int number;
    int i;
    int j;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Status getStatus() {
        return status;
    }
    
    
    public House(JLabel label, Status status, int i, int j) {
        this.jlabel = label;
        this.status = status;
        this.i = i;
        this.j = j;
    }

}
