//code is in 1 file for now as i have some issues with intellij and creating new class files

import java.util.*;

public class Duke {
    private static List<String> taskString = new ArrayList<>();
    private static List<Task> taskList = new ArrayList<>();
    private static int taskNumber = 1;
    private final static String LINE = "-----------------------------------------";
    private final static String DEFAULT_MESSAGE = LINE + "\nHello! I'm Duke \nWhat can I do for you?\n" + LINE;

    /**
     * Adds a task to the list and prints the addition.
     * @param task Task to be added
     */
    private static void addTask(Task task) {
        taskString.add(taskNumber + ". ");
        taskList.add(task);
        taskNumber++;
        System.out.println(LINE);
        System.out.println("Got it. I've added this task: \n" + task);
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Marks a task as completed and prints this change.
     * @param task Task to complete
     */
    private static void completeTask(String task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        String numString = task.replaceAll("[^0-9]", "");
        int num = Integer.parseInt(numString);
        for (int counter = 0; counter < taskString.size(); counter++) {
            if (counter == num - 1) {
                taskList.get(num - 1).markAsDone();
                System.out.println(taskString.get(counter) + taskList.get(counter).toString());
            }
        }
        System.out.println(LINE);
    }

    /**
     * Prints the current task list.
     */
    private static void printList() {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        int counter = 0;
        for (String str : taskString) {
            System.out.println(str + taskList.get(counter).toString());
            counter++;
        }
        System.out.println(LINE);
    }

    /**
     * Adds a specific task according to the correct class
     * @param task task to be added
     */
    private static void addSpecificTask(String task) throws DukeException {
        if (task.startsWith("deadline")) {
            addTask(new Deadline(task));
        } else if (task.startsWith("event") && task.contains("/at ")) {
            addTask(new Event(task));
        } else if (task.startsWith("todo")) {
            addTask(new Todo(task));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(DEFAULT_MESSAGE);
        String task = sc.nextLine();

        while(!task.equals("bye")) {
            try {
                if (task.equals("list")) {
                    printList();
                } else if (task.startsWith("done")) {
                    completeTask(task);
                } else if (task.startsWith("deadline") || task.startsWith("event") || task.startsWith("todo")) {
                    switch(task) {
                        case "deadline":
                            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
                        case "event":
                            throw new DukeException("OOPS!!! The description of a event cannot be empty.");
                        case "todo":
                            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    addSpecificTask(task);
                } else {
                    throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
            task = sc.nextLine();
        }
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
        sc.close();
    }
}

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status of the task (X if done, blank if not done).
     * @return the status of task
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks a task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}

class Deadline extends Task {
    protected String by;

    public Deadline(String description) {
        super(description.substring(0, description.indexOf("/by")));
        this.by = description.substring(description.indexOf("/by") + 4);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

class Event extends Task {
    protected String at;

    public Event(String description) {
        super(description.substring(0, description.indexOf("/at")));
        this.at = description.substring(description.indexOf("/at ") + 4);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }
}

class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class DukeException extends Exception {
    public DukeException(String errorMessage) {
        super(errorMessage);
    }
}