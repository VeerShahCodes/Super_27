package pkg;
import java.util.*;
import java.io.*;

public class BBoard {		// This is your main file that connects all classes.
	// Think about what your global variables need to be.
	private String title;
	private ArrayList<User> userList;
	private ArrayList<Message> messageList;
	private User currentUser;
	// Default constructor that creates a board with a defaulttitle, empty user and message lists,
	// and no current user
	public BBoard() {
		title = "Default Title";
		userList = new ArrayList<User>();
		messageList = new ArrayList<Message>();
		currentUser = null;
	}

	// Same as the default constructor except it sets the title of the board
	public BBoard(String ttl) {	
		title = ttl;
		userList = new ArrayList<User>();
		messageList = new ArrayList<Message>();
		currentUser = null;
	}

	// Gets a filename of a file that stores the user info in a given format (users.txt)
	// Opens and reads the file of all authorized users and passwords
	// Constructs a User object from each name/password pair, and populates the userList ArrayList.
	public void loadUsers(String inputFile) throws FileNotFoundException {
		Scanner fileScanner = new Scanner(new File(inputFile));
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String username = line.substring(0, line.indexOf((" ")));
			String password = line.substring(line.indexOf(" ") + 1);
			userList.add(new User(username, password));
		}
		fileScanner.close();
	}

	// Asks for and validates a user/password. 
	// This function asks for a username and a password, then checks the userList ArrayList for a matching User.
	// If a match is found, it sets currentUser to the identified User from the list
	// If not, it will keep asking until a match is found or the user types 'q' or 'Q' as username to quit
	// When the users chooses to quit, sayu "Bye!" and return from the login function
	public void login(){
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("Username: ");
			String username = scanner.nextLine();
			if(username.equalsIgnoreCase("q")) {
				System.out.println("Bye!");
				return;
			}
			System.out.print("Password: ");
			String password = scanner.nextLine();
			boolean found = false;
			
			for(int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				System.out.println(user.getUsername());
				if(user.check(username, password)) {
					currentUser = user;
					found = true;
					break;
				}
			}

			if(found) break;
			System.out.println("Invalid username or password. Try again.");
		}
	}
	
	// Contains main loop of Bulletin Board
	// IF and ONLY IF there is a valid currentUser, enter main loop, displaying menu items
	// --- Display Messages ('D' or 'd')
	// --- Add New Topic ('N' or 'n')
	// --- Add Reply ('R' or 'r')
	// --- Change Password ('P' or 'p')
	// --- Quit ('Q' or 'q')
	// With any wrong input, user is asked to try again
	// Q/q should reset the currentUser to 0 and then end return
	// Note: if login() did not set a valid currentUser, function must immediately return without showing menu
	public void run(){
		login();
		if(currentUser != null) {
			while(true) {
				System.out.println("Menu");
				System.out.println("- Display Messages ('D' or 'd')");
				System.out.println("- Add New Topic ('N' or 'n')");
				System.out.println("- Add Reply ('R' or 'r')");
				System.out.println("- Change Password ('P' or 'p')");
				System.out.println("- Quit ('Q' or 'q')");
				System.out.print("Choose an action: ");
				Scanner scanner = new Scanner(System.in);
				String choice = scanner.nextLine();
				if(choice.equalsIgnoreCase("D")) {
					display();
				} else if(choice.equalsIgnoreCase("N")) {
					addTopic();
				} else if(choice.equalsIgnoreCase("R")) {
					addReply();
				} else if(choice.equalsIgnoreCase("P")) {
					setPassword();
				} else if(choice.equalsIgnoreCase("Q")) {
					currentUser = null;
					return;
				} else {
					System.out.println("Invalid choice. Try again.");
				}
			}
		}
	}

	// Traverse the BBoard's message list, and invote the print function on Topic objects ONLY
	// It will then be the responsibility of the Topic object to invoke the print function recursively on its own replies
	// The BBoard display function will ignore all reply objects in its message list
	private void display(){
		for(int i = 0; i < messageList.size(); i++) {
			if(messageList.get(i) instanceof Topic) {
				System.out.println("------------------------------------------");
				((Topic) messageList.get(i)).print(0);
				System.out.println("------------------------------------------");
			}
		}
	}


	// This function asks the user to create a new Topic (i.e. the first message of a new discussion "thread")
	// Every Topic includes a subject (single line), and body (single line)

	/* 
	Subject: "Thanks"
	Body: "I love this bulletin board that you made!"
	*/

	// Each Topic also stores the username of currentUser; and message ID, which is (index of its Message + 1)

	// For example, the first message on the board will be a Topic who's index will be stored at 0 in the messageList ArrayList,
	// so its message ID will be (0+1) = 1
	// Once the Topic has been constructed, add it to the messageList
	// This should invoke your inheritance of Topic to Message
	private void addTopic(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Topic: ");
		String topicString = scanner.nextLine();
		System.out.print("Body: ");
		String body = scanner.nextLine();
		int id = messageList.size() + 1;
		Topic topic = new Topic(currentUser.getUsername(), topicString, body, id);
		messageList.add((Message)topic);
	}

	// This function asks the user to enter a reply to a given Message (which may be either a Topic or a Reply, so we can handle nested replies).
	//		The addReply function first asks the user for the ID of the Message to which they are replying;
	//		if the number provided is greater than the size of messageList, it should output and error message and loop back,
	// 		continuing to ask for a valid Message ID number until the user enters it or -1.
	// 		(-1 returns to menu, any other negative number asks again for a valid ID number)
	
	// If the ID is valid, then the function asks for the body of the new message, 
	// and constructs the Reply, pushing back the Reply on to the messageList.
	// The subject of the Reply is a copy of the parent Topic's subject with the "Re: " prefix.
	// e.g., suppose the subject of message #9 was "Thanks", the user is replying to that message:


	/*
			Enter Message ID (-1 for Menu): 9
			Body: It was a pleasure implementing this!
	*/

	// Note: As before, the body ends when the user enters an empty line.
	// The above dialog will generate a reply that has "Re: Thanks" as its subject
	// and "It was a pleasure implementing this!" as its body.

	// How will we know what Topic this is a reply to?
	// In addition to keeping a pointer to all the Message objects in BBoard's messageList ArrayList
	// Every Message (wheather Topic or Reply) will also store an ArrayList of pointers to all of its Replies.
	// So whenever we build a Reply, we must immediately store this Message in the parent Message's list. 
	// The Reply's constructor should set the Reply's subject to "Re: " + its parent's subject.
	// Call the addChild function on the parent Message to push back the new Message (to the new Reply) to the parent's childList ArrayList.
	// Finally, push back the Message created to the BBoard's messageList. 
	// Note: When the user chooses to return to the menu, do not call run() again - just return fro mthis addReply function. 
	private void addReply(){
		System.out.println("Enter Message ID (-1 for Menu): ");
		Scanner scanner = new Scanner(System.in);
		int id = scanner.nextInt();
		if(id == -1) {
			return;
		}
		if(id <= 0 || id > messageList.size()) {
			System.out.println("Invalid Message ID. Please try again.");
			addReply();
			return;
		}
		Message parentMessage = messageList.get(id - 1);
		System.out.print("Body: ");
		scanner.nextLine(); 
		String body = scanner.nextLine();
		String subject = "Re: " + parentMessage.getSubject();
		int newId = messageList.size() + 1;
		Reply reply = new Reply(currentUser.getUsername(), subject, body, newId);
		parentMessage.addChild(reply);
		messageList.add(reply);
	}

	// This function allows the user to change their current password.
	// The user is asked to provide the old password of the currentUser.
	// 		If the received password matches the currentUser password, then the user will be prompted to enter a new password.
	// 		If the received password doesn't match the currentUser password, then the user will be prompted to re-enter the password. 
	// 		The user is welcome to enter 'c' or 'C' to cancel the setting of a password and return to the menu.
	// Any password is allowed except 'c' or 'C' for allowing the user to quit out to the menu. 
	// Once entered, the user will be told "Password Accepted." and returned to the menu.
	private void setPassword(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter old password (or 'c' to cancel): ");
		String oldPassword = scanner.nextLine();
		if(oldPassword.equalsIgnoreCase("c")) {
			return;
		}
		while(!currentUser.check(currentUser.getUsername(), oldPassword)) {
			System.out.println("Incorrect password. Please try again.");
			System.out.print("Enter old password (or 'c' to cancel): ");
			oldPassword = scanner.nextLine();
			if(oldPassword.equalsIgnoreCase("c")) {
				return;
			}
		}
		System.out.print("Enter new password (or 'c' to cancel): ");
		String newPassword = scanner.nextLine();
		if(newPassword.equalsIgnoreCase("c")) {
			return;
		}
		currentUser.setPassword(oldPassword, newPassword);
		System.out.println("Password Accepted.");
	}

}
