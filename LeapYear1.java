import static javax.swing.JOptionPane.*;

public class LeapYear1{
    public static void main (String[] args) {
        String input 
        while((input= showInputDialog("Skriv ett årtal")) != null){
            int year = Integer.parseInt(input);
            
            if(year%4==0 && year%100!=0 && year%400==0){
                showMessageDialog(null,"Skottår");
            }
            else{
                showMessageDialog(null,"Inte skottår");
            }
        }
    }
}