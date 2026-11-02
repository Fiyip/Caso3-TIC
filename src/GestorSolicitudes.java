public class GestorSolicitudes extends Thread {
    private final int idGestor;
    private final BuzonEntrada buzon;
    private final ZonaEspera zonaEspera;
    private final ControladorProcesadores ctrlProc;
    private final ControladorMemoria ctrlMem;
    private final ControladorAlmacenamiento ctrlAlm;

    public GestorSolicitudes(int idGestor, BuzonEntrada buzon, ZonaEspera zonaEspera,
                             ControladorProcesadores ctrlProc, ControladorMemoria ctrlMem,
                             ControladorAlmacenamiento ctrlAlm) {
        this.idGestor = idGestor;
        this.buzon = buzon;
        this.zonaEspera = zonaEspera;
        this.ctrlProc = ctrlProc;
        this.ctrlMem = ctrlMem;
        this.ctrlAlm = ctrlAlm;
    }

    @Override
    public void run() {
        // TODO: Bucle principal mientras buzon.hayTrabajoPendiente()
        // TODO: 1. zonaEspera.verificarUmbral()
        // TODO: 2. Intentar extraer del buzón
        //       - Si no hay elementos -> Thread.yield() (Espera semi-activa)
        //       - Si hay solicitud -> Crear e iniciar instancia de MaquinaVirtual
    }
}