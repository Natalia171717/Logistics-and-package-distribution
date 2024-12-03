package aed;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapSobreArrayListTests {

    private HeapSobreArrayList<Traslado> colaPrioridadAntiguedad;
    private HeapSobreArrayList<Traslado> colaPrioridadRedituable;
    private HeapSobreArrayList<Ciudad> colaPrioridadSuperavit;
    private ComparadorAntiguedad comparadorAntiguedad;
    private ComparadorRedituable comparadorRedituable;
    private ComparadorSuperavit comparadorSuperavit;
    private ArrayList<Traslado> traslados;
    private ArrayList<Ciudad> ciudades;

    @BeforeEach
    public void init() {
        // Inicializamos comparadores
        comparadorAntiguedad = new ComparadorAntiguedad();
        comparadorRedituable = new ComparadorRedituable();
        comparadorSuperavit = new ComparadorSuperavit();

        // Creamos una lista de traslados para las pruebas
        traslados = new ArrayList<>();
        traslados.add(new Traslado(1, 100, 200, 1500, 2023));
        traslados.add(new Traslado(2, 101, 201, 1000, 2022));
        traslados.add(new Traslado(3, 102, 202, 2000, 2021));

        // Creamos una lista de ciudades para las pruebas
        ciudades = new ArrayList<>();
        Ciudad ciudad1 = new Ciudad(0); ciudad1.modificarSuperavit(4000);
        Ciudad ciudad2 = new Ciudad(1); ciudad2.modificarSuperavit(6000);
        Ciudad ciudad3 = new Ciudad(2); ciudad3.modificarSuperavit(5000);
        Ciudad ciudad4 = new Ciudad(3); ciudad4.modificarSuperavit(5000); // Igual superávit, mayor ID
        ciudades.add(ciudad1);
        ciudades.add(ciudad2);
        ciudades.add(ciudad3);
        ciudades.add(ciudad4);

        // Inicializamos los heaps con los comparadores respectivos
        colaPrioridadAntiguedad = new HeapSobreArrayList<>(comparadorAntiguedad, traslados);
        colaPrioridadRedituable = new HeapSobreArrayList<>(comparadorRedituable, traslados);
        colaPrioridadSuperavit = new HeapSobreArrayList<>(comparadorSuperavit, ciudades);
    }

    // Tests para ComparadorAntiguedad
    @Test
    public void testEncolarTrasladoPorAntiguedad() {
        Traslado nuevoTraslado = new Traslado(4, 103, 203, 2500, 2020); // El más antiguo ahora
        colaPrioridadAntiguedad.encolar(nuevoTraslado);
        assertEquals(4, colaPrioridadAntiguedad.obtenerTamano(), "El tamaño del heap debería ser 4 después de encolar");
        assertEquals(nuevoTraslado, colaPrioridadAntiguedad.verRaiz(), "El traslado más antiguo debería estar en la raíz");
    }

    @Test
    public void testDesencolarTrasladoPorAntiguedad() {
        Traslado trasladoDesencolado = colaPrioridadAntiguedad.desencolarTraslado();
        assertEquals(2021, trasladoDesencolado.obtenerTimestamp(), "El traslado más antiguo debería ser desencolado primero");
        assertEquals(2, colaPrioridadAntiguedad.verRaiz().obtenerId(), "ver que el siguiente traslado con menor timestamp sea ahora la raíz");
        assertEquals(2, colaPrioridadAntiguedad.obtenerTamano(), "El tamaño del heap debería reducirse después de desencolar");
    }

    // Tests para ComparadorRedituable
    @Test
    public void testEncolarTrasladoPorRedituable() {
        Traslado nuevoTraslado = new Traslado(4, 103, 203, 3000, 2024); // Mayor ganancia neta
        colaPrioridadRedituable.encolar(nuevoTraslado);
        assertEquals(4, colaPrioridadRedituable.obtenerTamano(), "El tamaño del heap debería ser 4 después de encolar");
        assertEquals(nuevoTraslado, colaPrioridadRedituable.verRaiz(), "El traslado con mayor ganancia neta debería estar en la raíz");
    }

    @Test
    public void testDesencolarTrasladoPorRedituable() {
        Traslado trasladoDesencolado = colaPrioridadRedituable.desencolarTraslado();
        assertEquals(2000, trasladoDesencolado.obtenerGananciaNeta(), "El traslado con mayor ganancia neta debería ser desencolado primero");
        assertEquals(1, colaPrioridadRedituable.verRaiz().obtenerId(), "ver que el siguiente traslado con mayor ganancia neta sea ahora la raíz");
        assertEquals(2, colaPrioridadRedituable.obtenerTamano(), "El tamaño del heap debería reducirse después de desencolar");
    }

    @Test
    public void testCambiarPrioridadCiudad() {
        Ciudad ciudad = ciudades.get(0); // Ciudad con ID 1, superávit 4000
        colaPrioridadSuperavit.cambiarPrioridadCiudad(ciudad, 6000); // Modificamos el superávit a 6000 para que sea la de mayor prioridad
        assertEquals(6000, colaPrioridadSuperavit.verRaiz().obtenerSuperavit(), "La ciudad con el mayor superávit debería estar en la raíz después de cambiar prioridad");
        assertEquals(ciudad.obtenerId(), colaPrioridadSuperavit.verRaiz().obtenerId(), "En caso de empate de superávit, la ciudad con menor ID debería tener la mayor prioridad por lo que debería estar en la raíz"); 
    }

    @Test
    public void testCambiarPrioridadCiudadDisminuirSuperavit() {
        Ciudad ciudad = ciudades.get(0); // Ciudad con ID 1 y superávit 6000 (actualmente en la raíz)
        colaPrioridadSuperavit.cambiarPrioridadCiudad(ciudad, 2000);// Disminuimos el superávit para que sea menor que el de la otra ciudad
        // Verificamos que ahora una ciudad diferente esté en la raíz
        assertNotEquals(2000, colaPrioridadSuperavit.verRaiz().obtenerSuperavit(), "La ciudad con menor superávit no debería estar en la raíz después de reducir su superávit");
        assertEquals(5000, colaPrioridadSuperavit.verRaiz().obtenerSuperavit(), "La ciudad con el mayor superávit debería estar en la raíz");
        assertEquals(2, colaPrioridadSuperavit.verRaiz().obtenerId(), "En caso de empate de superávit, la ciudad con el menor ID debería estar en la raíz");
    }

    @Test

    public void heapDeNumeritos() {
        Comparator<Integer> comparador = (a, b) -> a.compareTo(b);
        ArrayList<Integer> numeritos = new ArrayList<Integer>();
        Integer heap_size = 11;

        for (Integer i = 0; i < heap_size; i++){
            numeritos.add(i*i);
        }

        HeapSobreArrayList<Integer> heap = new HeapSobreArrayList<Integer> (comparador, numeritos);

        assertEquals(heap_size, heap.obtenerTamano()); 
        assertEquals(100, heap.verRaiz());

    }
}