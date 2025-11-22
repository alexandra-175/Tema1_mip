// Clasa de bază pentru toate produsele din meniu
public class Produs {

    // Atribute comune tuturor produselor
    protected String nume;
    protected double pret;

    // Constructor care inițializează numele și prețul
    public Produs(String nume, double pret) {
        this.nume = nume;   // atribuim numele produsului
        this.pret = pret;   // atribuim prețul produsului
    }

    // Metodă folosită pentru afișarea produsului sub formă de text
    // Va fi suprascrisă în clasele copil pentru afișări specifice
    @Override
    public String toString() {
        return nume + " - " + pret + " RON";
    }
}
