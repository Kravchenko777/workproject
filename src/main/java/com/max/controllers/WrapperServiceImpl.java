package com.max.controllers;

import org.springframework.stereotype.Service;

@Service
public class WrapperServiceImpl implements IWrapperService {
    @Override
    public Object getData(Object body) {
        return "Additional Information";
    }
}
