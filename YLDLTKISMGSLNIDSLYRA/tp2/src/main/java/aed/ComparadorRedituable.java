package aed;
import java.util.Comparator;

public class ComparadorRedituable implements Comparator<Traslado>{
    @Override
    public int compare(Traslado t1, Traslado t2){
        int id1 = t1.obtenerId();
        int id2 = t2.obtenerId();
        int comparacion = Integer.compare(t1.obtenerGananciaNeta(), t2.obtenerGananciaNeta());
        if (comparacion == 0){
            if (id1 < id2){
                comparacion = 1;
            }
            else{
                comparacion = -1;
            }
        }
        return comparacion;
    }
}