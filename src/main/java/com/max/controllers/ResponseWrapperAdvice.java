package com.max.controllers;

import lombok.AllArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@ControllerAdvice(annotations = EnableResponseWrapper.class)
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {
    private final IWrapperService wrapperService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        for (Annotation a : returnType.getMethodAnnotations()) {
            if (a.annotationType() == DisableResponseWrapper.class) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(
            @Nullable Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        if (body == null) {
            return null;
        }

        // получаем wrapperClass из аннотации
        Class<? extends IWrapperModel> wrapperClass = null;
        for (Annotation annotation : returnType.getContainingClass().getAnnotations()) {
            if (annotation.annotationType() == EnableResponseWrapper.class) {
                wrapperClass = ((EnableResponseWrapper) annotation).wrapperClass();
                break;
            }
        }

        if (wrapperClass == null) {
            return body;
        }
        if (Collection.class.isAssignableFrom(body.getClass())) {
            try {
                Collection<?> bodyCollection = (Collection<?>) body;

                // проверяем, что collection не пустой
                if (bodyCollection.isEmpty()) {
                    return body;
                }
                // оборачиваем каждый элемент коллекции
                return generateListOfResponseWrapper(bodyCollection, wrapperClass);
            } catch (Exception e) {
                return body;
            }
        }

        return generateResponseWrapper(body, wrapperClass);
    }

    private List<IWrapperModel> generateListOfResponseWrapper(Collection<?> bodyCollection, Class<? extends IWrapperModel> wrapperClass) {
        return bodyCollection.stream()
                .map((t) -> t == null ?
                        null :
                        generateResponseWrapper(t, wrapperClass)
                )
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private IWrapperModel generateResponseWrapper(Object body, Class<? extends IWrapperModel> wrapperClass) {
        // wrapperClass должен иметь конструктор без параметров - получаем объект класса, реализующего IWrapperModel
        IWrapperModel wrapper = wrapperClass.getDeclaredConstructor().newInstance();
        wrapper.setBody(body);
        wrapper.setData(wrapperService.getData(body));
        return wrapper;
    }


}
