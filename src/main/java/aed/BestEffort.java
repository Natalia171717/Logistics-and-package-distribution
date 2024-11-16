package aed;

import java.util.ArrayList;

public class BestEffort {
    private Ciudad[] ciudades;
    private ArrayList<Integer> idCiudadesMayorGanancia;
    private ArrayList<Integer> idCiudadesMayorPerdida;
    private HeapSobreArrayList<Traslado> trasladosRedituables;
    private HeapSobreArrayList<Traslado> trasladosAntiguos;
    private HeapSobreArrayList<Ciudad> ciudadesSuperavit;
    private int gananciaTotal; 
    private int cantDespachados;
    private ComparadorRedituable comparadorRedituable;
    private ComparadorAntiguedad comparadorAntiguedad;
    private ComparadorSuperavit comparadorSuperavit; 

    //Observación sobre complejidades: encolar, eliminar y desencolar tienen complejidad O(log(n)). Buscar en este heap es O(1) gracias a los handlers.
    public BestEffort(int cantCiudades, Traslado[] traslados){
        
        comparadorRedituable = new ComparadorRedituable();
        comparadorAntiguedad = new ComparadorAntiguedad();
        comparadorSuperavit = new ComparadorSuperavit();
    
        idCiudadesMayorGanancia = new ArrayList<Integer>(); //O(1)
        idCiudadesMayorPerdida = new ArrayList<Integer>();  //O(1)
        
        gananciaTotal = 0;   
        cantDespachados = 0; 

    //Creo dos arrayList para almacenar los traslados y no tener aliasing con el arreglo recibido por parametro ni entre los dos heaps
        ArrayList<Traslado> listaRedituables = new ArrayList<Traslado>(); //O(1)
        ArrayList<Traslado> listaAntiguos = new ArrayList<Traslado>(); //O(1)
        Traslado traslado;

        for (int i=0; i<traslados.length; i++){     //O(|T|)
            traslado = new Traslado(traslados[i]);  //O(1)
            traslado.modificarHandlerRedituable(i); //O(1)
            traslado.modificarHandlerAntiguo(i);    //O(1)
            listaAntiguos.add(traslado);           //O(1)
            listaRedituables.add(traslado);           //O(1)
        }//Todo este bloque es de complejidad O(|T|), ya que se hacen T veces operaciones de complejidad O(1)

        trasladosRedituables = new HeapSobreArrayList<Traslado>(comparadorRedituable, listaRedituables); //O(|T|) porque hace heapify
        trasladosAntiguos = new HeapSobreArrayList<Traslado>(comparadorAntiguedad, listaAntiguos);    //O(|T|) porque hace heapify

        ciudades = new Ciudad[cantCiudades];    //O(|C|)
        //Creo ArrayList para inicializar mi heap de ciudadesSuperavit
        ArrayList<Ciudad> ciudadesLista = new ArrayList<Ciudad>();  //O(1)

        for (int i=0; i<cantCiudades; i++){ //O(|C|)
            Ciudad nueva = new Ciudad(i);   //O(1)
            ciudadesLista.add(nueva); 
            nueva.modificarHandler(i); //O(1)  //Inicializo el handler que se usará en el heap
            ciudades[i] = nueva;
        }//Todo este bloque es de complejidad O(|C|), ya que se hacen C veces operaciones de complejidad O(1) 

        ciudadesSuperavit = new HeapSobreArrayList<Ciudad>(comparadorSuperavit, ciudadesLista); //O(1) porque para tipo ciudad no hace heapify

    }//nuevoSistema (constructor BestEffort) es de complejidad O(|T|+|T|+|T|+|C|+|C|)= O(|T|+|C|)

    
    public void registrarTraslados(Traslado[] traslados){
        for (int i = 0; i < traslados.length; i++){ //O(|traslados|(log|T|))
            Traslado nuevoTraslado = new Traslado(traslados[i]);
            trasladosAntiguos.encolar(nuevoTraslado); //O(log|T|)
	        trasladosRedituables.encolar(nuevoTraslado); //O(log|T|)
        }
    }

    public int[] despacharMasRedituables(int n){
        int tamaño = trasladosRedituables.obtenerTamano();
        int[] res;
        if(tamaño<=0){
            res = new int[0];
        }
        else{
            int i = 0;
            Traslado traslado;
            int origen;
            int destino;
            int ganancia;
            if (n>tamaño){
                n=tamaño;
            }
            res = new int[n];

            //Despacho los n traslados más redituables y realizo las actualizaciones correspondientes
            while(i < n){
                traslado = trasladosRedituables.desencolarTraslado(); //Esto es O(log(|T|))
                trasladosAntiguos.eliminarTraslado(traslado); 
                //Todo esto es O(1)
                res[i] = traslado.obtenerId();
                origen  = traslado.obtenerOrigen();
                destino = traslado.obtenerDestino();
                ganancia = traslado.obtenerGananciaNeta();

                //Actualizo arrays idCiudadesMayorGanancia e idCiudadesMayorPerdida primero para evitar agregar repetidos
                actualizarCiudadesMayorGanancia(origen, ciudades[origen].obtenerGanancia() + ganancia); //O(1)
                actualizarCiudadesMayorPerdida(destino, ciudades[destino].obtenerPerdida() + ganancia); //O(1)

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
        if(n > trasladosAntiguos.obtenerTamano() || trasladosAntiguos.obtenerTamano()==0){ //O(1)
            n=trasladosAntiguos.obtenerTamano();
        }
        int[] res= new int[n]; // creo el array donde se guardan los despachos O(1)
        for(int i=0; i<n ; i++){ //O(n(log |T|+log|C|))
            //despacho el traslado más antiguo y guardo los datos de las ciudades involucradas 
            Traslado trasladoMasAntiguo = trasladosAntiguos.desencolarTraslado(); //O(log|T|)
            Ciudad ciudadOrigen = ciudades[trasladoMasAntiguo.obtenerOrigen()]; //O(1)
            Ciudad ciudadDestino = ciudades[trasladoMasAntiguo.obtenerDestino()]; //O(1)
            int gananciaNeta = trasladoMasAntiguo.obtenerGananciaNeta(); //O(1)

            //elimino el traslado despachado de trasladosRedituables y agrego el id a res 
            trasladosRedituables.eliminarTraslado(trasladoMasAntiguo); //O(log |T|)
            res[i]= trasladoMasAntiguo.obtenerId(); //O(1)

            //Actualizo las estadisticas de las ciudades incolucradas en el traslado 
            //Actualizo arrays idCiudadesMayorGanancia e idCiudadesMayorPerdida primero para evitar agregar repetidos
            actualizarCiudadesMayorGanancia(ciudadOrigen.obtenerId(), ciudades[trasladoMasAntiguo.obtenerOrigen()].obtenerGanancia() + gananciaNeta); //O(1)
            actualizarCiudadesMayorPerdida(ciudadDestino.obtenerId(), ciudades[trasladoMasAntiguo.obtenerDestino()].obtenerPerdida() + gananciaNeta); //O(1)
            
            ciudadOrigen.aumentarGanancia(gananciaNeta); //O(1)
            ciudadDestino.aumentarPerdida(gananciaNeta); //O(1)

            //Ordeno ciudadesSuperavit 
            ciudadesSuperavit.cambiarPrioridadCiudad(ciudadDestino, ciudadDestino.obtenerGanancia()-ciudadDestino.obtenerPerdida()); //O(log|C|)
            ciudadesSuperavit.cambiarPrioridadCiudad(ciudadOrigen, ciudadOrigen.obtenerGanancia()-ciudadOrigen.obtenerPerdida()); //O(log|C|)
            
            //Actualizo las estadisticas globales 
            gananciaTotal+=gananciaNeta; //O(1)
            cantDespachados+=1; //O(1)
        }
        //Complejidad total: O(n (log(|C|) + log(|T|)))
        return res; 
    }

    public int ciudadConMayorSuperavit(){ //O(1)
        Ciudad resCiudad = (Ciudad) ciudadesSuperavit.verRaiz(); //O(1)
        int res = resCiudad.obtenerId();
        return res;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){ //O(1)
        return idCiudadesMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){ //O(1)
        return idCiudadesMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){ //O(1)
        return (gananciaTotal / cantDespachados);
    }
}

