import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Waktu {
   private int jam;
   private int menit;
   private int detik;

   public Waktu() {
   }

   public Waktu(int jam, int menit, int detik) {
       this.jam = jam;
       this.menit = menit;
       this.detik = detik;
   }

   public int getJam() {
       return jam;
   }

   public void setJam(int jam) {
       this.jam = jam;
   }

   public int getMenit() {
       return menit;
   }

   public void setMenit(int menit) {
       this.menit = menit;
   }

   public int getDetik() {
       return detik;
   }

   public void setDetik(int detik) {
       this.detik = detik;
   }

   public int totalTimeInSeconds() {
       return (jam * 3600) + (menit * 60) + detik;
   }

   @Override
   public String toString() {
       return String.format("%02d:%02d:%02d", jam, menit, detik);
   }
}

class Mahasiswa {
   private String name;
   private String npm;
   private Waktu mulai;
   private Waktu selesai;

   public Mahasiswa() {
   }

   public Mahasiswa(String name, String npm, Waktu mulai, Waktu selesai) {
       this.name = name;
       this.npm = npm;
       this.mulai = mulai;
       this.selesai = selesai;
   }

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }

   public Waktu getSelesai() {
       return selesai;
   }

   public void setSelesai(Waktu selesai) {
       this.selesai = selesai;
   }

   public Waktu getMulai() {
       return mulai;
   }

   public void setMulai(Waktu mulai) {
       this.mulai = mulai;
   }

   public String getNpm() {
       return npm;
   }

   public void setNpm(String npm) {
       this.npm = npm;
   }
}

public class Soal1 {
   private static final Scanner SCANNER = new Scanner(System.in);

   private static Waktu parseTime(String t) {
       String[] pt = t.split(":");
       return new Waktu(Integer.parseInt(pt[0]), Integer.parseInt(pt[1]), Integer.parseInt(pt[2]));
   }

   private static Mahasiswa input() {
       System.out.print("Masukkan nama: ");
       String nama = SCANNER.nextLine();
       System.out.print("Masukkan npm: ");
       String npm = SCANNER.nextLine();
       System.out.print("Masukkan waktu mulai (HH:mm:ss): ");
       String mulai = SCANNER.nextLine();
       System.out.print("Masukkan waktu selesai (HH:mm:ss): ");
       String selesai = SCANNER.nextLine();

       return new Mahasiswa(nama, npm, parseTime(mulai), parseTime(selesai));
   }

   private static Waktu difference(Waktu mulai, Waktu selesai) {
       int totalDetikMulai = mulai.totalTimeInSeconds();
       int totalDetikSelesai = selesai.totalTimeInSeconds();
       int selisihDetikTotal = totalDetikSelesai - totalDetikMulai;

       if (selisihDetikTotal < 0) selisihDetikTotal += 24 * 3600;

       int selisihJam = selisihDetikTotal / 3600;
       int selisihMenit = (selisihDetikTotal % 3600) / 60;
       int selisihDetik = selisihDetikTotal % 60;

       return new Waktu(selisihJam, selisihMenit, selisihDetik);
   }

   private static Map<String, String> calculateGrade(Mahasiswa mhs) {
       Map<String, String> hasil = new HashMap<>();
       Waktu selisihWaktu = difference(mhs.getMulai(), mhs.getSelesai());
       int totalDetik = selisihWaktu.totalTimeInSeconds();

       hasil.put("status", "Lulus");
       if (totalDetik >= 0 && totalDetik < (7.5 * 60)) {
           hasil.put("HM", "A");
       } else if (totalDetik >= (7.5 * 60) && totalDetik < (12.5 * 60)) {
           hasil.put("HM", "B");
       } else if (totalDetik >= (12.5 * 60) && totalDetik < (30 * 60)) {
           hasil.put("HM", "C");
       } else {
           hasil.put("HM", "D");
           hasil.put("status", "Gagal");
       }

       return hasil;
   }

   private static void outputBuilder(Mahasiswa mhs, Map<String, String> hasil) {
       String format = "| %-20s | %-12s | %-10s | %-8s | %-12s | %-14s | %-10s |%n";
       System.out.println("\nUjian Lari");
       System.out.println("-------------------------------------------------------------------------------------------------------------");
       System.out.format(format, "Nama", "NPM", "Huruf Mutu", "Status", "Waktu Mulai", "Waktu Selesai", "Lama Lari");
       System.out.println("-------------------------------------------------------------------------------------------------------------");
       Waktu lamaLari = difference(mhs.getMulai(), mhs.getSelesai());
       System.out.format(format,
           mhs.getName(),
           mhs.getNpm(),
           hasil.get("HM"),
           hasil.get("status"),
           mhs.getMulai().toString(),
           mhs.getSelesai().toString(),
           lamaLari
       );
       System.out.println("-------------------------------------------------------------------------------------------------------------");
   }

   public static void main(String[] args) {
       Mahasiswa mhs = input();
       Map<String, String> hasil = calculateGrade(mhs);
       outputBuilder(mhs, hasil);
   }
}
