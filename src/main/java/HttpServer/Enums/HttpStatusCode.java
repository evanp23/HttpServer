package HttpServer.Enums;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;


public final class HttpStatusCode{
    public static final Map<Integer, String> statusCodes = new HashMap<>();

    public static void initialize(){
        statusCodes.put(100, "CONTINUE");
        statusCodes.put(101, "SWITCHING_PROTOCOLS");
        statusCodes.put(102, "PROCESSING");
        statusCodes.put(103, "EARLY_HINTS");
        statusCodes.put(200, "OK");
        statusCodes.put(201, "CREATED");
        statusCodes.put(202, "ACCEPTED");
        statusCodes.put(203, "NON_AUTHORITATIVE_INFORMATION");
        statusCodes.put(204, "NO_CONTENT");
        statusCodes.put(205, "RESET_CONTENT");
        statusCodes.put(206, "PARTIAL_CONTENT");
        statusCodes.put(207, "MULTI_STATUS");
        statusCodes.put(208, "ALREADY_REPORTED");
        statusCodes.put(226, "IM_USED");
        statusCodes.put(300, "REDIRECTION");
        statusCodes.put(301, "MOVED_PERMANENTLY");
        statusCodes.put(302, "MOVED_TEMPORARILY");
        statusCodes.put(303, "SEE_OTHER");
        statusCodes.put(304, "NOT_MODIFIED");
        statusCodes.put(305, "USE_PROXY");
        statusCodes.put(307, "TEMPORARY_REDIRECT");
        statusCodes.put(308, "PERMANENT_REDIRECT");
        statusCodes.put(400, "BAD_REQUEST");
        statusCodes.put(402, "PAYMENT_REQUIRED");
        statusCodes.put(403, "FORBIDDEN");
        statusCodes.put(404, "NOT_FOUND");
        statusCodes.put(405, "METHOD_NOT_ALLOWED");
        statusCodes.put(406, "NOT_ACCEPTABLE");
        statusCodes.put(407, "PROXY_AUTHENTICATION_REQUIRED");
        statusCodes.put(408, "REQUEST_TIMEOUT");
        statusCodes.put(409, "CONFLICT");
        statusCodes.put(410, "GONE");
        statusCodes.put(411, "LENGTH_REQUIRED");
        statusCodes.put(412, "PRECONDITION_FAILED");
        statusCodes.put(413, "REQUEST_TOO_LONG");
        statusCodes.put(414, "REQUEST_URI_TOO_LONG");
        statusCodes.put(415, "UNSUPPORTED_MEDIA_TYPE");
        statusCodes.put(416, "REQUESTED_RANGE_NOT_SATISFIABLE");
        statusCodes.put(417, "EXPECTATION_FAILED");
        statusCodes.put(421, "MISDIRECTED_REQUEST");
        statusCodes.put(419, "INSUFFICIENT_SPACE_ON_RESOURCE");
        statusCodes.put(420, "METHOD_FAILURE");
        statusCodes.put(422, "UNPROCESSABLE_ENTITY");
        statusCodes.put(423, "LOCKED");
        statusCodes.put(424, "FAILED_DEPENDENCY");
        statusCodes.put(425, "TOO_EARLY");
        statusCodes.put(426, "UPGRADE_REQUIRED");
        statusCodes.put(428, "PRECONDITION_REQUIRED");
        statusCodes.put(429, "TOO_MANY_REQUESTS");
        statusCodes.put(431, "REQUEST_HEADER_FIELDS_TOO_LARGE");
        statusCodes.put(451, "UNAVAILABLE_FOR_LEGAL_REASONS");
        statusCodes.put(500, "INTERNAL_SERVER_ERROR");
        statusCodes.put(501, "NOT_IMPLEMENTED");
        statusCodes.put(502, "BAD_GATEWAY");
        statusCodes.put(503, "SERVICE_UNAVAILABLE");
        statusCodes.put(504, "GATEWAY_TIMEOUT");
        statusCodes.put(505, "HTTP_VERSION_NOT_SUPPORTED");
        statusCodes.put(506, "VARIANT_ALSO_NEGOTIATES");
        statusCodes.put(507, "INSUFFICIENT_STORAGE");
        statusCodes.put(508, "LOOP_DETECTED");
        statusCodes.put(510, "NOT_EXTENDED");
        statusCodes.put(511, "NETWORK_AUTHENTICATION_REQUIRED");
    }


}