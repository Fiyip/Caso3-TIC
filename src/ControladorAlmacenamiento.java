import java.util.HashMap;
import java.util.Map;

public class ControladorAlmacenamiento {
    private final int totalBloques;
    private final int tamBloqueKiB;
    private int bloquesDisponibles;
    private final Map<Integer, String> asignaciones; // ID Bloque -> ID MV

    public ControladorAlmacenamiento(int capacidadGiB, int tamBloqueKiB) {
        this.tamBloqueKiB = tamBloqueKiB;
        this.totalBloques = (int) (((long) capacidadGiB * 1024 * 1024) / tamBloqueKiB);
        this.bloquesDisponibles = this.totalBloques;
        this.asignaciones = new HashMap<>();
    }

    public synchronized boolean asignar(String idVM, int almacenamientoGiB) {
        // TODO: Traducir almacenamientoGiB a bloques usando traducirGiBABloques
        // TODO: Validar disponibilidad, asignar bloques específicos y registrar idVM
        return false;
    }

    public synchronized void liberar(String idVM) {
        // TODO: Liberar bloques asignados a idVM y restaurar disponibilidad
    }

    public int traducirGiBABloques(int almacenamientoGiB) {
        // TODO: Traducir al múltiplo más cercano del tamaño de bloque
        return 0;
    }
}