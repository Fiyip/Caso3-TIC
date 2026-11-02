import java.util.HashMap;
import java.util.Map;

public class ControladorProcesadores {
    private final int totalProcesadores;
    private int procesadoresDisponibles;
    private final Map<Integer, String> asignaciones; // ID Núcleo -> ID MV

    public ControladorProcesadores(int totalProcesadores) {
        this.totalProcesadores = totalProcesadores;
        this.procesadoresDisponibles = totalProcesadores;
        this.asignaciones = new HashMap<>();
    }

    public synchronized boolean asignar(String idVM, int cantidad) {
        // TODO: Validar disponibilidad, asignar partes específicas y registrar idVM
        return false;
    }

    public synchronized void liberar(String idVM) {
        // TODO: Liberar los procesadores asociados a idVM y restaurar disponibilidad
    }
}