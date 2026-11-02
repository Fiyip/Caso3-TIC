public class Usuario extends Thread {
    private final int idUsuario;
    private final int totalSolicitudes;
    private final BuzonEntrada buzon;
    private final int maxCpu;
    private final int maxMem;
    private final int maxAlm;

    public Usuario(int idUsuario, int baseSolicitudes, BuzonEntrada buzon, int maxCpu, int maxMem, int maxAlm) {
        this.idUsuario = idUsuario;
        this.totalSolicitudes = baseSolicitudes * idUsuario;
        this.buzon = buzon;
        this.maxCpu = maxCpu;
        this.maxMem = maxMem;
        this.maxAlm = maxAlm;
    }

    @Override
    public void run() {
        // TODO: Iterar hasta totalSolicitudes
        // TODO: Generar solicitud con identificador único y valores seudoaleatorios
        // TODO: buzon.depositar(solicitud)
        // TODO: Al finalizar el ciclo: buzon.reportarTerminacion()
    }

    private Solicitud generarSolicitud(int secuencial) {
        // TODO: Aplicar reglas de cálculo (3/4 max, potencias de 2, múltiplos de 2)
        return null;
    }
}