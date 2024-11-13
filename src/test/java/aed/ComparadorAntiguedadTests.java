package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComparadorAntiguedadTests {

    private ComparadorAntiguedad comparador;
    private Traslado trasladoAntiguo1;
    private Traslado trasladoMasReciente;
    private Traslado trasladoAntiguo2;

    @BeforeEach
    public void init() {
        comparador = new ComparadorAntiguedad();

        trasladoAntiguo1 = new Traslado(0,1,4,5000,2021); // Antiguo
        trasladoMasReciente = new Traslado(0,1,4,5000,2024); // Más reciente
        trasladoAntiguo2 = new Traslado(1,1,4,5000,2021); // Misma antiguedad que el trasladoAntiguo1
    }

    @Test
    public void testCompararTrasladoMenorTimestampMayorPrioridad() {
        int resultado = comparador.compare(trasladoAntiguo1, trasladoMasReciente);
        assertTrue(resultado > 0, "El traslado con menor timestamp debería tener mayor prioridad.");
    }

    @Test
    public void testCompararTrasladoMayorTimestampMenorPrioridad() {
        int resultado = comparador.compare(trasladoMasReciente, trasladoAntiguo1);
        assertTrue(resultado < 0, "El traslado con mayor timestamp debería tener menor prioridad.");
    }

    @Test
    public void testCompararMismoTimestamp() {
        // Dos traslados con el mismo timestamp deberían ser iguales en prioridad
        assertEquals(0, comparador.compare(trasladoAntiguo1, trasladoAntiguo2), "Traslados con el mismo timestamp deberían tener la misma prioridad.");
    }
}
