import java.time.LocalDate;
import java.util.Scanner;

public class TaskManagerApp {
    private TaskManager taskManager;
    private Scanner scanner;

    public TaskManagerApp() {
        this.taskManager = new TaskManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("🚀 Добро пожаловать в Менеджер Задач!");

        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addTask(); break;
                case "2": taskManager.showAllTasks(); break;
                case "3": markTaskCompleted(); break;
                case "4": deleteTask(); break;
                case "5": searchTasks(); break;
                case "6": showTasksByPriority(); break;
                case "7": taskManager.showStatistics(); break;
                case "0":
                    System.out.println("👋 До свидания!");
                    return;
                default:
                    System.out.println("❌ Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n📋 МЕНЮ:");
        System.out.println("1. 📝 Добавить задачу");
        System.out.println("2. 👀 Показать все задачи");
        System.out.println("3. ✅ Отметить как выполненную");
        System.out.println("4. 🗑️ Удалить задачу");
        System.out.println("5. 🔍 Поиск задач");
        System.out.println("6. 🎯 Задачи по приоритету");
        System.out.println("7. 📊 Статистика");
        System.out.println("0. ❌ Выход");
        System.out.print("Выберите действие: ");
    }

    private void addTask() {
        System.out.print("Введите название задачи: ");
        String title = scanner.nextLine();

        System.out.print("Введите описание: ");
        String description = scanner.nextLine();

        System.out.print("Введите срок (гггг-мм-дд): ");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Выберите приоритет:");
        System.out.println("1. 🟢 Низкий");
        System.out.println("2. 🟡 Средний");
        System.out.println("3. 🟠 Высокий");
        System.out.println("4. 🔴 Срочный");
        System.out.print("Ваш выбор: ");

        Priority priority = switch (scanner.nextLine()) {
            case "1" -> Priority.LOW;
            case "2" -> Priority.MEDIUM;
            case "3" -> Priority.HIGH;
            case "4" -> Priority.URGENT;
            default -> Priority.MEDIUM;
        };

        taskManager.addTask(title, description, dueDate, priority);
    }

    private void markTaskCompleted() {
        System.out.print("Введите ID задачи для отметки о выполнении: ");
        int taskId = Integer.parseInt(scanner.nextLine());
        taskManager.markAsCompleted(taskId);
    }

    private void deleteTask() {
        System.out.print("Введите ID задачи для удаления: ");
        int taskId = Integer.parseInt(scanner.nextLine());
        taskManager.deleteTask(taskId);
    }

    private void searchTasks() {
        System.out.print("Введите ключевое слово для поиска: ");
        String keyword = scanner.nextLine();
        taskManager.searchTasks(keyword);
    }

    private void showTasksByPriority() {
        System.out.println("Выберите приоритет:");
        System.out.println("1. 🟢 Низкий");
        System.out.println("2. 🟡 Средний");
        System.out.println("3. 🟠 Высокий");
        System.out.println("4. 🔴 Срочный");
        System.out.print("Ваш выбор: ");

        Priority priority = switch (scanner.nextLine()) {
            case "1" -> Priority.LOW;
            case "2" -> Priority.MEDIUM;
            case "3" -> Priority.HIGH;
            case "4" -> Priority.URGENT;
            default -> {
                System.out.println("❌ Неверный выбор. Показываю все задачи.");
                taskManager.showAllTasks();
                yield null;
            }
        };

        if (priority != null) {
            taskManager.showTasksByPriority(priority);
        }
    }

    public static void main(String[] args) {
        new TaskManagerApp().start();
    }
}
