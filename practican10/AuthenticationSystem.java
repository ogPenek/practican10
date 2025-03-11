import java.util.Scanner;

public class AuthenticationSystem {

    static final int MAX_USERS = 15;
    static String[] users = new String[MAX_USERS];  
    static String[] passwords = new String[MAX_USERS];  
    static int currentUserCount = 0; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1 - Додати користувача");
            System.out.println("2 - Видалити користувача");
            System.out.println("3 - Виконати дію від імені користувача");
            System.out.println("4 - Вийти");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (choice) {
                    case 1:
                        registerUser(scanner);
                        break;
                    case 2:
                        deleteUser(scanner);
                        break;
                    case 3:
                        authenticateUser(scanner);
                        break;
                    case 4:
                        System.out.println("Вихід з програми.");
                        return;
                    default:
                        System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        if (currentUserCount >= MAX_USERS) {
            throw new IllegalArgumentException("Неможливо додати більше користувачів. Ліміт досягнуто.");
        }

        System.out.print("Введіть ім'я користувача: ");
        String username = scanner.nextLine();
        validateUsername(username);

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();
        validatePassword(password);

        users[currentUserCount] = username;
        passwords[currentUserCount] = password;
        currentUserCount++;
        System.out.println("Користувача успішно зареєстровано.");
    }

    private static void deleteUser(Scanner scanner) {
        System.out.print("Введіть ім'я користувача для видалення: ");
        String username = scanner.nextLine();
        
        boolean found = false;
        for (int i = 0; i < currentUserCount; i++) {
            if (users[i].equals(username)) {
                for (int j = i; j < currentUserCount - 1; j++) {
                    users[j] = users[j + 1];
                    passwords[j] = passwords[j + 1];
                }
                users[currentUserCount - 1] = null;
                passwords[currentUserCount - 1] = null;
                currentUserCount--;
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Користувача з таким ім'ям не знайдено.");
        }

        System.out.println("Користувача успішно видалено.");
    }

    private static void authenticateUser(Scanner scanner) {
        System.out.print("Введіть ім'я користувача: ");
        String username = scanner.nextLine();

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        boolean authenticated = false;
        for (int i = 0; i < currentUserCount; i++) {
            if (users[i].equals(username) && passwords[i].equals(password)) {
                authenticated = true;
                break;
            }
        }

        if (authenticated) {
            System.out.println("Користувача успішно аутентифіковано.");
        } else {
            throw new IllegalArgumentException("Невірне ім'я користувача або пароль.");
        }
    }

    private static void validateUsername(String username) {
        if (username.length() < 5) {
            throw new IllegalArgumentException("Ім'я користувача повинно містити хоча б 5 символів.");
        }
        if (username.contains(" ")) {
            throw new IllegalArgumentException("Ім'я користувача не може містити пробіли.");
        }
    }

    private static void validatePassword(String password) {
        if (password.length() < 10) {
            throw new IllegalArgumentException("Пароль повинен бути не менше 10 символів.");
        }
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Пароль не може містити пробіли.");
        }
        
        int digitCount = 0;
        boolean hasSpecialChar = false;
        String[] forbiddenPasswords = {"admin", "pass", "password", "qwerty", "ytrewq"};
        
        for (String forbidden : forbiddenPasswords) {
            if (password.equals(forbidden)) {
                throw new IllegalArgumentException("Цей пароль заборонений.");
            }
        }

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                digitCount++;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        if (digitCount < 3) {
            throw new IllegalArgumentException("Пароль повинен містити хоча б 3 цифри.");
        }
        if (!hasSpecialChar) {
            throw new IllegalArgumentException("Пароль повинен містити хоча б 1 спеціальний символ.");
        }
    }
}
