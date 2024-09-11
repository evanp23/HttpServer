package HttpServer.Interfaces;

import HttpServer.Request.HttpRequest;
import HttpServer.Response.HttpResponse;

public interface RequestRunner {
    HttpResponse run(HttpRequest request);
}
