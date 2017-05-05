package com.ligl.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class ByteUtilTest {
	
	@Test
	public void toHexTest() {
		byte[] bs = new byte[]{0x01, 0x0a, (byte)0xaa, (byte)0xbb, (byte)0xff};
		String hex = "010aaabbff";
		String hexResult = ByteUtil.toHex(bs);
		Assert.assertEquals(hex, hexResult);
	}
	
	@Test
	public void fromHexTest() {
		byte[] bs = new byte[]{0x01, 0x0a, (byte)0xaa, (byte)0xbb, (byte)0xff};
		String hex = "010aaabbff";
		byte[] bResult = ByteUtil.fromHex(hex);
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
		System.out.println("short ret=" + Integer.toHexString(ret));
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
		System.out.println(Integer.toHexString(ret));
		Assert.assertEquals(i, ret);
	}
	
	@Test
	public void combineTest() {
		byte[] b = new byte[]{(byte) 0xab, (byte) 0xab,(byte) 0xab,(byte) 0xab,(byte) 0xab,(byte) 0xab,(byte) 0xab,};
		byte[] ret = ByteUtil.combine((byte)0xab, new byte[]{(byte) 0xab, (byte) 0xab}, "abab", 0xefab, (long) 0xefefab);
		Assert.assertArrayEquals(b, ret);
	}
}
