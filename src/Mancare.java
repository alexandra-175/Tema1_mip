// Clasa Mancare moștenește Produs
// Adaugă un atribut specific: gramajul
public class Mancare extends Produs {

    // Atribut specific mâncării (în grame)
    private int gramaj;

    // Constructorul clasei Mancare
    public Mancare(String nume, double pret, int gramaj) {
        super(nume, pret);      // apelăm constructorul clasei părinte Produs
        this.gramaj = gramaj;   // setăm gramajul specific mâncării
    }

    // Suprascriem metoda toString() pentru afișare completă
    @Override
    public String toString() {
        return nume + " - " + pret + " RON - Gramaj: " + gramaj + "g";
    }
}
