package aed;

public class Ciudad {
    int id;
    int ganancia;
    int perdida;
    int superavit;

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

    public void aumentarSuperavit(int valor){
        superavit += valor;
    }

    public void disminuirSuperavit(int valor){
        superavit -= valor;
    }
}