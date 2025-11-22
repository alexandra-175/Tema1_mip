public final class Bautura extends Produs {

    private int volum;

    public Bautura(String nume, double pret, boolean vegetarian, int volum) {
        super(nume, pret, vegetarian);
        this.volum = volum;
    }

    @Override
    public String toString() {
        return nume + " - " + pret + " RON - Volum: " + volum + "ml";
    }
}
