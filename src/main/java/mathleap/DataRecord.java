package mathleap;

public class DataRecord {
    private String data1;
    private String data2;

    public DataRecord(String data1, String data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public String getCountry() {
        return data1;
    }

    public String getNum() {
        return data2;
    }
}

