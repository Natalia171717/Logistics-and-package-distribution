package aed;
import java.util.Comparator;

public class ComparadorSuperavit implements Comparator<Ciudad>{
    @Override
    public int compare(Ciudad c1, Ciudad c2){
        int id1 = c1.obtenerId();
        int id2 = c2.obtenerId();
        int comparacion = Integer.compare(c1.obtenerSuperavit(), c2.obtenerSuperavit());
        if (comparacion == 0){
            if (id1 > id2){
                comparacion = 1;
            }
            else{
                comparacion = -1;
            }
        }
        return comparacion;
    }
}