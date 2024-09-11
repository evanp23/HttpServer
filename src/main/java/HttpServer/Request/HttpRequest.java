package HttpServer.Request;

import HttpServer.Enums.HttpMethod;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final URI uri;
    private final Map<String, List<String>> requestHeaders;
    private final String requestBody;

    private HttpRequest(HttpMethod opCode, URI uri, Map<String, List<String>> requestHeaders, String requestBody){
        this.httpMethod = opCode;
        this.uri = uri;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public URI getUri(){
        return this.uri;
    }

    public HttpMethod getHttpMethod(){
        return this.httpMethod;
    }

    public Map<String, List<String>> getRequestHeaders(){
        return this.requestHeaders;
    }

    public static class Builder{
        private HttpMethod httpMethod;
        private URI uri;
        private Map<String, List<String>> requestHeaders;

        private String requestBody;

        public Builder(){

        }

        public void setHttpMethod(HttpMethod httpMethod){
            this.httpMethod = httpMethod;
        }

        public void setUri(URI uri){
            this.uri = uri;
        }

        public void setRequestHeaders(Map<String, List<String>> requestHeaders){
            this.requestHeaders = requestHeaders;
        }

        public void setRequestBody(String requestBody){
            this.requestBody = requestBody;
        }

        public HttpRequest build() {
            return new HttpRequest(httpMethod, uri, requestHeaders, requestBody);
        }
    }
}
