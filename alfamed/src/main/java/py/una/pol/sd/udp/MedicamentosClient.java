package py.una.pol.sd.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.json.JSONObject;

public class MedicamentosClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 7000; // Puerto UDP del servidor MedTec

        System.out.println("Enviando solicitud UDP de medicamentos a MedTec...");

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(3000); // Timeout de 3 segundos para la respuesta

            String mensaje = "GET_MEDICAMENTOS";
            byte[] bufferOut = mensaje.getBytes();

            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packetOut = new DatagramPacket(bufferOut, bufferOut.length, address, port);
            socket.send(packetOut);

            // Recibir respuesta
            byte[] bufferIn = new byte[1024];
            DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
            socket.receive(packetIn);

            String respuestaStr = new String(packetIn.getData(), 0, packetIn.getLength()).trim();
            
            // Parsear y mostrar el JSON recibido
            JSONObject jsonResp = new JSONObject(respuestaStr);
            System.out.println("Respuesta recibida del servidor MedTec:");
            System.out.println(jsonResp.toString(4)); // Imprime el JSON formateado

        } catch (Exception e) {
            System.err.println("Error en la comunicación UDP: " + e.getMessage());
            e.printStackTrace();
        }
    }
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
