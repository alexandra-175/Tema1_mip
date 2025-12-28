import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GuiApp extends Application {

    @Override
    public void start(Stage stage) {

        // Creăm meniul
        Meniu meniu = new Meniu();

        // Adăugăm produse reale
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL,
                new Mancare("Pizza Margherita", 45, false, 450));

        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL,
                new Mancare("Paste Carbonara", 52.5, false, 400));

        meniu.adaugaProdus(Categorie.BAUTURI_RACORITOARE,
                new Bautura("Limonada", 15, true, 500));

        meniu.adaugaProdus(Categorie.BAUTURI_RACORITOARE,
                new Bautura("Apa Plata", 8, true, 500));

        // LISTA din stânga
        ListView<Produs> listaProduse = new ListView<>();
        listaProduse.getItems().addAll(meniu.toateProdusele());

        // Panoul de DETALII din dreapta
        TextArea detalii = new TextArea();
        detalii.setEditable(false);

        // Când selectez un produs -> afișez detalii
        listaProduse.getSelectionModel().selectedItemProperty().addListener((obs, vechi, produs) -> {
            if (produs == null)
                return;

            StringBuilder text = new StringBuilder();
            text.append("Nume: ").append(produs.getNume()).append("\n");
            text.append("Preț: ").append(produs.getPret()).append(" RON\n");
            text.append("Vegetarian: ").append(produs.isVegetarian() ? "DA" : "NU").append("\n");

            if (produs instanceof Mancare m)
                text.append("Gramaj: ").append(m.getGramaj()).append(" g\n");

            if (produs instanceof Bautura b)
                text.append("Volum: ").append(b.getVolum()).append(" ml\n");

            detalii.setText(text.toString());
        });

        // Layout
        BorderPane root = new BorderPane();
        root.setLeft(listaProduse);
        root.setCenter(detalii);

        // Fereastra
        Scene scene = new Scene(root, 650, 450);
        stage.setTitle("Restaurant La Andrei - Meniu (Iterația 5)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
