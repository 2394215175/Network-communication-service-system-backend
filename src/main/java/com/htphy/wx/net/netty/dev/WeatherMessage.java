package com.htphy.wx.net.netty.dev;

public class WeatherMessage {
    private short temperature = 0;
    private short humidity = 0;
    private int illumination = 0;
    private byte pressure = 0;
    private short velocity = 0;
    //1:东，2:西，3:南,4:北
    private byte direction = 0;
    private short rainfall = 0;
    private String date = null;
    private int terminalid = 0;

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

    public short getTemperature() {
        return temperature;
    }

    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    public short getHumidity() {
        return humidity;
    }

    public void setHumidity(short humidity) {
        this.humidity = humidity;
    }

    public int getIllumination() {
        return illumination;
    }

    public void setIllumination(int illumination) {
        this.illumination = illumination;
    }

    public byte getPressure() {
        return pressure;
    }

    public void setPressure(byte pressure) {
        this.pressure = pressure;
    }

    public short getVelocity() {
        return velocity;
    }

    public void setVelocity(short velocity) {
        this.velocity = velocity;
    }

    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }

    public short getRainfall() {
        return rainfall;
    }

    public void setRainfall(short rainfall) {
        this.rainfall = rainfall;
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[14];
        byte[] tmp = new byte[2];
        tmp = BytesTransform.shortToBytes(temperature);
        bytes[0] = tmp[0];
        bytes[1] = tmp[1];
        tmp = BytesTransform.shortToBytes(humidity);
        bytes[2] = tmp[0];
        bytes[3] = tmp[1];
        byte[] tmp2 = new byte[4];
        tmp2 = BytesTransform.intToBytes(illumination);
        bytes[4] = tmp2[0];
        bytes[5] = tmp2[1];
        bytes[6] = tmp2[2];
        bytes[7] = tmp2[3];
        bytes[8] = pressure;
        tmp = BytesTransform.shortToBytes(velocity);
        bytes[9] = tmp[0];
        bytes[10] = tmp[1];
        bytes[11] = direction;
        tmp = BytesTransform.shortToBytes(rainfall);
        bytes[12] = tmp[0];
        bytes[13] = tmp[1];
        return bytes;
    }
    /*
    * 由字节转换为成员
    * */
    public void fromToBytes(byte[] bytes) {
        byte[] bytes2 = new byte[2];
        bytes2[0] = bytes[0];
        bytes2[1] = bytes[1];
        temperature = BytesTransform.bytesToShort(bytes2);
        bytes2[0] = bytes[2];
        bytes2[1] = bytes[3];
        humidity = BytesTransform.bytesToShort(bytes2);
        byte[] bytes1 = new byte[4];
        bytes1[0] = bytes[4];
        bytes1[1] = bytes[5];
        bytes1[2] = bytes[6];
        bytes1[3] = bytes[7];
        illumination = BytesTransform.bytes2int(bytes1);
        pressure = bytes[8];
        bytes2[0] = bytes[9];
        bytes2[1] = bytes[10];
        velocity = BytesTransform.bytesToShort(bytes2);
        direction = bytes[11];
        bytes2[0] = bytes[12];
        bytes2[1] = bytes[13];
        rainfall = BytesTransform.bytesToShort(bytes2);
    }

    @Override
    public String toString() {
        String str = "";
        str += "温度为：" + temperature + "℃  " + "湿度为：" + humidity + "%RH  " + "光照为：" + illumination + "Lux  " + "气压为：" + pressure + "Kpa  " + "风速为：" + velocity + "m/s ";
        switch (direction) {
            case 1:
                str += "风向为东 ";
                break;
            case 2:
                str += "风向为西 ";
                break;
            case 3:
                str += "风向为南 ";
                break;
            case 4:
                str += "风向为北 ";
                break;
            default:
                break;
        }
        str += "雨量为：" + rainfall + "mm ";
        return str;
    }

    public String toJson() {
        String str = "";
        str = "{\"temperature\":" + temperature + "," +
                "\"humidity\":" + humidity + "," +
                "\"illumination\":" + illumination + "," +
                "\"pressure\":" + pressure + "," +
                "\"velocity\":" + velocity + "," +
                "\"direction\":" + direction + "," +
                "\"rainfall\":" + rainfall +","+
                "\"terminalid\":" + terminalid +","+
                "\"type\":0"+
                "}";
        return str;
    }
}
