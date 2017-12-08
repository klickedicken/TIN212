import static java.lang.Math.*;

public class RatNum{
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
    //Instansvariabler
    private int m;
    private int n;
    //Konstruktor
    public RatNum(){
        m = 0;
        n = 1;
    }
    public RatNum(int a){
        m = a;
        n = 1;
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
    //Instansmetoder
    public int getNumerator(){
        return m;
    }
    public int getDenominator(){
        return n;
    }
}

