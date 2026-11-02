public class Solicitud {
    private final String idSolicitud;
    private final int procesadoresRequeridos;
    private final int memoriaGiB;
    private final int almacenamientoGiB;

    public Solicitud(String idSolicitud, int procesadoresRequeridos, int memoriaGiB, int almacenamientoGiB) {
        this.idSolicitud = idSolicitud;
        this.procesadoresRequeridos = procesadoresRequeridos;
        this.memoriaGiB = memoriaGiB;
        this.almacenamientoGiB = almacenamientoGiB;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public int getProcesadoresRequeridos() {
        return procesadoresRequeridos;
    }

    public int getMemoriaGiB() {
        return memoriaGiB;
    }

    public int getAlmacenamientoGiB() {
        return almacenamientoGiB;
    }
}