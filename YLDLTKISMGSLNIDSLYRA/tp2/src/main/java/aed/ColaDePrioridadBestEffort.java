package aed;

interface ColaDePrioridadBestEffort<T> {

    /**
     * Modifica el superavit de la ciudad pasada por parámetro y reordena un heap de tipo ciudad
     */
    public void cambiarPrioridadCiudad(Ciudad ciudad, int valor);

    /**
     * Agrega el elemento a un heap (sin importar si esta repetido)
     */
    public void encolar(T elemento);

    /**
     * Elimina el traslado pasado por parámetro de un heap de tipo traslado. 
     * Requiere que el traslado pertenezca al heap
     */
    public void eliminarTraslado(Traslado traslado);

    /**
     * Desencola el elemento de mayor prioridad en un heap de tipo Traslado
     * 
     */
    public Traslado desencolarTraslado();

    /**
     * Devuelve cantidad de elementos de un heap
     */
    public int obtenerTamano();

    /**
     * Muestra el elemento de mayor prioridad en un heap
     */
    public T verRaiz();
}