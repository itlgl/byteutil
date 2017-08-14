package com.itlgl.java.util;

import java.io.ByteArrayOutputStream;

public class ByteUtil {

	/**
	 * 将byte[]数据转换为16进制的字符串，如byte[]{0x11, 0xaa, 0xbb, 0xcc}转换成"11aabbcc"<br/>
	 * 默认转换出来的字符串全部为小写
	 * 
	 * @param src
	 *            byte数组
	 * @return
	 */
	public static String toHex(byte[] src) {
		if (src == null || src.length == 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0, len = src.length; i < len; i++) {
			builder.append(String.format("%02x", src[i]));
		}
		return builder.toString();
	}

	public static String toHex(byte[] src, int start) {
		if (src == null || src.length == 0) {
			return null;
		}
		return toHex(src, start, src.length);
	}

	public static String toHex(byte[] src, int start, int end) {
		if (src == null || src.length == 0) {
			return null;
		}
		if (start > end) {
			throw new IllegalArgumentException();
		}
		if (start < 0 || end > src.length) {
			throw new ArrayIndexOutOfBoundsException();
		}
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < end; i++) {
			builder.append(String.format("%02x", src[i]));
		}
		return builder.toString();
	}

	/**
	 * 将一个int值的前byteLength个字节转换为十六进制的字符，比如0xab转换成"ab"
	 * @param src 要转换的int值
	 * @param byteLength 转换前几个字节，范围[1,4]，否则会返回null值
	 * @return
	 */
	public static String toHex(int src, int byteLength) {
		switch(byteLength) {
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
	
	private static final String HEX_STR = "0123456789abcdefABCDEF";

	/**
	 * 将16进制的字符串转换为byte[]，如"11aabbcc"转换成byte[]{0x11, 0xaa, 0xbb, 0xcc}<br/>
	 * 函数会过滤字符串中不合法的字符
	 * 
	 * @param hex
	 *            16进制的字符串，不区分大小写
	 * @return
	 */
	public static byte[] fromHex(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0, len = hex.length(); i < len; i++) {
			char c = hex.charAt(i);
			if (HEX_STR.indexOf(c) != -1) {
				builder.append(c);
			}
		}
		String hexSrc = builder.toString();
		int strLength = hexSrc.length();
		byte[] result = new byte[strLength / 2];
		for (int i = 0, len = strLength / 2; i < len; i++) {
			result[i] = (byte) Integer.parseInt(hexSrc.substring(i * 2, i * 2 + 2), 16);
		}
		return result;
	}

	/**
	 * 将short转成2个字节byte[]<br/>
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
	 * @return
	 */
	public static byte[] fromInt(int i) {
		byte[] ret = new byte[4];
		ret[0] = (byte) ((i >> 24) & 0xff);
		ret[1] = (byte) ((i >> 16) & 0xff);
		ret[2] = (byte) ((i >> 8) & 0xff);
		ret[3] = (byte) (i & 0xff);
		return ret;
	}

	public static int toInt(byte b1, byte b2, byte b3, byte b4) {
		int ret = (((b1 & 0xff) << 24) | ((b2 & 0xff) << 16) | ((b3 & 0xff) << 8) | (b4 & 0xff));
		return ret;
	}

	/**
	 * 将多个参数组合成一个byte[]<br/>
	 * 现在支持String、Byte、Short、Integer、Long、byte[]<br/>
	 * <b>注意：Byte、Short、Integer、Long都会被当做byte处理</b>
	 * 
	 * @param args
	 * @return
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
