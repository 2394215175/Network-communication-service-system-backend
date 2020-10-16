package com.htphy.wx.net.netty.dev;

public class AntennaMessage {
    //俯仰角度
    private short lowAngle = 0;
    //方位角度
    private short directionAngle = 0;
    //卫星标识
    private String signstr = "";
    private char[] sign = new char[2];
    //差路极化 1：左旋，2：右旋
    private byte differ = 0;
    //和路极化 1：左旋，2：右旋
    private byte sum = 0;
    //载波锁定 1：锁定，2：失锁
    private byte locking = 0;
    private String date = "";
    private int terminalid = 0;

    public String getSignstr() {
        return this.signstr;
    }

    public short getLowAngle() {
        return lowAngle;
    }

    public void setLowAngle(short lowAngle) {
        this.lowAngle = lowAngle;
    }

    public short getDirectionAngle() {
        return directionAngle;
    }

    public void setDirectionAngle(short directionAngle) {
        this.directionAngle = directionAngle;
    }

    public char[] getSign() {
        return sign;
    }

    public void setSign(char[] sign) {
        this.sign = sign;
    }

    public byte getDiffer() {
        return differ;
    }

    public void setDiffer(byte differ) {
        this.differ = differ;
    }

    public byte getSum() {
        return sum;
    }

    public void setSum(byte sum) {
        this.sum = sum;
    }

    public byte getLocking() {
        return locking;
    }

    public void setLocking(byte locking) {
        this.locking = locking;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[11];
        byte[] tmp = new byte[2];
        tmp = BytesTransform.shortToBytes(lowAngle);
        bytes[0] = tmp[0];
        bytes[1] = tmp[1];
        tmp = BytesTransform.shortToBytes(directionAngle);
        bytes[2] = tmp[0];
        bytes[3] = tmp[1];
        tmp = BytesTransform.charToByte(sign[0]);
        bytes[4] = tmp[0];
        bytes[5] = tmp[1];
        tmp = BytesTransform.charToByte(sign[1]);
        bytes[6] = tmp[0];
        bytes[7] = tmp[1];
        bytes[8] = differ;
        bytes[9] = sum;
        bytes[10] = locking;
        return bytes;
    }

    public void fromToBytes(byte[] bytes) {
        byte[] bytes1 = new byte[2];
        bytes1[0] = bytes[0];
        bytes1[1] = bytes[1];
        lowAngle = BytesTransform.bytesToShort(bytes1);
        bytes1[0] = bytes[2];
        bytes1[1] = bytes[3];
        directionAngle = BytesTransform.bytesToShort(bytes1);
        bytes1[0] = bytes[4];
        bytes1[1] = bytes[5];
        sign[0] = BytesTransform.byteToChar(bytes1);
        bytes1[0] = bytes[6];
        bytes1[1] = bytes[7];
        sign[1] = BytesTransform.byteToChar(bytes1);
        differ = bytes[8];
        sum = bytes[9];
        locking = bytes[10];
        signstr=""+sign[0]+sign[1];
    }

    @Override
    public String toString() {
        String str = "";
        str += "俯仰角度为：" + lowAngle + "° " + "方位角度为：" + directionAngle + "° " + "卫星标识为：" + sign[0] + sign[1];
        if (differ == 1) {
            str += " 差路极化左旋";
        } else {
            str += " 差路极化右旋";
        }
        if (sum == 1) {
            str += " 和路极化左旋";
        } else {
            str += " 和路极化右旋";
        }
        if (locking == 1) {
            str += " 载波锁定";
        } else {
            str += " 载波失锁";
        }
        return str;
    }

    public String toJson() {
        String str = "";
        str = "{\"lowAngle\":" + lowAngle + "," +
                "\"directionAngle\":" + directionAngle + "," +
                "\"sign\":" + "\"" + sign[0] + sign[1] + "\"" + "," +
                "\"differ\":" + differ + "," +
                "\"sum\":" + sum + "," +
                "\"locking\":" + locking +","+
                "\"terminalid\":" + terminalid +","+
                "\"type\":1"+
                "}";
        return str;
    }
}
