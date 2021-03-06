
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 *
 * @author Eddie
 */
public class Main {

    public static void main(String[] args) {

        char[][] aud1 = null;
        char[][] aud2 = null;
        char[][] aud3 = null;

        //safely opens all the files
        try {
            aud1 = readAud(new File("A1.txt"));
        } catch (IOException ex) {
            System.out.println("A1.txt not found");
        }

        try {
            aud2 = readAud(new File("A2.txt"));
        } catch (IOException ex) {
            System.out.println("A2.txt not found");
        }

        try {
            aud3 = readAud(new File("A3.txt"));
        } catch (IOException ex) {
            System.out.println("A3.txt not found");
        }

        //Has table for users
        Hashtable table = new Hashtable();

        //opens and reads users into hashtable
        try {
            File usersF = new File("userdb.dat");
            Scanner users = new Scanner(usersF);
            while (users.hasNextLine()) {
                User user = new User(users.next(), users.next());
                table.put(user.getName(), user);
            }
            users.close();

        } catch (IOException e) {
            // complain to user
            System.out.println("File not found" + e);
        }

        //creates only input scanner
        Scanner input = new Scanner(System.in);
        
        //main menu unput loop
        boolean login = true;
        while (login) {
            //gets user name and looks for it in table
            System.out.println("Enter username:");
            String username = input.next();
            if (table.containsKey(username)) {

                int tries = 3;

                //gives user 3 tries to login
                while (tries > 0) {
                    System.out.println("Enter password:");
                    String password = input.next();

                    //checks if password is correct
                    if (password.equals(((User) table.get(username)).getPassword())) {
                        //if user is admin and pasword matches admin menu is opened
                        if (username.equals("admin")) {
                            boolean adminMenu = true;
                            while (adminMenu) {
                                System.out.println("1. View Auditorium\n" + "2. Print Report\n" + "3. Exit\n");
                                int in = input.nextInt();
                                switch (in) {
                                    case 1:
                                        boolean audSelect = true;
                                        //admin menu
                                        while (audSelect) {
                                            //displays foramted table based on input
                                            System.out.println("0. Exit\n" + "1. Auditorium 1\n" + "2. Auditorium 2\n" + "3. Auditorium 3");
                                            int in2 = input.nextInt();
                                            switch (in2) {
                                                case 1:
                                                    display(aud1);
                                                    break;
                                                case 2:
                                                    display(aud2);
                                                    break;
                                                case 3:
                                                    display(aud3);
                                                    break;
                                                case 0:
                                                    audSelect = false;
                                                    break;
                                                default:
                                                    System.out.println("Invalid Auditorium");
                                                    break;
                                            }
                                        }
                                        break;
                                    case 2:
                                        //formated report
                                        System.out.printf("%16s%16s%16s%16s%16s\n", "Labels", "Auditorium 1", "Auditorium 2", "Auditorium 3", "Total");
                                        int aud1open = 0;
                                        int aud1res = 0;
                                        //sums up type of seats for each aud
                                        for (int i = 0; i < aud1.length; i++) {
                                            for (int j = 0; j < aud1[0].length; j++) {
                                                if (aud1[i][j] == '.') {
                                                    aud1res++;
                                                } else {
                                                    aud1open++;
                                                }
                                            }
                                        }
                                        int aud2open = 0;
                                        int aud2res = 0;
                                        for (int i = 0; i < aud2.length; i++) {
                                            for (int j = 0; j < aud2[0].length; j++) {
                                                if (aud2[i][j] == '.') {
                                                    aud2res++;
                                                } else {
                                                    aud2open++;
                                                }
                                            }
                                        }
                                        int aud3open = 0;
                                        int aud3res = 0;
                                        for (int i = 0; i < aud3.length; i++) {
                                            for (int j = 0; j < aud3[0].length; j++) {
                                                if (aud3[i][j] == '.') {
                                                    aud3res++;
                                                } else {
                                                    aud3open++;
                                                }
                                            }
                                        }
                                        System.out.printf("%16s%16d%16d%16d%16d\n", "Open seats", aud1open, aud2open, aud3open, (aud1open + aud2open + aud3open));
                                        System.out.printf("%16s%16d%16d%16d%16d\n", "Open seats", aud1res, aud2res, aud3res, (aud1res + aud2res + aud3res));

                                        //calculates how many of each tickets in each aud
                                        int aud1adult = 0;
                                        int aud1senior = 0;
                                        int aud1child = 0;
                                        int aud2adult = 0;
                                        int aud2senior = 0;
                                        int aud2child = 0;
                                        int aud3adult = 0;
                                        int aud3senior = 0;
                                        int aud3child = 0;
                                        Set<String> keys = table.keySet();
                                        for (String user : keys) {
                                            User u = (User) table.get(user);
                                            for (int i = 0; i < u.orders.size(); i++) {
                                                if (((Order) u.orders.get(i)).aud == 1) {
                                                    aud1adult += ((Order) u.orders.get(i)).adults;
                                                    aud1senior += ((Order) u.orders.get(i)).senior;
                                                    aud1child += ((Order) u.orders.get(i)).child;
                                                }
                                                if (((Order) u.orders.get(i)).aud == 2) {
                                                    aud2adult += ((Order) u.orders.get(i)).adults;
                                                    aud2senior += ((Order) u.orders.get(i)).senior;
                                                    aud2child += ((Order) u.orders.get(i)).child;
                                                }
                                                if (((Order) u.orders.get(i)).aud == 3) {
                                                    aud3adult += ((Order) u.orders.get(i)).adults;
                                                    aud3senior += ((Order) u.orders.get(i)).senior;
                                                    aud3child += ((Order) u.orders.get(i)).child;
                                                }
                                            }
                                        }
                                        System.out.printf("%16s%16d%16d%16d%16d\n", "Adult seats", aud1adult, aud2adult, aud3adult, (aud1adult + aud2adult + aud3adult));
                                        System.out.printf("%16s%16d%16d%16d%16d\n", "Senior seats", aud1senior, aud2senior, aud3senior, (aud1senior + aud2senior + aud3senior));
                                        System.out.printf("%16s%16d%16d%16d%16d\n", "Child seats", aud1child, aud2child, aud3child, (aud1child + aud2child + aud3child));
                                        System.out.printf("%16s%15.2f$%15.2f$%15.2f$%15.2f$\n", "Ticket sales($)", ((aud1adult * 10) + (aud1senior * 7.5) + (aud1child * 5.25) ), ((aud2adult * 10) + (aud2senior * 7.5) + (aud2child* 5.25)), ((aud3adult * 10) + (aud3senior * 7.5) + (aud3child * 5.25)), (((aud1adult * 10) + (aud1senior * 7.5) + (aud1child * 5.25) ) + ((aud2adult * 10) + (aud2senior * 7.5) + (aud2child* 5.25)) + ((aud3adult * 10) + (aud3senior * 7.5) + (aud3child * 5.25))));
                                        break;
                                    case 3:
                                        adminMenu = false;
                                        login = false;
                                        break;
                                }
                            }
                            }else {
                            boolean mainmenu = true;
                            //Noraml user main menu
                            while (mainmenu) {
                                System.out.println("1. Reserve Seats\n" + "2. View Orders\n" + "3. Update Order\n" + "4. Display Receipt\n" + "5. Log Out");
                                int in = 0;
                                if(input.hasNextInt()){
                                    in = input.nextInt();
                                }
                                else{
                                    System.out.println("Invalid Input");
                                    input.next();
                                    continue;
                                }
                                
                                switch (in) {
                                    case 1:
                                        boolean aud = true;
                                        while (aud) {
                                            //amesk new order and calls reserve for chossen aud
                                            System.out.println("Which auditorium\n" + "1. Auditorium 1\n" + "2. Auditorium 2\n" + "3. Auditorium 3");
                                            int num = input.nextInt();
                                            if (num == 1 || num == 2 || num == 3) {
                                                if (num == 1) {
                                                    Order newOrder = new Order();
                                                    newOrder.aud = 1;
                                                    
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                    reserve(input, aud1, newOrder,(User) table.get(username));
                                                }
                                                if (num == 2) {
                                                    Order newOrder = new Order();
                                                    newOrder.aud = 2;
                                                    
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                    reserve(input, aud2, newOrder,(User) table.get(username));
                                                }
                                                if (num == 3) {
                                                    Order newOrder = new Order();
                                                    newOrder.aud = 3;
                                                   
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                    reserve(input, aud3, newOrder,(User) table.get(username));
                                                }

                                                break;
                                            } else {
                                                System.out.println("Invalid input");
                                            }
                                        }
                                        break;
                                    case 2:
                                        viewOrders((User) table.get(username));
                                        break;
                                    case 3:
                                        updateOrder(input,(User) table.get(username),aud1,aud2,aud3);
                                        break;
                                    case 4:
                                        receipt((User) table.get(username));
                                        break;
                                    case 5:
                                        mainmenu = false;
                                        break;
                                    default:
                                        System.out.println("Invalid input");
                                        break;
                                }

                            }
                        }
                            break;
                        }
                    else{
                        tries--;
                    }
                    }
                }
            }
        //outpus data to files for future use
            try {
                writeF(aud1, new File("A1.txt"));
                writeF(aud2, new File("A2.txt"));
                writeF(aud3, new File("A3.txt"));
            } catch (Exception ex) {

            }

        }

    //takes care of reserving seats and the menu for it
    public static void reserve(Scanner input, char[][] aud, Order order, User user) {
        display(aud);
        //stored incase roll back is needed in invalid seats are added and best cant be found
        int oldA = order.adults;
        int oldS = order.senior;
        int oldC = order.child;
        
        //asks for how many of each ticket
        boolean looping = true;
        while (looping) {
            System.out.println("How many adult tickets");
            if (input.hasNextInt()) {
                order.adults = input.nextInt();
                break;
            } else {
                System.out.println("Invalid input");
            }
        }

        looping = true;
        while (looping) {
            System.out.println("How many senior tickets");
            if (input.hasNextInt()) {
                order.senior = input.nextInt();
                break;
            } else {
                System.out.println("Invalid input");
            }
        }

        looping = true;
        while (looping) {
            System.out.println("How many child tickets");
            if (input.hasNextInt()) {
                order.child = input.nextInt();
                break;
            } else {
                System.out.println("Invalid input");
            }
        }

        //stores old order incase users enters invalid seats
        int[] tmpr = order.rows;
        int[] tmps = order.seats;
        char[] tmpt = order.types;
        int offset = order.sum;
        
        //creates arrays in ordder object
        order.sum = offset + order.child + order.senior + order.adults;
        order.rows = new int[order.sum];
        order.seats = new int[order.sum];
        order.types = new char[order.sum];
        
        for(int i = 0; i < offset;i++){
            order.rows[i] = tmpr[i];
            order.seats[i] = tmps[i];
            order.types[i] = tmpt[i];
        }
            

        //promts for each seat based on seat type
        for (int i = 0; i < order.adults; i++) {
            System.out.println("Enter row number");
            while (looping) {
                if (input.hasNextInt()) {
                    order.rows[offset + i] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while (looping) {
                if (input.hasNextInt()) {
                    //accounts seats previously on the order
                    order.seats[offset + i] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            order.types[offset + i] = 'A';
        }
        for (int i = 0; i < order.senior; i++) {
            System.out.println("Enter row number");
            while (looping) {
                if (input.hasNextInt()) {
                    //accounts for adult seats already added and ones previously on the order
                    order.rows[offset + i + order.adults] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while (looping) {
                if (input.hasNextInt()) {
                    //accounts for adult seats already added and ones previously on the order
                    order.seats[offset + i + order.adults] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            order.types[offset + i + order.adults] = 'S';
        }

        for (int i = 0; i < order.child; i++) {
            System.out.println("Enter row number");
            while (looping) {
                if (input.hasNextInt()) {
                    //accounts for adult seats already added and ones previously on the order
                    order.rows[offset + i + order.adults + order.senior] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while (looping) {
                if (input.hasNextInt()) {
                    //accounts for adult seats already added and ones previously on the order
                    order.seats[offset + i + order.adults + order.senior] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            order.types[offset + i + order.adults + order.senior] = 'C';
        }
        boolean g2r = true; // good to reserve
        for (int i = offset; i < order.sum; i++) {
            if (!available(aud, order.rows[i], order.seats[i], 1)) {
                g2r = false;
                //best avalible

                if(bestAvalible(aud, order.sum - offset, input, order, user)){
                    order.adults += oldA;
                    order.senior += oldS;
                    order.child += oldC;
                }
                else{
                    order.rows = tmpr;
                    order.seats = tmps;
                    order.types = tmpt;
                    order.sum = order.rows.length;
                }
                break;

            }
        }
        if (g2r) {
            //reserves seats
            for (int i = offset; i < order.sum; i++) {
                aud[order.rows[i]][order.seats[i]] = '.';
            }
            order.adults += oldA;
            order.senior += oldS;
            order.child += oldC;
            
            
            System.out.println("Seats reserved");
        }

    }

    //loops though file line by line char by char and simply adds to 2d array
    public static char[][] readAud(File fileraw) throws IOException {
        Scanner tmp = new Scanner(fileraw);
        String line = "";
        //Scanner tmp = new Scanner(null);
        int j = 0;
        while (tmp.hasNextLine()) {
            line = tmp.nextLine();
            j++;
        }
        char[][] aud = new char[j][line.length()];
        Scanner file = new Scanner(fileraw);
        j = 0;
        while (file.hasNextLine()) {
            line = file.nextLine();
            for (int i = 0; i < line.length(); i++) {
                aud[j][i] = line.charAt(i);
            }
            j++;
        }
        return aud;
    }

    //Prints formated auditorium
    public static void display(char[][] aud) {
        //Prints colum headers as 1 digit
        System.out.print(" ");
        for (int i = 1; i <= aud[0].length; i++) {
            System.out.print(i % 10);
        }
        //Loops through 2d array and prints seats
        System.out.print("\n");
        for (int i = 0; i < aud.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < aud[0].length; j++) {
                System.out.print("" + aud[i][j]);
            }
            System.out.print("\n");
        }

    }

    //Check is the requested seats are avalible
    public static boolean available(char[][] aud, int row, int seat, int quantity) {
        //Checks if best seat didn't find a seat
        if (seat == 999) {
            return false;
        }

        //out of bounds
        if (seat < 0 || row < 0) {
            return false;
        }
        //Checks if requested seats would go out of bounds
        if (seat + quantity > aud[0].length) {
            return false;
        }
        //checks if row is out
        if(row > aud.length){
            return false;
        }
        //Checks if seats are avalible
        for (int i = 0; i < quantity; i++) {
            if (aud[row][seat + i] == '.') {
                return false;
            }
        }
        return true;
    }

    public static double distance(int row, int seat, int mr, int ms) {
        //uses standard distance formula to find distance fron point (usaully middle seat)
        double dis = Math.sqrt(Math.pow(Math.abs((double) row - (double) mr), 2) + Math.pow(Math.abs((double) seat - (double) ms), 2));
        if (row == mr) {
            //Tie breaker
            dis -= .1;
        }
        return dis;
    }

    public static boolean bestAvalible(char[][] aud, int quan, Scanner input, Order order, User user) {
        //finds middle seat and sets best to max int value
        int middleR = aud.length / 2;
        int middleC = aud[0].length / 2;
        double shortestDistance = 2147483647;
        int bestR = -1;
        int bestC = -1;

        //if a seat can accomadte party it's distance is checked and if its better starting seat is recorded
        for (int i = 0; i < aud.length; i++) {
            for (int j = 0; j < aud[0].length; j++) {
                if (available(aud, i, j, quan)) {
                    if (shortestDistance > distance(i, j + (quan / 2), middleR, middleC)) {
                        shortestDistance = distance(i, j + (quan / 2), middleR, middleC);
                        bestR = i;
                        bestC = j;
                    }
                }
            }
        }
        //takes care of menu and reserving the seats
        System.out.println("Seat Requested could not be reserved");
        if (bestR != -1) {
            System.out.println("Would you like row:" + (bestR + 1)+ " Seat: " + (bestC + 1) + " instead ? (y/n)");
            char yn = input.next().charAt(0);
            if (yn == 'Y' || yn == 'y') {
                //count which new seat to reserve
                int count = 0;
                //reserves seats and adds them to order
                for (int i = order.sum - quan; i < order.sum; i++) {
                    aud[bestR][bestC + count] = '.';
                    order.rows[i] = bestR;
                    order.seats[i] = bestC + count;
                    count++;
                }
                System.out.println("Seats reserved");
            }
            else{
                user.orders.remove(order);
            }
        } else {
            System.out.println("No seats can accommodate your party size");
            //cleans up order if user can not be acomadated
            if(quan == order.sum){
                user.orders.remove(order);
            }
            return false;
        }
        return true;
    }

    //writes 2d array to file
    public static void writeF(char[][] aud, File file) throws IOException {
        OutputStream f = new FileOutputStream(file);
        for (int i = 0; i < aud.length; i++) {
            for (int j = 0; j < aud[0].length; j++) {
                f.write(aud[i][j]);
            }
            f.write('\r');
            f.write('\n');

        }
    }

    //displays all orders for the user
    public static void viewOrders(User user) {
        for (int i = 0; i < user.orders.size(); i++) {
            System.out.print("Order: " + (i + 1));
            System.out.print("\tAuditorium: " + ((Order) user.orders.get(i)).aud);
            System.out.print("\tAdult tickets: " + ((Order) user.orders.get(i)).adults);
            System.out.print("\tSenior tickets: " + ((Order) user.orders.get(i)).senior);
            System.out.print("\tSenior tickets: " + ((Order) user.orders.get(i)).child);
            System.out.print("\tSeats: ");
            for (int j = 0; j < ((Order) user.orders.get(i)).sum; j++) {
                System.out.print("(" + (((Order) user.orders.get(i)).rows[j] + 1) + "," + (((Order) user.orders.get(i)).seats[j] + 1) + ")");
            }
            System.out.println();
        }
    }

    public static void receipt(User user) {
        //displays and sums price of tickets
        double priceSum = 0;
        for (int i = 0; i < user.orders.size(); i++) {
            System.out.print("Order: " + (i + 1));
            System.out.print("\tAuditorium: " + ((Order) user.orders.get(i)).aud);
            System.out.print("\tAdult tickets: " + ((Order) user.orders.get(i)).adults + " $" + (((Order) user.orders.get(i)).adults) * 10);
            System.out.print("\tSenior tickets: " + ((Order) user.orders.get(i)).senior + " $" + (((Order) user.orders.get(i)).senior * 7.50));
            System.out.print("\tChild tickets: " + ((Order) user.orders.get(i)).child + " $" + (((Order) user.orders.get(i)).child * 5.25));
            System.out.print("\tTotal: $" + ((((Order) user.orders.get(i)).child * 5.25) + (((Order) user.orders.get(i)).senior * 7.50) + (((Order) user.orders.get(i)).adults * 10)));
            priceSum += ((((Order) user.orders.get(i)).child * 5.25) + (((Order) user.orders.get(i)).senior * 7.50) + (((Order) user.orders.get(i)).adults * 10));
            System.out.print("\tSeats: ");
            for (int j = 0; j < ((Order) user.orders.get(i)).sum; j++) {
                System.out.print("(" + (((Order) user.orders.get(i)).rows[j] + 1)+ "," + (((Order) user.orders.get(i)).seats[j] + 1)+ ")");
            }
            System.out.println();
        }
        System.out.println("User total: $" + priceSum);
    }

    public static void updateOrder(Scanner input, User user, char[][] aud1,char[][] aud2, char[][] aud3) {
        //updates order info
        char[][] aud = null;
        viewOrders(user);
        //selects order
        System.out.println("Choose an order to update (or 0 to exit): ");
        int order = input.nextInt() - 1;
        if(order == -1){
            return;
        }
        boolean menu = true;
        while (menu) {
            //sets the aud beased on the Order
            System.out.println("1. Add tickets to order\n" + "2. Delete tickets from order\n" + "3. Cancel Order");
            switch(((Order) user.orders.get(order)).aud){
                case 1:
                    aud = aud1;
                    break;
                case 2:
                    aud = aud2;
                    break;
                case 3:
                    aud = aud3;
                    break;
            }
            
            //to reserve it just calls reserve on same order
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    reserve(input, aud, ((Order) user.orders.get(order)),user);
                    menu = false;
                    break;
                case 2:
                    
                    //delete individual seat
                    boolean submenu = true;
                    while (submenu) {
                        System.out.println("Which seat would you like to remove? (0 to exit)");
                        for (int j = 0; j < ((Order) user.orders.get(order)).sum; j++) {
                            System.out.print( "\t" + (j + 1) + ": " + ((Order) user.orders.get(order)).types[j] + "(" + (((Order) user.orders.get(order)).rows[j] + 1)+ "," + (((Order) user.orders.get(order)).seats[j] + 1) + ")");
                        }
                        System.out.println();
                        int seat = input.nextInt() - 1;
                        if(seat == -1){
                            return;
                        }
                        if (seat >= ((Order) user.orders.get(order)).sum) {
                            System.out.println("Invalid seat");
                        } else {
                            
                            //sets the seat to # the figures out what type it is and remove that from order
                            aud[((Order) user.orders.get(order)).rows[seat]][((Order) user.orders.get(order)).seats[seat]] = '#';

                            if (((Order) user.orders.get(order)).types[seat] == 'A') {
                                ((Order) user.orders.get(order)).adults--;
                            } else if (((Order) user.orders.get(order)).types[seat] == 'S') {
                                ((Order) user.orders.get(order)).senior--;
                            } else if (((Order) user.orders.get(order)).types[seat] == 'C') {
                                ((Order) user.orders.get(order)).child--;
                            }
                            ((Order) user.orders.get(order)).sum--;

                            if (((Order) user.orders.get(order)).sum == 0) {
                                user.orders.remove(order);
                                return;
                            } else {
                                ((Order) user.orders.get(order)).rows = removeElement(((Order) user.orders.get(order)).rows, seat);
                                ((Order) user.orders.get(order)).seats = removeElement(((Order) user.orders.get(order)).seats, seat);
                                ((Order) user.orders.get(order)).types = removeElementC(((Order) user.orders.get(order)).types, seat);
                            }

                        }
                    }

                    menu = false;
                    break;
                case 3:
                    //sets all seats back to # and removes from order array list
                    for (int i = 0; i < ((Order) user.orders.get(order)).sum; i++) {
                        aud[((Order) user.orders.get(order)).rows[i]][((Order) user.orders.get(order)).seats[i]] = '#';
                    }
                    user.orders.remove(order);
                    menu = false;
                    break;
                default:
                    System.out.println("Invalid input");

            }
        }

    }

    //helper method for remove to get rid of specfic element and resize
    public static int[] removeElement(int[] original, int element) {
        int[] n = new int[original.length - 1];
        System.arraycopy(original, 0, n, 0, element);
        System.arraycopy(original, element + 1, n, element, original.length - element - 1);
        return n;
    }

    //helper method for remove to get rid of specfic element and resize
    public static char[] removeElementC(char[] original, int element) {
        char[] n = new char[original.length - 1];
        System.arraycopy(original, 0, n, 0, element);
        System.arraycopy(original, element + 1, n, element, original.length - element - 1);
        return n;
    }

}
