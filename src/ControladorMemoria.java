import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class ControladorMemoria {
    private final int totalPaginas;
    private final int tamPaginaKiB;
    private int paginasDisponibles;
    private final Map<Integer, String> asignaciones; 
    // Key-> Value: ID Página -> ID MV

    public ControladorMemoria(int capacidadGiB, int tamPaginaKiB) {
        this.tamPaginaKiB = tamPaginaKiB;
        this.totalPaginas = (int) (((long) capacidadGiB*1024*1024)/tamPaginaKiB);
        this.paginasDisponibles = this.totalPaginas;
        this.asignaciones = new HashMap<>();
    }

    public synchronized boolean asignar(String idVM, int memoriaGiB) {
        int paginasRequeridas = traducirGiBAPaginas(memoriaGiB);

        if (paginasRequeridas <= 0 || paginasRequeridas > paginasDisponibles) {
            return false;
        }

        int asignadas = 0;
        for (int i = 0; i < totalPaginas && asignadas < paginasRequeridas; i++) {
            if (!asignaciones.containsKey(i)) {
                asignaciones.put(i, idVM);
                asignadas++;
            }
        }

        paginasDisponibles -= paginasRequeridas;
        return true;
    }

    public synchronized void liberar(String idVM) {
        int liberadas = 0;
        Iterator<Map.Entry<Integer,String>> it = asignaciones.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            if (entry.getValue().equals(idVM)) {
                it.remove();
                liberadas++;
            }
        }

        paginasDisponibles += liberadas;
    }

    public int traducirGiBAPaginas(int memoriaGiB) {
        long totalKiB = (long) memoriaGiB*1024*1024;
        return (int) Math.round((double) totalKiB / tamPaginaKiB);
    }

    public synchronized int getPaginasDisponibles() {
        return paginasDisponibles;
    }

    public synchronized int getTotalPaginas() {
        return totalPaginas;
    }
}