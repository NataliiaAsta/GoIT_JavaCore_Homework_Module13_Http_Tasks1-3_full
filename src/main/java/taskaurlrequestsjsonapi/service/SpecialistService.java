package taskaurlrequestsjsonapi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import taskaurlrequestsjsonapi.dto.CommentDto;
import taskaurlrequestsjsonapi.dto.PostDto;
import taskaurlrequestsjsonapi.dto.SpecialistDto;
import taskaurlrequestsjsonapi.dto.TodoDto;
import taskaurlrequestsjsonapi.http.HttpClientUtil;

public class SpecialistService {
    private static final String BASE_URL =
            "https://jsonplaceholder.typicode.com/users";
    private static final String POSTS_URL =
            "https://jsonplaceholder.typicode.com/posts";

    private HttpClientUtil http = new HttpClientUtil();
    private Gson gson = new Gson();

    // 1. CREATE (POST)
    public SpecialistDto create(SpecialistDto dto) {

        String jsonBody = gson.toJson(dto);

        String response = http.sendPost(BASE_URL, jsonBody);

        return gson.fromJson(response, SpecialistDto.class);
    }

    // 2. UPDATE (PUT)
    public SpecialistDto update(int id, SpecialistDto dto) {

        String jsonBody = gson.toJson(dto);

        String response = http.sendPut(BASE_URL + "/" + id, jsonBody);

        return gson.fromJson(response, SpecialistDto.class);
    }

    // 3. DELETE
    public boolean delete(int id) {

        int status = http.sendDelete(BASE_URL + "/" + id);

        return status >= 200 && status < 300;
    }

    //4. GET ALL (READ all)
    public List<SpecialistDto> getAll() {

        String json = http.sendGet(BASE_URL);

        Type type = TypeToken
                .getParameterized(List.class, SpecialistDto.class)
                .getType();

        return gson.fromJson(json, type);
    }

    // 5. GET BY ID
    public SpecialistDto getById(int id) {

        String json = http.sendGet(BASE_URL + "/" + id);

        return gson.fromJson(json, SpecialistDto.class);
    }

    // 6. GET BY USERNAME
    public List<SpecialistDto> getByUsername(String username) {

        String json = http.sendGet(BASE_URL + "?username=" + username);

        Type type = TypeToken
                .getParameterized(List.class, SpecialistDto.class)
                .getType();

        return gson.fromJson(json, type);
    }

    /*
    * // GET BY USERNAME - Version2 for API without filtration
    * public SpecialistDto findByUsername(String username) {
    *       return getAll().stream()
    *       .filter(u -> u.getUsername().equals(username))
    *       .findFirst()
    *       .orElse(null);
    * }
    */

    //========= Task 2
    // SAVE: Last Comments on Post by User (by id)- save to file
    public void saveLastPostComments(int userId) throws IOException {

        Gson gson = new Gson();

        // получить посты пользователя GET /users/{userId}/posts
        // GET /users/1/posts - returns json with posts by userId=1.
        String postsJson = http.sendGet(BASE_URL + "/" + userId + "/posts");

        Type postListType = TypeToken
                .getParameterized(List.class, PostDto.class)
                .getType();

        List<PostDto> posts = gson.fromJson(postsJson, postListType);

        // find post with maximum id - max(post.id)
        // найдёт пост:d = 10
        PostDto lastPost = posts.stream()
                .max(Comparator.comparingInt(PostDto::getId))
                .orElseThrow();

        int postId = lastPost.getId();

        // get comments on post - GET /posts/{postId}/comments
        // сделает: GET /posts/10/comments
        String commentsJson = http.sendGet(
                POSTS_URL + "/" + postId + "/comments"
        );

        Type type = TypeToken
                .getParameterized(List.class, CommentDto.class)
                .getType();

        List<CommentDto> comments = gson.fromJson(commentsJson, type);

        String jsonOutput = gson.toJson(comments);

        // записать файл
        // создаём файл: user-1-post-10-comments.json в корне проекта.
        String fileName =
                "user-" + userId
                        + "-post-"
                        + postId
                        + "-comments.json";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonOutput);
        }

        System.out.println("File created: " + fileName);
    }

    //========= Task 3
    // PRINT todos of User (id) all of which have status: completed = false
    public void printOpenTodos(int userId) {

        // Endpoint: GET /users/{userId}/todos
        // request GET /users/1/todos
        Gson gson = new Gson();

        String url = BASE_URL + "/" + userId + "/todos";

        String json = http.sendGet(url);

        Type type = TypeToken
                .getParameterized(List.class, TodoDto.class)
                .getType();

        List<TodoDto> todos = gson.fromJson(json, type);

        // filter: completed = false
        List<TodoDto> openTodos = todos.stream()
                .filter(todo -> !todo.isCompleted())
                .toList();

        // result List to System out
        openTodos.forEach(System.out::println);
    }

}

