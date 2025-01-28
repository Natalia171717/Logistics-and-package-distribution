package aed;

public class Traslado {
    
    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;
    private int[] handler; //handler[0] es la posición en trasladosRedituables y handler[1] es la posición en trasladosAntiguos

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){//O(1)
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
        this.handler = new int[2]; //O(1) porque está acotado el tamaño del arreglo (O(2))
    }

    public Traslado(Traslado traslado){ //O(1)
        this.id = traslado.id;
        this.origen = traslado.origen;
        this.destino = traslado.destino;
        this.gananciaNeta = traslado.gananciaNeta;
        this.timestamp = traslado.timestamp;
        this.handler = new int[2];  //O(1) porque está acotado el tamaño del arreglo (O(2))
        for (int i=0; i<this.handler.length; i++){  //O(1) porque está acotada la cant de iteraciones (2 iteraciones siempre)
            this.handler[i] = traslado.handler[i];
        }
    }

    @Override
    public boolean equals(Object otro) { //Lo usamos en testing, su complejidad no afecta a la del sistema
        boolean otroEsNull = (otro == null);
        boolean claseDistinta = otro.getClass() != this.getClass();
        if (otroEsNull || claseDistinta) {
            return false;
        }
        Traslado otroTraslado = (Traslado) otro;
        return (id == otroTraslado.id &&
               origen == otroTraslado.origen &&
               destino == otroTraslado.destino &&
               gananciaNeta == otroTraslado.gananciaNeta &&
               timestamp == otroTraslado.timestamp &&
               handler[0] == otroTraslado.handler[0] &&
               handler[1] == otroTraslado.handler[1]); //No hay aliassing acá porque es un handler de ints
    }

    public int obtenerId(){
        return id; 
    }

    public int obtenerOrigen(){
        return origen; 
    }

    public int obtenerDestino(){
        return destino; 
    }

    public int obtenerGananciaNeta(){
        return gananciaNeta; 
    }

    public int obtenerTimestamp(){
        return timestamp; 
    }

    public int obtenerPosicionRedituable(){
        return handler[0];
    }

    public int obtenerPosicionAntiguos(){
        return handler[1];
    }

    public void modificarHandlerRedituable(int posRedituable){
        handler[0] = posRedituable;
    }

    public void modificarHandlerAntiguo(int posAntiguo){
        handler[1] = posAntiguo;
    }
    
}
