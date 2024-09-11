package HttpServer.Handler;

import HttpServer.Decoder.HttpDecoder;
import HttpServer.Interfaces.RequestRunner;
import HttpServer.Writers.ResponseWriter;
import HttpServer.Request.HttpRequest;
import HttpServer.Response.HttpResponse;

import java.io.*;
import java.util.Map;
import java.util.Optional;

public class HttpHandler {
    private final Map<String, RequestRunner> routes;

    public HttpHandler(final Map<String, RequestRunner> routes){
        this.routes = routes;
    }

    public void handleConnection(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        Optional<HttpRequest> request = HttpDecoder.decode(inputStream);
        if(request.isPresent()){
            handleRequest(request.get(), bufferedWriter);
        }
        else{
            handleInvalidRequest(bufferedWriter);
        }

        bufferedWriter.close();
        inputStream.close();
    }

    private void handleInvalidRequest(BufferedWriter bufferedWriter){
        HttpResponse notFoundResponse = new HttpResponse.Builder().setEntity("Bad Request").build();
        ResponseWriter.writeResponse(bufferedWriter, notFoundResponse);
    }

    private void handleRequest(final HttpRequest request, final BufferedWriter bufferedWriter){
        final String routeKey = request.getHttpMethod().name().concat(request.getUri().getRawPath());

        if(routes.containsKey(routeKey)){
            ResponseWriter.writeResponse(bufferedWriter, routes.get(routeKey).run(request));
        }
        else{
            ResponseWriter.writeResponse(bufferedWriter, new HttpResponse.Builder().setEntity("Route Not Found...").build());
        }
    }
}
