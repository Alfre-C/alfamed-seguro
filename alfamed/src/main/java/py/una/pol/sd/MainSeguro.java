package py.una.pol.sd;
import java.util.Scanner;

import org.json.JSONObject;

import py.una.pol.sd.tcp.CopagosServer;
import py.una.pol.sd.udp.MedicamentosClient;
import py.una.pol.sd.tcp.TurnosClient;

public class MainSeguro {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== ALFAMED - SEGURO ===");

        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Iniciar servidor de Copagos (TCP)");
            System.out.println("2. Consultar medicamentos al hospital (UDP)");
            System.out.println("3. Reservar turno en el hospital (TCP)");
            System.out.println("4. Salir");

            System.out.print("Opción: ");
            int op = Integer.parseInt(sc.nextLine());

            switch (op) {

                case 1:
                    System.out.println("Servidor de copagos iniciado en puerto 6000...");
                    new Thread(() -> {
                        try {
                            CopagosServer.main(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                    break;

                case 2:
                    JSONObject meds = MedicamentosClient.consultarMedicamentos();
                    System.out.println("Medicamentos recibidos:");
                    System.out.println(meds.toString(2));
                    break;
                
                /* Version modificada para permitir la comunicacion interactiva */
                case 3:
                    TurnosClient.iniciarReservaInteractiva();
                    break;
                /*----------------------------------------- */

                /* Version original */
                /*case 3:
                    System.out.print("Ingrese cédula: ");
                    String cedula = sc.nextLine();

                    System.out.print("Especialidad: ");
                    String esp = sc.nextLine();

                    System.out.print("Horario: ");
                    String horario = sc.nextLine();

                    System.out.print("Medico: ");
                    String medico = sc.nextLine();

                    JSONObject turno = TurnosClient.reservarTurno(cedula, esp, horario, medico);
                    System.out.println("Turno confirmado:");
                    System.out.println(turno.toString(2));
                    break;
                    */
                case 4:
                    System.out.println("Saliendo...");
                    sc.close();
                    return;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
