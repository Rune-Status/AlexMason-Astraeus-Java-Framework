package astraeus.util;

import io.netty.buffer.ByteBuf;
import astraeus.Server;
import astraeus.game.model.entity.item.Item;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {

	/**
	 * An array containing valid characters that can be used in the server.
	 */
	public static final char VALID_CHARACTERS[] = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=', ':', ';', '.', '>', '<',
			',', '"', '[', ']', '|', '?', '/', '`' };

	/**
	 * An array containing valid username characters.
	 */
	public static final char VALID_PLAYER_CHARACTERS[] = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '[', ']', '/', '-', ' ' };

	private static char decodeBuf[] = new char[4096];

	public static String capitalizeSentence(final String string) {
		int pos = 0;
		boolean capitalize = true;
		StringBuilder sb = new StringBuilder(string);
		while (pos < sb.length()) {
			if (sb.charAt(pos) == '.') {
				capitalize = true;
			} else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
				sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
				capitalize = false;
			}
			pos++;
		}
		return sb.toString();
	}
	
	/**
	 * Gets the elapsed time the server has been running for.
	 * 
	 * @return The elapsed time.
	 */
  public static String getUptime() {
    long elapsedSeconds = Server.getUptime().elapsed(TimeUnit.SECONDS);    
        
    long minute = elapsedSeconds >= 60 ? elapsedSeconds / 60 : 0;
    
    long seconds = (Server.getUptime().elapsed(TimeUnit.SECONDS) >= 60 ? Server.getUptime().elapsed(TimeUnit.SECONDS) - (minute * 60) : Server.getUptime().elapsed(TimeUnit.SECONDS));
    
    long hour = minute >= 60 ? minute / 60 : 0;
    long day = hour >= 24 ? hour / 24 : 0;
    
    return String.format("[uptime= %d Days %d Hours %d Minutes %d Seconds]", day, hour, minute, seconds);
  }
	
	/**
	 * Gets the names of an array of items.
	 * 
	 * @param items
	 *            The items to get the name of.
	 * 
	 * @return A single string of the names.
	 */
	public static String getItemNames(Item[] items) {
		String tradeItems = "@lre@Absolutely nothing!";
		String tradeAmount = "";

		int count = 0;

		for (Item item : items) {
			if (item == null) {
				continue;
			}

			tradeAmount = String.format("@whi@%s (%s)", StringUtils.formatPrice(item.getAmount()),
					StringUtils.formatValue(item.getAmount()));

			tradeItems = count == 0 ? "@lre@" + item.getName()
					: String.format("@lre@%s\\n\\n@lre@%s", tradeItems, item.getName());

			if (item.isStackable()) {
				tradeItems = tradeItems + " @whi@x " + tradeAmount;
			}
			count++;
		}

		return tradeItems;
	}

	/**
	 * Capitalize the strings
	 * 
	 * @param string
	 * @return
	 */
	public static String capitalize(final String string) {
		return Character.toUpperCase(string.charAt(0)) + string.substring(1);
	}

	public static String format(int num) {
		return NumberFormat.getInstance().format(num);
	}
	
	/**
	 * Formats a username
	 * 
	 * E.g Mod Seven
	 * 
	 * @param string
	 *		The username to modify.
	 *
	 * @return The formatted string.
	 */
	public static String formatName(final String string) {
		return Stream.of(string.trim().split("\\s")).filter(word -> word.length() > 0).map(word -> word.substring(0, 1).toUpperCase() + word.substring(1)).collect(Collectors.joining(" "));
	}

	public static String formatBoolean(boolean param) {
		if (param) {
			return "True";
		}
		return "False";
	}

	public static String formatPlayerName(String str) {
		str = ucFirst(str);
		str.replace("_", " ");
		return str;
	}

	public static String formatPrice(int amount) {
		if (amount >= 1_000 && amount < 1_000_000) {
			return (amount / 1_000) + "K";
		}
		if (amount >= 1_000_000) {
			return (amount / 1_000_000) + "M";
		}
		if (amount >= 1_000_000_000) {
			return (amount / 1_000_000_000) + "B";
		}
		return "" + amount;
	}
	
	/**
	 * Formats a number into a string with commas.
	 */
	public static String formatValue(int value) {
		return new DecimalFormat("#, ###").format(value);
	}

	/**
	 * Formats number
	 * 
	 * @param amount
	 * @return
	 */
	public static String formatPrice(long amount) {
		if (amount >= 1_000 && amount < 1_000_000) {
			return (amount / 1_000) + "K";
		}
		if (amount >= 1_000_000) {
			return (amount / 1_000_000) + "M";
		}
		if (amount >= 1_000_000_000) {
			return (amount / 1_000_000_000) + "B";
		}
		return amount + " GP";
	}

	public static String formatText(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
			}
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)),
							s.substring(i + 2));
				}
			}
		}
		return s.replace("_", " ");
	}

	/**
	 * A or an
	 * 
	 * @param nextWord
	 * @return
	 */
	public static String getAOrAn(String nextWord) {
		String s = "a";
		final char c = nextWord.toUpperCase().charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}
		return s;
	}

	public static String getRS2String(final ByteBuf buf) {
		final StringBuilder bldr = new StringBuilder();
		byte b;
		while (buf.isReadable() && (b = buf.readByte()) != 10) {
			bldr.append((char) b);
		}
		return bldr.toString();
	}

	public static String Hex(byte data[]) {
		return Hex(data, 0, data.length);
	}

	public static String Hex(byte data[], int offset, int len) {
		String temp = "";
		for (int cntr = 0; cntr < len; cntr++) {
			final int num = data[offset + cntr] & 0xFF;
			String myStr;
			if (num < 16) {
				myStr = "0";
			} else {
				myStr = "";
			}
			temp += myStr + Integer.toHexString(num) + " ";
		}
		return temp.toUpperCase().trim();
	}

	public static int hexToInt(byte data[], int offset, int len) {
		int temp = 0;
		int i = 1000;
		for (int cntr = 0; cntr < len; cntr++) {
			final int num = (data[offset + cntr] & 0xFF) * i;
			temp += num;
			if (i > 1) {
				i = i / 1000;
			}
		}
		return temp;
	}

	public static String longToString(long l) {
		int i = 0;
		final char ac[] = new char[12];

		while (l != 0L) {
			final long l1 = l;

			l /= 37L;
			ac[11 - i++] = VALID_CHARACTERS[(int) (l1 - l * 37L)];
		}
		return new String(ac, 12 - i, i);
	}

	public static String optimizeText(String text) {
		final char buf[] = text.toCharArray();
		boolean endMarker = true;
		for (int i = 0; i < buf.length; i++) {
			final char c = buf[i];
			if (endMarker && c >= 'a' && c <= 'z') {
				buf[i] -= 0x20;
				endMarker = false;
			}
			if (c == '.' || c == '!' || c == '?') {
				endMarker = true;
			}
		}
		return new String(buf, 0, buf.length);
	}

	public static long stringToLong(String s) {
		long l = 0L;
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z') {
				l += (1 + c) - 65;
			} else if (c >= 'a' && c <= 'z') {
				l += (1 + c) - 97;
			} else if (c >= '0' && c <= '9') {
				l += (27 + c) - 48;
			}
		}
		while (l % 37L == 0L && l != 0L) {
			l /= 37L;
		}
		return l;
	}

	public static void textPack(byte packedData[], java.lang.String text) {
		if (text.length() > 80) {
			text = text.substring(0, 80);
		}
		text = text.toLowerCase();

		int carryOverNibble = -1;
		int ofs = 0;
		for (int idx = 0; idx < text.length(); idx++) {
			final char c = text.charAt(idx);
			int tableIdx = 0;
			for (int i = 0; i < VALID_CHARACTERS.length; i++) {
				if (c == VALID_CHARACTERS[i]) {
					tableIdx = i;
					break;
				}
			}
			if (tableIdx > 12) {
				tableIdx += 195;
			}
			if (carryOverNibble == -1) {
				if (tableIdx < 13) {
					carryOverNibble = tableIdx;
				} else {
					packedData[ofs++] = (byte) (tableIdx);
				}
			} else if (tableIdx < 13) {
				packedData[ofs++] = (byte) ((carryOverNibble << 4) + tableIdx);
				carryOverNibble = -1;
			} else {
				packedData[ofs++] = (byte) ((carryOverNibble << 4) + (tableIdx >> 4));
				carryOverNibble = tableIdx & 0xf;
			}
		}

		if (carryOverNibble != -1) {
			packedData[ofs++] = (byte) (carryOverNibble << 4);
		}
	}

	public static String textUnpack(byte packedData[], int size) {
		int idx = 0, highNibble = -1;
		for (int i = 0; i < size * 2; i++) {
			final int val = packedData[i / 2] >> (4 - 4 * (i % 2)) & 0xf;
			if (highNibble == -1) {
				if (val < 13) {
					decodeBuf[idx++] = VALID_CHARACTERS[val];
				} else {
					highNibble = val;
				}
			} else {
				decodeBuf[idx++] = VALID_CHARACTERS[((highNibble << 4) + val) - 195];
				highNibble = -1;
			}
		}

		return new String(decodeBuf, 0, idx);
	}

	public static String ucFirst(String str) {
		str = str.toLowerCase();
		if (str.length() > 1) {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			return str.toUpperCase();
		}
		return str;
	}

}
