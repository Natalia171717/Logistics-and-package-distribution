package aed;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DespacharMasRedituablesTests {

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
        traslados[0] = new Traslado(1, 1, 2, 1000, 2023); //Igual ganancia que el siguiente
        traslados[1] = new Traslado(2, 4, 3, 1000, 2022); //Igual ganancia que el anterior
        traslados[2] = new Traslado(3, 3, 2, 200, 2021);
        traslados[3] = new Traslado(4, 3, 2, 1800, 2020);
        traslados[4] = new Traslado(5, 4, 6, 2900, 2019);
        traslados[5] = new Traslado(6, 5, 6, 3500, 2018); //Mayor ganancia
        traslados[6] = new Traslado(7, 3, 5, 650, 2017);
        traslados[7] = new Traslado(8, 1, 0, 2, 2016); //Menor ganancia
        traslados[8] = new Traslado(9, 7, 1, 925, 2015); //Igual ganancia que el siguiente
        traslados[9] = new Traslado(10, 5, 6, 925, 2014); //Igual ganancia que el anterior

        // Inicializo BestEffort
        sistema = new BestEffort(8, traslados);
    }

    // Tests para DespacharMasRedituables(int n)
    @Test
    public void testNestaEnRango() {
        assertArrayEquals(new int[]{6, 5, 4, 1}, sistema.despacharMasRedituables(4), "Despacho los 4 más redituables");
    }

    @Test
    public void testNesCero() {
        assertArrayEquals(new int[]{}, sistema.despacharMasRedituables(0), "Despacho los 0 más redituables");
    }

    @Test
    public void testNesMayorALongitudTraslados() {
        assertArrayEquals(new int[]{6, 5, 4, 1, 2, 9, 10, 7, 3, 8}, sistema.despacharMasRedituables(89), "Despacho toda la lista porque la cantidad que me piden es mayor");;
    }

    @Test
    public void testNesIgualALongitudTraslados() {
        assertArrayEquals(new int[]{6, 5, 4, 1, 2, 9, 10, 7, 3, 8}, sistema.despacharMasRedituables(10), "Despacho toda la lista porque la cantidad que me piden es su longitud");
    }

    @Test
    public void testTrasladosVacios() {
        sistema.despacharMasRedituables(10);
        assertArrayEquals(new int[]{}, sistema.despacharMasRedituables(3), "No hay nada que despachar");
    }
}