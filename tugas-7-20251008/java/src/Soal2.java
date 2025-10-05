//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//class Waktu {
//    private int jam;
//    private int menit;
//    private int detik;
//
//    public Waktu() {
//    }
//
//    public Waktu(int jam, int menit, int detik) {
//        this.jam = jam;
//        this.menit = menit;
//        this.detik = detik;
//    }
//
//    public int getJam() {
//        return jam;
//    }
//
//    public void setJam(int jam) {
//        this.jam = jam;
//    }
//
//    public int getMenit() {
//        return menit;
//    }
//
//    public void setMenit(int menit) {
//        this.menit = menit;
//    }
//
//    public int getDetik() {
//        return detik;
//    }
//
//    public void setDetik(int detik) {
//        this.detik = detik;
//    }
//
//    public int totalTimeInSeconds() {
//        return (jam * 3600) + (menit * 60) + detik;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%02d:%02d:%02d", jam, menit, detik);
//    }
//}
//
//class Mahasiswa {
//    private String name;
//    private String npm;
//    private Waktu mulai;
//    private Waktu selesai;
//
//    public Mahasiswa() {
//    }
//
//    public Mahasiswa(String name, String npm, Waktu mulai, Waktu selesai) {
//        this.name = name;
//        this.npm = npm;
//        this.mulai = mulai;
//        this.selesai = selesai;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Waktu getSelesai() {
//        return selesai;
//    }
//
//    public void setSelesai(Waktu selesai) {
//        this.selesai = selesai;
//    }
//
//    public Waktu getMulai() {
//        return mulai;
//    }
//
//    public void setMulai(Waktu mulai) {
//        this.mulai = mulai;
//    }
//
//    public String getNpm() {
//        return npm;
//    }
//
//    public void setNpm(String npm) {
//        this.npm = npm;
//    }
//}
//
//class LarikMahasiswa {
//    private final Mahasiswa[] daftarMahasiswa;
//    private final int size;
//    private int indexPointer = 0;
//
//    public LarikMahasiswa(int size) {
//        this.daftarMahasiswa = new Mahasiswa[size];
//        this.size = size;
//    }
//
//    public Mahasiswa[] getDaftarMahasiswa() {
//        return daftarMahasiswa;
//    }
//
//    public Mahasiswa getMahasiswaAt(int index) {
//        return daftarMahasiswa[index];
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public int getIndexPointer() {
//        return indexPointer;
//    }
//
//    public void setIndexPointer(int indexPointer) {
//        this.indexPointer = indexPointer;
//    }
//
//    public void push(Mahasiswa mahasiswa) {
//        if (indexPointer >= size) {
//            System.out.println("Maximum size of array exceeded.");
//            return;
//        }
//
//        daftarMahasiswa[indexPointer] = mahasiswa;
//        indexPointer += 1;
//    }
//
//    public Mahasiswa pop() {
//        Mahasiswa data = daftarMahasiswa[0];
//
//        for (int i = 1; i < indexPointer; i++) {
//            daftarMahasiswa[i - 1] = daftarMahasiswa[i];
//        }
//        indexPointer -= 1;
//        daftarMahasiswa[indexPointer] = null;
//
//        return data;
//    }
//
//    public Mahasiswa deleteAt(int index) {
//        Mahasiswa data = daftarMahasiswa[index];
//
//        for (int i = index + 1; i < indexPointer; i++) {
//            daftarMahasiswa[i - 1] = daftarMahasiswa[i];
//        }
//        indexPointer -= 1;
//        daftarMahasiswa[indexPointer] = null;
//
//        return data;
//    }
//}
//
//public class Soal2 {
//    private static final Scanner SCANNER = new Scanner(System.in);
//
//    private static Waktu parseTime(String t) {
//        String[] pt = t.split(":");
//        return new Waktu(Integer.parseInt(pt[0]), Integer.parseInt(pt[1]), Integer.parseInt(pt[2]));
//    }
//
//    private static Mahasiswa input() {
//        System.out.print("Masukkan nama: ");
//        String nama = SCANNER.nextLine();
//        System.out.print("Masukkan npm: ");
//        String npm = SCANNER.nextLine();
//        System.out.println("Masukkan waktu mulai (HH:mm:ss): ");
//        String mulai = SCANNER.nextLine();
//        System.out.println("Masukkan waktu selesai (HH:mm:ss): ");
//        String selesai = SCANNER.nextLine();
//
//        return new Mahasiswa(nama, npm, parseTime(mulai), parseTime(selesai));
//    }
//
//    private static Waktu difference(Waktu mulai, Waktu selesai) {
//        int jamMulai = mulai.getJam();
//        int menitMulai = mulai.getMenit();
//        int detikMulai = mulai.getDetik();
//        int jamSelesai = selesai.getJam();
//        int menitSelesai = selesai.getMenit();
//        int detikSelesai = selesai.getDetik();
//
//        int selisihDetik = detikSelesai - detikMulai;
//        if (detikSelesai < detikMulai) {
//            selisihDetik += 60;
//            menitSelesai -= 1;
//        }
//
//        int selisihMenit = menitSelesai - menitMulai;
//        if (menitSelesai < menitMulai) {
//            selisihMenit += 60;
//            jamSelesai -= 1;
//        }
//
//        int selisihJam = jamSelesai - jamMulai;
//
//        return new Waktu(selisihJam, selisihMenit, selisihDetik);
//    }
//
//    private static Map<String, String> calculateGrade(Mahasiswa mhs) {
//        Map<String, String> hasil = new HashMap<>();
//        Waktu selisihWaktu = difference(mhs.getMulai(), mhs.getSelesai());
//        int totalDetik = selisihWaktu.totalTimeInSeconds();
//
//        hasil.put("status", "Lulus");
//        if (totalDetik >= 0 && totalDetik < (7.5 * 60)) {
//            hasil.put("HM", "A");
//        } else if (totalDetik >= (7.5 * 60) && totalDetik < (12.5 * 60)) {
//            hasil.put("HM", "B");
//        } else if (totalDetik >= (12.5 * 60) && totalDetik < (30 * 60)) {
//            hasil.put("HM", "C");
//        } else {
//            hasil.put("HM", "D");
//            hasil.put("status", "Gagal");
//        }
//
//        return hasil;
//    }
//
//    private static void outputBuilder(Mahasiswa mhs, Map<String, String> hasil) {
//        System.out.println("\nUjian Lari");
//        System.out.println("Nama Mahasiswa: " + mhs.getName());
//        System.out.println("NPM Mahasiswa: " + mhs.getNpm());
//        System.out.println("Huruf Mutu: " + hasil.get("HM"));
//        System.out.println("Status: " + hasil.get("status"));
//        System.out.println("Waktu Mulai: " + mhs.getMulai().toString());
//        System.out.println("Waktu Selesai: " + mhs.getSelesai().toString());
//        System.out.println("Lama Lari: " + difference(mhs.getMulai(), mhs.getSelesai()));
//    }
//
//    public static void main(String[] args) {
//        System.out.println("Masukkan jumlah mahasiswa: ");
//        int n = Integer.parseInt(SCANNER.nextLine());
//        LarikMahasiswa daftarMhs = new LarikMahasiswa(n);
//
//        for (int i = 0; i < n; i++) {
//            daftarMhs.push(input());
//        }
//
//        for (int i = 0; i < n; i++) {
//            Map<String, String> hasil = calculateGrade(daftarMhs.getMahasiswaAt(i));
//            outputBuilder(daftarMhs.getMahasiswaAt(i), hasil);
//        }
//    }
//}
