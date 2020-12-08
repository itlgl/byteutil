package com.itlgl.java.util;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ByteUtil {

	private static final char[] ENCODING_TABLE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static final byte[] DECODING_TABLE = new byte[128];

	static {
		Arrays.fill(DECODING_TABLE, (byte) 0xff);
		for (int i = '0'; i <= '9'; i++) {
			DECODING_TABLE[i] = (byte) (i - '0');
		}
		for (char i = 'a'; i <= 'f'; i++) {
			DECODING_TABLE[i] = (byte) (i - 'a' + 0xa);
		}
		for (char i = 'A'; i <= 'F'; i++) {
			DECODING_TABLE[i] = (byte) (i - 'A' + 0xa);
		}
	}

	/**
	 * 将byte[]数据转换为16进制的字符串，如byte[]{0x11, 0xaa, 0xbb, 0xcc}转换成"11aabbcc"
	 * 默认转换出来的字符串全部为小写
	 * 
	 * @param src
	 *            byte数组
	 * @return hex字符串
	 */
	public static String toHex(byte[] src) {
		if (src == null || src.length == 0) {
			return null;
		}
		char[] chars = new char[src.length * 2];
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xff;
			chars[i * 2] = ENCODING_TABLE[v >>> 4];
			chars[i * 2 + 1] = ENCODING_TABLE[v & 0xf];
		}
		return new String(chars);
	}

	/**
	 * 将byte[]数据转换为16进制的字符串，如byte[]{0x11, 0xaa, 0xbb, 0xcc}转换成"11aabbcc"
	 * 默认转换出来的字符串全部为小写 从开始位置到byte数组的结尾
	 * 
	 * @param src
	 *            byte数组
	 * @param start
	 *            开始位置
	 * @return hex字符串
	 */
	public static String toHex(byte[] src, int start) {
		if (src == null || src.length == 0) {
			return null;
		}
		return toHex(src, start, src.length);
	}

	/**
	 * 将byte[]数据转换为16进制的字符串，如byte[]{0x11, 0xaa, 0xbb, 0xcc}转换成"11aabbcc"
	 * 默认转换出来的字符串全部为小写 从开始位置到结束位置
	 * 
	 * @param src
	 *            byte数组
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return hex字符串
	 */
	public static String toHex(byte[] src, int start, int end) {
		if (src == null || src.length == 0) {
			return null;
		}
		if (start > end) {
			throw new IllegalArgumentException(start + " > " + end);
		}
		if(start < 0) {
			throw new ArrayIndexOutOfBoundsException(start + " < 0");
		}
		if(start > src.length) {
			throw new ArrayIndexOutOfBoundsException(start + " > " + src.length);
		}
		if (end > src.length) {
			throw new ArrayIndexOutOfBoundsException(start + " > " + src.length);
		}
		char[] chars = new char[(end - start) * 2];
		for (int i = 0; i < end - start; i++) {
			int v = src[i + start] & 0xff;
			chars[i * 2] = ENCODING_TABLE[v >>> 4];
			chars[i * 2 + 1] = ENCODING_TABLE[v & 0xf];
		}
		return new String(chars);
	}

	/**
	 * 将一个int值的前byteLength个字节转换为十六进制的字符，比如0xab转换成"ab"
	 * 
	 * @param src
	 *            要转换的int值
	 * @param byteLength
	 *            转换前几个字节，范围[1,4]，否则会返回null值
	 * @return hex字符串
	 */
	public static String toHex(int src, int byteLength) {
		switch (byteLength) {
		case 1:
			return String.format("%02x", src & 0xff);
		case 2:
			return String.format("%04x", src & 0xffff);
		case 3:
			return String.format("%06x", src & 0xffffff);
		case 4:
			return String.format("%08x", src & 0xffffffff);
		}
		return null;
	}

	/**
	 * 将16进制的字符串转换为byte[]，如"11aabbcc"转换成byte[]{0x11, 0xaa, 0xbb, 0xcc}
	 * 函数会过滤字符串中不合法的字符
	 * 
	 * @param hex
	 *            16进制的字符串，不区分大小写
	 * @return byte数组结果
	 */
	public static byte[] fromHex(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0, len = hex.length(); i < len; i++) {
			char c = hex.charAt(i);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
				builder.append(c);
			}
		}
		String hexSrc = builder.toString();
		int strLength = hexSrc.length();
		byte[] result = new byte[strLength / 2];
		for (int i = 0, len = strLength / 2; i < len; i++) {
			byte b1 = DECODING_TABLE[hexSrc.charAt(i * 2)];
			byte b2 = DECODING_TABLE[hexSrc.charAt(i * 2 + 1)];
			result[i] = (byte) ((b1 << 4) | b2);
		}
		return result;
	}

	/**
	 * 将short转成2个字节byte[]
	 * 如(short)0xabcd转换成byte[]{0xab, 0xcd}
	 * 
	 * @param s
	 *            short数据
	 * @return 转换后的两个字节byte数组
	 */
	public static byte[] fromShort(short s) {
		byte[] ret = new byte[2];
		ret[0] = (byte) ((s >> 8) & 0xff);
		ret[1] = (byte) (s & 0xff);
		return ret;
	}

	public static short toShort(byte b1, byte b2) {
		short s1 = (short) ((b1 & 0xff) << 8);
		short s2 = (short) (b2 & 0xff);
		short ret = (short) (s1 | s2);
		return ret;
	}

	/**
	 * 将int转换为4个字节byte[]数组
	 * 
	 * @param i
	 *            integer数据
	 * @return byte数组，共四个字节
	 */
	public static byte[] fromInt(int i) {
		byte[] ret = new byte[4];
		ret[0] = (byte) ((i >> 24) & 0xff);
		ret[1] = (byte) ((i >> 16) & 0xff);
		ret[2] = (byte) ((i >> 8) & 0xff);
		ret[3] = (byte) (i & 0xff);
		return ret;
	}

	/**
	 * 将四个byte组合成一个int值
	 * 
	 * @param b1
	 *            byte1
	 * @param b2
	 *            byte2
	 * @param b3
	 *            byte3
	 * @param b4
	 *            byte4
	 * @return int值结果
	 */
	public static int toInt(byte b1, byte b2, byte b3, byte b4) {
		int ret = (((b1 & 0xff) << 24) | ((b2 & 0xff) << 16) | ((b3 & 0xff) << 8) | (b4 & 0xff));
		return ret;
	}

	/**
	 * 将多个参数组合成一个byte[]
	 * 现在支持String、Byte、Short、Integer、Long、byte[]
	 * <b>注意：Byte、Short、Integer、Long都会被当做byte处理</b>
	 * 
	 * @param args
	 *            可以包括byte、int、short、long、byte[]、String
	 * @return byte数组结果
	 */
	public static byte[] combine(Object... args) {
		if (args == null || args.length == 0) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				continue;
			}
			try {
				if (args[i] instanceof String) {
					String hex = (String) args[i];
					byte[] bytearr = fromHex(hex);
					if (bytearr != null) {
						baos.write(bytearr);
					}
				} else if (args[i] instanceof byte[]) {
					byte[] ba = (byte[]) args[i];
					baos.write(ba);
				} else if (args[i] instanceof Byte) {
					Byte b = (Byte) args[i];
					int num = b & 0xff;
					baos.write(num);
				} else if (args[i] instanceof Short) {
					Short s = (Short) args[i];
					int num = s & 0xff;
					baos.write(num);
				} else if (args[i] instanceof Integer) {
					Integer s = (Integer) args[i];
					int num = s & 0xff;
					baos.write(num);
				} else if (args[i] instanceof Long) {
					Long s = (Long) args[i];
					int num = (int) (s & 0xff);
					baos.write(num);
				} else {
					System.out.println("不被支持的参数[" + i + "]");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}
}
