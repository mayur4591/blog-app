package com.example.blog_app.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends  RuntimeException{
    String resoueceName;
    String fieldName;
    long fieldValue;

     public ResourceNotFoundException(String resoueceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s: %s",resoueceName,fieldName,fieldValue));
        this.resoueceName=resoueceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

}
