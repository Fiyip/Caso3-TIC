import java.util.HashSet;
import java.util.Set;

public class ZonaEspera {
    private final int umbral;
    private int pendientes;
    private boolean bloqueado;
    private final Set<Thread> hilosPendientes;

    public ZonaEspera(int umbral) {
        this.umbral = umbral;
        this.pendientes = 0;
        this.bloqueado = false;
        this.hilosPendientes = new HashSet<>();
    }

    // Invocado por Gestor antes de procesar una solicitud
    // Si las solicitudes pendientes son >= umbral, se activa
    // espera total y los gestores esperan hasta pendientes == 0 nuevamente
    public synchronized void verificarUmbral() throws InterruptedException {
        if (pendientes >= umbral) {
            bloqueado = true;
        }
        //Espera hasta q se vacien los pendientes
        while (bloqueado){
            wait();
        }
    }
    // Invocado por MV si falla la reserva
    public synchronized void entrarEspera() {
        hilosPendientes.add(Thread.currentThread());
        pendientes = hilosPendientes.size();
        // el threat entrante alcanza el umbral, activa el bloqueo temporal
        if (pendientes >= umbral) {
            bloqueado = true;
        }
    }

    // Invocado por MV al despertar y conseguir todos los recursos
    public synchronized void registrarSalidaExito() {
        if (hilosPendientes.remove(Thread.currentThread())) {
            pendientes = hilosPendientes.size();
            // Solo vuelven los gestores cuando la zona queda vacía nuevamente
            if (pendientes == 0 && bloqueado) {
                bloqueado = false;
                // Despierta los Gestores suspendidos en verificarUmbral()
                notifyAll(); 
            }
        }
    }

    // Invocado por MV al terminar ejecución y liberar recursos
    public synchronized void despertarTodas() {
        notifyAll();
    }
    public synchronized int getPendientes() {
        return pendientes;
    }

    public synchronized boolean isBloqueado() {
        return bloqueado;
    }

    public int getUmbral() {
        return umbral;
    }
}