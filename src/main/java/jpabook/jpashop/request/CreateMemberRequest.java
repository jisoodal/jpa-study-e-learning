package jpabook.jpashop.request;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateMemberRequest {

    @NotEmpty
    private String name;
}
