import java.util.Random;

public class Usuario extends Thread {
    private final int idUsuario;
    private final int totalSolicitudes;
    private final BuzonEntrada buzon;
    private final int maxCpu;
    private final int maxMem;
    private final int maxAlm;
    private final Random random = new Random();

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
        try {
            for (int i = 0; i < totalSolicitudes; i++) {
                Solicitud solicitud = generarSolicitud(i);
                buzon.depositar(solicitud);
            }} 
            catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }
        finally {
            buzon.reportarTerminacion();
        }
    }

    private Solicitud generarSolicitud(int secuencial) {

        
        int cpu = 1 + random.nextInt((int)Math.round(0.75 * maxCpu));
        int mem = Integer.highestOneBit(1 + random.nextInt((int)Math.round(0.75 * maxMem)));
        
        int alm = (1 + random.nextInt((int)Math.round(0.75 * maxAlm))) ;
        if (alm % 2 != 0 && alm > 1) {
            alm--; 
        }

        return new Solicitud(idUsuario + "-" + secuencial, cpu, mem, alm);
    }
}