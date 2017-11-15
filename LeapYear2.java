import static javax.swing.JOptionPane.*;

public class LeapYear2 {
    public static void main (String[] args) {
       String input;
       while((input = showInputDialog("Skriv ett datum på formen åååå-mm-dd")) != null){
           int day = Integer.parseInt(input.substring(8,10));
           int month = Integer.parseInt(input.substring(5,7));
           int year = Integer.parseInt(input.substring(0,4));
           int n = 0;
           
           for(int i=1;month>i;i++) {
               int m;
               if(i==1 || i==3 || i==5 || i==7 || i==8 || i==10){
                   m=31;
               }
               else if(i==2){
                   m=28;
               }
               else{
                   m=30;
               }
               n = n + m;
           } 
           n = n + day;
           if(year%4==0 && (year%100!=0 || year%400==0) && month>2){
               n=n+1;
           }
            showMessageDialog(null,"Dagens nummer är " + n);
       }
    }
}
