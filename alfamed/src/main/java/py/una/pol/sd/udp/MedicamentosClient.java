package py.una.pol.sd.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import org.json.JSONObject;

public class MedicamentosClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 7000; // Puerto UDP del servidor MedTec

        System.out.println("Iniciando cliente UDP para MedTec...");
        System.out.println("Escribe 'GET_MEDICAMENTOS' para consultar o 'Bye' para salir.");

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
            
            socket.setSoTimeout(3000); // Timeout de 3 segundos para la respuesta
            InetAddress address = InetAddress.getByName(host);

            while (true) {
                System.out.print("\nIngrese mensaje para el servidor: ");
                String mensaje = scanner.nextLine().trim();

                // Condición de salida
                if ("Bye".equalsIgnoreCase(mensaje)) {
                    System.out.println("Cerrando sesión de cliente...");
                    break;
                }

                // Enviar datagrama al servidor
                byte[] bufferOut = mensaje.getBytes();
                DatagramPacket packetOut = new DatagramPacket(bufferOut, bufferOut.length, address, port);
                socket.send(packetOut);

                try {
                    // Recibir respuesta
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packetIn);

                    String respuestaStr = new String(packetIn.getData(), 0, packetIn.getLength()).trim();
                    
                    // Validar si la respuesta es un JSON válido o un mensaje de texto plano
                    if (respuestaStr.startsWith("{")) {
                        JSONObject jsonResp = new JSONObject(respuestaStr);
                        System.out.println("Respuesta recibida del servidor MedTec:");
                        System.out.println(jsonResp.toString(4));
                    } else {
                        System.out.println("Respuesta del servidor: " + respuestaStr);
                    }

                } catch (java.net.SocketTimeoutException e) {
                    System.err.println("El servidor no respondió a tiempo (Timeout).");
                }
            }

        } catch (Exception e) {
            System.err.println("Error en la comunicación UDP: " + e.getMessage());
            e.printStackTrace();
        }
    }
}