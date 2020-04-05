package com.mcsunnyside.cloudban;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Data
public class BanList {
    private int version  = 1;
    private List<BanContainer> bans = new ArrayList<>();

    public static BanList load(@NotNull File file) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        return gson.fromJson(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8), BanList.class);
    }

    public static boolean save(@NotNull File file, @NotNull Config config) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String json = gson.toJson(config);
        try {
            Files.write(file.toPath(), json.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
@Data
class BanContainer{
    @NotNull private UUID uuid;
    @NotNull private UUID serverId;
    private long banTime;
    @NotNull private String reason;
}
