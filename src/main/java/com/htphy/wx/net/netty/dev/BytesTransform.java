package com.htphy.wx.net.netty.dev;

public class BytesTransform {
    public static byte[] shortToBytes(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) ((n >> 8) & 0xff);
        return b;
    }

    public static short bytesToShort(byte[] b) {
        return (short) (b[1] & 0xff
                | (b[0] & 0xff) << 8);
    }

    /**
     * int类型转换成byte[]
     *
     * @param num int数
     * @return byte[]
     */
    public static byte[] intToBytes(int num) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (num >>> (24 - i * 8));
        }
        return b;
    }

    public static int bytes2int(byte[] b) {
        int mask = 0xff;
        int temp = 0;
        int res = 0;
        for (int i = 0; i < 4; i++) {
            res <<= 8;
            temp = b[i] & mask;
            res |= temp;
        }
        return res;
    }

    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }
    public static byte[] doubleToBytes(double d) {
        return longToBytes(Double.doubleToLongBits(d));
    }

    public static double byte2Double(byte[] bytes){
        return Double.longBitsToDouble(ByteToLong(bytes));
    }
    public static long ByteToLong(byte[] bytes) {
        return (((long) bytes[0] & 0xff) << 56)
                | (((long) bytes[1] & 0xff) << 48)
                | (((long) bytes[2] & 0xff) << 40)
                | (((long) bytes[3] & 0xff) << 32)
                | (((long) bytes[4] & 0xff) << 24)
                | (((long) bytes[5] & 0xff) << 16)
                | (((long) bytes[6] & 0xff) << 8)
                | (((long) bytes[7] & 0xff) << 0);
    }
    public static byte[] longToBytes(long l) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (l >> 56 & 0xff);
        bytes[1] = (byte) (l >> 48 & 0xff);
        bytes[2] = (byte) (l >> 40 & 0xff);
        bytes[3] = (byte) (l >> 32 & 0xff);
        bytes[4] = (byte) (l >> 24 & 0xff);
        bytes[5] = (byte) (l >> 16 & 0xff);
        bytes[6] = (byte) (l >> 8 & 0xff);
        bytes[7] = (byte) (l >> 0 & 0xff);
        return bytes;
    }

}
