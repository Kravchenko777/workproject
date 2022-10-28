package com.max.controllers;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wrapper implements IWrapperModel, Serializable {

    @JsonUnwrapped
    Object main;

    String someInfo;

    @Override
    public void setData(Object object) {
        someInfo = object.toString();
    }

    @Override
    public void setBody(Object object) {
        main = object;
    }
}
