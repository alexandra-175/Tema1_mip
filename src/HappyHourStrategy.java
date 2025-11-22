import java.time.LocalTime;

// Implementarea regulii Happy Hour: 20% reducere pentru Băuturi
public class HappyHourStrategy implements DiscountStrategy {

    @Override
    public double aplicaDiscount(Produs produs, int cantitate) {

        // Interval Happy Hour: 17:00 - 19:00
        LocalTime acum = LocalTime.now();
        LocalTime start = LocalTime.of(17, 0);
        LocalTime end = LocalTime.of(19, 0);

        boolean esteHappyHour = acum.isAfter(start) && acum.isBefore(end);

        // Reducere doar pentru băuturi
        if (esteHappyHour && produs instanceof Bautura) {
            double pretBaza = produs.pret * cantitate;
            return pretBaza * 0.20; // 20% reducere
        }

        return 0; // fără reducere
    }
}
