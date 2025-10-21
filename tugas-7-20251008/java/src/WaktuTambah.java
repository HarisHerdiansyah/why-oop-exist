import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class WaktuTambah {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Tekan Enter untuk menampilkan waktu sekarang...");
        input.nextLine(); // tunggu user tekan Enter

        // Ambil waktu sistem sekali
        LocalDateTime waktuSekarang = LocalDateTime.now();
        String waktuFormatted = WaktuSistem.formatWaktu(waktuSekarang);
        System.out.println("🕒 Waktu Sekarang: " + waktuFormatted);

        System.out.println("\nTekan Enter untuk menampilkan waktu +10 jam...");
        input.nextLine(); // tunggu Enter lagi

        // Tambahkan 10 jam dari waktu yang sama
        String waktuTambah = WaktuSistem.formatWaktu(waktuSekarang.plusHours(10));
        System.out.println("⏰ +10 Jam: " + waktuTambah);

        input.close();
    }
}

class WaktuSistem {
    // Format 24 jam (HH = 00–23)
    private static final DateTimeFormatter format =
        DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy HH:mm:ss", new Locale("id", "ID"));

    public static String formatWaktu(LocalDateTime waktu) {
        return waktu.format(format);
    }
}
