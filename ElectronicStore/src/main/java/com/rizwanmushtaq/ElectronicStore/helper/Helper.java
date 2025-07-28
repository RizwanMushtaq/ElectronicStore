package com.rizwanmushtaq.ElectronicStore.helper;

import com.rizwanmushtaq.ElectronicStore.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
  // U is Entity type, V is DTO type
  // This method converts a PageableResponse of type U to a PageableResponse of type V
  public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page,
                                                               Class<V> type) {
    List<U> entity = page.getContent();
    List<V> dtoList = entity.stream()
        .map(user -> new ModelMapper().map(user, type))
        .collect(Collectors.toList());
    PageableResponse<V> pageableResponse = new PageableResponse<>();
    pageableResponse.setContent(dtoList);
    pageableResponse.setPageNumber(page.getNumber());
    pageableResponse.setPageSize(page.getSize());
    pageableResponse.setTotalElements(page.getTotalElements());
    pageableResponse.setTotalPages(page.getTotalPages());
    pageableResponse.setLastPage(page.isLast());
    return pageableResponse;
  }
}
