package pkg;
import java.util.Scanner;
import java.util.Random;


public class MySinglyLinkedList {
	Node head;

	/* 
		Postcondition: The head will be null 
	*/
	public MySinglyLinkedList() {
		head = null;
	}

	/* 
		Receives an integer position, searches through the SinglyLinkedList for the position and returns the data at that positon
	   	If the position doesn't exist, it returns -1
	*/ 
	public int get(int pos){
		Node current = head;
		for(int i = 0; i < pos && current != null; i++) {
			current = current.getNext();
		}
		return current.getData();
	}

	/*
		Insert a new Node at the given position with the data given
	*/
	public void insert(int pos, int data) {
		Node newNode = new Node(data);

		if (pos == 0 || head == null) {
			newNode.setNext(head);
			head = newNode;
			return;
		}

		Node current = head;
		for (int i = 0; i < pos - 1 && current != null; i++) {
			current = current.getNext();
		}

		if (current == null) {
			return;
		}

		newNode.setNext(current.getNext());
		current.setNext(newNode);
	}

	/*
		Remove the node at the given position
		If no position exists, don't change the list
	*/
	public void remove(int pos){
		if (pos == 0) {
			head = head.getNext();
			return;
		}
		Node current = head;
		for(int i = 0; i < pos - 1 && current != null; i++) {
			current = current.getNext();
		}
		if (current != null && current.getNext() != null) {
			current.setNext(current.getNext().getNext());
		}
	}

	/*
		Print all data values in the LinkedList 
	*/
	public void printList(){
		Node current = head;
		while(current != null) {
			System.out.println(current.getData());
			current = current.getNext();
		}
	}
}
