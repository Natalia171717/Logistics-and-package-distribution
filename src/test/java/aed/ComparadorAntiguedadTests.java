package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComparadorAntiguedadTests {

    private ComparadorAntiguedad comparador;
    private Traslado trasladoReciente;
    private Traslado trasladoAntiguo;
    private Traslado trasladoIgual1;
    private Traslado trasladoIgual2;

    @BeforeEach
    public void setUp() {
        comparador = new ComparadorAntiguedad();

        // Creamos traslados con diferentes timestamps
        trasladoReciente = new Traslado(0,1,4,5000,2023);  // Timestamp más reciente
        trasladoAntiguo = new Traslado(0,1,4,5000,2021);   // Timestamp más antiguo
        trasladoIgual1 = new Traslado(0,1,4,5000,2022);    // Timestamp igual entre dos traslados
        trasladoIgual2 = new Traslado(0,1,4,5000,2022);
    }

    @Test
    public void testCompare_TrasladoRecienteEsMayor() {
        int resultado = comparador.compare(trasladoReciente, trasladoAntiguo);
        assertTrue(resultado < 0, "El traslado más reciente debería ser menor en orden inverso de antigüedad");
    }

    @Test
    public void testCompare_TrasladoAntiguoEsMenor() {
        int resultado = comparador.compare(trasladoAntiguo, trasladoReciente);
        assertTrue(resultado > 0, "El traslado más antiguo debería ser mayor en orden inverso de antigüedad");
    }

    @Test
    public void testCompare_TrasladosIguales() {
        int resultado = comparador.compare(trasladoIgual1, trasladoIgual2);
        assertEquals(0, resultado, "Los traslados con el mismo timestamp deberían ser considerados iguales");
    }
}
