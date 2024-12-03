package aed;

public class Ciudad {
    private int id;
    private int ganancia;
    private int perdida;
    private int superavit;
    private int handler;    //posicion en ciudadesSuperavit

    public Ciudad(int id){  //O(1)
        this.id = id;
        ganancia = 0;
        perdida = 0;
        superavit = 0;
        handler = id;
    }

    @Override
    public boolean equals(Object otra) {    //Lo usamos en testing, su complejidad no afecta a la del sistema
        boolean otraEsNull = (otra == null);
        boolean claseDistinta = otra.getClass() != this.getClass();
        if (otraEsNull || claseDistinta) {
            return false;
        }
        Ciudad otraCiudad = (Ciudad) otra;
        return (id == otraCiudad.id &&
               ganancia == otraCiudad.ganancia &&
               perdida == otraCiudad.perdida &&
               superavit == otraCiudad.superavit &&
               handler == otraCiudad.handler);
    }

    public int obtenerId(){
        return id;
    }

    public int obtenerGanancia(){
        return ganancia;
    }

    public int obtenerPerdida(){
        return perdida;
    }

    public int obtenerSuperavit(){
        return superavit;
    }

    public void aumentarGanancia(int valor){
        ganancia += valor;
    }

    public void aumentarPerdida(int valor){
        perdida += valor;
    }

    public void modificarSuperavit(int valor){
        superavit = valor;
    }

    public int obtenerHandler(){
        return handler;
    }

    public void modificarHandler(int nuevo){
        handler = nuevo;
    }

}