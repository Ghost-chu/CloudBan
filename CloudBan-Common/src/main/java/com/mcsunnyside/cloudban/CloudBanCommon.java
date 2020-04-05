package com.mcsunnyside.cloudban;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
@Data
public class CloudBanCommon {
    private File configFile;
    private Config config;
    private BanList banList;
    private NettyServer nettyServer;

    public CloudBanCommon(@NotNull File configFile, @NotNull File banListFile) throws IOException, InterruptedException {
        this.configFile = configFile;
        this.config = Config.load(configFile);
        this.banList = BanList.load(banListFile);
        this.nettyServer = new NettyServer(config.getWebService().getPort(),this.banList);
        this.nettyServer.start();
    }

}
