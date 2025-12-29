import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_andrei";
    private static final String USER = "postgres";
    private static final String PASS = "1q2w3e";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ================= SALVARE PRODUSE =================
    public static void saveProducts(List<Produs> produse) {
        String sql = "INSERT INTO produse(nume, pret, vegetarian, tip, gramaj, volum) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            for (Produs p : produse) {
                st.setString(1, p.getNume());
                st.setDouble(2, p.getPret());
                st.setBoolean(3, p.isVegetarian());

                if (p instanceof Mancare m) {
                    st.setString(4, "mancare");
                    st.setInt(5, m.getGramaj());
                    st.setNull(6, java.sql.Types.INTEGER);
                } else if (p instanceof Bautura b) {
                    st.setString(4, "bautura");
                    st.setNull(5, java.sql.Types.INTEGER);
                    st.setInt(6, b.getVolum());
                }

                st.executeUpdate();
            }

            System.out.println("✔ Produsele au fost salvate în baza de date!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= CITIRE PRODUSE =================
    public static List<Produs> loadProducts() {
        List<Produs> list = new ArrayList<>();

        String sql = "SELECT * FROM produse";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String tip = rs.getString("tip");

                if (tip.equals("mancare")) {
                    list.add(new Mancare(
                            rs.getString("nume"),
                            rs.getDouble("pret"),
                            rs.getBoolean("vegetarian"),
                            rs.getInt("gramaj")
                    ));
                } else {
                    list.add(new Bautura(
                            rs.getString("nume"),
                            rs.getDouble("pret"),
                            rs.getBoolean("vegetarian"),
                            rs.getInt("volum")
                    ));
                }
            }

            System.out.println("✔ Produsele au fost încărcate din baza de date!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static void saveOrder(int masa, List<Produs> produse, double total) {
        String insertOrder = "INSERT INTO comenzi(masa, total) VALUES (?, ?) RETURNING id";
        String insertProd = "INSERT INTO comenzi_produse(comanda_id, produs, pret) VALUES (?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            int comandaId;

            try (PreparedStatement st = conn.prepareStatement(insertOrder)) {
                st.setInt(1, masa);
                st.setDouble(2, total);
                ResultSet rs = st.executeQuery();
                rs.next();
                comandaId = rs.getInt(1);
            }

            try (PreparedStatement st = conn.prepareStatement(insertProd)) {
                for (Produs p : produse) {
                    st.setInt(1, comandaId);
                    st.setString(2, p.toString());
                    st.setDouble(3, p.getPret());
                    st.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("✔ Comandă salvată în DB!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<String> loadOrders() {
        List<String> list = new ArrayList<>();

        String sql = "SELECT c.id, c.masa, c.total " +
                "FROM comenzi c ORDER BY c.id DESC";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add("Comanda #" + rs.getInt("id")
                        + " | Masa " + rs.getInt("masa")
                        + " | Total: " + rs.getDouble("total") + " RON");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static List<String> loadOrderDetails(int id) {
        List<String> list = new ArrayList<>();

        String sql = """
        SELECT produs, pret 
        FROM comenzi_produse 
        WHERE comanda_id = ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("produs") + " - " +
                        rs.getDouble("pret") + " RON");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



}
