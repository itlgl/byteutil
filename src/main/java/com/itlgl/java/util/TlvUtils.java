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
        // 如果最高bit位是1，表示有后续数据，判断条件就是data[i] & 0x80 == 0x80，判断最高位bit为0则是data[i] & 0x80 == 0
        // 0x82(1000 0010)1234 表示后续2个字节是实际长度，长度为0x1234
        // 0x65(0110 0101)表示后续长度是0x65
        int i = index;
        if(i >= data.length) return -1;

        // 判断最高位bit为0 data[i] & 0x80 == 0
        if((data[i] & 0x80) == 0) {
            index++;
            return data[i] & 0xff;
        }
        // 获取后续字节长度
        int subBytesLen = data[i] & 0x7f;
        i++;
        // java int值最大4个字节，如果后续长度4个字节同时最高bit位是1，转出来int值就是负值
        if(i + subBytesLen >= data.length || subBytesLen > 4 || (subBytesLen == 4 && (data[i] & 0x80) == 0x80)) {
            return -1;
        }

        int len = 0;
        if(subBytesLen == 1) {
            len = data[i] & 0xff;
            index += 2;
        } else if(subBytesLen == 2) {
            len =  ((data[i] & 0xff) << 8) | (data[i + 1] & 0xff);
            index += 3;
        } else if(subBytesLen == 3) {
            len =  ((data[i] & 0xff) << 16) | ((data[i + 1] & 0xff) << 8) | (data[i + 2] & 0xff);
            index += 4;
        } else if(subBytesLen == 4) {
            len =  ((data[i] & 0xff) << 24) | ((data[i + 1] & 0xff) << 16) | ((data[i + 2] & 0xff) << 8) | (data[i + 3] & 0xff);
            index += 5;
        }
        return len;
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
        return new TagValue(tag, len, value);
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
        public int length;
        public byte[] value;
        private String tagString = null;
        private String valueString = null;

        public TagValue() {}

        public TagValue(byte[] tag, int length, byte[] value) {
            this.tag = tag;
            this.length = length;
            this.value = value;
        }

        public TagValue(String tag, int length, byte[] value) {
            this.tag = ByteUtils.fromHex(tag);
            this.length = length;
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
