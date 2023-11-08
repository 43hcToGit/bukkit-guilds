package net.juxyc.guilds.data.impl.role;


import lombok.Getter;
import lombok.Setter;
import net.juxyc.guilds.data.Guild;
import net.juxyc.guilds.data.impl.role.permission.GuildRolePermissionType;
import net.juxyc.guilds.data.impl.role.type.GuildRoleType;

@Getter
@Setter
public class GuildRole {

    private final String roleName;

    private final GuildRoleType roleType;

    private final GuildRolePermissionType guildRolePermissionType;

    public GuildRole(final String roleName, final GuildRoleType roleType, final GuildRolePermissionType guildRolePermissionType){
        this.roleName = roleName;
        this.roleType = roleType;
        this.guildRolePermissionType = guildRolePermissionType;
    }
}
