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
        super("Gestor-" + idGestor);
        this.idGestor = idGestor;
        this.buzon = buzon;
        this.zonaEspera = zonaEspera;
        this.ctrlProc = ctrlProc;
        this.ctrlMem = ctrlMem;
        this.ctrlAlm = ctrlAlm;
    }

    @Override
    public void run() {
        try{
            while (buzon.hayTrabajoPendiente()) {
                //Verificamos Umbral y si hay pendientes >= umbral, zonaEspera
                //ejecuta la espera pasiva total hasta pendientes == 0 nuevamente
                zonaEspera.verificarUmbral();
                
                Solicitud solicitud = buzon.extraer();
                if (solicitud != null){
                    //Creamos e iniciamos el hilo de la VM
                    MaquinaVirtual vm = new MaquinaVirtual(solicitud,zonaEspera,ctrlProc,ctrlMem,ctrlAlm);
                    vm.start();
                }
                else{
                    //Espera semiactiva si el buzón está vacío pero aún hay usuarios activos
                    Thread.yield();
                }
            }
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    public int getIdGestor() {
        return idGestor;
    }
}