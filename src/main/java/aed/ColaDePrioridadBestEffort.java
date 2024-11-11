package aed;

interface ColaDePrioridadBestEffort<T> {

    /**
     * Modifica el superavit de la ciudad pasada por parámetro y reordena el heap de tipo ciudad
     */
    public void cambiarPrioridadCiudad(Ciudad ciudad, int valor);

    /**
     * Agrega el elemento al heap (sin importar si esta repetido)
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
     * Devuelve cantidad de elementos del heap
     */
    public int obtenerTamano();

    /**
     * Muestra el elemento de mayor prioridad en el heap
     */
    public T verRaiz();
}