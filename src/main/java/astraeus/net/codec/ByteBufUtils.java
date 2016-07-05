package astraeus.net.codec;

import io.netty.buffer.ByteBuf;

public final class ByteBufUtils {

    private ByteBufUtils() {

    }

    /**
     * Reads a RuneScape {@code String} value.
     *
     * @return the value of the string.
     */
    public static String readString(ByteBuf buf) {
        byte temp;
        StringBuilder builder = new StringBuilder();
        while (buf.isReadable() && (temp = buf.readByte()) != 10) {
            builder.append((char) temp);
        }
        return builder.toString();
    }


}
