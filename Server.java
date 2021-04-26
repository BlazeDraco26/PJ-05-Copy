import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread {

    public static final String CREATE_ACCOUNT = "CREATE_ACCOUNT";
    public static final String LOGIN_ACCOUNT = "LOGIN_ACCOUNT";
    public static final String GET_ALL_ACCOUNTS = "GET_ALL_ACCOUNTS";

    public static final int DEFAULT_PORT = 8080;
    private ArrayList<Account> accounts;
    private int port;

    public Server(int port) {
        this.port = port;
        accounts = new ArrayList<Account>();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for the client to connect...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                String command = (String) ois.readObject();
                switch (command) {
                    case CREATE_ACCOUNT: // create account and returns the Account, if successful
                        Account account = null;
                        try {
                            account = createAccount((String) ois.readObject(), (String) ois.readObject());
                        } catch (ExistingAccountException e) {
                            System.out.println(e.getMessage());
                        }
                        oos.writeObject(account);
                        break;
                    case LOGIN_ACCOUNT: // login to account returns the Account, if successful
                        String username = (String) ois.readObject();
                        if (checkPassword(username, (String) ois.readObject())) {
                            oos.writeObject(findAccount(username));
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                    case GET_ALL_ACCOUNTS:
                        oos.writeObject(getAllAccounts());
                        break;

                    default:
                        System.out.println("Unknown command: " + command);
                }

                ois.close();
                oos.close();
//                socket.close();
            }
//            writer.close();
//            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server exiting");
    }

    public static void main(String[] args) {
        new Server(args.length == 0 ? DEFAULT_PORT : Integer.parseInt(args[0]))
            .run();
    }

    public ArrayList<Account> getAllAccounts() {
        return this.accounts;
    }

    // creates a new account object and adds it to the account ArrayList
    // || username.length() < 5 || password.length() < 5 ||
    //                !(username.matches("^[a-zA-Z0-9]*$")) || !(password.matches("^[a-zA-Z0-9]*$"))
    public Account createAccount(String username, String password) throws ExistingAccountException {
        if (findAccount(username) != null) {
            throw new ExistingAccountException("Please use a different username.");
        }
        Account account = new Account(username, password);
        accounts.add(account);
        return account;
    }

    public Account createAccount(String username, String password, String firstName, String lastName,
                                 boolean isPublic, String bio, String interests) throws ExistingAccountException {
        if (findAccount(username) != null) {
            throw new ExistingAccountException("Please use a different username.");
        }
        Account account = new Account(username, password);
        accounts.add(account);
        return account;
    }


    // finds an account in the account ArrayList searching by username
    public Account findAccount(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    // validates that the username exists, and validates that the password associated with that username is correct
    public boolean checkPassword(String username, String password) {
        Account account = findAccount(username);
        if (account != null) {
            if (account.getPassword().equalsIgnoreCase(password)) {
                return true;
            }
        }
        return false;
    }

    // changes the password of an account, provided the account exists
    public void changePassword(String username, String newPassword) {
        Account account = findAccount(username);
        if (account != null) {
            account.setPassword(newPassword);
        }
    }

    // deletes an account from the account Arraylist
    public void deleteAccount(String username) {
        Account account = findAccount(username);
        if (account != null) {
            accounts.remove(account);
        }
    }

    // creates a new profile
    public void createProfile(Account account, String firstName, String lastName, String isPublic, String bio, String interests, ArrayList <String> friendsList) {
        for (int i = 0; i < firstName.length(); i++) {
            if (!Character.isLowerCase(firstName.charAt(i)) && !Character.isUpperCase(firstName.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < lastName.length(); i++) {
            if (!Character.isLowerCase(lastName.charAt(i)) && !Character.isUpperCase(lastName.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
        account.setProfile(new Profile(firstName, lastName, isPublic, bio, interests, friendsList));
    }

    public void changeFirstName(String username, String firstName) {
        for (int i = 0; i < firstName.length(); i++) {
            if (!Character.isLowerCase(firstName.charAt(i)) && !Character.isUpperCase(firstName.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
        Account account = findAccount(username);
        if (account != null) {
            account.getProfile().setFirstName(firstName);
        }
    }

    public void changeLastName(String username, String lastName) {
        for (int i = 0; i < lastName.length(); i++) {
            if (!Character.isLowerCase(lastName.charAt(i)) && !Character.isUpperCase(lastName.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
        Account account = findAccount(username);
        if (account != null) {
            account.getProfile().setLastName(lastName);
        }
    }

    public void changePrivacy(String username, String isPublic) {
        Account account = findAccount(username);
        if (account != null) {
            account.getProfile().setIsPublic(isPublic);
        }
    }

    public void editBio(String username, String bio) {
        Account account = findAccount(username);
        if (account != null) {
            account.getProfile().setBio(bio);
        }
    }

    public void editInterests(String username, String interests) {
        Account account = findAccount(username);
        if (account != null) {
            account.getProfile().setInterests(interests);
        }
    }

    public void deleteProfile(String username) {
        Account account = findAccount(username);
        if (account != null) {
            account.setProfile(null);
        }
    }

    public void importandexportProfileToFile() {
        try {
            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(accounts);

            o.close();
            f.close();

            FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            ArrayList<Account> pr1 = (ArrayList<Account>) oi.readObject();

            System.out.println(pr1.toString());

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    // private String firstName; // stores the first name
    //    private String lastName; // stores the last name
    //    private boolean isPublic; //whether the account is public or not
    //    private String bio; // Stores the bio or "about me" of a specific profile
    //    private String interests;
    // given
//    public String delegate(String message) {
//        String[] splitMessage = message.split(" ");
//        switch (splitMessage[0]) {
//            case "Create":
//                String username = splitMessage[1];
//                String password = splitMessage[2];
//                String firstName = splitMessage[3];
//                String lastName = splitMessage[4];
//                boolean isPublic = Boolean.parseBoolean(splitMessage[5]);
//                String bio = splitMessage[6];
//                String interests = splitMessage[7];
////                Account account = new Account(username, password, firstName, lastName, isPublic, bio, interests);
////                accounts.add(account);
//                break;
//        }
//    }
}

// Add friend - adds each of the users to each others list
// remove friend - removes each of the users from each others list
// request friend - adds a friend request to the recipient's request list
// check requests - parameters: username (recipient) ensures that there are not duplicate friend requests from the same user
// check friends - cannot have duplicate usernames in friends list, cannot remove a friend you don't have

