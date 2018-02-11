import javax.swing.*;
import java.awt.*;

public class Card extends JButton{
    private Icon picture;
    private Status status;
    public enum Status{HIDDEN, VISIBLE, MISSING}
    
    public Card(Icon icon){
        this(icon, Status.MISSING);
    }
    
    public Card(Icon icon, Status status){
        picture = icon; 
        super.setBackground(Color.blue);
        super.setOpaque(true);
        setStatus(status);
    }
    
    public void setStatus(Status newS){
        status = newS;
        switch(status){
            case MISSING:
                super.setIcon(null);
                super.setBackground(Color.white);
                break;
            case VISIBLE:
                super.setIcon(picture);
                super.setBackground(Color.blue);
                break;
            case HIDDEN:
                super.setBackground(Color.blue);
                super.setIcon(null);
                break;
        }
    }
    public Status getStatus(){
        return status;
    }
    public Card copy(){
        return new Card(picture, status);
    }
    public boolean equalIcon(Card c){
        return c.equalIcon(picture);
    }
    public boolean equalIcon(Icon i){
        return i.equals(picture);
    }
    
    
    public static void main(String[] args){
        Status s1 = Status.HIDDEN;
        Status s2 = s1;
        s1 = Status.VISIBLE;
        System.out.println(s2.toString());
    }

}
