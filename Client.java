import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: Client <server> <command>");
			System.exit(0);
		}
		String server = args[0];
		String command = args[1];

		System.out.println("Loading contents of URL: " + server);

		try {
			// Connect to the server
			Socket socket = new Socket(server, Server.DEFAULT_PORT);

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			if (command.equalsIgnoreCase(Server.GET_ALL_ACCOUNTS)) {
				oos.writeObject(args[1].toUpperCase());
				oos.flush();
				List<Account> accounts = (List<Account>) ois.readObject();
				print(accounts);
			} else if (command.equalsIgnoreCase(Server.CREATE_ACCOUNT)) {
//				oos.writeObject(String.format("%s %s, %s", args[1].toUpperCase(), args[2], args[3]));
				oos.writeObject(args[1].toUpperCase()); // command
				oos.writeObject(args[2]);
				oos.writeObject(args[3]);
				oos.flush();

				Object o = ois.readObject();
				if (o == null) {
					System.out.println("Failed to create account");
				} else {
					Account account = (Account) o;
					System.out.println("Created " + account);
				}
//				String ack = (String) ois.readObject();
			} else if (command.equalsIgnoreCase(Server.LOGIN_ACCOUNT)) {
				oos.writeObject(args[1].toUpperCase());
				oos.writeObject(args[2]);
				oos.writeObject(args[3]);
				oos.flush();
				Object o = ois.readObject();
				if (o == null) {
					System.out.println("Authentication failed");
				} else {
					System.out.println((Account) o);
				}
			} else if (command.equalsIgnoreCase("GET_ALL_ACCOUNTS")) {
				oos.writeObject(args[1].toUpperCase());
				Object o = ois.readObject();
				if (o == null) {
					System.out.println("Authentication failed");
				} else {
					print((List<Account>) o);
				}
			} else if (command.equalsIgnoreCase("CHANGE_PASSWORD")) {
				oos.writeObject(args[1].toUpperCase());
				oos.writeObject(args[2]);
				oos.writeObject(args[3]);
				oos.flush();
				Boolean o = (Boolean) ois.readObject();
				System.out.println(o);
			} else if (command.equalsIgnoreCase("DELETE_ACCOUNT")) {
				oos.writeObject(args[1].toUpperCase());
				oos.writeObject(args[2]);
				oos.flush();
				Boolean o = (Boolean) ois.readObject();
				System.out.println(o);
			} else if (command.equalsIgnoreCase("CREATE_PROFILE")) {
				oos.writeObject(args[1].toUpperCase());
				oos.writeObject(args[2]);
				oos.writeObject(args[3]);
				oos.writeObject(args[4]);
				oos.writeObject(args[5]);
				oos.writeObject(args[6]);
				oos.writeObject(args[7]);
				oos.flush();
				Object o = ois.readObject();
				if (o == null) {
					System.out.println("Unable to create profile.");
				} else {
					System.out.println((Account) o);
				}
			} else if (command.equalsIgnoreCase("EDIT_PROFILE")) {
				oos.writeObject(args[1].toUpperCase());
				oos.writeObject(args[2]);
				oos.writeObject(args[3]);
				oos.writeObject(args[4]);
				oos.writeObject(args[5]);
				oos.writeObject(args[6]);
				oos.writeObject(args[7]);
				oos.flush();
				Object o = ois.readObject();
				if (o == null) {
					System.out.println("Unable to edit profile.");
				} else {
					System.out.println((Account) o);
				}
			} else if (command.equalsIgnoreCase("DELETE_PROFILE")) {
				oos.writeObject(args[1].toUpperCase());
				oos.writeObject(args[2]);
				oos.flush();
				Boolean o = (Boolean) ois.readObject();
				System.out.println(o);
			}
			oos.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void print(List<Account> accounts) {
		if (accounts == null || accounts.size() == 0) {
			System.out.println("No accounts!");
		}
		for (Account account : accounts) {
			System.out.println(account);
		}
	}
}
