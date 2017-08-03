package com.ligl.util;

import org.junit.Assert;
import org.junit.Test;

public class ByteUtilTest {
	
	@Test
	public void toHexTest() {
		byte[] bs = new byte[]{0x01, 0x0a, (byte)0xaa, (byte)0xbb, (byte)0xff};
		String hex = "010aaabbff";
		String hexResult = ByteUtil.toHex(bs);
		Assert.assertEquals(hex, hexResult);
		
		String hex2 = "bbff";
		String hexResult2 = ByteUtil.toHex(bs, bs.length - 2);
		Assert.assertEquals(hex2, hexResult2);
		
		String hex3 = "aabb";
		String hexResult3 = ByteUtil.toHex(bs, bs.length - 3, bs.length - 1);
		Assert.assertEquals(hex3, hexResult3);
		
		int value4 = 0xab;
		String hex4 = "ab";
		String hexResult4 = ByteUtil.toHex(value4, 1);
		Assert.assertEquals(hex4, hexResult4);
		
		int value5 = 0xabcd;
		String hex5 = "abcd";
		String hexResult5 = ByteUtil.toHex(value5, 2);
		Assert.assertEquals(hex5, hexResult5);
		
		int value6 = 0xabcdef;
		String hex6 = "abcdef";
		String hexResult6 = ByteUtil.toHex(value6, 3);
		Assert.assertEquals(hex6, hexResult6);
		
		int value7 = 0xabcdefab;
		String hex7 = "abcdefab";
		String hexResult7 = ByteUtil.toHex(value7, 4);
		Assert.assertEquals(hex7, hexResult7);
	}
	
	@Test
	public void fromHexTest() {
		byte[] bs = new byte[]{0x01, 0x0a, (byte)0xaa, (byte)0xbb, (byte)0xff};
		String hex = "010aaabbff";
		byte[] bResult = ByteUtil.fromHex(hex);
		Assert.assertArrayEquals(bs, bResult);
		
		String hex2 = "01 0a	a yyuu abbff";
		bResult = ByteUtil.fromHex(hex2);
		Assert.assertArrayEquals(bs, bResult);
	}
	
	@Test
	public void fromShortTest() {
		short s = (short) 0xabcd;
		byte[] b = new byte[]{(byte) 0xab, (byte) 0xcd};
		byte[] ret = ByteUtil.fromShort(s);
		Assert.assertArrayEquals(b, ret);
	}
	
	@Test
	public void toShortTest() {
		short s = (short) 0xabcd;
		byte[] b = new byte[]{(byte) 0xab, (byte) 0xcd};
		short ret = ByteUtil.toShort(b[0], b[1]);
		Assert.assertEquals(s, ret);
	}
	
	@Test
	public void fromIntTest() {
		int i = 0xabcdefab;
		byte[] b = new byte[]{(byte) 0xab,(byte) 0xcd,(byte) 0xef,(byte) 0xab};
		byte[] ret = ByteUtil.fromInt(i);
		Assert.assertArrayEquals(b, ret);
	}
	
	@Test
	public void toIntTest() {
		int i = 0xabcdefab;
		byte[] b = new byte[]{(byte) 0xab,(byte) 0xcd,(byte) 0xef,(byte) 0xab};
		int ret = ByteUtil.toInt(b[0],b[1],b[2],b[3]);
		Assert.assertEquals(i, ret);
	}
	
	@Test
	public void combineTest() {
		byte[] b = new byte[]{(byte) 0xab, (byte) 0xab,(byte) 0xab,(byte) 0xab,(byte) 0xab,(byte) 0xab,(byte) 0xab,};
		byte[] ret = ByteUtil.combine((byte)0xab, new byte[]{(byte) 0xab, (byte) 0xab}, "abab", 0xefab, (long) 0xefefab);
		Assert.assertArrayEquals(b, ret);
	}
}
