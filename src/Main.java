public class Main {

    public static void main(String[] args) {

        // ================== CONSTRUIM MENIUL (ca în iterațiile vechi) ==================
        Meniu meniu = new Meniu();

        // Aperitive
        meniu.adaugaProdus(Categorie.APERITIVE,
                new Mancare("Bruschette cu rosii", 25.0, true, 200));
        meniu.adaugaProdus(Categorie.APERITIVE,
                new Mancare("Platou mezeluri", 40.0, false, 300));

        // Fel principal
        Produs pizzaMargherita = new Mancare("Pizza Margherita", 45.0, false, 450);
        Produs pasteCarbonara = new Mancare("Paste Carbonara", 52.5, false, 400);

        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, pizzaMargherita);
        meniu.adaugaProdus(Categorie.FEL_PRINCIPAL, pasteCarbonara);

        // Băuturi răcoritoare
        Produs limonada = new Bautura("Limonada", 15.0, true, 500);
        Produs apaPlata = new Bautura("Apa Plata", 8.0, true, 500);

        meniu.adaugaProdus(Categorie.BAUTURI_RACORITOARE, limonada);
        meniu.adaugaProdus(Categorie.BAUTURI_RACORITOARE, apaPlata);

        // Băuturi alcoolice
        meniu.adaugaProdus(Categorie.BAUTURI_ALCOOLICE,
                new Bautura("Vin rosu", 60.0, true, 150));

        // ================== SALVĂM ÎN BAZA DE DATE (O DATĂ!) ==================
        Database.saveProducts(meniu.toateProdusele());

        // (opțional) mesaj în consolă
        System.out.println("Meniul a fost salvat în baza de date.");

        // ================== PORNIM INTERFAȚA GRAFICĂ ==================
        //GuiApp.main(args);
    }
}
