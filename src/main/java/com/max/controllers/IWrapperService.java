package com.max.controllers;

import org.springframework.stereotype.Service;

@Service
public interface IWrapperService {
    Object getData(Object body);
}
