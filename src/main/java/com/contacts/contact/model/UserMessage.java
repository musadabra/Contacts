package com.contacts.contact.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Musa Dabra
 * This class is a DATA TRANSFER OBJECT MODEL, USED TAKES INFROMATION FROM BODY OF THE REQUST
 * DESERIALIZE THE REQUEST USING SERIALIZABLE
 * VALIDATE THE REQUEST BODY
 * FORM AN OBJECT OF THE REQEUST DATA FOR PERSISTANCE TO DB.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMessage implements Serializable {

    // Not empty and string min length 6 max length 16
    @NotNull(message = "From is missing")
    @Size(min = 6, max = 16, message = "From is Invalid, minimum length is 6 and maximum length 16.")
    private String from;

    // Not empty and string min length 6 max length 16
    @NotNull(message = "To is missing")
    @Size(min = 6, max = 16, message = "To is Invalid, minimum length is 6 and maximum length 16.")
    private String to;

    @NotNull(message = "Text is missing")
    @Size(min = 1, max = 120, message = "Text is Invalid, minimum length is 1 and maximum length 120.")
    private String text;
}
