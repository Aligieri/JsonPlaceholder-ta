package com.epam.ta.spec;

import com.epam.ta.model.Post;
import com.epam.ta.utils.Utils;
import com.epam.ta.utils.RequestBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;
import static com.epam.ta.utils.Utils.assertThat;
import static org.hamcrest.Matchers.*;


@Test(suiteName = "CRUD Operations")
public class CRUDTests extends CommonTest {
    @Test(description = "Get all posts")
    public void getALLPosts() throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(API_URI + "posts");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);
        String expectedBody = Utils.readJsonToString(EXPECTED_FILES_PATH + "all_posts.json");

        // Then
        assertThat("Response should be 200 OK",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
        assertThat("All posts present in the expected posts",
                mapper.readTree(responseBody),
                equalTo(mapper.readTree(expectedBody))
        );
    }

    @Test(description = "Get post by PID")
    public void getPostByPID() throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(API_URI + "posts/7");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);
        String expectedBody = Utils.readJsonToString(EXPECTED_FILES_PATH + "PID7_post.json");

        // Then
        assertThat("Response should be 200 OK",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
        assertThat("Post with PID 7 matches expected post",
                mapper.readTree(responseBody),
                equalTo(mapper.readTree(expectedBody))
        );
    }

    @Test(description = "Get post by UID")
    public void getPostByUID() throws IOException {
        // Given
        int testUserID = 7;
        HttpUriRequest request = new HttpGet(API_URI + "posts?userId=" + testUserID);

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);
        List<Post> posts = mapper.readValue(responseBody, new TypeReference<List<Post>>() {
        });

        // Then
        assertThat("Response should be 200 OK",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
        posts.forEach(post -> assertThat("Each post have UID: " + testUserID,
                post.getUserId(), equalTo(testUserID)));
    }

    @Test(description = "Get not existing post")
    public void getNotExistingPost() throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(API_URI + "posts/0");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);

        // Then
        assertThat("Response should be 404 NOT_FOUND",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
        assertThat("Response body should be empty",
                mapper.readTree(responseBody),
                equalTo(mapper.readTree("{}"))
        );
    }

    @Test(description = "Create new Post")
    public void createNewPost() throws IOException {
        // Given
        HttpUriRequest request = new HttpPost(API_URI + "posts");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);
        String expectedBody = Utils.readJsonToString(EXPECTED_FILES_PATH + "CreatedPost.json");

        // Then
        assertThat("Response should be 201", httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_CREATED));
//        assertThat(
//                httpResponse.getStatusLine().getStatusCode(),
//                equalTo(HttpStatus.SC_CREATED));
        assertThat("Created post should return it's ID",
                mapper.readTree(responseBody),
                equalTo(mapper.readTree(expectedBody))
        );
    }

    @Test(description = "Update existing post(PUT)")
    public void updateExistingPost() throws IOException {
        // Given
        HttpUriRequest request = new HttpPut(API_URI + "posts/1");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);
        String expectedBody = Utils.readJsonToString(EXPECTED_FILES_PATH + "UpdatedPost.json");

        // Then
        assertThat("Response should be 200 OK",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
        assertThat("Updated post return it's ID",
                mapper.readTree(responseBody),
                equalTo(mapper.readTree(expectedBody))
        );
    }

    @Test(description = "Update not existing post(PUT)")
    public void updateNotExistingPost() throws IOException {
        // Given
        HttpUriRequest request = new HttpPut(API_URI + "posts/0");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);

        // Then
        assertThat("Response should be 500 INTERNAL_ERROR",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        assertThat("Error message should contain TypeError description",
                Utils.toString(httpResponse),
                containsString("TypeError: Cannot read property 'id' of undefined"));
    }

    @Test(description = "Update existing post(PATCH)")
    public void patchExistingPost() throws IOException {
        // Given
        HttpUriRequest request = new HttpPatch(API_URI + "posts/7");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);
        String expectedBody = Utils.readJsonToString(EXPECTED_FILES_PATH + "PID7_post.json");

        // Then
        assertThat("Response should be 200 OK",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
        assertThat("Should return all post information after PATCH",
                mapper.readTree(responseBody),
                equalTo(mapper.readTree(expectedBody)));
    }

    @Test(description = "Delete existing post")
    public void deleteExistingPost() throws IOException {
        // Given
        HttpUriRequest request = new HttpDelete(API_URI + "posts/7");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);

        // Then
        assertThat("Response should be 200 OK",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_FAILED_DEPENDENCY));
        assertThat("Response body should be empty",
                mapper.readTree(responseBody),
                equalTo(mapper.readTree("{}"))
            );
        }

    @Test(description = "Delete not existing post")
    public void deleteNotExistingPost() throws IOException {
        // Given
        HttpUriRequest request = new HttpDelete(API_URI + "posts/0");

        // When
        HttpResponse httpResponse = RequestBuilder.execute(request);
        String responseBody = Utils.toString(httpResponse);

        // Then
        assertThat("Response should be 200 OK",
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
        assertThat("Response body should be empty",
                mapper.readTree(responseBody),
                equalTo(mapper.readTree("{}"))
        );
    }
}
