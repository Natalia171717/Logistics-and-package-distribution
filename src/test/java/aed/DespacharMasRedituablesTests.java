package aed;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DespacharMasRedituablesTests {

    private HeapSobreArrayList<Traslado> colaPrioridadRedituable;
    private HeapSobreArrayList<Ciudad> colaPrioridadSuperavit;
    private ComparadorRedituable comparadorRedituable;
    private ComparadorSuperavit comparadorSuperavit;
    private Traslado[] traslados;
    private ArrayList<Ciudad> ciudades;
    private BestEffort sistema;

/*
Lista de cosas que quiero testear:
n es cero LISTO
n es menor que la cantidad de traslados LISTO
n es igual que la cantidad de traslados LISTO
n es mayor que la cantidad de traslados LISTO
despacho algo de una cola de prioridad vacía LISTO
que res sea en todos efectivamente la lista de los n traslados más redituables, claramente.
probar jerarquía de traslados con igual ganancia para ver qué hace con los ids
*/
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
        traslados[5] = new Traslado(6, 8, 6, 3500, 2018); //Mayor ganancia
        traslados[6] = new Traslado(7, 3, 5, 650, 2017);
        traslados[7] = new Traslado(8, 1, 8, 2, 2016); //Menor ganancia
        traslados[8] = new Traslado(9, 7, 1, 925, 2015); //Igual ganancia que el siguiente
        traslados[9] = new Traslado(10, 5, 6, 925, 2014); //Igual ganancia que el anterior

        // Inicializo BestEffort
        sistema = new BestEffort(8, traslados);
    }

    // Tests para DespacharMasRedituables(int n)
    @Test
    public void testNestaEnRango() {
        assertArrayEquals(new int[]{6, 5, 4, 1}, sistema.despacharMasRedituables(4), "Despacho los 4 más redituables. Espero obtener la siguiente lista de ids: [6, 5, 4, 1]");
    }

    @Test
    public void testNesCero() {
        assertArrayEquals(new int[]{}, sistema.despacharMasRedituables(0), "Despacho los 0 más redituables. Espero obtener la siguiente lista de ids: []");
    }

    @Test
    public void testNesMayorALongitudTraslados() {
        assertArrayEquals(new int[]{6, 5, 4, 1, 2, 9, 10, 7, 3, 8}, sistema.despacharMasRedituables(89), "Despacho toda la lista porque la cantidad que me piden es mayor. Espero obtener la siguiente lista de ids: [6, 5, 4, 1, 2, 9, 10, 7, 3, 8]");;
    }

    @Test
    public void testNesIgualALongitudTraslados() {
        assertArrayEquals(new int[]{6, 5, 4, 1, 2, 9, 10, 7, 3, 8}, sistema.despacharMasRedituables(10), "Despacho toda la lista porque la cantidad que me piden es su longitud. Espero obtener la siguiente lista de ids: [6, 5, 4, 1, 2, 9, 10, 7, 3, 8]");
    }

    @Test
    public void testTrasladosVacios() {
        assertArrayEquals(new int[]{}, sistema.despacharMasRedituables(3), "No hay nada que despachar. Espero obtener la siguiente lista de ids: []");
    }
}