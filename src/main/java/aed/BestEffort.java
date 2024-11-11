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
        return null;
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

