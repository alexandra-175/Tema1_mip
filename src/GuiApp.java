import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GuiApp extends Application {

    @Override
    public void start(Stage stage) {
        showLoginScreen(stage);
    }

    /* =====================  ECRAN LOGIN  ===================== */

    private void showLoginScreen(Stage stage) {
        Label title = new Label("Restaurant La Andrei");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label subtitle = new Label("Alege rolul cu care intri în aplicație:");
        subtitle.setStyle("-fx-font-size: 14px;");

        Button clientBtn = new Button("Client");
        Button ospatarBtn = new Button("Ospătar");
        Button managerBtn = new Button("Manager");

        clientBtn.setPrefWidth(150);
        ospatarBtn.setPrefWidth(150);
        managerBtn.setPrefWidth(150);

        // ----- ACȚIUNI BUTOANE -----
        clientBtn.setOnAction(e -> showClientScreen(stage));
        ospatarBtn.setOnAction(e -> showOspatarScreen(stage));
        managerBtn.setOnAction(e -> showManagerScreen(stage));

        VBox root = new VBox(15, title, subtitle, clientBtn, ospatarBtn, managerBtn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 400, 250);
        stage.setTitle("Login - Restaurant La Andrei");
        stage.setScene(scene);
        stage.show();
    }

    /* =====================  ECRAN CLIENT (Guest Mode)  ===================== */

    private void showClientScreen(Stage stage) {
        // Listă cu produse (stânga)
        ListView<Produs> listaProduse = new ListView<>();

        // Detalii produs (dreapta)
        TextArea detalii = new TextArea();
        detalii.setEditable(false);

        // TODO: aici folosim baza de date (clasa ta Database) ca în Iterația 6.
        //      ADAPTEAZĂ numele metodei astfel încât să folosești exact ce foloseai înainte
        //      când îți încărcai produsele pentru GUI.

        try {
            // Exemplu generic – schimbă Database.getAllProduse() cu metoda ta reală
            List<Produs> produse = Database.loadProducts();
            // <<< ADAPTEAZĂ AICI
            listaProduse.getItems().setAll(produse);
            System.out.println("Produsele au fost încărcate din baza de date (Client Mode).");
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Eroare la încărcarea produselor din baza de date:\n" + ex.getMessage());
        }

        // Când selectez un produs -> afișez detalii
        listaProduse.getSelectionModel().selectedItemProperty().addListener((obs, vechi, produs) -> {
            if (produs == null) {
                detalii.clear();
                return;
            }

            StringBuilder text = new StringBuilder();
            text.append("Nume: ").append(produs.getNume()).append("\n");
            text.append("Preț: ").append(produs.getPret()).append(" RON\n");
            text.append("Vegetarian: ").append(produs.isVegetarian() ? "DA" : "NU").append("\n");

            if (produs instanceof Mancare m) {
                text.append("Tip: Mâncare\n");
                text.append("Gramaj: ").append(m.getGramaj()).append(" g\n");
            }

            if (produs instanceof Bautura b) {
                text.append("Tip: Băutură\n");
                text.append("Volum: ").append(b.getVolum()).append(" ml\n");
            }

            detalii.setText(text.toString());
        });

        // Layout: stânga listă, dreapta detalii
        BorderPane root = new BorderPane();
        VBox layout = new VBox(filters, splitPane);
        root.setCenter(layout);

        root.setLeft(listaProduse);
        root.setCenter(detalii);

        BorderPane.setMargin(listaProduse, new Insets(10));
        BorderPane.setMargin(detalii, new Insets(10));

        // Bară de sus simplă cu buton de întoarcere la login
        Button backBtn = new Button("← Înapoi la Login");
        backBtn.setOnAction(e -> showLoginScreen(stage));
        HBox topBar = new HBox(backBtn);
        topBar.setPadding(new Insets(5));
        root.setTop(topBar);

        Scene scene = new Scene(root, 700, 450);
        stage.setTitle("Client - Restaurant La Andrei");
        stage.setScene(scene);
        stage.show();


        ComboBox<String> filterVeg = new ComboBox<>();
        filterVeg.getItems().addAll("Toate", "Vegetariene", "Non-Vegetariene");
        filterVeg.setValue("Toate");

        ComboBox<String> filterTip = new ComboBox<>();
        filterTip.getItems().addAll("Toate", "Mancare", "Bautura");
        filterTip.setValue("Toate");

        TextField minPrice = new TextField();
        minPrice.setPromptText("Min");

        TextField maxPrice = new TextField();
        maxPrice.setPromptText("Max");

        TextField search = new TextField();
        search.setPromptText("Cauta produs...");

        HBox filters = new HBox(10, filterVeg, filterTip, minPrice, maxPrice, search);
        filters.setPadding(new Insets(10));

    }

    /* =====================  ECRAN OSPĂTAR (placeholder)  ===================== */

    private void showOspatarScreen(Stage stage) {
        Label label = new Label("Ecranul pentru Ospătar încă nu este implementat.\n"
                + "Îl facem după ce terminăm complet modul Client. 🙂");

        Button backBtn = new Button("← Înapoi la Login");
        backBtn.setOnAction(e -> showLoginScreen(stage));

        VBox root = new VBox(15, label, backBtn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 500, 250);
        stage.setTitle("Ospătar - în lucru");
        stage.setScene(scene);
        stage.show();
    }

    /* =====================  ECRAN MANAGER (placeholder)  ===================== */

    private void showManagerScreen(Stage stage) {
        Label label = new Label("Ecranul pentru Manager încă nu este implementat.\n"
                + "Îl facem după ce terminăm Client și Ospătar. 👀");

        Button backBtn = new Button("← Înapoi la Login");
        backBtn.setOnAction(e -> showLoginScreen(stage));

        VBox root = new VBox(15, label, backBtn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 500, 250);
        stage.setTitle("Manager - în lucru");
        stage.setScene(scene);
        stage.show();
    }

    /* =====================  HELPER ALERTS  ===================== */

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setHeaderText("Eroare");
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
