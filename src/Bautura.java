import jakarta.persistence.*;

@Entity
@DiscriminatorValue("BAUTURA")
public class Bautura extends Produs {

    private int volum;

    public Bautura() {}

    public Bautura(String nume, double pret, boolean vegetarian, int volum) {
        super(nume, pret, vegetarian);
        this.volum = volum;
    }

    @Override
    public String toString() {
        return super.toString() + " - Volum: " + volum + "ml";
    }
    public int getVolum() {
        return volum;
    }

}
