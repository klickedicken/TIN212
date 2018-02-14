import java.util.*;

public class Tools{
    public static void randomOrder(Object[] list){
        Random r = new Random();
        ArrayList<Object> l = new ArrayList<Object>();
        for (int i = 0; i<list.length;i++){
            l.add(list[i]);
        }
        for (int i=list.length; i>0; i--){
            list[i-1] = l.remove(r.nextInt(i));
        }      
    }
}
