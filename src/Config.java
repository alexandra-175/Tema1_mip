import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {

    public String numeRestaurant;
    public double tva;

    public static Config incarcaConfiguratia() {

        Gson gson = new Gson();

        try {
            FileReader reader = new FileReader("config.json");
            return gson.fromJson(reader, Config.class);

        } catch (FileNotFoundException e) {
            System.err.println("❗ Eroare: Fișierul config.json nu a fost găsit. Se folosesc valori implicite.");
            Config defaultConfig = new Config();
            defaultConfig.numeRestaurant = "Restaurant Necunoscut";
            defaultConfig.tva = 0.09;
            return defaultConfig;

        } catch (JsonSyntaxException e) {
            System.err.println("❗ Eroare: Fișierul config.json este corupt sau invalid. Se folosesc valori implicite.");
            Config defaultConfig = new Config();
            defaultConfig.numeRestaurant = "Restaurant Necunoscut";
            defaultConfig.tva = 0.09;
            return defaultConfig;
        }
    }
}
