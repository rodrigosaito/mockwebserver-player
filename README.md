# Mockwebserver Player

Mockwebserver Player is a tool to simplify the use of [mockwebserver](https://github.com/square/okhttp/tree/master/mockwebserver)
in your JUnit tests

## Motivation
When returning large responses such as JSON/XML it could be really boring to write all the code to prepare
your MockResponses this project makes it easier to write your tests.

## Usage

### Simple response

Add a ```@Rule Player``` property to your test class.

Annotate your test methods with ```@Play("simple_play")``` where ```simple_play```
is the name of the file containing the MockResponse attributes.

```java
@Rule
public Player player = new Player();

@Test
@Play("simple_play")
public void testPlayerWithDefaultSettings() throws IOException {
    URL url = player.getURL("/");

    // Execute your application code that makes the request to mockwebserver
    CloseableHttpResponse response = executeGet(url.toString());
    String body = EntityUtils.toString(response.getEntity());
    int statusCode = response.getStatusLine().getStatusCode();

    assertEquals("Simple Tape", body);
    assertEquals(200, statusCode);
}
```

Create a ```tapes/simple_play.yaml``` file inside your test directory.

```yaml
!!com.rodrigosaito.mockwebserver.player.Tape
name: simple play
interactions:
- response:
    status: 200
    headers:
    body: Simple Tape
```
