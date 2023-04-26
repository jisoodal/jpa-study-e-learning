package jpabook.jpashop.dto;

import jpabook.jpashop.controller.BookForm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateItemDto {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    public static UpdateItemDto of(BookForm form) {
        return UpdateItemDto.builder()
            .id(form.getId())
            .name(form.getName())
            .price(form.getPrice())
            .stockQuantity(form.getStockQuantity())
            .author(form.getAuthor())
            .isbn(form.getIsbn())
            .build();
    }
}
