package aed;

public class Traslado {
    
    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;
    private int[] handler; //handle[0] es la posición en HeapRedituable y handle[1] es la posición en HeapAntiguos

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
        this.handler = new int[2];
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

    public int[] obtenerHandler(){
        int[] copia = new int[2];
        copia[0] = handler[0];
        copia[1] = handler[1];
        return copia;
    }

    public void modificarHandlerRedituable(int posRedituable){
        handler[0] = posRedituable;
    }

    public void modificarHandlerAntiguo(int posAntiguo){
        handler[1] = posAntiguo;
    }
    
}
