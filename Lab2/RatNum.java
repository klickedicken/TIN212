import static java.lang.Math.*;
import static java.lang.Integer.parseInt;

public class RatNum{
    public static void main(String[] args){
        System.out.println(parse(args[0]).toString());
    }
    //Klassvariabler
    //Klassmetoder
    public static int sgd(int m, int n){
        int r;
        if(m==0 || n==0) throw new IllegalArgumentException ();
        while((r = m%n)!= 0){
            m = n;
            n = r;
        }
        return abs(n);
    }
    public static RatNum parse(String s){
        String[] b = s.split("/");
        int m;
        int n;
        if (b.length > 2) throw new NumberFormatException();
        else if (b.length == 1) {
            m = parseInt(b[0]);
            n = 1;
        } 
        else {
            m = parseInt(b[0]);
            n = parseInt(b[1]);
        }      
        return new RatNum(m,n);
    }
    //Instansvariabler
    private int m;
    private int n;
    //Konstruktor
    public RatNum(){
        this(0,1);
    }
    public RatNum(int a){
        this(a,1);
    }
    public RatNum(int a, int b){
        if (b == 0) throw new NumberFormatException("Denominator = 0");        
        if(a == 0) {
            m = 0;
            n = 1;
        }
        else {
            int d = sgd(a,b);
            m = Integer.signum(a)*Integer.signum(b)* abs(a) / d;
            n = abs(b) / d;
        }
    }
    public RatNum(RatNum r){
        m = r.getNumerator();
        n = r.getDenominator();
    }
    public RatNum(String s){
        this(parse(s));
    }
    //Instansmetoder
    public int getNumerator(){
        return m;
    }
    public int getDenominator(){
        return n;
    }
    public String toString(){
        return m + "/" + n;
    }
    public double toDouble(){
        return (double) m/n;
    }
}

