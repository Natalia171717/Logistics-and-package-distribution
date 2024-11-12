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
        while(i < n && i < trasladosRedituables.obtenerTamano()){ //¿Esto funciona en vez de hacer 2 for, uno por si n se pasa del tamaño del heap y otro por si no?
            traslado = trasladosRedituables.desencolarTraslado();
            res[i] = traslado.obtenerId();
            origen  = traslado.obtenerOrigen();
            destino = traslado.obtenerDestino();
            ganancia = traslado.obtenerGananciaNeta();

            //Aumento ganancias y pérdidas correspondientes
            ciudades[origen].aumentarGanancia(ganancia);
            ciudades[destino].aumentarPerdida(ganancia);

            //Actualizo heap de superavit en dos pasos, primero para origen y luego para destino
            ciudadesSuperavit.cambiarPrioridadCiudad(ciudades[origen], ciudades[origen].obtenerSuperavit() + ganancia);
            ciudadesSuperavit.cambiarPrioridadCiudad(ciudades[destino], ciudades[destino].obtenerSuperavit() - ganancia);

            //Actualizo arrays idCiudadesMayorGanancia e idCiudadesMayorPerdida
            actualizarCiudadesMayorGanancia(origen, traslado);
            actualizarCiudadesMayorPerdida(destino, traslado);

            cantDespachados++;
            gananciaTotal += ganancia;
            
            i++;
        }
        return res;
    }

    private void actualizarCiudadesMayorGanancia(int origen, Traslado traslado){
        if (!idCiudadesMayorGanancia.isEmpty()){
            if (ciudades[origen].obtenerGanancia() > ciudades[idCiudadesMayorGanancia.get(0)].obtenerGanancia()){
                ArrayList<Integer> nuevo = new ArrayList<Integer>();
                nuevo.add(traslado.obtenerId());
                idCiudadesMayorGanancia = nuevo;
            }
            else if (ciudades[origen].obtenerGanancia() == ciudades[idCiudadesMayorGanancia.get(0)].obtenerGanancia()){
                idCiudadesMayorGanancia.add(origen);
            }
        }
        else{
            idCiudadesMayorGanancia.add(origen);
        }
    }

    private void actualizarCiudadesMayorPerdida(int destino, Traslado traslado){
        if (!idCiudadesMayorPerdida.isEmpty()){
            if (ciudades[destino].obtenerPerdida() > ciudades[idCiudadesMayorPerdida.get(0)].obtenerPerdida()){
                ArrayList<Integer> nuevo = new ArrayList<Integer>();
                nuevo.add(traslado.obtenerId());
                idCiudadesMayorPerdida = nuevo;
            }
            else if (ciudades[destino].obtenerPerdida() == ciudades[idCiudadesMayorPerdida.get(0)].obtenerPerdida()){
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

