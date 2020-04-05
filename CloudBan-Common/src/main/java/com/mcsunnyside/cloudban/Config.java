package com.mcsunnyside.cloudban;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@EqualsAndHashCode
public final class Config {
    private int version = 1;
    private List<SubscriptionContainer> subscriptions = new ArrayList<>();
    private List<UUID> whitelistuuid = new ArrayList<>();
    private Settings settings = new Settings();
    private Network network = new Network();
    private ServerID serverID = new ServerID();
    private WebService webService = new WebService();
    private Message message = new Message();

    public static Config load(@NotNull File file) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        return gson.fromJson(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8), Config.class);
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
class Settings {
    private int warningThreshold = 1;
    private int banThreshold = 1;
    private boolean ignoreOP = true;
}

@Data
class Network {
    private int connectTimeout = 300000;
    private int readTimeout = 600000;
}

@Data
class ServerID {
    @NotNull
    private UUID serverUuid = UUID.randomUUID();
    @NotNull
    private UUID nodeUuid = UUID.randomUUID();
}

@Data
class SubscriptionContainer {
    @NotNull
    private String name = "Unknown";
    @NotNull
    private String url = "https://127.0.0.1";
    @NotNull
    private UUID id = UUID.randomUUID();
}
@Data
class WebService{
    private short port = 10086;
    private String pingAddress = "http://localhost/banlist/ping";
}

@Data
class Message {
    private String warningMessage = "警告！玩家 {0}(UUID: {1}) 正尝试加入服务器，但已被 {2} 个服务器封禁：{3}。";
    private String banMessage = "很抱歉！由于您在其他 {0} 个服务器被封禁，因此您无法加入本服务器！请联系以下服务器的服务器管理员解除对您的封禁，您才能加入本服务器： {2}";
}
