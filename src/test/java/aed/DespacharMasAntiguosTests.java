package aed;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DespacharMasAntiguosTests {

    private HeapSobreArrayList<Traslado> colaPrioridadRedituable;
    private HeapSobreArrayList<Ciudad> colaPrioridadSuperavit;
    private ComparadorRedituable comparadorRedituable;
    private ComparadorSuperavit comparadorSuperavit;
    private Traslado[] traslados;
    private ArrayList<Ciudad> ciudades;
    private BestEffort sistema;

    @BeforeEach
    public void init() {
        // Inicializamos comparadores
        comparadorRedituable = new ComparadorRedituable();
        comparadorSuperavit = new ComparadorSuperavit();

        // Creamos una lista de traslados para las pruebas
        //Recuerdo: traslado(id, idOrigen, idDestino, gananciaNeta, timestamp)
        traslados = new Traslado[10];
        traslados[0] = new Traslado(1, 1, 2, 1000, 2023); 
        traslados[1] = new Traslado(2, 4, 3, 1000, 2022); 
        traslados[2] = new Traslado(3, 3, 2, 200, 2021);
        traslados[3] = new Traslado(4, 3, 2, 1800, 2020);
        traslados[4] = new Traslado(5, 4, 6, 2900, 2019);
        traslados[5] = new Traslado(6, 5, 6, 3500, 2018); 
        traslados[6] = new Traslado(7, 3, 5, 650, 2017);
        traslados[7] = new Traslado(8, 1, 5, 2, 2016); 
        traslados[8] = new Traslado(9, 7, 1, 925, 2015); 
        traslados[9] = new Traslado(10, 5, 6, 925, 2014); 

        // Inicializamos BestEffort
        sistema = new BestEffort(8, traslados);
    }

    // Tests para DespacharMasRedituables(int n)
    @Test
    public void testNestaEnRango() {
        assertArrayEquals(new int[]{10, 9, 8, 7}, sistema.despacharMasAntiguos(4), "Despacho los 4 más antiguos");
    }

    @Test
    public void testNesCero() {
        assertArrayEquals(new int[]{}, sistema.despacharMasAntiguos(0), "Despacho los 0 más redituables");
    }

    @Test
    public void testNesMayorALongitudTraslados() {
        assertArrayEquals(new int[]{10, 9,8,7,6,5,4,3,2,1}, sistema.despacharMasAntiguos(89), "Despacho toda la lista");
    }

    @Test
    public void testNesIgualALongitudTraslados() {
        assertArrayEquals(new int[]{10, 9,8,7,6,5,4,3,2,1}, sistema.despacharMasAntiguos(10), "Despacho toda la lista porque la cantidad que me piden es su longitud");
    }

    @Test
    public void testTrasladosVacios() {
        sistema.despacharMasAntiguos(10); 
        assertArrayEquals(new int[]{}, sistema.despacharMasAntiguos(3), "No hay nada que despachar");
    }
}

