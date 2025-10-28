package com.example.linkedlist;

import com.example.mahasiswa.*;
import com.example.waktu.*;

public class LinkedList {
    private Node head;
    
    public LinkedList() {
        this.head = null;
    }

    public Node getHead() { return head; }
    
    public void prepend(MhsTPB mhs) {
        if (head == null) {
            head = new Node(mhs);
        } else {
            Node newNode = new Node(mhs);
            newNode.setNext(head);
            head = newNode;
        }
    }
    
    public void append(MhsTPB mhs) {
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
    
    public void insert(MhsTPB mhs, int pos) {
        if (pos <= 0) {
            prepend(mhs);
            return;
        }
        
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
    
    public void deleteFirst() {
        if (head == null) return;
        head = head.getNext();
    }
    
    public void deleteLast() {
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
    
    public Node find(String npm) {        
        Node current = head;
        while (current != null) {
            if (current.getMhs().getNpm().equals(npm)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }
    
    public void tampilkanDenganNilai() {
        Node current = head;
        int no = 1;
        System.out.println("=======================================================================================");
        System.out.println("No.  NPM      Nama          Waktu Mulai  Waktu Selesai  Lama         HM  Status");
        System.out.println("=======================================================================================");
        
        while (current != null) {
            MhsTPB mhs = current.getMhs();
            String lamaFormatted = hitungLamaFormatted(mhs);
            String hurufMutu = mhs.getHurufMutu();
            String status = mhs.getStatus();
            
            System.out.printf("%-4d %-8s %-13s %-11s  %-12s  %-11s  %-2s  %-6s\n",
                no,
                mhs.getNpm(),
                mhs.getNama(),
                formatTime(mhs.getWaktuMulai()),
                formatTime(mhs.getWaktuSelesai()),
                lamaFormatted,
                hurufMutu,
                status
            );
            
            current = current.getNext();
            no++;
        }
        System.out.println("=======================================================================================");
    }
    
    private String hitungLamaFormatted(MhsTPB mhs) {
        double totalDetik = mhs.getWaktuMulai().hitungSelisih(mhs.getWaktuSelesai());
        int menit = (int) (totalDetik / 60);
        int detik = (int) (totalDetik % 60);
        
        return menit + ":" + detik;
    }
    
    public void printNode() {
        Node current = head;
        while (current != null) {
            System.out.println(current.getMhs().getNama());
            current = current.getNext();
        }
    }
    
    private String formatTime(Time time) {
        return String.format("%02d:%02d:%02d", 
            time.getHour(), time.getMinute(), time.getSecond());
    }
}
