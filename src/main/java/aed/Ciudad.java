package aed;

public class Ciudad {
    private int id;
    private int ganancia;
    private int perdida;
    private int superavit;
    private int handler;

    public Ciudad(int id){
        this.id = id;
        ganancia = 0;
        perdida = 0;
        superavit = 0;
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