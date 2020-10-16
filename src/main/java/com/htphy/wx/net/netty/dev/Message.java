package com.htphy.wx.net.netty.dev;

public class Message {
    private byte[] data = new byte[14];
    private short identification = 0;
    private byte srcaddress = 0;
    private byte desaddress = 0;
    private byte[] rewords = new byte[2];
    //0为天气数据，1为天线状态数据，3为心跳包，16为ACK数据
    private byte datatype = 0;
    private int datalength = 0;
    private int terminalid = 0;

    public Message() {
        rewords[0] = (byte) Integer.parseInt("00", 16);
        rewords[1] = (byte) Integer.parseInt("00", 16);
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }

    public short getIdentification() {
        return identification;
    }

    public byte getSrcaddress() {
        return srcaddress;
    }

    public void setSrcaddress(byte srcaddress) {
        this.srcaddress = srcaddress;
    }

    public byte getDesaddress() {
        return desaddress;
    }

    public void setDesaddress(byte desaddress) {
        this.desaddress = desaddress;
    }

    public byte[] getRewords() {
        return rewords;
    }

    public void setRewords(byte[] rewords) {
        this.rewords = rewords;
    }

    public byte getDatatype() {
        return datatype;
    }

    public void setDatatype(byte datatype) {
        this.datatype = datatype;
    }

    public int getDatalength() {
        return datalength;
    }

    public void setDatalength(int datalength) {
        this.datalength = datalength;
    }

    public void setIdentification(short identification) {
        if (identification == (short) 32767) {
            identification = 0;
        } else {
            this.identification = identification;
        }

    }

    public byte[] getData() {
        return data;
    }

    public void setWeatherData(WeatherMessage msg) {
        this.data = msg.toBytes();
        this.terminalid=msg.getTerminalid();
    }
    public void setAntennaData(AntennaMessage msg) {
        this.data = msg.toBytes();
        this.terminalid=msg.getTerminalid();
    }
}
