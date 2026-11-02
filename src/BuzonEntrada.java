import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrada {
    private final int capacidad;
    private int usuariosActivos;
    private final Queue<Solicitud> cola;

    public BuzonEntrada(int capacidad, int totalUsuariosIniciales) {
        this.capacidad = capacidad;
        this.usuariosActivos = totalUsuariosIniciales;
        this.cola = new LinkedList<>();
    }
    
    // Productor (Usuario)
    public synchronized void depositar(Solicitud solicitud) throws InterruptedException {
        // TODO: Espera pasiva si la cola está llena (wait)
        // TODO: Agregar solicitud y notificar a gestores (notifyAll)
    }

    // Consumidor (Gestor)
    public synchronized Solicitud extraer() {
        // TODO: Retorno no bloqueante o extracción si hay elementos
        return null;
    }

    public synchronized void reportarTerminacion() {
        // TODO: Decrementar usuariosActivos y notificar
    }

    public synchronized boolean hayTrabajoPendiente() {
        // TODO: Retorna true si hay solicitudes en cola o si aún quedan usuarios activos
        return false;
    }

    public synchronized int getTamanoCola() {
        return cola.size();
    }
}