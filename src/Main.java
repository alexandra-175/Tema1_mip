import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // ====== ITERAȚIA 4 ======
        Config config = Config.incarcaConfiguratia();

        System.out.println("Aplicatia rulează pentru restaurantul: " + config.numeRestaurant);

        // setăm TVA în Comanda
        Comanda.setTva(config.tva);


        //        ITERATIA 1
        System.out.println("---- Meniul Restaurantului \"La Andrei\" (Iterația 1) ----");

        ArrayList<Produs> meniuSimplu = new ArrayList<>();

        Produs pizzaMargherita = new Mancare("Pizza Margherita", 45.0, true, 450);
        Produs pasteCarbonara = new Mancare("Paste Carbonara", 52.5, false, 400);
        Produs limonada = new Bautura("Limonada", 15.0, true, 500);
        Produs apaPlata = new Bautura("Apa Plata", 8.0, true, 500);

        meniuSimplu.add(pizzaMargherita);
        meniuSimplu.add(pasteCarbonara);
        meniuSimplu.add(limonada);
        meniuSimplu.add(apaPlata);

        for (Produs p : meniuSimplu) {
            System.out.println(p);
        }

        System.out.println();



        //        ITERATIA 2
        System.out.println("---- Sistem Comenzi Restaurant \"La Andrei\" (Iterația 2) ----");

        DiscountStrategy happyHour = new HappyHourStrategy();
        Comanda comanda = new Comanda(happyHour);

        comanda.adaugaProdus(pizzaMargherita, 2);
        comanda.adaugaProdus(pasteCarbonara, 1);
        comanda.adaugaProdus(limonada, 3);
        comanda.adaugaProdus(apaPlata, 2);

        double totalFaraTVA = comanda.calculeazaTotalFaraTVA();
        double totalCuTVA = comanda.calculeazaTotalCuTVA();

        System.out.println("Total fără TVA: " + totalFaraTVA + " RON");
        System.out.println("Total cu TVA: " + totalCuTVA + " RON");

        System.out.println();



        //        ITERATIA 3

        System.out.println("---- Meniu structurat pe categorii (Iterația 3) ----");

        Meniu meniu = new Meniu();

        // Aperitive
        meniu.adaugaProdus(Categorie.APERITIVE,
                new Mancare("Bruschette cu rosii", 25.0, true, 200));
        meniu.adaugaProdus(Categorie.APERITIVE,
                new Mancare("Platou mezeluri", 40.0, false, 300));

        // Fel principal
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, pizzaMargherita);
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, pasteCarbonara);

        // Deserturi
        meniu.adaugaProdus(Categorie.DESERT,
                new Mancare("Tiramisu", 28.0, true, 150));
        meniu.adaugaProdus(Categorie.DESERT,
                new Mancare("Papanasi", 30.0, false, 200));

        // Băuturi răcoritoare
        meniu.adaugaProdus(Categorie.BAUTURI_RACORITOARE, limonada);
        meniu.adaugaProdus(Categorie.BAUTURI_RACORITOARE, apaPlata);

        // Băuturi alcoolice
        meniu.adaugaProdus(Categorie.BAUTURI_ALCOOLICE,
                new Bautura("Vin rosu", 60.0, true, 150));

        // Pizza customizabilă (două tipuri)
        Pizza pizzaVeg = new Pizza.Builder(
                "Pizza Verdure", 55.0, true,
                "Blat subtire", "Sos rosii")
                .adaugaTopping("Mozzarella")
                .adaugaTopping("Ciuperci")
                .adaugaTopping("Ardei")
                .build();

        Pizza pizzaCarnivora = new Pizza.Builder(
                "Pizza Carnivora", 70.0, false,
                "Blat pufos", "Sos rosii")
                .adaugaTopping("Mozzarella")
                .adaugaTopping("Salam")
                .adaugaTopping("Bacon")
                .build();

        meniu.adaugaProdus(Categorie.PIZZA, pizzaVeg);
        meniu.adaugaProdus(Categorie.PIZZA, pizzaCarnivora);

        // Afișăm meniul pe categorii
        for (Categorie c : Categorie.values()) {
            System.out.println("Categoria: " + c);
            for (Produs p : meniu.getProduseDinCategorie(c)) {
                System.out.println("  " + p);
            }
        }

        System.out.println();

        // Interogări complexe

        System.out.println("Produse vegetariene (sortate alfabetic):");
        for (Produs p : meniu.getVegetarieneSortate()) {
            System.out.println("  " + p);
        }

        double pretMediuDeserturi = meniu.getPretMediuDeserturi();
        System.out.println("Preț mediu deserturi: " + pretMediuDeserturi + " RON");

        boolean existaPeste100 = meniu.existaProdusPeste100();
        System.out.println("Există produs cu preț > 100 RON? " + (existaPeste100 ? "DA" : "NU"));

        System.out.println();

        // Căutare sigură în meniu (Optional)
        String numeCautat1 = "Limonada";
        String numeCautat2 = "Shaorma";

        meniu.cautaProdusDupaNume(numeCautat1)
                .ifPresentOrElse(
                        p -> System.out.println("Produs găsit: " + p),
                        () -> System.out.println("Produsul \"" + numeCautat1 + "\" nu a fost găsit.")
                );

        meniu.cautaProdusDupaNume(numeCautat2)
                .ifPresentOrElse(
                        p -> System.out.println("Produs găsit: " + p),
                        () -> System.out.println("Produsul \"" + numeCautat2 + "\" nu a fost găsit.")
                );
        // Export în JSON
        meniu.exportToJson("menu.json");

    }
}
