package HttpServer.Decoder;

import HttpServer.Enums.HttpMethod;
import HttpServer.JSON.*;
import HttpServer.Request.HttpRequest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class HttpDecoder {

    public static Optional<HttpRequest> decode(final InputStream inputStream){
        return readMessage(inputStream).flatMap(HttpDecoder::buildRequest);
    }

    private static Optional<Queue<String>> readMessage(final InputStream inputStream) {
        try {
            if (!(inputStream.available() > 0)) {
                return Optional.empty();
            }

            final char[] inBuffer = new char[inputStream.available()];
            final InputStreamReader inReader = new InputStreamReader(inputStream);
            final int read = inReader.read(inBuffer);

            Queue<String> message = new LinkedList<>();

            try (Scanner sc = new Scanner(new String(inBuffer))) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    message.add(line);
                }
            }

            return Optional.of(message);
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    private static Optional<HttpRequest> buildRequest(Queue<String> message) {
        if (message.isEmpty()) {
            return Optional.empty();
        }

        String firstLine = message.remove();
        String[] httpInfo = firstLine.split(" ");

        if (httpInfo.length != 3) {
            return Optional.empty();
        }

        String protocolVersion = httpInfo[2];
        if (!protocolVersion.equals("HTTP/1.1")) {
            return Optional.empty();
        }

        try {
            HttpRequest.Builder requestBuilder = new HttpRequest.Builder();
            requestBuilder.setHttpMethod(HttpMethod.valueOf(httpInfo[0]));
            requestBuilder.setUri(new URI(httpInfo[1]));
            requestBuilder.setRequestHeaders(extractHeaders(message));
            requestBuilder.setRequestBody(extractRequestBody(message));
            return Optional.of(requestBuilder.build());
        } catch (IllegalArgumentException | URISyntaxException e) {
            return Optional.empty();
        }
    }

    private static Map<String, List<String>> extractHeaders(final Queue<String> message){
        final Map<String, List<String>> requestHeaders = new HashMap<>();
        int messageSize = message.size();

        if(message.size() > 1){
            for(int i = 1; i < messageSize; i++){
                String header = message.remove();
                if(header == "") break;
                int colonIndex = header.indexOf(":");

                if(!(colonIndex > 0 && header.length() > colonIndex + 1)){
                    break;
                }

                String headerName = header.substring(0, colonIndex);
                String headerValue = header.substring(colonIndex + 1);

                requestHeaders.compute(headerName, (key, values) -> {
                   if(values != null){
                       values.add(headerValue);
                   }
                   else{
                       values = new ArrayList<>();
                       values.addAll(List.of(headerValue.split(",")));
                   }
                   return values;
                });
            }
        }

        return requestHeaders;
    }

    public static String extractRequestBody(Queue<String> message){
        String requestBody = "";
        int bodyIdx = 0;
        int messageSize = message.size();

        for(int i = bodyIdx; i < messageSize; i++){
            String bodyLine = message.remove();
            if(!bodyLine.isBlank()) requestBody += bodyLine;
        }

        try {
            JsonObject jsonObject = new JsonObject(requestBody);
            System.out.println("j");
        } catch (Exception e){
            System.out.println(e);
        }

        return requestBody;
    }

}
