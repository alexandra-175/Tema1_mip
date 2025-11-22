import java.util.ArrayList;
import java.util.List;

public final class Pizza extends Produs {

    private String blat;
    private String sos;
    private List<String> toppinguri;

    private Pizza(Builder builder) {
        super(builder.nume, builder.pret, builder.vegetarian);
        this.blat = builder.blat;
        this.sos = builder.sos;
        this.toppinguri = builder.toppinguri;
    }

    public static class Builder {

        private String nume;
        private double pret;
        private boolean vegetarian;

        private final String blat;
        private final String sos;
        private List<String> toppinguri = new ArrayList<>();

        public Builder(String nume, double pret, boolean vegetarian, String blat, String sos) {
            this.nume = nume;
            this.pret = pret;
            this.vegetarian = vegetarian;
            this.blat = blat;
            this.sos = sos;
        }

        public Builder adaugaTopping(String topping) {
            toppinguri.add(topping);
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }

    @Override
    public String toString() {
        return nume + " (Pizza) - " + pret + " RON | Blat: " + blat +
                ", Sos: " + sos + ", Toppinguri: " + toppinguri;
    }
}
