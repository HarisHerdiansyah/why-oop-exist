class Time:
    def __init__(self, hour, minute, second):
        self.__hour = hour
        self.__minute = minute
        self.__second = second
    
    def get_hour(self):
        return self.__hour
    
    def set_hour(self, hour):
        self.__hour = hour
    
    def get_minute(self):
        return self.__minute
    
    def set_minute(self, minute):
        self.__minute = minute
    
    def get_second(self):
        return self.__second
    
    def set_second(self, second):
        self.__second = second


class MhsTPB:
    def __init__(self, nama, npm, waktu_mulai, waktu_selesai):
        self.__nama = nama
        self.__npm = npm
        self.__waktu_mulai = waktu_mulai
        self.__waktu_selesai = waktu_selesai
    
    def get_nama(self):
        return self.__nama
    
    def set_nama(self, nama):
        self.__nama = nama
    
    def get_npm(self):
        return self.__npm
    
    def set_npm(self, npm):
        self.__npm = npm
    
    def get_waktu_mulai(self):
        return self.__waktu_mulai
    
    def set_waktu_mulai(self, waktu_mulai):
        self.__waktu_mulai = waktu_mulai
    
    def get_waktu_selesai(self):
        return self.__waktu_selesai
    
    def set_waktu_selesai(self, waktu_selesai):
        self.__waktu_selesai = waktu_selesai


class Node:
    def __init__(self, mhs):
        self.__mhs = mhs
        self.__next = None
    
    def get_mhs(self):
        return self.__mhs
    
    def set_mhs(self, mhs):
        self.__mhs = mhs
    
    def get_next(self):
        return self.__next
    
    def set_next(self, next_node):
        self.__next = next_node


class LinkedList:
    def __init__(self):
        self.head = None
    
    def prepend(self, mhs):
        if self.head is None:
            self.head = Node(mhs)
        else:
            new_node = Node(mhs)
            new_node.set_next(self.head)
            self.head = new_node
    
    def append(self, mhs):
        if self.head is None:
            self.head = Node(mhs)
            return
        
        current = self.head
        while current.get_next() is not None:
            current = current.get_next()
        current.set_next(Node(mhs))
    
    def insert(self, mhs, pos):
        if self.head is None:
            self.head = Node(mhs)
            return
        
        current = self.head
        i = 0
        while i < pos - 1 and current.get_next() is not None:
            current = current.get_next()
            i += 1
        
        new_node = Node(mhs)
        new_node.set_next(current.get_next())
        current.set_next(new_node)
    
    def delete_first(self):
        if self.head is None:
            return
        temp = self.head
        self.head = temp.get_next()
    
    def delete_last(self):
        if self.head is None:
            return
        
        if self.head.get_next() is None:
            self.head = None
            return
        
        current = self.head
        while current.get_next().get_next() is not None:
            current = current.get_next()
        current.set_next(None)
    
    def find(self, npm):
        if self.head is None:
            return None
        
        if self.head.get_next() is None:
            return self.head if self.head.get_mhs().get_npm() == npm else None
        
        current = self.head
        while current is not None:
            if current.get_mhs().get_npm() == npm:
                return current
            current = current.get_next()
        return None
    
    def print_node(self):
        current = self.head
        while current is not None:
            print(current.get_mhs().get_nama())
            current = current.get_next()


if __name__ == "__main__":
    ll = LinkedList()
    
    t1 = Time(8, 0, 0)
    t2 = Time(10, 0, 0)
    mhs1 = MhsTPB("Budi", "123456", t1, t2)
    
    ll.append(mhs1)
    ll.print_node()