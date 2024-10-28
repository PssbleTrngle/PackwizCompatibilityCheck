package com.possible_triangle.packwiz_compatibility_check;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.network.protocol.status.ServerStatus;

import java.io.File;
import java.io.FileReader;
import java.util.Optional;

public class VersionResolver {

    private static final File PACKWIZ_FILE = new File("packwiz.json");
    private static String cachedVersion = null;

    private static final Gson GSON = new GsonBuilder().create();

    private static Optional<String> getHash() {
        if(cachedVersion != null) return Optional.of(cachedVersion);

        if(!PACKWIZ_FILE.exists()) {
            Constants.LOGGER.warn("Unable to locate packwiz.json file");
            return Optional.empty();
        }

        try(var reader = new FileReader(PACKWIZ_FILE)) {
            var json = GSON.fromJson(reader, JsonObject.class);
            var packFileHash = json.getAsJsonObject("packFileHash").get("value").getAsString();
            return Optional.of(packFileHash);
        } catch(Exception ex) {
            Constants.LOGGER.error("An error occured parsing packwiz.json file:");
            Constants.LOGGER.error(ex.toString());
            return Optional.empty();
        }
    }

    public static Optional<ServerStatus.Version> get() {
        return getHash().map(hash -> new ServerStatus.Version(hash, -1));
    }

}
