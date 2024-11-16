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

    //Observación sobre complejidades: encolar, eliminar y desencolar tienen complejidad O(log(n)) porque están implementadas sobre un heap 
    //Buscar en este heap es O(1) gracias a los handlers.

    public BestEffort(int cantCiudades, Traslado[] traslados){  //O(|T|+|C|)
        
        comparadorRedituable = new ComparadorRedituable();
        comparadorAntiguedad = new ComparadorAntiguedad();
        comparadorSuperavit = new ComparadorSuperavit();
    
        idCiudadesMayorGanancia = new ArrayList<Integer>(); 
        idCiudadesMayorPerdida = new ArrayList<Integer>();  
        
        gananciaTotal = 0;   
        cantDespachados = 0; 

    //Creamos dos arrayList para almacenar los traslados y no tener aliasing con el arreglo recibido por parametro ni entre las dos colas de prioridad
        ArrayList<Traslado> listaRedituables = new ArrayList<Traslado>(); 
        ArrayList<Traslado> listaAntiguos = new ArrayList<Traslado>(); 
        Traslado traslado;

        for (int i=0; i<traslados.length; i++){     //O(|T|)
            traslado = new Traslado(traslados[i]);  //O(1)
            traslado.modificarHandlerRedituable(i); //O(1)
            traslado.modificarHandlerAntiguo(i);    //O(1)
            listaAntiguos.add(traslado);           
            listaRedituables.add(traslado);         
        }

        trasladosRedituables = new HeapSobreArrayList<Traslado>(comparadorRedituable, listaRedituables); //O(|T|) porque hace heapify
        trasladosAntiguos = new HeapSobreArrayList<Traslado>(comparadorAntiguedad, listaAntiguos);       //O(|T|) porque hace heapify

        ciudades = new Ciudad[cantCiudades];    //O(|C|)

        //Creamos ArrayList para inicializar la cola de prioridad de ciudadesSuperavit
        ArrayList<Ciudad> ciudadesLista = new ArrayList<Ciudad>(); 

        for (int i=0; i<cantCiudades; i++){ //O(|C|)
            Ciudad nueva = new Ciudad(i);   //O(1)
            ciudadesLista.add(nueva); 
            nueva.modificarHandler(i); //O(1)  //Inicializamos la posicion en ciudadesSuperavit
            ciudades[i] = nueva;
        } 

        ciudadesSuperavit = new HeapSobreArrayList<Ciudad>(comparadorSuperavit, ciudadesLista); //O(1) porque para tipo ciudad no hace heapify
    }

    
    public void registrarTraslados(Traslado[] traslados){   //O(|traslados|(log|T|))
        for (int i = 0; i < traslados.length; i++){ //O(|traslados|(log|T|))
            Traslado nuevoTraslado = new Traslado(traslados[i]);    //O(1)
            trasladosAntiguos.encolar(nuevoTraslado);    //O(log|T|)
	        trasladosRedituables.encolar(nuevoTraslado); //O(log|T|)
        }
    }

    public int[] despacharMasRedituables(int n){    //O(n (log(|C|) + log(|T|)))
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
            res = new int[n];   //O(n)

            //Despachamos los n traslados más redituables y realizamos las actualizaciones correspondientes
            while(i < n){   //O(n (log(|C|) + log(|T|)))
                traslado = trasladosRedituables.desencolarTraslado(); //O(log(|T|))
                trasladosAntiguos.eliminarTraslado(traslado);         //O(log|T|)

                res[i] = traslado.obtenerId();
                origen  = traslado.obtenerOrigen();
                destino = traslado.obtenerDestino();
                ganancia = traslado.obtenerGananciaNeta();

                //Actualizamos arrays idCiudadesMayorGanancia e idCiudadesMayorPerdida primero, para evitar agregar repetidos
                actualizarCiudadesMayorGanancia(origen, ciudades[origen].obtenerGanancia() + ganancia); //O(1)
                actualizarCiudadesMayorPerdida(destino, ciudades[destino].obtenerPerdida() + ganancia); //O(1)

                //Aumentamos ganancias y pérdidas correspondientes
                ciudades[origen].aumentarGanancia(ganancia); //O(1)
                ciudades[destino].aumentarPerdida(ganancia); //O(1)

                //Actualizamos ciudadesSuperavit en dos pasos, primero para origen y luego para destino
                ciudadesSuperavit.cambiarPrioridadCiudad(ciudades[origen], ciudades[origen].obtenerSuperavit() + ganancia);   //O(log |C|)
                ciudadesSuperavit.cambiarPrioridadCiudad(ciudades[destino], ciudades[destino].obtenerSuperavit() - ganancia); //O(log |C|)

                cantDespachados++;
                gananciaTotal += ganancia;
                
                i++;
            }
        }
        return res;
    }

    public int[] despacharMasAntiguos(int n){   //O(n(log |T|+log|C|))
        if(n > trasladosAntiguos.obtenerTamano() || trasladosAntiguos.obtenerTamano()==0){ 
            n=trasladosAntiguos.obtenerTamano();
        }
        int[] res= new int[n]; // creamos el array donde se guardan los despachos O(n)

        for(int i=0; i<n ; i++){ //O(n(log |T|+log|C|))
            //despachamos el traslado más antiguo y guardamos los datos de las ciudades involucradas 
            Traslado trasladoMasAntiguo = trasladosAntiguos.desencolarTraslado(); //O(log|T|)
            Ciudad ciudadOrigen = ciudades[trasladoMasAntiguo.obtenerOrigen()];
            Ciudad ciudadDestino = ciudades[trasladoMasAntiguo.obtenerDestino()]; 
            int gananciaNeta = trasladoMasAntiguo.obtenerGananciaNeta(); 

            //eliminamos el traslado despachado de trasladosRedituables y agregamos el id a res 
            trasladosRedituables.eliminarTraslado(trasladoMasAntiguo); //O(log |T|)
            res[i]= trasladoMasAntiguo.obtenerId(); 

            //Actualizamos las estadisticas de las ciudades incolucradas en el traslado 
            //Actualizamos arrays idCiudadesMayorGanancia e idCiudadesMayorPerdida primero, para evitar agregar repetidos
            actualizarCiudadesMayorGanancia(ciudadOrigen.obtenerId(), ciudades[trasladoMasAntiguo.obtenerOrigen()].obtenerGanancia() + gananciaNeta); //O(1)
            actualizarCiudadesMayorPerdida(ciudadDestino.obtenerId(), ciudades[trasladoMasAntiguo.obtenerDestino()].obtenerPerdida() + gananciaNeta); //O(1)
            
            ciudadOrigen.aumentarGanancia(gananciaNeta); 
            ciudadDestino.aumentarPerdida(gananciaNeta); 

            //Ordenamos ciudadesSuperavit 
            ciudadesSuperavit.cambiarPrioridadCiudad(ciudadDestino, ciudadDestino.obtenerGanancia()-ciudadDestino.obtenerPerdida()); //O(log|C|)
            ciudadesSuperavit.cambiarPrioridadCiudad(ciudadOrigen, ciudadOrigen.obtenerGanancia()-ciudadOrigen.obtenerPerdida());    //O(log|C|)
            
            //Actualizamos las estadisticas globales 
            gananciaTotal+=gananciaNeta; 
            cantDespachados+=1; 
        }
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

    private void actualizarCiudadesMayorGanancia(int origen, int nuevaGananciaOrigen){  //O(1)
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

    private void actualizarCiudadesMayorPerdida(int destino, int nuevaPerdidaDestino){  //O(1)
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
}