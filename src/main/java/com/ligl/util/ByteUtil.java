package com.ligl.util;

import java.io.ByteArrayOutputStream;

public class ByteUtil {

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

	private static final String HEX_STR = "0123456789abcdefABCDEF";

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
	 * 将short转成2个字节byte[]
	 * 
	 * @param s
	 * @return
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
		System.out.println(Integer.toHexString((b1 << 8) & 0xff00));
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
