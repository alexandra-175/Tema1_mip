public final class Mancare extends Produs {

    private int gramaj;

    public Mancare(String nume, double pret, boolean vegetarian, int gramaj) {
        super(nume, pret, vegetarian);
        this.gramaj = gramaj;
    }

    @Override
    public String toString() {
        return nume + " - " + pret + " RON - Gramaj: " + gramaj + "g";
    }
}
