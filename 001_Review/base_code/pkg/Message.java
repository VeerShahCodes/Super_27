package pkg;
import java.util.*;
import java.io.*;

public class Message {

	private String author;
	private String subject;
	private String body;
	private int id;

	private ArrayList<Message> childList;

	// Default Constructor
	public Message() {
		author = "";
		subject = "";
		body = "";
		id = -1;
		childList = new ArrayList<Message>();
	}
	
	// Parameterized Constructor
	public Message(String auth, String subj, String bod, int i) {
		author = auth;
		subject = subj;
		body = bod;
		id = i;
		childList = new ArrayList<Message>();
	}

	// This function is responsbile for printing the Message
	// (whether Topic or Reply), and all of the Message's "subtree" recursively:

	// After printing the Message with indentation n and appropriate format (see output details),
	// it will invoke itself recursively on all of the Replies inside its childList, 
	// incrementing the indentation value at each new level.

	// Note: Each indentation increment represents 2 spaces. e.g. if indentation ==  1, the reply should be indented 2 spaces, 
	// if it's 2, indent by 4 spaces, etc. 
	public void print(int indentation){
		for(int i = 0; i < indentation; i++) {
			System.out.print("  ");
		}
		System.out.println("Message #" + id + ": " + subject);
		System.out.println("From " + author + ": " + body);

		for (Message child : childList) {
			child.print(indentation + 1);
		}

	}

	// Default function for inheritance
	public boolean isReply(){
		if(this instanceof Reply) {
			return true;
		}
		return false;
	}

	// Returns the subject String
	public String getSubject(){
		return subject;
	} 

	// Returns the ID
	public int getId(){
		return id;
	}

	// Adds a child pointer to the parent's childList.
	public void addChild(Message child){
		childList.add(child);
	}

}
