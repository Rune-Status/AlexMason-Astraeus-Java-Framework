package astraeus.net.codec.login;

import astraeus.net.codec.IsaacCipher;
import io.netty.channel.ChannelHandlerContext;

public final class LoginDetailsPacket {

    private final ChannelHandlerContext context;

    /**
     * The username for this user.
     */
    private final String username;

    /**
     * The password for this user.
     */
    private final String password;

    private final String uuid;

    private final IsaacCipher encryptor;

    private final IsaacCipher decryptor;

    public LoginDetailsPacket(ChannelHandlerContext context, String username, String password, String uuid,
                IsaacCipher encryptor, IsaacCipher decryptor) {
          this.context = context;
          this.username = username;
          this.uuid = uuid;
          this.password = password;
          this.encryptor = encryptor;
          this.decryptor = decryptor;
    }

    public ChannelHandlerContext getContext() {
          return context;
    }

    public String getUsername() {
          return username;
    }

    public String getPassword() {
          return password;
    }

    public String getUUID() {
        return uuid;
    }

    public IsaacCipher getEncryptor() {
          return encryptor;
    }

    public IsaacCipher getDecryptor() {
          return decryptor;
    }

}

