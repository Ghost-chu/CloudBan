package com.mcsunnyside.cloudban;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Data
public class CloudBanCommon {
    private File banListFile;
    private File configFile;
    private Config config;
    private BanList banList;
    private NettyServer nettyServer;

    public CloudBanCommon(@NotNull File configFile, @NotNull File banListFile) throws IOException {
        this.configFile = configFile;
        this.banListFile = banListFile;
        this.config = Config.load(configFile);
        this.banList = BanList.load(banListFile);
        this.nettyServer = new NettyServer(config.getWebService().getPort(),this.banList);
    }

    public void onEnable(){
        try {
            this.nettyServer.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDisable(){
        Config.save(configFile,this.config);
        BanList.save(banListFile,this.banList);
    }

    public void updateBanList(List<BanContainer> banContainerList){
        this.getBanList().setBans(banContainerList);
        BanList.save(banListFile,this.banList);
    }

}
