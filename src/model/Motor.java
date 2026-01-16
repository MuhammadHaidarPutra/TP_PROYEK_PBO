package model;

public class Motor extends Kendaraan {
    private int mesinCC;

    public Motor(String plat, String merk, String jenis, String tahun, int cc) {
           super(plat, merk, jenis, tahun);
           this.mesinCC = cc;
           this.hargaPerHari = 70000;
    }

    public int getMesinCC() { 
        return mesinCC; 
    }
    public void setMesinCC(int cc) { 
        this.mesinCC = cc;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + "\nMesin CC: " + mesinCC;
    }
}
