import java.util.HashMap;
import java.util.Map;

public class ControladorMemoria {
    private final int totalPaginas;
    private final int tamPaginaKiB;
    private int paginasDisponibles;
    private final Map<Integer, String> asignaciones; 
    // Key-> Value: ID Página -> ID MV

    public ControladorMemoria(int capacidadGiB, int tamPaginaKiB) {
        this.tamPaginaKiB = tamPaginaKiB;
        this.totalPaginas = (int) (((long) capacidadGiB * 1024 * 1024) / tamPaginaKiB);
        this.paginasDisponibles = this.totalPaginas;
        this.asignaciones = new HashMap<>();
    }

    public synchronized boolean asignar(String idVM, int memoriaGiB) {
        // TODO: Traducir memoriaGiB a páginas usando traducirGiBAPaginas
        // TODO: Validar disponibilidad, asignar páginas específicas y registrar idVM
        return false;
    }

    public synchronized void liberar(String idVM) {
        // TODO: Liberar páginas asignadas a idVM y restaurar disponibilidad
    }

    public int traducirGiBAPaginas(int memoriaGiB) {
        // TODO: Traducir al múltiplo más cercano del tamaño de página
        return 0;
    }
}