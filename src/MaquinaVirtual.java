import java.util.Random;

public class MaquinaVirtual extends Thread {
    private final Solicitud solicitud;
    private final ZonaEspera zonaEspera;
    private final ControladorProcesadores ctrlProc;
    private final ControladorMemoria ctrlMem;
    private final ControladorAlmacenamiento ctrlAlm;
    private final Random random;

    public MaquinaVirtual(Solicitud solicitud, ZonaEspera zonaEspera,
                          ControladorProcesadores ctrlProc, ControladorMemoria ctrlMem,
                          ControladorAlmacenamiento ctrlAlm, Random random) {
        this.solicitud = solicitud;
        this.zonaEspera = zonaEspera;
        this.ctrlProc = ctrlProc;
        this.ctrlMem = ctrlMem;
        this.ctrlAlm = ctrlAlm;
        this.random = random;
    }

    @Override
    public void run() {
        boolean asignoRecursos = false;
        boolean entroEspera = false;

        try{
            while(!asignoRecursos){
                if (!intentarAsignacionCompleta()){
                    asignoRecursos = true;
                }
                //Si no asigna los recursos mandar a ZonaEspera
                else{
                    entroEspera = true;
                    zonaEspera.entrarEspera();
                }
            }
            //Si esperó descuenta del contador
            if (entroEspera) {
                zonaEspera.registrarSalidaExito();
            }
            //Simula chamba
            simularEjecutar();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        finally{
            //Liberar lo alocado y notifyAll
            if(asignoRecursos){
                liberarTodo();
                zonaEspera.despertarTodas();
            }
        }
    }

    private boolean intentarAsignacionCompleta() {
        String idVM = solicitud.getIdSolicitud();
        // Orden estricto CPU -> Memoria -> Disco
        // Si falla se libera lo alocado antes de retornar false
        //CPU
        if (!ctrlProc.asignar(idVM, solicitud.getProcesadoresRequeridos())) {
            return false;
        }
        //RAM
        if (!ctrlMem.asignar(idVM, solicitud.getMemoriaGiB())) {
            // Libera CPU de antes si no aloco RAM
            ctrlProc.liberar(idVM);
            return false;
        }
        //Almacenamiento
        if (!ctrlAlm.asignar(idVM, solicitud.getAlmacenamientoGiB())) {
            // Libera CPU y RAM de antes si no aloco Almacenamiento
            ctrlProc.liberar(idVM);
            ctrlMem.liberar(idVM);
            return false;
        }
        return true;
    }

    private void simularEjecutar() throws InterruptedException {
        //Simulamos tiempo de chamba random entre 4.000 ms y 40.000 ms
        long tiempoMs = 4000L + (long) (random.nextDouble() * (40000L - 4000L));
        Thread.sleep(tiempoMs);
    }

    private void liberarTodo() {
        String idVM = solicitud.getIdSolicitud();
        ctrlProc.liberar(idVM);
        ctrlMem.liberar(idVM);
        ctrlAlm.liberar(idVM);    }
}