import java.util.ArrayList;

// Clasa principală a programului
public class Main {
    public static void main(String[] args) {

        // Afișăm titlul meniului
        System.out.println("---- Meniul Restaurantului \"La Andrei\" ----");

        // Creăm o listă polimorfă de produse
        // Putem adăuga atât Mancare, cât și Bautura deoarece ambele sunt Produs
        ArrayList<Produs> meniu = new ArrayList<>();

        // Adăugăm produse în meniu (valorile sunt hardcodate, conform cerinței)
        meniu.add(new Mancare("Pizza Margherita", 45.0, 450));
        meniu.add(new Mancare("Paste Carbonara", 52.5, 400));
        meniu.add(new Bautura("Limonada", 15.0, 500));
        meniu.add(new Bautura("Apa Plata", 8.0, 500));

        // Parcurgem lista și afișăm fiecare produs
        // Aici se vede polimorfismul: se apelează toString() specific clasei reale
        for (Produs p : meniu) {
            System.out.println(p);
        }
    }
}
