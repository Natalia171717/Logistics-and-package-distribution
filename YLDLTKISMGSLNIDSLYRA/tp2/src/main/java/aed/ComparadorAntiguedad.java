package aed;
import java.util.Comparator;

public class ComparadorAntiguedad implements Comparator<Traslado>{
    @Override
    public int compare(Traslado t1, Traslado t2){ 
        int comparacion = Integer.compare(t1.obtenerTimestamp(), t2.obtenerTimestamp());
        return ((comparacion * (-1)));
    }
}