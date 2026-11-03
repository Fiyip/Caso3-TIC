import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int nu;
    private static int base;
    private static int ng;
    private static int tam1;
    private static int tam2;
    private static int procesadores;
    private static int memoria;
    private static int pagina;
    private static int almacenamiento;
    private static int bloque;

    public static void main(String[] args) {
        // Cargar archivo de config
        String rutaArchivo = (args.length > 0) ? args[0] : "config.txt";
        cargarConfig(rutaArchivo);
        // Instanciar monitores
        BuzonEntrada buzon = new BuzonEntrada(tam1, nu);
        ZonaEspera zonaEspera = new ZonaEspera(tam2);

        ControladorProcesadores ctrlProc = new ControladorProcesadores(procesadores);
        ControladorMemoria ctrlMem = new ControladorMemoria(memoria, pagina);
        ControladorAlmacenamiento ctrlAlm = new ControladorAlmacenamiento(almacenamiento, bloque);
        // Crear e iniciar hilos Gestores
        List<GestorSolicitudes> gestores = new ArrayList<>();
        for (int i = 1; i <= ng; i++) {
            GestorSolicitudes gestor = new GestorSolicitudes(
                i, buzon, zonaEspera, ctrlProc, ctrlMem, ctrlAlm
            );
            gestores.add(gestor);
            gestor.start();
        }
        // Iniciar Usuarios
        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 1; i <= nu; i++) {
            Usuario usuario = new Usuario(
                i, base, buzon, procesadores, memoria, almacenamiento
            );
            usuarios.add(usuario);
            usuario.start();
        }
        // Sincronizar terminación (join de los hilos)
        for (Usuario u : usuarios) {
            try {
                u.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        for (GestorSolicitudes g : gestores) {
            try {
                g.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Esperar liberación total de los recursos alocados pa las VMs y la Zona de Espera
        while (zonaEspera.getPendientes() > 0 ||
               ctrlProc.getProcesadoresDisponibles() < ctrlProc.getTotalProcesadores() ||
               ctrlMem.getPaginasDisponibles() < ctrlMem.getTotalPaginas() ||
               ctrlAlm.getBloquesDisponibles() < ctrlAlm.getTotalBloques()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        //Validar Finalización
        validarFin(buzon, zonaEspera, ctrlProc, ctrlMem, ctrlAlm);
    }

    private static void cargarConfig(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }
                String[] partes = linea.split("=");
                if (partes.length == 2) {
                    String clave = partes[0].trim();
                    int valor = Integer.parseInt(partes[1].trim());
                    switch (clave) {
                        case "nu": nu = valor; break;
                        case "base": base = valor; break;
                        case "ng": ng = valor; break;
                        case "tam1": tam1 = valor; break;
                        case "tam2": tam2 = valor; break;
                        case "procesadores": procesadores = valor; break;
                        case "memoria": memoria = valor; break;
                        case "pagina": pagina = valor; break;
                        case "almacenamiento": almacenamiento = valor; break;
                        case "bloque": bloque = valor; break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura de la config: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void validarFin(BuzonEntrada buzon, ZonaEspera zonaEspera,
                                          ControladorProcesadores ctrlProc,
                                          ControladorMemoria ctrlMem,
                                          ControladorAlmacenamiento ctrlAlm) {
        System.out.println("Telemetria Final");
        System.out.println("Solicitudes en buzon: " + buzon.getTamanoCola() + " (Esperado: 0)");
        System.out.println("MVs pendientes en zona: " + zonaEspera.getPendientes() + " (Esperado: 0)");
        System.out.println("Procesadores libres: " + ctrlProc.getProcesadoresDisponibles() + "/" + ctrlProc.getTotalProcesadores());
        System.out.println("Paginas libres: " + ctrlMem.getPaginasDisponibles() + "/" + ctrlMem.getTotalPaginas());
        System.out.println("Bloques libres: " + ctrlAlm.getBloquesDisponibles() + "/" + ctrlAlm.getTotalBloques());

        boolean correcto = (buzon.getTamanoCola() == 0) &&
                           (zonaEspera.getPendientes() == 0) &&
                           (ctrlProc.getProcesadoresDisponibles() == ctrlProc.getTotalProcesadores()) &&
                           (ctrlMem.getPaginasDisponibles() == ctrlMem.getTotalPaginas()) &&
                           (ctrlAlm.getBloquesDisponibles() == ctrlAlm.getTotalBloques());

        if (correcto) {
            System.out.println(">> Todos los recursos fueron liberados. Programa finalizado con éxito");
        } else {
            System.err.println(">> Error con el fin de la ejecucion.");
        }
    }
}