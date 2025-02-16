package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class HeapSobreArrayList<T> implements ColaDePrioridadBestEffort<T> {
   //Atributos
    private ArrayList<T> elementos;
    private int size;
    private Comparator<T> comparador;

    public HeapSobreArrayList(Comparator<T> comparador, ArrayList<T> elementos){
        this.comparador = comparador;
        this.elementos = elementos;
        size = elementos.size();  
        if (size!= 0){
            heapify();  //O(size)
        }
    }

    private void heapify(){ //O(size)
        int posPadre = elementos.size() - 1;
        while (posPadre >= 0){  
            bajar(posPadre);    
            posPadre--;
        }
    }

    public void cambiarPrioridadCiudad(Ciudad ciudad, int valor){   //O(log(size)) porque buscar es O(1)
        ciudad.modificarSuperavit(valor);

        int posCambiar = ciudad.obtenerHandler();
        int posPadre = posPadre(posCambiar);

        if (posPadre != posCambiar && posMayorPrioridad(posCambiar, posPadre) == posCambiar){
            subir(posCambiar);
        }
        else{
            bajar(posCambiar);  
        }
    }

    public void encolar(T elemento){    //O(log(size))
        elementos.add(elemento);
        size++;
        int pos = size-1;
        
        //Inicializo handler traslado
        if (elementos.get(0).getClass() == Traslado.class){
            Traslado traslado = (Traslado) elemento;

            if(comparador.getClass() == ComparadorRedituable.class){
                traslado.modificarHandlerRedituable(pos);
            }
            else if(comparador.getClass() == ComparadorAntiguedad.class){
                traslado.modificarHandlerAntiguo(pos);
            }
        }
        //Inicializo handler ciudad
        else if(elementos.get(0).getClass() == Ciudad.class){
            Ciudad ciudad = (Ciudad) elemento;

            ciudad.modificarHandler(pos);
        }

        subir(size-1); //elemento está en la ultima posicion, es decir size-1
    }

    public void eliminarTraslado(Traslado traslado){    //O(log(size)) porque buscar es O(1)
        int posicion;
        int posUltimo = size-1;

        if (comparador.getClass() == ComparadorRedituable.class){
            posicion = traslado.obtenerPosicionRedituable();
        }
        else{
            posicion = traslado.obtenerPosicionAntiguos();
        }

        swap(posicion, posUltimo); 
        elementos.remove(posUltimo);
        size--;

        int posPadre = posPadre(posicion);
        if (posicion!=size){
            if (posicion != 0 && posMayorPrioridad(posicion, posPadre) == posicion){
                subir(posicion);
            }
            else{
                bajar(posicion);
            }
        }
    }

    public Traslado desencolarTraslado(){   //O(log(size))
        T raiz = verRaiz();
        Traslado res = (Traslado) raiz;
        eliminarTraslado(res);  //O(log(size))
        return res;
    }

    public int obtenerTamano(){
        return size;
    }

    public T verRaiz(){ 
        return elementos.get(0);
    }

    private int posPadre(int posHijo){
        return (posHijo - 1) / 2;
    }

    private int posHijoDerecho(int posPadre){
        return 2 * posPadre + 2;
    }

    private int posHijoIzquierdo(int posPadre){
        return 2 * posPadre + 1;
    }

    private void bajar(int posicion){   //O(log(size))
        int posHijoDer = posHijoDerecho(posicion);
        int posHijoIzq = posHijoIzquierdo(posicion);
        int posHijoMayor;

        if (posHijoDer < size && posHijoIzq < size){
            posHijoMayor = posMayorPrioridad(posHijoDer, posHijoIzq);
            if (posMayorPrioridad(posHijoMayor, posicion) == posHijoMayor){
                swap(posHijoMayor, posicion);
                bajar(posHijoMayor); //ahora la posicion a bajar está en la posición de su hijo mayor
            }
        }
        else if (posHijoIzq < size){
            if (posMayorPrioridad(posHijoIzq, posicion) == posHijoIzq){
                swap(posHijoIzq, posicion);
                bajar(posHijoIzq); //ahora la posición a bajar está en la posición de su hijo izquierdo
            }
        }
    }

    private void subir(int posicion){   //O(log(size))
        int posPadre = posPadre(posicion);
        if(posicion != 0 && posMayorPrioridad(posicion, posPadre) == posicion){
            swap(posicion, posPadre);
            subir(posPadre); //ahora la posición a subir está en la posición de su padre
        }
    }

    private int posMayorPrioridad(int pos1, int pos2){  //O(1)
        if (comparador.compare(elementos.get(pos1), elementos.get(pos2)) > 0){
            return pos1;
        }
        else{
            return pos2;
        }
    }

    private void swap(int pos1, int pos2){  //O(1)
        T elemento1 = elementos.get(pos1);
        T elemento2 = elementos.get(pos2);
        elementos.set(pos1, elemento2);
        elementos.set(pos2, elemento1);

        //Modificamos handlers correspondientes si estamos swapeando traslados
        if (elementos.get(0).getClass() == Traslado.class){
            Traslado traslado1 = (Traslado) elemento1;
            Traslado traslado2 = (Traslado) elemento2;
            
            if(comparador.getClass() == ComparadorRedituable.class){
                traslado1.modificarHandlerRedituable(pos2);
                traslado2.modificarHandlerRedituable(pos1);
            }
            else if(comparador.getClass() == ComparadorAntiguedad.class){
                traslado1.modificarHandlerAntiguo(pos2);
                traslado2.modificarHandlerAntiguo(pos1);
            }
        }
        //Modificamos handlers correspondientes si estamos swapeando ciudades
        else if(elementos.get(0).getClass() == Ciudad.class){
            Ciudad ciudad1 = (Ciudad) elemento1;
            Ciudad ciudad2 = (Ciudad) elemento2;

            ciudad1.modificarHandler(pos2);
            ciudad2.modificarHandler(pos1);
        }
    }
}
