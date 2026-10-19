package py.una.pol.sd.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class SeguroDB {
    //Recordar que si no conecta es por que hay que cambiar el password
    private static final String URL = "jdbc:postgresql://localhost:5432/db_alfamed";// nombre de la base de datos
    private static final String USER = "postgres";// ingresar el usuario
    private static final String PASS = "postgres";// ingresar la contraseña de postgresql

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Cobertura del seguro para un servicio
    public static int obtenerCobertura(String cedula, String servicio) {
        int cobertura = 0;

        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT cobertura FROM coberturas WHERE cedula = ? AND servicio = ?"
            );
            stmt.setString(1, cedula);
            stmt.setString(2, servicio);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cobertura = rs.getInt("cobertura");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cobertura;
    }

    // Calcula cuánto paga el cliente en total
    public static int calcularMontoCliente(String cedula, JSONArray servicios) {
        int totalCliente = 0;

        try {
            for (int i = 0; i < servicios.length(); i++) {
                JSONObject s = servicios.getJSONObject(i);
                String nombre = s.getString("nombre");
                int precio = s.getInt("precio");

                int cobertura = obtenerCobertura(cedula, nombre);
                int pagaCliente = Math.max(precio - cobertura, 0);

                totalCliente += pagaCliente;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCliente;
    }

    /*Anteriormente usaba una bas de datos que no debia usar la organizacion */
    /*private static final String URL = "jdbc:postgresql://localhost:5432/alfamed_seguro";
    private static final String USER = "postgres";
    private static final String PASS = "1234"; // cambia si es otra

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Obtener cobertura del seguro para un servicio
    public static int obtenerCobertura(String cedula, String servicio) {
        int cobertura = 0;

        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT cobertura FROM coberturas WHERE cedula = ? AND servicio = ?"
            );
            stmt.setString(1, cedula);
            stmt.setString(2, servicio);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cobertura = rs.getInt("cobertura");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cobertura;
    }

    // Calcular cuánto paga el cliente en total (usando precios del hospital)
    public static int calcularMontoCliente(String cedula, JSONArray servicios) {
        int totalCliente = 0;

        try {
            for (int i = 0; i < servicios.length(); i++) {
                String servicio = servicios.getString(i);

                int precioHospital = HospitalDB.obtenerPrecioServicio(servicio);
                int coberturaSeguro = obtenerCobertura(cedula, servicio);

                int pagaCliente = Math.max(precioHospital - coberturaSeguro, 0);

                totalCliente += pagaCliente;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCliente;
    }*/
}
