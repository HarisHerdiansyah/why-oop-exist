import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class WaktuSistemApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Tekan Enter untuk menampilkan tanggal dan jam sistem...");
        input.nextLine(); // tunggu user tekan Enter

        // Panggil method dari class WaktuSistem
        WaktuSistem.tampilkanTanggalDanJam();

        System.out.println("Tekan Enter untuk menampilkan tanggal dan jam sistem...");
        input.nextLine(); // tunggu user tekan Enter

        // Panggil method dari class WaktuSistem
        WaktuSistem.tampilkanTanggalDanJam();

        input.close();
    }
}

// Class pembantu di file yang sama
class WaktuSistem {

    // Method untuk mengambil tanggal dan jam sistem
    public static String getTanggalDanJam() {
        LocalDateTime sekarang = LocalDateTime.now();

        // Format: Hari, tanggal bulan tahun | jam:menit:detik WIB
        DateTimeFormatter format = DateTimeFormatter.ofPattern(
            "EEEE, dd MMMM yyyy | HH:mm:ss 'WIB'",
            new Locale("id", "ID")
        );

        return sekarang.format(format);
    }

    // Method untuk menampilkan ke layar
    public static void tampilkanTanggalDanJam() {
        System.out.println("Tanggal dan Waktu Sekarang: " + getTanggalDanJam());
    }
}
