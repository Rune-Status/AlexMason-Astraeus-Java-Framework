package astraeus.net.codec.login;

import astraeus.game.model.entity.mob.player.PlayerRights;

public final class LoginResponsePacket {

    private final LoginResponse response;

    private final PlayerRights rights;

    private final boolean flagged;

    public LoginResponsePacket(LoginResponse response, PlayerRights rights, boolean flagged) {
        this.response = response;
        this.rights = rights;
        this.flagged = flagged;
    }

    public LoginResponsePacket(LoginResponse response) {
        this(response, PlayerRights.PLAYER, false);
    }

    public LoginResponse getResponse() {
        return response;
    }

    public PlayerRights getRights() {
        return rights;
    }

    public boolean isFlagged() {
        return flagged;
    }
}

