package net.juxyc.guilds.data.impl.camera;

import lombok.Getter;

import javax.xml.stream.Location;

@Getter
public class GuildCamera {

    private final String cameraName;

    private final Location location;

    public GuildCamera(final String cameraName, final Location location){
        this.cameraName = cameraName;
        this.location = location;
    }
}
