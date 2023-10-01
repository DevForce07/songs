package br.com.songs.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableUtil {

    public static Pageable getPageable(int page, int size){
        if(size == 0){
            size = 50;
        }

        return PageRequest.of(page, size);
    }
}
