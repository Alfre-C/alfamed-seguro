package py.una.pol.sd.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.json.JSONObject;

public class MedicamentosClient {
    /**
     * Solicita al hospital la lista completa de medicamentos y su stock.
     * @return JSONObject con todos los medicamentos y sus cantidades.
     *         Ejemplo:
     *         {
     *             "medicamentos": [
     *                 {"nombre": "Paracetamol", "stock": 25},
     *                 {"nombre": "Ibuprofeno", "stock": 10}
     *             ]
     *         }
     */
    public static JSONObject consultarMedicamentos() {
        JSONObject resultado = new JSONObject();

        try {
            DatagramSocket socket = new DatagramSocket();

            // Mensaje de solicitud
            byte[] msg = "GET_MEDICAMENTOS".getBytes();

            DatagramPacket packet = new DatagramPacket(
                msg,
                msg.length,
                InetAddress.getByName("localhost"),
                7000 // puerto del servidor UDP del hospital
            );

            socket.send(packet);

            // Recibir respuesta
            byte[] buffer = new byte[4096]; // más grande por si hay muchos medicamentos
            DatagramPacket resp = new DatagramPacket(buffer, buffer.length);
            socket.receive(resp);

            String json = new String(resp.getData(), 0, resp.getLength());
            socket.close();

            resultado = new JSONObject(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    /*Originalmente era asi */
    /*public static JSONObject consultarStock() throws Exception {
        DatagramSocket socket = new DatagramSocket();

        byte[] msg = "GET_MEDICAMENTOS".getBytes();
        DatagramPacket packet = new DatagramPacket(msg, msg.length,
                InetAddress.getByName("localhost"), 7000);

        socket.send(packet);

        byte[] buffer = new byte[1024];
        DatagramPacket resp = new DatagramPacket(buffer, buffer.length);
        socket.receive(resp);

        String json = new String(resp.getData(), 0, resp.getLength());
        socket.close();

        return new JSONObject(json);
    }*/
}
