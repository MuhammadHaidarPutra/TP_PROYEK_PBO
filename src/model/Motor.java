package model;

public class Motor extends Kendaraan {

    private int mesinCC;

    public Motor(String id, String merek, String model, double harga, int cc) {
        super(id, merek, model, harga);
        this.mesinCC = cc;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\nMesin CC: " + mesinCC;
    }
}
