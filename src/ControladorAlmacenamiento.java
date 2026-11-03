import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class ControladorAlmacenamiento {
    private final int totalBloques;
    private final int tamBloqueKiB;
    private int bloquesDisponibles;
    private final Map<Integer, String> asignaciones; 
    //Key-> Value: ID Bloque -> ID MV

    public ControladorAlmacenamiento(int capacidadGiB, int tamBloqueKiB) {
        this.tamBloqueKiB = tamBloqueKiB;
        this.totalBloques = (int) (((long) capacidadGiB*1024*1024)/tamBloqueKiB);
        this.bloquesDisponibles = this.totalBloques;
        this.asignaciones = new HashMap<>();
    }

    public synchronized boolean asignar(String idVM, int almacenamientoGiB) {
        int bloquesRequeridos = traducirGiBABloques(almacenamientoGiB);

        if (bloquesRequeridos <= 0 || bloquesRequeridos > bloquesDisponibles) {
            return false;
        }

        int asignados = 0;
        for (int i = 0; i < totalBloques && asignados < bloquesRequeridos; i++) {
            if (!asignaciones.containsKey(i)) {
                asignaciones.put(i, idVM);
                asignados++;
            }
        }

        bloquesDisponibles -= bloquesRequeridos;
        return true;
    }

    public synchronized void liberar(String idVM) {
        int liberados = 0;
        Iterator<Map.Entry<Integer,String>> it = asignaciones.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            if (entry.getValue().equals(idVM)) {
                it.remove();
                liberados++;
            }
        }

        bloquesDisponibles += liberados;
    }

    public int traducirGiBABloques(int almacenamientoGiB) {
        long totalKiB = (long) almacenamientoGiB*1024*1024;
        return (int) Math.round((double) totalKiB/tamBloqueKiB);
    }

    public synchronized int getBloquesDisponibles() {
        return bloquesDisponibles;
    }

    public synchronized int getTotalBloques() {
        return totalBloques;
    }
}