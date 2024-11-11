package aed;
import java.util.Comparator;

public class ComparadorRedituable implements Comparator<Traslado>{
    @Override
    public int compare(Traslado t1, Traslado t2){
        return Integer.compare(t1.obtenerGananciaNeta(), t2.obtenerGananciaNeta());
    }
}