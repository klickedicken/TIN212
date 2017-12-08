public class Euclides{
    //Klassvariabler
    //Klassmetod
    public static int sgd(int m, int n){
        int r = m % n;
        while(r!=0){
            m = n;
            n = r;
            r = m % n;
        }
        return n;
    }
    public static int gcd(int m, int n){
        int r;
        while((r = m % n) != 0){
            m = n;
            n = r;
        }
        return n;
    }
    public static void main(String[] args){
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        System.out.println(sgd(m,n));
    }
    /*    
    public static sgd(int m, int n){
        int r = m % n;
        if(r==0) return n;
        else return sgd(n,r);
    }
    */
}