public class ZonaEspera {
    private final int umbral;
    private int pendientes;
    private boolean bloqueado;

    public ZonaEspera(int umbral) {
        this.umbral = umbral;
        this.pendientes = 0;
        this.bloqueado = false;
    }

    // Invocado por Gestor antes de procesar una solicitud
    public synchronized void verificarUmbral() throws InterruptedException {
        // TODO: Si pendientes >= umbral -> bloqueado = true
        // TODO: while (bloqueado) wait()
    }

    // Invocado por MV si falla la reserva
    public synchronized void entrarEspera() throws InterruptedException {
        // TODO: Incrementar pendientes, suspender con wait()
    }

    // Invocado por MV al despertar y conseguir todos los recursos
    public synchronized void registrarSalidaExito() {
        // TODO: Decrementar pendientes
        // TODO: Si pendientes == 0 -> bloqueado = false y notifyAll() al gestor
    }

    // Invocado por MV al terminar ejecución y liberar recursos
    public synchronized void despertarTodas() {
        // TODO: notifyAll() a las MVs suspendidas
    }
}