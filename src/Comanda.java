import java.util.HashMap;
import java.util.Map;

// Clasa Comanda gestionează produsele și calculele aferente
public class Comanda {

    // Map care reține fiecare produs + cantitatea cerută
    private Map<Produs, Integer> produse = new HashMap<>();

    // TVA fix (9%)
    private static final double TVA = 0.09;

    // Strategy pentru discount (ex: Happy Hour)
    private DiscountStrategy discountStrategy;

    // Constructor care permite setarea unei strategii de discount
    public Comanda(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    // Adăugăm un produs împreună cu o cantitate (ex: 2 Pizza)
    public void adaugaProdus(Produs produs, int cantitate) {
        produse.put(produs, produse.getOrDefault(produs, 0) + cantitate);
    }

    // Calcul total înainte de TVA, dar ținând cont de discount-uri
    public double calculeazaTotalFaraTVA() {
        double total = 0;

        for (Map.Entry<Produs, Integer> entry : produse.entrySet()) {
            Produs produs = entry.getKey();
            int cantitate = entry.getValue();

            // Prețul fără discount
            double pretBaza = produs.pret * cantitate;

            // Aplicăm discount-ul
            double discount = discountStrategy.aplicaDiscount(produs, cantitate);

            total += (pretBaza - discount);
        }
        return total;
    }

    // Calcul total CU TVA
    public double calculeazaTotalCuTVA() {
        double totalFaraTVA = calculeazaTotalFaraTVA();
        return totalFaraTVA + totalFaraTVA * TVA;
    }
}
