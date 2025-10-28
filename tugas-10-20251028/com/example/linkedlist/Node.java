/*
Nama - NPM    : ~ Raissa Christabel Sebayang - 140810240008
                ~ Abraham Gomes Samosir - 140810240044
                ~ Haris Herdiansyah - 140810240074
Kelas         : B
Nama File     : Node.java
*/

package com.example.linkedlist;

import com.example.mahasiswa.*;;

public class Node {
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
