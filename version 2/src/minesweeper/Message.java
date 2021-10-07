// IN THE NAME OF GOD
// ALIREZA Binesh

package minesweeper;

import java.io.Serializable;

public class Message implements Serializable{
    int[][] house;
    int i;
    int j;
    Gameover gameover;

    public Message(Gameover gameover) {
        this.gameover = gameover;
        i = -1;
    }

    
    
    public Message(int[][] house, int i, int j) {
        this.house = house;
        this.i = i;
        this.j = j;
        gameover = null;
    }
    
}
