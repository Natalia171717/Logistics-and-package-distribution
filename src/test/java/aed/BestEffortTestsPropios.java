package aed;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BestEffortTestsPropios {
    int cantCiudades;
    Traslado[] listaTraslados;

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
    assertEquals(s1.size(), s2.size());
    for (int e1 : s1) {
        boolean encontrado = false;
        for (int e2 : s2) {
            if (e1 == e2) encontrado = true;
        }
        assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
    }
    
    }
    @BeforeEach
    void init(){
        cantCiudades = 10;
        listaTraslados = new Traslado[] {};
    }
    @Test
    void stress(){
        BestEffort sistema = new BestEffort(this.cantCiudades, this.listaTraslados);

        assertArrayEquals(new int[]{}, sistema.despacharMasRedituables(4));
        assertArrayEquals(new int[]{}, sistema.despacharMasAntiguos(4));
        assertEquals(0, sistema.ciudadesConMayorGanancia().size());


        Traslado[] traslados = new Traslado[] {
        new Traslado(1, 0, 1, 100, 10),
        new Traslado(2, 0, 1, 400, 20),
        new Traslado(3, 3, 4, 500, 50),
        new Traslado(4, 4, 3, 500, 11),
        new Traslado(5, 1, 0, 1000, 40),
        new Traslado(6, 1, 9, 1000, 41),
        new Traslado(7, 6, 3, 2000, 42),
        new Traslado(8, 9, 3, 300000, 30),
        new Traslado(9, 1, 5, 200, 15),
        new Traslado(10, 5, 3, 2200, 9)};

        sistema.registrarTraslados(traslados);

        traslados[0].modificarHandlerAntiguo(1000);
        traslados[5].modificarHandlerRedituable(-28);

        sistema.despacharMasAntiguos(1);
        sistema.despacharMasRedituables(1);
        sistema.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(9)), sistema.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sistema.ciudadesConMayorPerdida());
        assertEquals(9, sistema.ciudadConMayorSuperavit());

        sistema.despacharMasRedituables(1);
        sistema.despacharMasAntiguos(1);
        sistema.despacharMasRedituables(1);

        traslados[8].modificarHandlerAntiguo(700);
        traslados[2].modificarHandlerRedituable(-69);

        sistema.despacharMasAntiguos(1);
        sistema.despacharMasRedituables(1);
        sistema.despacharMasAntiguos(1);
        sistema.despacharMasRedituables(2);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(9)), sistema.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sistema.ciudadesConMayorPerdida());
        assertEquals(9, sistema.ciudadConMayorSuperavit());

        assertEquals(30790, sistema.gananciaPromedioPorTraslado());
    }
}