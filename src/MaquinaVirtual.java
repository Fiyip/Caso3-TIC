public class MaquinaVirtual extends Thread {
    private final Solicitud solicitud;
    private final ZonaEspera zonaEspera;
    private final ControladorProcesadores ctrlProc;
    private final ControladorMemoria ctrlMem;
    private final ControladorAlmacenamiento ctrlAlm;

    public MaquinaVirtual(Solicitud solicitud, ZonaEspera zonaEspera,
                          ControladorProcesadores ctrlProc, ControladorMemoria ctrlMem,
                          ControladorAlmacenamiento ctrlAlm) {
        this.solicitud = solicitud;
        this.zonaEspera = zonaEspera;
        this.ctrlProc = ctrlProc;
        this.ctrlMem = ctrlMem;
        this.ctrlAlm = ctrlAlm;
    }

    @Override
    public void run() {
        // TODO: Bucle hasta que intentarAsignacionCompleta() sea true
        //       - Si falla: zonaEspera.entrarEspera()
        // TODO: zonaEspera.registrarSalidaExito()
        // TODO: ejecutarSimulacion() (sleep aleatorio entre 4000 y 40000 ms)
        // TODO: liberarTodosRecursos()
        // TODO: zonaEspera.despertarTodas()
    }

    private boolean intentarAsignacionCompleta() {
        // TODO: Orden estricto 1. CPU -> 2. Memoria -> 3. Disco
        // TODO: Si falla en algún punto, liberar inmediatamente lo reservado antes de retornar false
        return false;
    }

    private void ejecutarSimulacion() throws InterruptedException {
        // TODO: Simulación de ejecución con tiempo variable
    }

    private void liberarTodosRecursos() {
        // TODO: Invocar liberar() en los 3 controladores
    }
}