import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.Callable;

public class GuiApp extends Application {

    @Override
    public void start(Stage stage) {
        showLoginScreen(stage);
    }

    /* ===================== HELPER ASYNC ===================== */

    private <T> void loadAsync(
            Runnable onStart,
            Callable<List<T>> taskWork,
            java.util.function.Consumer<List<T>> onSuccess,
            Runnable onFail
    ) {
        new Thread(() -> {
            try {
                Platform.runLater(onStart);
                List<T> result = taskWork.call();
                Platform.runLater(() -> onSuccess.accept(result));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(onFail);
            }
        }).start();
    }

    /* ===================== LOGIN ===================== */

    private void showLoginScreen(Stage stage) {
        Label title = new Label("Restaurant La Andrei");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label subtitle = new Label("Alege rolul cu care intri în aplicație:");
        Button clientBtn = new Button("Client");
        Button ospatarBtn = new Button("Ospătar");
        Button managerBtn = new Button("Manager");

        clientBtn.setPrefWidth(150);
        ospatarBtn.setPrefWidth(150);
        managerBtn.setPrefWidth(150);

        clientBtn.setOnAction(e -> showClientScreen(stage));
        ospatarBtn.setOnAction(e -> showOspatarScreen(stage));
        managerBtn.setOnAction(e -> showManagerScreen(stage));

        VBox root = new VBox(15, title, subtitle, clientBtn, ospatarBtn, managerBtn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        stage.setScene(new Scene(root, 400, 250));
        stage.setTitle("Login - Restaurant La Andrei");
        stage.show();
    }

    /* ===================== CLIENT ===================== */

    private void showClientScreen(Stage stage) {
        ListView<Produs> listaProduse = new ListView<>();
        TextArea detalii = new TextArea();
        detalii.setEditable(false);

        Label loading = new Label("Se încarcă produsele...");
        listaProduse.setPlaceholder(loading);

        loadAsync(
                () -> loading.setText("Loading produse..."),
                Database::loadProducts,
                result -> listaProduse.getItems().setAll(result),
                () -> showError("Eroare DB!")
        );

        listaProduse.getSelectionModel().selectedItemProperty().addListener((o, v, p) -> {
            if (p == null) {
                detalii.clear();
                return;
            }

            StringBuilder t = new StringBuilder();
            t.append("Nume: ").append(p.getNume()).append("\n");
            t.append("Preț: ").append(p.getPret()).append(" RON\n");
            t.append("Vegetarian: ").append(p.isVegetarian() ? "DA" : "NU").append("\n");

            if (p instanceof Mancare m) t.append("Gramaj: ").append(m.getGramaj()).append(" g\n");
            if (p instanceof Bautura b) t.append("Volum: ").append(b.getVolum()).append(" ml\n");

            detalii.setText(t.toString());
        });

        SplitPane splitPane = new SplitPane(listaProduse, detalii);
        splitPane.setDividerPositions(0.4);

        Button back = new Button("← Înapoi");
        back.setOnAction(e -> showLoginScreen(stage));

        BorderPane root = new BorderPane();
        root.setTop(back);
        root.setCenter(splitPane);

        stage.setScene(new Scene(root, 900, 550));
        stage.setTitle("Client");
        stage.show();
    }

    /* ===================== OSPĂTAR ===================== */

    private void showOspatarScreen(Stage stage) {

        ComboBox<Integer> masaCombo = new ComboBox<>();
        masaCombo.getItems().addAll(1,2,3,4,5);
        masaCombo.setPromptText("Selectează masa");

        ListView<Produs> listaProduse = new ListView<>();
        TextArea detalii = new TextArea();
        detalii.setEditable(false);

        Label loading = new Label("Loading produse...");
        listaProduse.setPlaceholder(loading);

        loadAsync(
                () -> loading.setText("Se încarcă produsele..."),
                Database::loadProducts,
                result -> listaProduse.getItems().setAll(result),
                () -> showError("Nu pot încărca produsele din DB")
        );

        listaProduse.getSelectionModel().selectedItemProperty().addListener((obs,v,p) -> {
            if(p==null){
                detalii.clear();
                return;
            }

            StringBuilder t=new StringBuilder();
            t.append("Nume: ").append(p.getNume()).append("\n");
            t.append("Preț: ").append(p.getPret()).append(" RON\n");
            t.append("Vegetarian: ").append(p.isVegetarian()?"DA":"NU").append("\n");

            if(p instanceof Mancare m){
                t.append("Tip: Mâncare\nGramaj: ").append(m.getGramaj()).append(" g\n");
            }
            if(p instanceof Bautura b){
                t.append("Tip: Băutură\nVolum: ").append(b.getVolum()).append(" ml\n");
            }

            detalii.setText(t.toString());
        });

        Button addBtn = new Button("Adaugă în coș");

        ListView<Object> cos = new ListView<>();
        Label totalLabel = new Label("Total: 0 RON");

        addBtn.setOnAction(e -> {
            Produs p = listaProduse.getSelectionModel().getSelectedItem();

            if(masaCombo.getValue() == null){
                showError("Selectează mai întâi masa!");
                return;
            }

            if(p==null){
                showError("Selectează un produs!");
                return;
            }

            cos.getItems().add(p);
            calculeazaTotal(cos,totalLabel);
        });

        Button removeBtn = new Button("Șterge din coș");
        removeBtn.setOnAction(e -> {
            Object sel = cos.getSelectionModel().getSelectedItem();
            if(sel instanceof Produs){
                cos.getItems().remove(sel);
                calculeazaTotal(cos,totalLabel);
            }
        });

        Button finalizeBtn = new Button("Finalizează comanda");
        finalizeBtn.setOnAction(e -> {
            if(masaCombo.getValue()==null){
                showError("Selectează masa!");
                return;
            }

            if(cos.getItems().isEmpty()){
                showError("Coșul este gol!");
                return;
            }

            double total = cos.getItems().stream()
                    .filter(o -> o instanceof Produs)
                    .map(o -> (Produs)o)
                    .mapToDouble(Produs::getPret)
                    .sum();

            Database.saveOrder(
                    masaCombo.getValue(),
                    cos.getItems().stream()
                            .filter(o -> o instanceof Produs)
                            .map(o -> (Produs)o)
                            .toList(),
                    total
            );

            showInfo("Comandă salvată!");
            cos.getItems().clear();
            totalLabel.setText("Total: 0 RON");
        });

        VBox rightTop = new VBox(detalii, addBtn);
        rightTop.setSpacing(10);
        rightTop.setPadding(new Insets(10));

        VBox rightBottom = new VBox(
                new Label("Comandă curentă:"),
                cos,
                removeBtn,
                totalLabel,
                finalizeBtn
        );
        rightBottom.setSpacing(10);
        rightBottom.setPadding(new Insets(10));

        SplitPane right = new SplitPane(rightTop, rightBottom);
        right.setDividerPositions(0.45);

        SplitPane main = new SplitPane(listaProduse, right);
        main.setDividerPositions(0.35);

        Button backBtn = new Button("← Înapoi la Login");
        backBtn.setOnAction(e -> showLoginScreen(stage));

        HBox top = new HBox(backBtn, new Label("   Masa: "), masaCombo);
        top.setSpacing(10);
        top.setPadding(new Insets(10));

        BorderPane comandaPane = new BorderPane();
        comandaPane.setTop(top);
        comandaPane.setCenter(main);

        TabPane tabs = new TabPane();

        Tab t1 = new Tab("Comandă Nouă", comandaPane);
        t1.setClosable(false);

        ListView<String> istoric = new ListView<>();
        istoric.setPlaceholder(new Label("Se încarcă comenzile..."));

        loadAsync(
                () -> {},
                Database::loadOrders,
                result -> istoric.getItems().setAll(result),
                () -> showError("Nu pot încărca comenzile")
        );

        Button refresh = new Button("Reîncarcă");
        refresh.setOnAction(e -> istoric.getItems().setAll(Database.loadOrders()));

        VBox historyLayout = new VBox(10, refresh, istoric);
        historyLayout.setPadding(new Insets(10));

        Tab t2 = new Tab("Istoric Comenzi", historyLayout);
        t2.setClosable(false);

        tabs.getTabs().addAll(t1,t2);

        BorderPane root = new BorderPane();
        root.setCenter(tabs);

        stage.setScene(new Scene(root, 950, 600));
        stage.setTitle("Ospătar");
        stage.show();
    }

    /* ===================== DISCOUNT ===================== */

    private void calculeazaTotal(ListView<Object> cos, Label totalLabel) {

        double totalProduse = cos.getItems().stream()
                .filter(o -> o instanceof Produs)
                .map(o -> (Produs)o)
                .mapToDouble(Produs::getPret)
                .sum();

        double discount = 0;

        cos.getItems().removeIf(it -> it instanceof String && it.toString().startsWith("Reducere"));

        var bauturi = cos.getItems().stream()
                .filter(o -> o instanceof Bautura)
                .map(o -> (Produs)o)
                .sorted((a,b) -> Double.compare(a.getPret(), b.getPret()))
                .toList();

        if(bauturi.size() >= 2){
            for(int i=1; i<bauturi.size(); i+=2){
                double reducere = bauturi.get(i).getPret() / 2;
                discount += reducere;
                cos.getItems().add("Reducere Happy Hour: -" + reducere + " RON");
            }
        }

        var pizza = cos.getItems().stream()
                .filter(o -> o instanceof Mancare)
                .map(o -> (Produs)o)
                .filter(p -> p.getNume().toLowerCase().contains("pizza"))
                .sorted((a,b)->Double.compare(a.getPret(), b.getPret()))
                .toList();

        if(pizza.size() >= 4){
            double reducere = pizza.get(0).getPret();
            discount += reducere;
            cos.getItems().add("Reducere Party Pack Pizza: -" + reducere + " RON");
        }

        totalLabel.setText("Total: " + (totalProduse - discount) + " RON");
    }

    /* ===================== MANAGER ===================== */

    private void showManagerScreen(Stage stage) {

        ListView<String> listaComenzi = new ListView<>();
        ListView<String> listaDetalii = new ListView<>();

        listaComenzi.setPlaceholder(new Label("Loading comenzi..."));

        loadAsync(
                () -> {},
                Database::loadOrders,
                result -> listaComenzi.getItems().setAll(result),
                () -> showError("Nu pot încărca comenzile")
        );

        listaComenzi.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
            if (val == null) return;

            listaDetalii.setPlaceholder(new Label("Loading..."));

            String idPart = val.split("\\|")[0];
            int orderId = Integer.parseInt(idPart.replaceAll("[^0-9]", ""));

            loadAsync(
                    () -> {},
                    () -> Database.loadOrderDetails(orderId),
                    result -> listaDetalii.getItems().setAll(result),
                    () -> showError("Nu pot încărca detalii comandă!")
            );
        });

        SplitPane split = new SplitPane(listaComenzi, listaDetalii);
        split.setDividerPositions(0.4);

        Button backBtn = new Button("← Înapoi");
        backBtn.setOnAction(e -> showLoginScreen(stage));

        VBox root = new VBox(10, backBtn, split);
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root, 800, 500));
        stage.setTitle("Manager");
        stage.show();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setHeaderText("Eroare");
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText("Info");
        a.showAndWait();
    }
}
