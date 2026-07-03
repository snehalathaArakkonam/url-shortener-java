import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * URL Shortener - Console Mini Project
 * Core Java only | HashMap based storage | No external libraries
 */
public class URLShortener {

    // Stores shortCode -> originalURL
    private static final Map<String, String> urlMap = new HashMap<>();
    private static final String BASE_URL = "https://short.ly/";
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final java.util.Random random = new java.util.Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("=========================================");
        System.out.println("        URL SHORTENER - MINI PROJECT     ");
        System.out.println("=========================================");

        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addUrl(sc);
                    break;
                case "2":
                    retrieveUrl(sc);
                    break;
                case "3":
                    showAllMappings();
                    break;
                case "4":
                    running = false;
                    System.out.println("\nThank you for using URL Shortener. Bye!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please enter a number between 1 and 4.\n");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ------------------");
        System.out.println("1. Add a long URL");
        System.out.println("2. Retrieve original URL from short code");
        System.out.println("3. Display all saved URL mappings");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    // Option 1: Add a long URL and generate a short code
    private static void addUrl(Scanner sc) {
        System.out.print("\nEnter the long URL: ");
        String longUrl = sc.nextLine().trim();

        // Basic input validation
        if (longUrl.isEmpty()) {
            System.out.println("Error: URL cannot be empty.");
            return;
        }
        if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
            System.out.println("Error: URL must start with http:// or https://");
            return;
        }

        String shortCode = generateUniqueShortCode();
        urlMap.put(shortCode, longUrl);

        System.out.println("Short URL created successfully!");
        System.out.println("Short URL: " + BASE_URL + shortCode);
        System.out.println("Short Code: " + shortCode);
    }

    // Option 2: Retrieve the original URL using a short code
    private static void retrieveUrl(Scanner sc) {
        System.out.print("\nEnter the short code (e.g., " + BASE_URL + "xxxxxx or just xxxxxx): ");
        String input = sc.nextLine().trim();

        // Allow user to paste full short URL or just the code
        String code = input.startsWith(BASE_URL) ? input.substring(BASE_URL.length()) : input;

        if (code.isEmpty()) {
            System.out.println("Error: Short code cannot be empty.");
            return;
        }

        String originalUrl = urlMap.get(code);
        if (originalUrl == null) {
            System.out.println("Error: No URL found for short code '" + code + "'.");
        } else {
            System.out.println("Original URL: " + originalUrl);
        }
    }

    // Option 3: Display all stored mappings
    private static void showAllMappings() {
        System.out.println("\n---------- SAVED URL MAPPINGS ----------");
        if (urlMap.isEmpty()) {
            System.out.println("No URLs have been shortened yet.");
            return;
        }
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            System.out.println(BASE_URL + entry.getKey() + "  ->  " + entry.getValue());
        }
    }

    // Generates a random alphanumeric code and ensures it's unique in the map
    private static String generateUniqueShortCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < CODE_LENGTH; i++) {
                sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
            }
            code = sb.toString();
        } while (urlMap.containsKey(code)); // avoid duplicate short codes
        return code;
    }
}