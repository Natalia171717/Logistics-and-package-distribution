package aed;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComparadorRedituableTests {

    private ComparadorRedituable comparador;
    private Traslado trasladoMasRedituable;
    private Traslado trasladoMenosRedituable;
    private Traslado trasladoIgual1;
    private Traslado trasladoIgual2;

    @BeforeEach
    public void init() {
        comparador = new ComparadorRedituable();

        // Creamos traslados con diferentes ganancias netas
        trasladoMasRedituable = new Traslado(0,1,4,5000,10); // Ejemplo de mayor ganancia neta
        trasladoMenosRedituable = new Traslado(0,1,4,2000,10); // Ejemplo de menor ganancia neta
        trasladoIgual1 = new Traslado(0,1,4,3000,10); // Ejemplo con la misma ganancia neta
        trasladoIgual2 = new Traslado(1,1,4,3000,10);
    }

    @Test
    public void testCompare_MasRedituableEsMayor() {
        int resultado = comparador.compare(trasladoMasRedituable, trasladoMenosRedituable);
        assertTrue(resultado > 0, "El traslado con mayor ganancia neta debería ser considerado más redituable");
    }

    @Test
    public void testCompare_MenosRedituableEsMenor() {
        int resultado = comparador.compare(trasladoMenosRedituable, trasladoMasRedituable);
        assertTrue(resultado < 0, "El traslado con menor ganancia neta debería ser considerado menos redituable");
    }

    @Test
    public void testCompare_TrasladosIgualGananciaDesempata_id() {
        int resultado = comparador.compare(trasladoIgual1, trasladoIgual2);
        assertTrue(resultado > 0, "Los traslados con la misma ganancia neta deberían ser considerados igual de redituables");
    }
}
