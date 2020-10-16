package com.htphy.wx.net.netty.dev;

public class PositionMessage {
    double lng=0;
    double lat=0;
    int terminalid=0;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }
    public void fromToBytes(byte[] bytes){
        byte[] bytes1=new byte[8];
        byte[] bytes2=new byte[8];
        for (int i = 0; i <8 ; i++) {
           bytes1[i]= bytes[i];
        }
        for (int i = 0; i <8 ; i++) {
            bytes2[i]= bytes[i+8];
        }
        lng=BytesTransform.byte2Double(bytes1);
        lat=BytesTransform.byte2Double(bytes2);
    }
}
