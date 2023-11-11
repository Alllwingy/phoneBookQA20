package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString

public class NewContactDto {

    String id; // always 0, needs for API
    String name;
    String lastName;
    String phone;
    String email;
    String description;
    String address;
}
