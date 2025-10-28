import java.util.Scanner;


class Time {
	private int hour, minute, second;
	
	public Time(int hour, int minute, int second) {
	    this.hour = hour;
	    this.minute = minute;
	    this.second = second;
	}
	
	int getHour() { return hour; }
	
	void setHour(int hour) { this.hour = hour; }
	
    int getMinute() { return minute; }
	
	void setMinute(int minute) { this.minute = minute; }
	
	int getSecond() { return second; }
	
	void setSecond(int second) { this.second = second; }
}

class MhsTPB {
    private String nama;
    private String npm;
    private Time waktuMulai;
    private Time waktuSelesai;

    public MhsTPB(String nama, String npm, Time waktuMulai, Time waktuSelesai) {
        this.nama = nama;
        this.npm = npm;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
    }

    public String getNama() {
        return nama;
    }

    public String getNpm() {
        return npm;
    }

    public Time getWaktuMulai() {
        return waktuMulai;
    }

    public Time getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public void setWaktuMulai(Time waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public void setWaktuSelesai(Time waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }
}

class Node {
    private MhsTPB mhs;
    private Node next;
    
    public Node(MhsTPB mhs) {
        this.mhs = mhs;
        this.next = null;
    }
    
    public MhsTPB getMhs() {
        return mhs;
    }
    
    public Node getNext() {
        return next;
    }
    
    public void setMhs(MhsTPB mhs) {
        this.mhs = mhs;
    }
    
    public void setNext(Node next) {
        this.next = next;
    }
}

class LinkedList {
    Node head;
    
    public LinkedList() {
        this.head = null;
    }
    
    void prepend(MhsTPB mhs) {
        if (head == null) {
            head = new Node(mhs);
        } else {
            Node newNode = new Node(mhs);
            newNode.setNext(head);
            head = newNode;
        }
    }
    
    void append(MhsTPB mhs) {
        if (head == null) {
            head = new Node(mhs);
            return;
        }
        
        Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
        current.setNext(new Node(mhs));
    }
    
    void insert(MhsTPB mhs, int pos) {
        if (head == null) {
            head = new Node(mhs);
            return;
        }
        
        Node current = head;
        for (int i = 0; i < pos - 1 && current.getNext() != null; i++) {
            current = current.getNext();
        }
        
        Node newNode = new Node(mhs);
        newNode.setNext(current.getNext());
        current.setNext(newNode);
    }
    
    void deleteFirst() {
        if (head == null) return;
        Node temp = head;
        head = temp.getNext();
    }
    
    void deleteLast() {
        if (head == null) return;
        
        if (head.getNext() == null) {
            head = null;
            return;
        }
        
        Node current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        current.setNext(null);
    }
    
    Node find(String npm) {        
        if (head.getNext() == null) {
            return head.getMhs().getNpm() == npm ? head : null;
        }
        
        Node current = head;
        while (current != null) {
            if (current.getMhs().getNpm() == npm) {
                return current;
            }
            current = current.getNext();
        }
        return null;
     }
            
    void printNode() {
        Node current = head;
        while (current != null) {
            System.out.println(current.getMhs().getNama());
            current = current.getNext();
        }
    }
}

public class TPB {
    private static Scanner scanner = new Scanner(System.in);
    private static LinkedList list = new LinkedList();
    
    public static void main(String[] args) {
        System.out.println("=== PROGRAM MANAJEMEN DATA MAHASISWA TPB ===");
        
        // Input jumlah data awal
        System.out.print("Masukkan jumlah data mahasiswa awal: ");
        int jumlahAwal = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        // Input data awal
        for (int i = 0; i < jumlahAwal; i++) {
            System.out.println("\nData Mahasiswa ke-" + (i + 1));
            inputDataMahasiswa(true);
        }
        
        // Menu utama
        int pilihan;
        do {
            tampilkanMenu();
            System.out.print("Pilih menu (1-8): ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (pilihan) {
                case 1:
                    tambahDiAwal();
                    break;
                case 2:
                    tambahDiAkhir();
                    break;
                case 3:
                    tambahDiPosisi();
                    break;
                case 4:
                    hapusPertama();
                    break;
                case 5:
                    hapusTerakhir();
                    break;
                case 6:
                    cariData();
                    break;
                case 7:
                    tampilkanSemua();
                    break;
                case 8:
                    System.out.println("Terima kasih telah menggunakan program!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 8);
    }
    
    private static void tampilkanMenu() {
        System.out.println("\n=== MENU UTAMA ===");
        System.out.println("1. Tambah Data di Awal");
        System.out.println("2. Tambah Data di Akhir");
        System.out.println("3. Tambah Data di Posisi Tertentu");
        System.out.println("4. Hapus Data Pertama");
        System.out.println("5. Hapus Data Terakhir");
        System.out.println("6. Cari Data berdasarkan NPM");
        System.out.println("7. Tampilkan Semua Data");
        System.out.println("8. Keluar");
    }
    
    private static void inputDataMahasiswa(boolean isNew) {
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        
        System.out.print("Masukkan NPM: ");
        String npm = scanner.nextLine();
        
        System.out.println("Waktu Mulai:");
        Time waktuMulai = inputWaktu();
        
        System.out.println("Waktu Selesai:");
        Time waktuSelesai = inputWaktu();
        
        MhsTPB mhs = new MhsTPB(nama, npm, waktuMulai, waktuSelesai);
        
        if (isNew) {
            list.append(mhs);
        }
        
        System.out.println("Data berhasil diinput!");
    }
    
    private static Time inputWaktu() {
        System.out.print("  Jam (0-23): ");
        int hour = scanner.nextInt();
        System.out.print("  Menit (0-59): ");
        int minute = scanner.nextInt();
        System.out.print("  Detik (0-59): ");
        int second = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        return new Time(hour, minute, second);
    }
    
    private static void tambahDiAwal() {
        System.out.println("\n=== TAMBAH DATA DI AWAL ===");
        inputDataMahasiswa(false);
        MhsTPB mhs = createMhsTPBFromInput();
        list.prepend(mhs);
        System.out.println("Data berhasil ditambahkan di awal!");
    }
    
    private static void tambahDiAkhir() {
        System.out.println("\n=== TAMBAH DATA DI AKHIR ===");
        inputDataMahasiswa(false);
        MhsTPB mhs = createMhsTPBFromInput();
        list.append(mhs);
        System.out.println("Data berhasil ditambahkan di akhir!");
    }
    
    private static void tambahDiPosisi() {
        System.out.println("\n=== TAMBAH DATA DI POSISI TERTENTU ===");
        
        System.out.print("Masukkan posisi (index dimulai dari 0): ");
        int posisi = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        inputDataMahasiswa(false);
        MhsTPB mhs = createMhsTPBFromInput();
        list.insert(mhs, posisi);
        System.out.println("Data berhasil ditambahkan di posisi " + posisi + "!");
    }
    
    private static MhsTPB createMhsTPBFromInput() {
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        
        System.out.print("Masukkan NPM: ");
        String npm = scanner.nextLine();
        
        System.out.println("Waktu Mulai:");
        Time waktuMulai = inputWaktu();
        
        System.out.println("Waktu Selesai:");
        Time waktuSelesai = inputWaktu();
        
        return new MhsTPB(nama, npm, waktuMulai, waktuSelesai);
    }
    
    private static void hapusPertama() {
        System.out.println("\n=== HAPUS DATA PERTAMA ===");
        list.deleteFirst();
        System.out.println("Data pertama berhasil dihapus!");
    }
    
    private static void hapusTerakhir() {
        System.out.println("\n=== HAPUS DATA TERAKHIR ===");
        list.deleteLast();
        System.out.println("Data terakhir berhasil dihapus!");
    }
    
    private static void cariData() {
        System.out.println("\n=== CARI DATA BERDASARKAN NPM ===");
        System.out.print("Masukkan NPM yang dicari: ");
        String npm = scanner.nextLine();
        
        Node hasil = list.find(npm);
        if (hasil != null) {
            MhsTPB mhs = hasil.getMhs();
            System.out.println("\nData ditemukan:");
            System.out.println("Nama    : " + mhs.getNama());
            System.out.println("NPM     : " + mhs.getNpm());
            System.out.println("Waktu   : " + formatTime(mhs.getWaktuMulai()) + " - " + 
                             formatTime(mhs.getWaktuSelesai()));
        } else {
            System.out.println("Data dengan NPM " + npm + " tidak ditemukan!");
        }
    }
    
    private static void tampilkanSemua() {
        System.out.println("\n=== SEMUA DATA MAHASISWA ===");
        if (list.head == null) {
            System.out.println("Tidak ada data mahasiswa.");
        } else {
            list.printNode();
        }
    }
    
    private static String formatTime(Time time) {
        return String.format("%02d:%02d:%02d", 
            time.getHour(), time.getMinute(), time.getSecond());
    }
}