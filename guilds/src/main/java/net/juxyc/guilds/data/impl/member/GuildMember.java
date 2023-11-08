package net.juxyc.guilds.data.impl.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GuildMember {

    private UUID uuid;

    private String nick;

    private String role;

}
