package com.mjc;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;



@Component
public class AppMenu {

    private final ApplicationContext context;

    public AppMenu(ApplicationContext context) {
        this.context = context;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Enter command number: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Command not found.");
                scanner.nextLine();
                continue;
            }
            int command = scanner.nextInt();
            scanner.nextLine();

            if (command == 0) {
                System.out.println("Goodbye!");
                break;
            }

            executeCommand(command, scanner);
        }
    }

    private void printMenu() {
        System.out.println("\n=== NEWS MANAGEMENT SYSTEM ===");
        System.out.println("1 - Get all news");
        System.out.println("2 - Get news by id");
        System.out.println("3 - Create news");
        System.out.println("4 - Update news");
        System.out.println("5 - Delete news");
        System.out.println("6 - Get all authors");
        System.out.println("7 - Get author by id");
        System.out.println("8 - Create author");
        System.out.println("9 - Update author");
        System.out.println("10 - Delete author");
        System.out.println("0 - Exit");
    }

    private void executeCommand(int commandId, Scanner scanner) {
        try {
            String[] controllerNames = context.getBeanNamesForAnnotation(Controller.class);

            for (String beanName : controllerNames) {
                Object controller = context.getBean(beanName);

                for (Method method : controller.getClass().getDeclaredMethods()) {

                    for (Annotation annotation : method.getAnnotations()) {
                        if (annotation.annotationType().getSimpleName().equals("CommandHandler")) {

                            Method valueMethod = annotation.annotationType().getMethod("value");
                            int handlerValue = (Integer) valueMethod.invoke(annotation);

                            if (handlerValue == commandId) {
                                Object[] arguments = new Object[method.getParameterCount()];

                                for (int i = 0; i < method.getParameters().length; i++) {
                                    Parameter param = method.getParameters()[i];
                                    boolean isCommandParam = false;
                                    boolean isCommandBody = false;

                                    // Читаем аннотации параметров тоже как текст
                                    for (Annotation paramAnn : param.getAnnotations()) {
                                        if (paramAnn.annotationType().getSimpleName().equals("CommandParam")) {
                                            isCommandParam = true;
                                        } else if (paramAnn.annotationType().getSimpleName().equals("CommandBody")) {
                                            isCommandBody = true;
                                        }
                                    }

                                    if (isCommandParam) {
                                        System.out.print("Enter id: ");
                                        arguments[i] = scanner.nextLong();
                                        scanner.nextLine();
                                    } else if (isCommandBody) {
                                        arguments[i] = createDtoFromConsole(param.getType(), scanner);
                                    }
                                }

                                Object result = method.invoke(controller, arguments);
                                if (result instanceof Iterable<?> iterable) {
                                    System.out.println("Result:");
                                    for (Object item : iterable) {
                                        System.out.println(item);
                                    }
                                } else {
                                    System.out.println("Result: " + result);
                                }
                                return;
                            }
                        }
                    }
                }
            }
            System.out.println("Command not found or not implemented yet.");
        } catch (Exception e) {

            if (e instanceof InvocationTargetException) {
                System.out.println(((InvocationTargetException) e).getTargetException().getMessage());
            } else {
                System.out.println("System Error: " + e.getMessage());
            }
        }
    }

    private Object createDtoFromConsole(Class<?> type, Scanner scanner) throws Exception {
        if (type.getSimpleName().equals("NewsDtoRequest")) {
            System.out.print("Enter news id (or 0 for new): ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Enter news title: ");
            String title = scanner.nextLine();
            System.out.print("Enter news content: ");
            String content = scanner.nextLine();
            System.out.print("Enter author id: ");
            Long authorId = scanner.nextLong();
            scanner.nextLine();

            return type.getConstructor(Long.class, String.class, String.class, Long.class)
                    .newInstance(id == 0L ? null : id, title, content, authorId);

        } else if (type.getSimpleName().equals("AuthorDtoRequest")) {
            System.out.print("Enter author id (or 0 for new): ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Enter author name: ");
            String name = scanner.nextLine();

            return type.getConstructor(Long.class, String.class)
                    .newInstance(id == 0L ? null : id, name);
        }
        return null;
    }
}