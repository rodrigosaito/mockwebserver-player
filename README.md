# Mockwebserver Player [![Circle CI](https://circleci.com/gh/rodrigosaito/mockwebserver-player.svg?style=svg)](https://circleci.com/gh/rodrigosaito/mockwebserver-player)

Mockwebserver Player is a tool to simplify the use of [mockwebserver](https://github.com/square/okhttp/tree/master/mockwebserver)
in your JUnit tests

## Motivation
When returning large responses such as JSON/XML it could be really boring to write all the code to prepare
your MockResponses this project makes it easier to write your tests.

## Usage

### Installation

Add dependency to your pom.xml.

```xml
<dependency>
  <groupId>com.github.rodrigosaito</groupId>
  <artifactId>mockwebserver-player</artifactId>
  <version>1.0.0</version>
</dependency>
```

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

Create a ```plays/simple_play.yaml``` file inside your test directory.

```yaml
!play
name: simple play
interactions:
- response:
    status: 200
    headers:
    body: Simple Tape
```

### Request Matching

Sometime you would like to match a request with a response in this case we have the following tape
that will matches the request with path ```/test_1``` with the response containing body ```Test 1```
and request with path ```/test_2``` with the response containing body ```Test 2```.

```yaml
!play
name: play with request matching
interactions:
- request:
    method: GET
    uri: /test_1
    headers:
      Host: localhost
  response:
    status: 200
    headers:
    body: Test 1
- request:
    method: GET
    uri: /test_2
    headers:
      Host: localhost
  response:
    status: 200
    body: Test 2
```

And the test.

```java
@Test
@Play("play_with_request_matching")
public void test() throws IOException {
    // Execute request that matches with the second request on tape
    URL url2 = player.getURL("/test_2");
    CloseableHttpResponse response2 = executeGet(url2.toString());
    assertEquals("Test 2", EntityUtils.toString(response2.getEntity()));

    // Execute request that matches with the first request on tape
    URL url1 = player.getURL("/test_1");
    CloseableHttpResponse response1 = executeGet(url1.toString());
    assertEquals("Test 1", EntityUtils.toString(response1.getEntity()));
}
```

### Custom Settings

```java
player.setPort(58880); // changes the port of mockwebserver default is a random port
```
