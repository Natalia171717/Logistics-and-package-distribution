package aed;

import java.util.ArrayList;

public class BestEffort {
    private Ciudad[] ciudades;
    private ArrayList<Integer> idCiudadesMayorGanancia;
    private ArrayList<Integer> idCiudadesMayorPerdida;
    private HeapSobreArrayList trasladosRedituables;
    private HeapSobreArrayList trasladosAntiguos;
    private HeapSobreArrayList ciudadesSuperavit;
    private int gananciaTotal; 
    private int cantDespachados;


    public BestEffort(int cantCiudades, Traslado[] traslados){
        // Implementar - NATI
    }

    public void registrarTraslados(Traslado[] traslados){
        for (int i = 0; i < traslados.length; i++){
            trasladosAntiguos.encolar(traslados[i]);
	        trasladosRedituables.encolar(traslados[i]);
        }
    }

    public int[] despacharMasRedituables(int n){
        // Implementar - VICKY
        int[] res = new int[n];

        int i = 0;
        Traslado traslado;
        int origen;
        int destino;
        int ganancia;

        //Despacho los n traslados más redituables y realizo las actualizaciones correspondientes
        //El while se ejecuta n veces
        while(i < n && i < trasladosRedituables.obtenerTamano()){ //¿Esto funciona en vez de hacer 2 for, uno por si n se pasa del tamaño del heap y otro por si no?
            traslado = trasladosRedituables.desencolarTraslado(); //Esto es O(log(|T|))

            //Todo esto es O(1)
            res[i] = traslado.obtenerId();
            origen  = traslado.obtenerOrigen();
            destino = traslado.obtenerDestino();
            ganancia = traslado.obtenerGananciaNeta();

            //Actualizo arrays idCiudadesMayorGanancia e idCiudadesMayorPerdida primero para evitar agregar repetidos
            actualizarCiudadesMayorGanancia(origen, ciudades[origen].obtenerGanancia() + ganancia); //O(1)
            actualizarCiudadesMayorPerdida(destino, ciudades[destino].obtenerPerdida() - ganancia); //O(1)

            //Aumento ganancias y pérdidas correspondientes
            ciudades[origen].aumentarGanancia(ganancia); //O(1)
            ciudades[destino].aumentarPerdida(ganancia); //O(1)

            //Actualizo heap de superavit en dos pasos, primero para origen y luego para destino
            ciudadesSuperavit.cambiarPrioridadCiudad(ciudades[origen], ciudades[origen].obtenerSuperavit() + ganancia); //O(log |C|)
            ciudadesSuperavit.cambiarPrioridadCiudad(ciudades[destino], ciudades[destino].obtenerSuperavit() - ganancia); //O(log |C|)

            cantDespachados++;
            gananciaTotal += ganancia;
            
            i++;
        }
        //Complejidad total: O(n (log(|C|) + log(|T|)))
        return res;
    }

    private void actualizarCiudadesMayorGanancia(int origen, int nuevaGananciaOrigen){
        if (!idCiudadesMayorGanancia.isEmpty()){ //O(1)
            if (nuevaGananciaOrigen > ciudades[idCiudadesMayorGanancia.get(0)].obtenerGanancia()){ //O(1)
                ArrayList<Integer> nuevo = new ArrayList<Integer>();
                nuevo.add(ciudades[origen].obtenerId());
                idCiudadesMayorGanancia = nuevo;
            }
            else if (nuevaGananciaOrigen == ciudades[idCiudadesMayorGanancia.get(0)].obtenerGanancia()){ //O(1)
                idCiudadesMayorGanancia.add(origen);
            }
        }
        else{
            idCiudadesMayorGanancia.add(origen);
        }
    }

    private void actualizarCiudadesMayorPerdida(int destino, int nuevaPerdidaDestino){
        if (!idCiudadesMayorPerdida.isEmpty()){ //O(1)
            if (nuevaPerdidaDestino > ciudades[idCiudadesMayorPerdida.get(0)].obtenerPerdida()){ //O(1)
                ArrayList<Integer> nuevo = new ArrayList<Integer>();
                nuevo.add(ciudades[destino].obtenerId());
                idCiudadesMayorPerdida = nuevo;
            }
            else if (nuevaPerdidaDestino == ciudades[idCiudadesMayorPerdida.get(0)].obtenerPerdida()){ //O(1)
                idCiudadesMayorPerdida.add(destino);
            }
        }
        else{
            idCiudadesMayorPerdida.add(destino);
        }
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar - DELFI
        return null;
    }

    public int ciudadConMayorSuperavit(){
        Ciudad resCiudad = (Ciudad) ciudadesSuperavit.verRaiz();
        int res = resCiudad.obtenerId();
        return res;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return idCiudadesMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return idCiudadesMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        return (gananciaTotal / cantDespachados);
    }
}

