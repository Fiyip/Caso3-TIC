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
        while (cola.size() == capacidad) {
            wait();
        }
        cola.add(solicitud);
        notifyAll();
    }

    // Consumidor (Gestor)
    public synchronized Solicitud extraer(){
        Solicitud solicitud = cola.poll();
        if (solicitud != null) {
            notifyAll(); 
        }
        return solicitud;
    }

    public synchronized void reportarTerminacion() {    
        usuariosActivos--; 
        notifyAll();
        
    }

    public synchronized boolean hayTrabajoPendiente()  {
        return !cola.isEmpty() || usuariosActivos > 0;
        
    }

    public synchronized int getTamanoCola() {
        return cola.size();
    }
}