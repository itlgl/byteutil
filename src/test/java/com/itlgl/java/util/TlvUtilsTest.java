package com.itlgl.java.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TlvUtilsTest {

    @Test
    public void testNext() {
        byte[] tlvData = ByteUtils.fromHex("010101 02020102 3f3210000102030405060708090a0b0c0d0e0f");
        byte[] tlvValue01 = new byte[] {0x01};
        byte[] tlvValue02 = new byte[] {0x01, 0x02};
        byte[] tlvValue03 = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};

        TlvUtils tlv = new TlvUtils(tlvData);

        TlvUtils.TagValue next1 = tlv.next();
        Assert.assertArrayEquals(next1.value, tlvValue01);
        Assert.assertEquals(next1.getTagString(), "01");

        TlvUtils.TagValue next2 = tlv.next();
        Assert.assertArrayEquals(next2.value, tlvValue02);
        Assert.assertEquals(next2.getTagString(), "02");

        TlvUtils.TagValue next3 = tlv.next();
        Assert.assertEquals(next3.getTagString(), "3f32");
        Assert.assertArrayEquals(next3.value, tlvValue03);
    }

    @Test
    public void parseInMap() {
        byte[] tlvData = ByteUtils.fromHex("010101 02020102 3f3210000102030405060708090a0b0c0d0e0f");
        byte[] tlvValue01 = new byte[] {0x01};
        byte[] tlvValue02 = new byte[] {0x01, 0x02};
        byte[] tlvValue03 = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};

        TlvUtils tlv = new TlvUtils(tlvData);

        Map<String, byte[]> stringMap = tlv.parseInMap();
        Assert.assertArrayEquals(stringMap.get("01"), tlvValue01);
        Assert.assertArrayEquals(stringMap.get("02"), tlvValue02);
        Assert.assertArrayEquals(stringMap.get("3f32"), tlvValue03);
    }

    @Test
    public void testList() {
        byte[] tlvData = ByteUtils.fromHex("010101 02020102 3f3210000102030405060708090a0b0c0d0e0f");
        byte[] tlvValue01 = new byte[] {0x01};
        byte[] tlvValue02 = new byte[] {0x01, 0x02};
        byte[] tlvValue03 = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};

        TlvUtils tlv = new TlvUtils(tlvData);

        List<TlvUtils.TagValue> tlvList = tlv.parseInList();
        Assert.assertArrayEquals(tlvList.get(0).value, tlvValue01);
        Assert.assertArrayEquals(tlvList.get(1).value, tlvValue02);
        Assert.assertArrayEquals(tlvList.get(2).value, tlvValue03);
    }
}
