package com.emmariescurrena.bookesy.book_service.dtos;

import lombok.Data;

@Data
public abstract class ExternalAuthorApiDto {
    
    public abstract String getId();
    public abstract String getName();

}
