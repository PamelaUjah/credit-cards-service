package com.clearscore.utils;

import com.clearscore.exceptions.InvalidParametersException;
import com.clearscore.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (
                httpResponse.getStatusCode().is4xxClientError()
                        || httpResponse.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new InvalidParametersException("The request contained invalid parameters");
        } else if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException("User Details Not Found");
        }
    }
}
