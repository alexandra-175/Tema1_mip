import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tip_produs")
public abstract class Produs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nume;
    protected double pret;
    protected boolean vegetarian;

    public Produs() {}

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
    public String getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }



}
