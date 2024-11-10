package aed;

public class Traslado {
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;
    int[] handle; //handle[0] es la posición en HeapRedituable y handle[1] es la posición en HeapAntiguos

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
        this.handle = new int[2];
    }

    public int obtenerId(){
        return id; //Agregar test para ver si se cambia por pasar por referencia (no debería porque es tipo primitivo)
    }

    public int obtenerOrigen(){
        return origen; //Agregar test para ver si se cambia por pasar por referencia (no debería porque es tipo primitivo)
    }

    public int obtenerDestino(){
        return destino; //Agregar test para ver si se cambia por pasar por referencia (no debería porque es tipo primitivo)
    }

    public int obtenerGananciaNeta(){
        return gananciaNeta; //Agregar test para ver si se cambia por pasar por referencia (no debería porque es tipo primitivo)
    }

    public int obtenerTimestamp(){
        return timestamp; //Agregar test para ver si se cambia por pasar por referencia (no debería porque es tipo primitivo)
    }

    public int[] obtenerHandle(){
        int[] copia = new int[2];
        copia[0] = handle[0];
        copia[1] = handle[1];
        return copia;
    }

    public void modificarHandle(int posRedituable, int posAntiguos){
        handle[0] = posRedituable;
        handle[1] = posAntiguos;
    }
}
