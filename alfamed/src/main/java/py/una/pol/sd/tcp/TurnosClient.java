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
    public static JSONObject reservarTurno(String cedula, String especialidad, String horario) {
        JSONObject respuesta = new JSONObject();

        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Construir solicitud JSON
            JSONObject req = new JSONObject();
            req.put("cedula", cedula);
            req.put("especialidad", especialidad);
            req.put("horario", horario);

            // Enviar solicitud
            out.println(req.toString());

            // Recibir respuesta
            String respStr = in.readLine();
            respuesta = new JSONObject(respStr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuesta;
    }

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
}
