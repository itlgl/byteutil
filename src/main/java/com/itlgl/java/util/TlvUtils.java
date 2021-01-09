package com.itlgl.java.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.*;

public class TlvUtils {
    private byte[] data;
    private int index;

    private int nextTagLength = 1;

    public TlvUtils(byte[] data, int index) {
        this.data = data;
        this.index = index;
    }

    public TlvUtils(byte[] data) {
        this(data, 0);
    }

    private boolean hasNextTag() {
        int i = index;
        if(i >= data.length) return false;

        // b1
        if((data[i] & 0x1f) != 0x1f) {
            nextTagLength = 1;
            return true;
        }

        // b2
        i++;
        if(i >= data.length) return false;
        if((data[i] & 0x80) != 0x80) {
            nextTagLength = 2;
            return true;
        }

        // b3
        i++;
        if(i >= data.length) return false;
        if((data[i] & 0x80) != 0x80) {
            nextTagLength = 3;
            return true;
        }

        // b4
        i++;
        if(i >= data.length) return false;
        if ((data[i] & 0x80) != 0x80) {
            nextTagLength = 4;
            return true;
        }
        return false;
    }

    private byte[] nextTag() {
        if(!hasNextTag()) {
            return null;
        }
        byte[] tag = new byte[nextTagLength];
        System.arraycopy(data, index, tag, 0, nextTagLength);
        index += nextTagLength;
        return tag;
    }

    private int nextLength() {
        int i = index;
        if(i >= data.length) return -1;

        if((data[i] & 0x80) != 0x80) {
            index++;
            return data[i] & 0xff;
        }
        int len = data[i] & 0x7f;
        if(index + 1 + len >= data.length) {
            return -1;
        }
        byte[] bytes = Arrays.copyOfRange(data, index + 1, index + 1 + len);
        index += 1 + len;
        return new BigInteger(bytes).intValue();
    }

    private byte[] nextValue(int len) {
        if(index + len > data.length) {
            return null;
        }
        byte[] result = Arrays.copyOfRange(data, index, index + len);
        index += len;
        return result;
    }

    public TagValue next() {
        if(!hasNextTag()) {
            return null;
        }
        byte[] tag = nextTag();
        int len = nextLength();
        if(len == -1) {
            return null;
        }
        byte[] value = nextValue(len);
        if(value == null) {
            return null;
        }
        return new TagValue(tag, value);
    }

    public List<TagValue> parseInList() {
        TagValue tagValue = null;
        List<TagValue> result = new ArrayList<>();
        while((tagValue = next()) != null) {
            result.add(tagValue);
        }
        return result;
    }

    public Map<String, byte[]> parseInMap() {
        TagValue tagValue = null;
        Map<String, byte[]> result = new HashMap<>();
        while((tagValue = next()) != null) {
            result.put(tagValue.getTagString(), tagValue.value);
        }
        return result;
    }

    public static byte[] generateTlv(List<TagValue> tagValues) {
        if(tagValues == null) {
            return null;
        }
        TagValue[] arrays = new TagValue[tagValues.size()];
        for (int i = 0; i < tagValues.size(); i++) {
            arrays[i] = tagValues.get(i);
        }
        return generateTlv(arrays);
    }

    public static byte[] generateTlv(TagValue... tagValues) {
        if(tagValues == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < tagValues.length; i++) {
            TagValue tv = tagValues[i];
            out.write(tv.tag, 0, tv.tag.length);
            if(tv.value.length < 0x80) {
                out.write(tv.value.length);
            } else if(tv.value.length < 0xff) {
                out.write(0x81);
                out.write(tv.value.length);
            } else if(tv.value.length < 0xffff) {
                out.write(0x82);
                out.write((tv.value.length >> 8) & 0xff);
                out.write(tv.value.length & 0xff);
            } else if(tv.value.length < 0xffffff) {
                out.write(0x82);
                out.write((tv.value.length >> 16) & 0xff);
                out.write((tv.value.length >> 8) & 0xff);
                out.write(tv.value.length & 0xff);
            }
            out.write(tv.value, 0, tv.value.length);
        }
        return out.toByteArray();
    }

    public static class TagValue {
        public byte[] tag;
        public byte[] value;
        private String tagString = null;
        private String valueString = null;

        public TagValue() {}

        public TagValue(byte[] tag, byte[] value) {
            this.tag = tag;
            this.value = value;
        }

        public TagValue(String tag, byte[] value) {
            this.tag = ByteUtils.fromHex(tag);
            this.value = value;
        }

        public String getTagString() {
            if(tagString == null) {
                tagString = ByteUtils.toHex(tag);
            }
            return tagString;
        }

        public String getValueString() {
            if(valueString == null) {
                valueString = ByteUtils.toHex(value);
            }
            return valueString;
        }
    }
}
