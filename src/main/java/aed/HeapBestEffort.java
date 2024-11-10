package aed;

import java.util.ArrayList;
import java.util.Comparator;

//Hacer interfaz heap
public class HeapBestEffort<T> { //Hay que poner comparable o no?
    ArrayList<T> elementos;
    int size;
    Comparator<T> comparador;

    public HeapBestEffort(Comparator<T> comparador, ArrayList<T> elementos){
        this.comparador = comparador;
        this.elementos = elementos;
        size = elementos.size();
        if (elementos.get(0).getClass() == Traslado.class){
            heapify(); //por qué está en amarillo esto?
        }
    }

    public void heapify(){
        int posPadre = elementos.size() - 1; //Esto es O(1)?
        while (posPadre >= 0){
            bajar(posPadre);
            posPadre--;
        }
    }

    public int posPadre(int posHijo){
        return (posHijo - 1) / 2;
    }

    public int posHijoDerecho(int posPadre){
        return 2 * posPadre + 1;
    }

    public int posHijoIzquierdo(int posPadre){
        return 2 * posPadre + 2;
    }

    public void bajar(int posicion){
        int posHijoDer = posHijoDerecho(posicion);
        int posHijoIzq = posHijoIzquierdo(posicion);
        int posHijoMayor;

        if (posHijoDer < size && posHijoIzq < size){
            posHijoMayor = posMayorPrioridad(posHijoDer, posHijoIzq);
            if (posMayorPrioridad(posHijoMayor, posicion) == posHijoMayor){
                swap(posHijoMayor, posicion);
                //Actualizar Handle!!
                bajar(posHijoMayor); //ahora la posicion  a bajar está en la posición de su hijo mayor
            }
        }
        else if (posHijoIzq < size){
            if (posMayorPrioridad(posHijoIzq, posicion) == posHijoIzq){
                swap(posHijoIzq, posicion);
                //Actualizar Handle!!
                bajar(posHijoIzq); //ahora la posición a bajar está en la posición de su hijo izquierdo
            }
        }
    }

    public int posMayorPrioridad(int pos1, int pos2){
        if (comparador.compare(elementos.get(pos1), elementos.get(pos2)) > 0){
            return pos1;
        }
        else{
            return pos2;
        }
    }

    public void swap(int pos1, int pos2){
        T elemento1 = elementos.get(pos1);
        T elemento2 = elementos.get(pos2);
        elementos.set(pos1, elemento2);
        elementos.set(pos2, elemento1);
    }
}