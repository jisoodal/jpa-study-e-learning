package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") // 엔티티 저장 시 구분 컬럼에 입력할 값을 지정. 만약 책 엔티티를 저장하면 구분 컬럼인 dtype에 값 B가 저장된다.
@Getter
@Setter
public class Book extends Item {

    private String author;
    private String isbn;
}
