// Clasa Bautura moștenește Produs
// Adaugă un atribut specific: volumul
public class Bautura extends Produs {

    // Atribut specific băuturilor (în mililitri)
    private int volum;

    // Constructor pentru Bautura
    public Bautura(String nume, double pret, int volum) {
        super(nume, pret);      // apelăm constructorul lui Produs
        this.volum = volum;     // setăm volumul băuturii
    }

    // Suprascriem metoda toString() pentru afișare completă
    @Override
    public String toString() {
        return nume + " - " + pret + " RON - Volum: " + volum + "ml";
    }
}
