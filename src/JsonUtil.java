import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static void exportToJson(List<Produs> produse, String file) throws Exception {
        try (FileWriter w = new FileWriter(file)) {
            gson.toJson(produse, w);
        }
    }

    public static List<Produs> importFromJson(String file) throws Exception {
        Type listType = new TypeToken<List<Produs>>() {}.getType();
        try (FileReader r = new FileReader(file)) {
            return gson.fromJson(r, listType);
        }
    }
}
