package net.truth.koth.config;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.loader.api.FabricLoader;
import net.truth.koth.KingOfTheHill;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;

public class Config {
    private static final int REQUIRED_VERSION = 1;
    private static final File file = FabricLoader.getInstance().getGameDir().resolve("config").resolve(KingOfTheHill.MOD_ID + ".json").toFile();

    public static Config load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .serializeNulls()
                .create();

        try {
            if (!file.exists()) {
                file.getParentFile().mkdir();

                Config defaults = new Config();

                FileWriter writer = new FileWriter(file);
                gson.toJson(defaults, writer);
                writer.close();

                return defaults;
            }

            Config config = gson.fromJson(new FileReader(file), Config.class);

            if (config.VERSION != REQUIRED_VERSION) {
                KingOfTheHill.LOGGER.error(String.format("Config file incompatible (requires version %d)", REQUIRED_VERSION));
            }

            return config;
        } catch (Exception e) {
            KingOfTheHill.LOGGER.error("An error occurred reading the factions config file", e);
            return new Config();
        }
    }

    @SerializedName("configVersion")
    public int VERSION = REQUIRED_VERSION;
    @SerializedName("exactCoordinates")
    public boolean EXACT_COORDINATES = false;
    @SerializedName("verticalShowRadius")
    public int VERTICAL_RADIUS = 16;

    public static class Deserializer<T> implements JsonDeserializer<T> {
        final Class<T> clazz;

        public Deserializer(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!json.isJsonObject() && !json.getAsBoolean()) {
                return null;
            }

            return new Gson().fromJson(json, clazz);
        }
    }
}
