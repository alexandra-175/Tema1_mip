import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // ============================
        //        ITERATIA 1
        // ============================
        System.out.println("---- Meniul Restaurantului \"La Andrei\" ----");

        ArrayList<Produs> meniu = new ArrayList<>();

        meniu.add(new Mancare("Pizza Margherita", 45.0, 450));
        meniu.add(new Mancare("Paste Carbonara", 52.5, 400));
        meniu.add(new Bautura("Limonada", 15.0, 500));
        meniu.add(new Bautura("Apa Plata", 8.0, 500));

        for (Produs p : meniu) {
            System.out.println(p);
        }

        System.out.println(); // Linie goală


        // ============================
        //        ITERATIA 2
        // ============================
        System.out.println("---- Sistem Comenzi Restaurant \"La Andrei\" ----");

        // Aplicăm Happy Hour (discount pentru băuturi)
        DiscountStrategy happyHour = new HappyHourStrategy();

        // Creăm comanda și îi dăm strategia
        Comanda comanda = new Comanda(happyHour);

        // Adaugăm produse + cantități
        comanda.adaugaProdus(new Mancare("Pizza Margherita", 45.0, 450), 2);
        comanda.adaugaProdus(new Mancare("Paste Carbonara", 52.5, 400), 1);
        comanda.adaugaProdus(new Bautura("Limonada", 15.0, 500), 3);
        comanda.adaugaProdus(new Bautura("Apa Plata", 8.0, 500), 2);

        // Afișăm totalurile
        System.out.println("Total fără TVA: " + comanda.calculeazaTotalFaraTVA() + " RON");
        System.out.println("Total cu TVA: " + comanda.calculeazaTotalCuTVA() + " RON");
    }
}
