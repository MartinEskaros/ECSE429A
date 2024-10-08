package ca.mcgill.ecse429.todos;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.*;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TodosGeneralTest {
    private OkHttpClient client;
    private static final String BASE_URL = "http://localhost:4567/todos";

    @Before
    public void setUp() {
        client = new OkHttpClient();
    }

    @Test
    public void testGetAllTodos() throws Exception {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .get()
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.body());
            assertEquals("application/json", response.header("Content-Type"));
        }
    }

    @Test
    public void testHeadTodos() throws Exception {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .head()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.headers());
            assertEquals("application/json", response.header("Content-Type"));
        }
    }

    @Test
    public void testCreateTodo() throws Exception {
        String json = "{ \"title\": \"lol\", \"doneStatus\": false, \"description\": \"Test POST\" }";
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(201, response.code()); // Check for 201 Created
            assertNotNull(response.body());
            assertEquals("application/json", response.header("Content-Type"));
        }
    }

    @Test
    public void testGetTodoById() throws Exception {
        String todoId = "2"; //

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId)
                .get()
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.body());
            assertEquals("application/json", response.header("Content-Type"));
        }
    }

    @Test
    public void testHeadTodoById() throws Exception {
        String todoId = "2";

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId)
                .head()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.headers());
        }
    }

    @Test
    public void testUpdateTodoById() throws Exception {
        String todoId = "2";
        String json = "{ \"description\": \"Updated description\" }";
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId)
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.body());
        }
    }

    @Test
    public void testReplaceTodoById() throws Exception {
        String todoId = "2";
        String json = "{ \"title\": \"Updated Title\", \"doneStatus\": true, \"description\": \"Updated fully\" }";
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId)
                .put(body)
                .header("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.body());
        }
    }

    @Test
    public void testDeleteTodoById() throws Exception {
        String todoId = "25";

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
        }
    }

    @Test
    public void testGetTodoCategories() throws Exception {
        String todoId = "2";

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId + "/categories")
                .get()
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.body());
            assertEquals("application/json", response.header("Content-Type"));
        }
    }

    @Test
    public void testHeadTodoCategories() throws Exception {
        String todoId = "2";

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId + "/categories")
                .head()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.headers());
        }
    }

    @Test
    public void testGetTodoProjects() throws Exception {
        String todoId = "2";

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId + "/tasksof")
                .get()
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.body());
            assertEquals("application/json", response.header("Content-Type"));
        }
    }

    @Test
    public void testHeadTodoProjects() throws Exception {
        String todoId = "2";

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + todoId + "/tasksof")
                .head()
                .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertNotNull(response.headers());
        }
    }




}



