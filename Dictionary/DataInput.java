package cse105;

/**
 *
 * @author ziheng.zhang14
 */
    import java.text.ParseException ;
    import java.text.SimpleDateFormat ;
    import java.util.Date ;
    import java.util.Scanner ;

    public class DataInput {

        static Scanner kb = new Scanner(System.in);

    // The Scanner variable kb is only used in one place. 
        // Easy to change way of doing keyboard input.
        public static String inputString() {
            return kb.nextLine();
        }

        public static String inputStringTrimmed() {
            return inputString().trim();
        }

        public static Date inputDateRange(Date lower, Date upper) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            boolean validInput = false;
            while (!validInput) {
                d = inputDate();
                if (d.compareTo(lower) >= 0 && d.compareTo(upper) <= 0) {
                    validInput = true;
                } else {
                    System.out.println("Not from " + df.format(lower) + " to " + df.format(upper));
                }
            }
            return d;
        }

        public static Date inputDate() {
            SimpleDateFormat df = new SimpleDateFormat("d M y");
            Date d = null;
            while (d == null) {
                System.out.println("Enter a date: ");
                String s = inputString();
                try {
                    d = df.parse(s);
                } catch (ParseException e) {
                    System.out.println("Not a valid date");
                }
            }
            return d;
        }

        public static Date inputDateFlexible() {
            SimpleDateFormat df1 = new SimpleDateFormat("d M y");
            SimpleDateFormat df2 = new SimpleDateFormat("d/M/y");
            Date d = null;
            while (d == null) {
                System.out.println("Enter a date: ");
                String s = inputString();
                try {
                    d = df1.parse(s);
                } catch (ParseException e1) {
                    try {
                        d = df2.parse(s);
                    } catch (ParseException e2) {
                        System.out.println("Not a valid date");
                    }
                }
            }
            return d;
        }

        public static int inputIntegerRange(int lower, int upper) {
            int n = 0;
            boolean validInput = false;
            while (!validInput) {
                n = inputInteger();
                if (n >= lower && n <= upper) {
                    validInput = true;
                } else {
                    System.out.println("Not from " + lower + " to " + upper);
                }
            }
            return n;
        }

        public static int inputInteger() {
            int i = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Enter an integer: ");
                String s = inputString();
                try {
                    i = Integer.parseInt(s);
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Not a valid integer");
                }
            }
            return i;
        }

        public static double inputDouble() {
            double d = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Enter a double: ");
                String s = inputString();
                try {
                    d = Double.parseDouble(s);
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Not a valid double");
                }
            }
            return d;
        }

        public static boolean inputBoolean() {
            boolean b = false;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Enter T or F: ");
                String s = inputString();
                if (s.equalsIgnoreCase("T") || s.equalsIgnoreCase("F")) {
                    validInput = true;
                    b = s.equalsIgnoreCase("T");
                } else {
                    System.out.println("Not a valid input");
                }
            }
            return b;
        }

}