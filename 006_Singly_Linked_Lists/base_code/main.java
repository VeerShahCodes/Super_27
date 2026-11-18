import pkg.*;
import java.util.*;
import java.time.*;
import java.lang.*;

class main {
	public static void main(String args[]) {
		MySinglyLinkedList list = new MySinglyLinkedList();
		for(int i = 0; i < 20; i++) {
			int num = (int)(Math.random() * 101);
			list.insert(0, num);
		}
		for(int i = 0; i < 20; i++) {
			int num = (int)(Math.random() * 101);
			list.insert(i, num);
		}

		list.printList();

		MySinglyLinkedList reversedList = new MySinglyLinkedList();
		for(int i = 0; i < 20; i++) {
			reversedList.insert(0, list.get(i));
		}

		System.out.println("Reversed List:");
		reversedList.printList();

	}
}
