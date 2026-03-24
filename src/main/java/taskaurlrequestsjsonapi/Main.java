package taskaurlrequestsjsonapi;

import java.io.IOException;
import java.util.List;
import taskaurlrequestsjsonapi.dto.SpecialistDto;
import taskaurlrequestsjsonapi.service.SpecialistService;

public class Main {
    public static void main(String[] args) throws IOException {
        SpecialistService service = new SpecialistService();

        // CREATE
        System.out.println("CREATE: Test User");
        SpecialistDto newUser = new SpecialistDto();
        newUser.setName("Test User");
        System.out.println(service.create(newUser));

        // GET ALL
        System.out.println("\nGET ALL:");
        List<SpecialistDto> users = service.getAll();
        users.forEach(System.out::println);
        System.out.println("Size of list is " + users.size());
        //service.getAll().forEach(System.out::println);

        // GET BY ID
        System.out.println("\nGET BY ID: 1");
        System.out.println(service.getById(1));

        // GET BY USERNAME
        System.out.println("\nGET BY USERNAME: Bret");
        System.out.println(service.getByUsername("Bret"));

        // UPDATE
        System.out.println("\nUPDATE Updated Name(id, object)");
        SpecialistDto updateUser = new SpecialistDto();
        updateUser.setId(1);
        updateUser.setName("Updated Name");
        System.out.println(service.update(1, updateUser));

        // DELETE
        System.out.println("\n DELETE by id: 1");
        System.out.println(service.delete(1));

        // =========== Task 2
        // added classes: CommentDto, PostDto
        System.out.println("\n Task 2. SAVE:");
        System.out.println("Last Comments on Post by User by id (1)- save to file");
        SpecialistService serviceSaveComments = new SpecialistService();

        serviceSaveComments.saveLastPostComments(1);

        // =========== Task 3
        // added class: TodoDto
        System.out.println("\n Task 3. PRINT:");
        System.out.println("Todos of User by id (1) all of which have status:\ncompleted = false");
        SpecialistService servicePrOpTodos = new SpecialistService();

        servicePrOpTodos.printOpenTodos(1);
    }
    /*
    * Task 1 Завдання 1
    * Програма повинна містити методи для реалізації наступного функціоналу:
    * - створення нового об'єкта в https://jsonplaceholder.typicode.com/users.
    * Можливо, ви не побачите одразу змін на сайті. Метод працює правильно,
    * якщо у відповідь на JSON з об'єктом повернувся такий самий JSON,
    * але зі значенням id більшим на 1, ніж найбільший id на сайті.
    * - оновлення об'єкту в https://jsonplaceholder.typicode.com/users.
    * Можливо, ви не побачите одразу змін на сайті. Вважаємо, що метод працює
    * правильно, якщо у відповідь ви отримаєте оновлений JSON (він повинен
    * бути таким самим, що ви відправили).
    * - видалення об'єкта з https://jsonplaceholder.typicode.com/users.
    * Тут будемо вважати коректним результат - статус відповіді
    * з групи 2xx (наприклад, 200).
    * - отримання інформації про всіх користувачів https://jsonplaceholder.typicode.com/users
    * - отримання інформації про користувача за id
    * https://jsonplaceholder.typicode.com/users/{id}
    * - отримання інформації про користувача за username -
    * https://jsonplaceholder.typicode.com/users?username={username}
    */

    /*
    * Task 2 Завдання 2
    * Доповніть програму методом, що буде виводити всі коментарі до останнього поста
    * певного користувача і записувати їх у файл.
    * https://jsonplaceholder.typicode.com/users/1/posts
    * Останнім вважаємо пост з найбільшим id.
    * https://jsonplaceholder.typicode.com/posts/10/comments
    * Файл повинен називатись:
    * user-X-post-Y-comments.json, де Х - id користувача, Y - номер посту.
    */

    /*
    * Task 3 Завдання 3
    * Доповніть програму методом, що буде виводити всі відкриті задачі для користувача
    * з ідентифікатором X.
    * https://jsonplaceholder.typicode.com/users/1/todos.
    * Відкритими вважаються всі задачі, у яких completed = false.
    */
}

