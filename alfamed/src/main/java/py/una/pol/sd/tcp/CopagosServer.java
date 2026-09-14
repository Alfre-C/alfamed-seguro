package py.una.pol.sd.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONObject;
import py.una.pol.sd.db.SeguroDB;

public class CopagosServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(6000);//el servidor se mantiene escuchando
        System.out.println("Servidor de copagos activo...");

        while (true) {
            Socket socket = server.accept();
            new Thread(() -> handle(socket)).start();
        }
    }

    private static void handle(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            JSONObject req = new JSONObject(in.readLine());
            String cedula = req.getString("cedula");
            JSONArray servicios = req.getJSONArray("servicios");

            int totalCliente = SeguroDB.calcularMontoCliente(cedula, servicios);

            JSONObject resp = new JSONObject();
            resp.put("cedula", cedula);
            resp.put("monto_total_cliente", totalCliente);

            out.println(resp.toString());
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Anteriormente creado*/
        /*try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            JSONObject req = new JSONObject(in.readLine());
            String cedula = req.getString("cedula");
            JSONArray servicios = req.getJSONArray("servicios");

            int monto = SeguroDB.calcularCopago(cedula, servicios);

            JSONObject resp = new JSONObject();
            resp.put("cedula", cedula);
            resp.put("monto_total", monto);

            out.println(resp.toString());
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
