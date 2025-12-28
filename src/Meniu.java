import java.util.*;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;


public class Meniu {

    private Map<Categorie, List<Produs>> categorii = new HashMap<>();

    public Meniu() {
        for (Categorie c : Categorie.values()) {
            categorii.put(c, new ArrayList<>());
        }
    }
    // Exportăm meniul în format JSON
    public void exportToJson(String filename) {

        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(filename)) {

            gson.toJson(categorii, writer);
            System.out.println("✔ Meniul a fost exportat în " + filename);

        } catch (IOException e) {
            System.err.println(" Eroare la exportul meniului: " + e.getMessage());
        }
    }

    public void adaugaProdus(Categorie categorie, Produs produs) {
        categorii.get(categorie).add(produs);
    }

    public List<Produs> getProduseDinCategorie(Categorie categorie) {
        return categorii.get(categorie);
    }

    // 1) Produse vegetariene sortate alfabetic
    public List<Produs> getVegetarieneSortate() {
        return categorii.values().stream()
                .flatMap(List::stream)
                .filter(Produs::isVegetarian)
                .sorted(Comparator.comparing(p -> p.nume))
                .collect(Collectors.toList());
    }

    // 2) Preț mediu deserturi
    public double getPretMediuDeserturi() {
        return categorii.get(Categorie.DESERT).stream()
                .mapToDouble(p -> p.pret)
                .average()
                .orElse(0.0);
    }

    // 3) Există produs > 100 RON?
    public boolean existaProdusPeste100() {
        return categorii.values().stream()
                .flatMap(List::stream)
                .anyMatch(p -> p.pret > 100);
    }

    // 4) Căutare sigură cu Optional
    public java.util.Optional<Produs> cautaProdusDupaNume(String nume) {
        return categorii.values().stream()
                .flatMap(List::stream)
                .filter(p -> p.nume.equalsIgnoreCase(nume))
                .findFirst();
    }
    public List<Produs> toateProdusele() {
        return categorii.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

}
