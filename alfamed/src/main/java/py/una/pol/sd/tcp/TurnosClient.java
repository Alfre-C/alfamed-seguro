/* Version Modificada para permitir la comunicacion bidireccional */
package py.una.pol.sd.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.json.JSONObject;

public class TurnosClient {
    public static void iniciarReservaInteractiva() {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado al servidor de turnos de MedTec.");
            System.out.println("Nota: Puedes escribir 'VOLVER' en cualquier momento para regresar al paso anterior.\n");

            String lineaServidor;
            while ((lineaServidor = in.readLine()) != null) {
                /*Nueva version funcional bidireccional */
                if (lineaServidor.equals("TURNO_CONFIRMADO")) {

                    String jsonResp = in.readLine();
                    JSONObject respuesta = new JSONObject(jsonResp);

                    if ("CONFIRMADO".equals(respuesta.getString("status"))) {

                        System.out.println("\n¡Turno registrado exitosamente en MedTec!");
                        System.out.println(respuesta.toString(2));

                    } else {

                        System.out.println("\nERROR:");
                        System.out.println(respuesta.getString("mensaje"));
                    }

                    break;
                }
                /*------------------------------------- */
                
                /*version funcional bidireccional anterior */
                /*if (lineaServidor.equals("TURNO_CONFIRMADO")) {
                    String jsonResp = in.readLine();
                    JSONObject respuesta = new JSONObject(jsonResp);
                    System.out.println("\n¡Turno registrado exitosamente en MedTec!");
                    System.out.println(respuesta.toString(2));
                    break;
                }*/
                /*---------------------------------------- */

                if (lineaServidor.equals("MENU")) {
                    // Leer todas las líneas del menú hasta encontrar "FIN_MENU"
                    StringBuilder menuBuilder = new StringBuilder();
                    String lineaMenu;
                    while ((lineaMenu = in.readLine()) != null && !lineaMenu.equals("FIN_MENU")) {
                        menuBuilder.append(lineaMenu).append("\n");
                    }

                    System.out.println("\n" + menuBuilder.toString().trim());
                    System.out.print("Opción > ");
                    String respuestaUsuario = scanner.nextLine();
                    out.println(respuestaUsuario);
                } else {
                    System.out.println(lineaServidor);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*public static void iniciarReservaInteractiva() {
        Scanner scanner = new Scanner(System.in); // Fuera del try-with-resources

        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado al servidor de turnos de MedTec.");
            System.out.println("Nota: Puedes escribir 'VOLVER' en cualquier momento para regresar al paso anterior.\n");

            String lineaServidor;
            while ((lineaServidor = in.readLine()) != null) {
                
                if (lineaServidor.equals("TURNO_CONFIRMADO")) {
                    String jsonResp = in.readLine();
                    JSONObject respuesta = new JSONObject(jsonResp);
                    System.out.println("\n¡Turno registrado exitosamente en MedTec!");
                    System.out.println(respuesta.toString(2));
                    break;
                }

                if (lineaServidor.equals("MENU")) {
                    String contenidoMenu = in.readLine();
                    System.out.println("\n" + contenidoMenu);
                    System.out.print("Opción > ");
                    String respuestaUsuario = scanner.nextLine();
                    out.println(respuestaUsuario);
                } else {
                    System.out.println(lineaServidor);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Nota: No cerramos el scanner aquí para no afectar a MainSeguro
    }*/

    /*public static void iniciarReservaInteractiva() {
        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor de turnos de MedTec.");
            System.out.println("Nota: Puedes escribir 'VOLVER' en cualquier momento para regresar al paso anterior.\n");

            String lineaServidor;
            while ((lineaServidor = in.readLine()) != null) {
                
                if (lineaServidor.equals("TURNO_CONFIRMADO")) {
                    String jsonResp = in.readLine();
                    JSONObject respuesta = new JSONObject(jsonResp);
                    System.out.println("\n¡Turno registrado exitosamente en MedTec!");
                    System.out.println(respuesta.toString(2));
                    break;
                }

                if (lineaServidor.equals("MENU")) {
                    String contenidoMenu = in.readLine();
                    System.out.println("\n" + contenidoMenu);
                    System.out.print("Opción > ");
                    String respuestaUsuario = scanner.nextLine();
                    out.println(respuestaUsuario);
                } else {
                    System.out.println(lineaServidor);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
/*--------------------------------------------------------------------------------- */







/*Version original funcional */
/* 
package py.una.pol.sd.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.JSONObject;


public class TurnosClient {
    /**
     * Solicita al hospital la reserva de un turno.
     * @param cedula Cédula del paciente.
     * @param especialidad Especialidad médica.
     * @param horario Horario solicitado.
     * @return JSONObject con la respuesta del servidor.
     *         Ejemplo:
     *         {
     *             "cedula": "1234567",
     *             "id_turno": 5
     *         }
     */

    /*

    public static JSONObject reservarTurno(String cedula, String especialidad, String horario, String medico) {
        JSONObject respuesta = new JSONObject();

        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Construir solicitud JSON
            JSONObject req = new JSONObject();
            req.put("cedula", cedula);
            req.put("especialidad", especialidad);
            req.put("horario", horario);
            req.put("medico", medico);

            // Enviar solicitud
            out.println(req.toString());

            // Recibir respuesta
            String respStr = in.readLine();
            respuesta = new JSONObject(respStr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuesta;
    }*/

    /*Version anterior */
    /**public static JSONObject reservarTurno(String cedula, String esp, String horario) throws Exception {
        Socket socket = new Socket("localhost", 5000);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        JSONObject req = new JSONObject();
        req.put("cedula", cedula);
        req.put("especialidad", esp);
        req.put("horario", horario);

        out.println(req.toString());

        JSONObject resp = new JSONObject(in.readLine());
        socket.close();

        return resp;
    }*/

   /* 

}*/
