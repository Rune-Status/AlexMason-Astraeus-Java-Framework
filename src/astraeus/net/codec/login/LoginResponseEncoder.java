package astraeus.net.codec.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LoginResponseEncoder extends MessageToByteEncoder<LoginResponsePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, LoginResponsePacket msg, ByteBuf out)
                throws Exception {
    	
    	System.out.println("encoding login response");    	
    	
          out.writeByte(msg.getResponse().getOpcode());

          if (msg.getResponse() == LoginResponse.NORMAL) {
              out.writeByte(msg.getRights().getProtocolValue());
              out.writeByte(msg.isFlagged() ? 1 : 0);
          }            
    }

}

