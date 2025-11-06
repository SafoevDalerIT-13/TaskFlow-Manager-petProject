import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;
    private int nextId;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
    }

    // Геттер для задач (может пригодиться)
    public List<Task> getTasks() {
        return new ArrayList<>(tasks); // возвращаем копию для безопасности
    }

    // Геттер для nextId
    public int getNextId() {
        return nextId;
    }

    // Сеттер для nextId (на случай сброса)
    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    // Добавить задачу
    public Task addTask(String title, String description, LocalDate dueDate, Priority priority) {
        Task task = new Task(nextId++, title, description, dueDate, priority);
        tasks.add(task);
        System.out.println("✅ Задача добавлена: " + title);
        return task;
    }

    // Получить задачу по ID
    public Task getTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    // Показать все задачи
    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("📝 Список задач пуст");
            return;
        }

        System.out.println("\n📋 ВСЕ ЗАДАЧИ:");
        tasks.forEach(this::printTask);
    }

    // Показать задачи по приоритету
    public void showTasksByPriority(Priority priority) {
        List<Task> filtered = tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("❌ Задачи с приоритетом " + priority + " не найдены");
            return;
        }

        System.out.println("\n🎯 ЗАДАЧИ С ПРИОРИТЕТОМ " + priority + ":");
        filtered.forEach(this::printTask);
    }

    // Отметить как выполненную
    public boolean markAsCompleted(int taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            task.setCompleted(true);
            System.out.println("🎉 Задача выполнена: " + task.getTitle());
            return true;
        }
        System.out.println("❌ Задача с ID " + taskId + " не найдена");
        return false;
    }

    // Удалить задачу
    public boolean deleteTask(int taskId) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId() == taskId) {
                iterator.remove();
                System.out.println("🗑️ Задача удалена: " + task.getTitle());
                return true;
            }
        }
        System.out.println("❌ Задача с ID " + taskId + " не найдена");
        return false;
    }

    // Поиск задач
    public void searchTasks(String keyword) {
        List<Task> results = tasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("🔍 Задачи по запросу '" + keyword + "' не найдены");
            return;
        }

        System.out.println("\n🔍 РЕЗУЛЬТАТЫ ПОИСКА '" + keyword + "':");
        results.forEach(this::printTask);
    }

    // Обновить задачу
    public boolean updateTask(int taskId, String newTitle, String newDescription,
                              LocalDate newDueDate, Priority newPriority) {
        Task task = getTaskById(taskId);
        if (task != null) {
            task.setTitle(newTitle);
            task.setDescription(newDescription);
            task.setDueDate(newDueDate);
            task.setPriority(newPriority);
            System.out.println("✏️ Задача обновлена: " + newTitle);
            return true;
        }
        System.out.println("❌ Задача с ID " + taskId + " не найдена");
        return false;
    }

    // Статистика
    public void showStatistics() {
        long total = tasks.size();
        long completed = tasks.stream().filter(Task::isCompleted).count();
        long pending = total - completed;

        System.out.println("\n📊 СТАТИСТИКА:");
        System.out.println("Всего задач: " + total);
        System.out.println("Выполнено: " + completed);
        System.out.println("Осталось: " + pending);
        System.out.println("Прогресс: " + (total > 0 ? (completed * 100 / total) + "%" : "0%"));

        // Статистика по приоритетам
        Map<Priority, Long> priorityStats = tasks.stream()
                .collect(Collectors.groupingBy(Task::getPriority, Collectors.counting()));

        System.out.println("📈 По приоритетам:");
        for (Priority priority : Priority.values()) {
            long count = priorityStats.getOrDefault(priority, 0L);
            System.out.println(getPriorityIcon(priority) + " " + priority + ": " + count);
        }
    }

    private void printTask(Task task) {
        String status = task.isCompleted() ? "✅" : "⏳";
        String priorityIcon = getPriorityIcon(task.getPriority());
        System.out.printf("%s [ID: %d] %s %s | До: %s | %s\n",
                status, task.getId(), priorityIcon, task.getTitle(),
                task.getDueDate(), task.getDescription());
    }

    private String getPriorityIcon(Priority priority) {
        switch (priority) {
            case LOW: return "🟢";
            case MEDIUM: return "🟡";
            case HIGH: return "🟠";
            case URGENT: return "🔴";
            default: return "⚪";
        }
    }
}
