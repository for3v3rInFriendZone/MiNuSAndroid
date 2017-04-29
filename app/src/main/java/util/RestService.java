package util;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestService {
    private static final RestTemplate ourInstance = new RestTemplate();

    public static RestTemplate getInstance() {
        ourInstance.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return ourInstance;
    }

    private RestService() {
    }
}
