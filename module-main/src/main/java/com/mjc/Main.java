//package com.mjc;
//
//import com.mjc.school.controller.NewsController;
//import com.mjc.school.repository.implementation.DataSource;
//import com.mjc.school.repository.implementation.NewsRepository;
//import com.mjc.school.service.implementation.NewsService;
//import com.mjc.school.service.dto.NewsDtoRequest;
//import com.mjc.school.service.exception.ValidatorException;
//
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        DataSource dataSource = new DataSource();
//        dataSource.init();
//        NewsRepository repository = new NewsRepository(dataSource);
//        NewsService service = new NewsService(repository);
//        NewsController controller = new NewsController(service);
//
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\n--- NEWS MANAGEMENT SYSTEM ---");
//            System.out.println("1 - Получить все новости");
//            System.out.println("2 - Получить новость по ID");
//            System.out.println("3 - Создать новость");
//            System.out.println("0 - Выход");
//            System.out.print("Выберите действие: ");
//
//            int choice;
//            try {
//                choice = Integer.parseInt(scanner.nextLine());
//            } catch (NumberFormatException e) {
//                System.out.println("Пожалуйста, введите цифру.");
//                continue;
//            }
//
//            try {
//                if (choice == 1) {
//                    controller.readAll().forEach(news ->
//                            System.out.println("[" + news.getId() + "] " + news.getTitle() + " (Автор: " + news.getAuthorId() + ")")
//                    );
//                } else if (choice == 2) {
//                    System.out.print("Введите ID новости: ");
//                    long readId = Long.parseLong(scanner.nextLine());
//                    System.out.println("Текст новости: " + controller.readById(readId).getContent());
//                } else if (choice == 3) {
//                    System.out.print("Введите заголовок (5-30 симв.): ");
//                    String title = scanner.nextLine();
//                    System.out.print("Введите текст (5-255 симв.): ");
//                    String content = scanner.nextLine();
//                    System.out.print("Введите ID автора: ");
//                    long authorId = Long.parseLong(scanner.nextLine());
//
//                    NewsDtoRequest request = new NewsDtoRequest(null, title, content, authorId);
//                    System.out.println("Успех! Новость создана. Её ID: " + controller.create(request).getId());
//                } else if (choice == 0) {
//                    System.out.println("Выход...");
//                    break;
//                } else {
//                    System.out.println("Неизвестная команда.");
//                }
//            } catch (ValidatorException e) {
//                // ВОТ ОНО! Требование из ТЗ: Красивый перехват бизнес-ошибок без "падения" программы.
//                System.out.println("\n❌ ОШИБКА [" + e.getErrorCode() + "]: " + e.getMessage());
//            } catch (Exception e) {
//                System.out.println("\n❌ Системная ошибка: " + e.getMessage());
//            }
//        }
//    }
//}


package com.mjc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        AppMenu menu = context.getBean(AppMenu.class);

        menu.start();

        context.close();
    }
}


