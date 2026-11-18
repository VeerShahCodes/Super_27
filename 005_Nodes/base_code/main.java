import pkg.*;
import java.util.*;
import java.time.*;
import java.lang.*;

class main {
	public static void main(String args[]) {
		/*
			Create an ArrayList of 100 Nodes
			Store random integers in each of them
			Print out all of the values
		*/

		ArrayList<Node> list = new ArrayList<Node>();

		for(int i = 0; i < 100; i++) {
			int num = (int)(Math.random() * 101);
			Node n = new Node(num);
			list.add(n);
		}

		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getData());
		}
		

	}
}
