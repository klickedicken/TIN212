import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import static javax.swing.JOptionPane.*;

public class Memory extends JFrame{
    private int rows;
    private int cols;
    private Card[] allCards;
    
    public Memory(){
        initGameBoard();
        initAllCards();
        initGameFrame();
        setSize(400,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void initGameFrame(){
        setLayout(new GridLayout(2,1));
        JPanel p1 = new JPanel();
        p1.setBackground(Color.pink);
        JPanel p2 = new JPanel();
        p2.setBackground(Color.blue);
        add(p1);
        add(p2);
        
    }
    public void newGame(){
        Card[] cards;
        Card[] temp = Arrays.copyOf(allCards,allCards.length);
        Tools.randomOrder(temp);        
        
        cards = new Card[rows*cols];
        System.arraycopy(temp,0,cards,0,rows*cols/2);
        System.arraycopy(temp,0,cards,rows*cols/2,rows*cols/2);
        
        Tools.randomOrder(cards);
    }
    public void initAllCards(){
        File folder = new File("mypictures");
        File[] pictures = folder.listFiles();
        allCards = new Card[pictures.length];
        for(int i=0; i<pictures.length; i++){
            allCards[i] = new Card(new ImageIcon(pictures[i].getPath()));
        }        
    }
    public void initGameBoard(){
        while (true) { 
            rows = Integer.parseInt(showInputDialog(
                        null, "Antal rader?", "Memory", QUESTION_MESSAGE));
            cols = Integer.parseInt(showInputDialog(
                        null, "Antal kolumner?", "Memory", QUESTION_MESSAGE));
            if (rows*cols>36){
                showMessageDialog(null,"För stor spelplan! Max 36 kort.", 
                                  "Memory", ERROR_MESSAGE);
            }
            else if (rows*cols % 2 == 1){
                showMessageDialog(null,"Välj jämnt antal kort!", 
                                  "Memory", ERROR_MESSAGE);
            }
            else break;
                
        }                         
    }
    public static void main(String[] args){
        new Memory();        
    }
}
