public sealed class Produs permits Mancare, Bautura, Pizza {

    protected String nume;
    protected double pret;
    protected boolean vegetarian;

    public Produs(String nume, double pret, boolean vegetarian) {
        this.nume = nume;
        this.pret = pret;
        this.vegetarian = vegetarian;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    @Override
    public String toString() {
        return nume + " - " + pret + " RON";
    }
}
