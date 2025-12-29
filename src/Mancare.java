import jakarta.persistence.*;

@Entity
@DiscriminatorValue("MANCARE")
public class Mancare extends Produs {

    private int gramaj;

    public Mancare() {}

    public Mancare(String nume, double pret, boolean vegetarian, int gramaj) {
        super(nume, pret, vegetarian);
        this.gramaj = gramaj;
    }

    @Override
    public String toString() {
        return super.toString() + " - Gramaj: " + gramaj + "g";
    }
    public int getGramaj() {
        return gramaj;
    }

}
