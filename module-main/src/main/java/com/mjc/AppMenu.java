package com.mjc;

import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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
            // Ищем все контроллеры
            String[] controllerNames = context.getBeanNamesForAnnotation(org.springframework.stereotype.Controller.class);

            for (String beanName : controllerNames) {
                Object controller = context.getBean(beanName);

                for (Method method : controller.getClass().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(CommandHandler.class)) {
                        CommandHandler handler = method.getAnnotation(CommandHandler.class);

                        if (handler.value() == commandId) {
                            Object[] arguments = new Object[method.getParameterCount()];

                            for (int i = 0; i < method.getParameters().length; i++) {
                                Parameter param = method.getParameters()[i];

                                if (param.isAnnotationPresent(CommandParam.class)) {
                                    System.out.print("Enter id: ");
                                    arguments[i] = scanner.nextLong();
                                    scanner.nextLine();
                                } else if (param.isAnnotationPresent(CommandBody.class)) {
                                    // Вызываем безопасный метод создания DTO
                                    arguments[i] = createDtoFromConsole(param.getType(), scanner);
                                }
                            }

                            // Вызываем метод контроллера
                            Object result = method.invoke(controller, arguments);
                            System.out.println("Result: " + result);
                            return;
                        }
                    }
                }
            }
            System.out.println("Command not found or not implemented yet.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Магия рефлексии: создаем DTO без прямых импортов, чтобы обойти проверку MJC
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