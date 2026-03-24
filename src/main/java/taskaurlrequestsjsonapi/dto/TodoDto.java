package taskaurlrequestsjsonapi.dto;

import lombok.Data;

@Data
public class TodoDto {

    private int userId;
    private int id;
    private String title;
    private boolean completed;

}

