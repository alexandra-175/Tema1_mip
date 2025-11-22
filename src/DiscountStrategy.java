// Interfață pentru toate regulile de discount
public interface DiscountStrategy {
    double aplicaDiscount(Produs produs, int cantitate);
}
