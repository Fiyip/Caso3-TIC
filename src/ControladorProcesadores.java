import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ControladorProcesadores {
    private final int totalProcesadores;
    private int procesadoresDisponibles;
    private final Map<Integer, String> asignaciones; 
    // Key-> Value: ID Núcleo (0 a total-1) -> ID MV

    public ControladorProcesadores(int totalProcesadores) {
        this.totalProcesadores = totalProcesadores;
        this.procesadoresDisponibles = totalProcesadores;
        this.asignaciones = new HashMap<>();
    }

    public synchronized boolean asignar(String idVM, int cantidad) {
        if (cantidad <= 0 || cantidad > procesadoresDisponibles) {
            return false;
        }

        int asignados = 0;
        for (int i = 0; i < totalProcesadores && asignados < cantidad; i++) {
            if (!asignaciones.containsKey(i)) {
                asignaciones.put(i, idVM);
                asignados++;
            }
        }

        procesadoresDisponibles -= cantidad;
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

        procesadoresDisponibles += liberados;
    }

    public synchronized int getProcesadoresDisponibles() {
        return procesadoresDisponibles;
    }

    public synchronized int getTotalProcesadores() {
        return totalProcesadores;
    }
}