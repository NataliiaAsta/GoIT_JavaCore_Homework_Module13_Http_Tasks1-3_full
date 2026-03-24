package taskaurlrequestsjsonapi.dto;

import lombok.Data;

@Data
public class PostDto {

    private int userId;
    private int id;
    private String title;
    private String body;

}

