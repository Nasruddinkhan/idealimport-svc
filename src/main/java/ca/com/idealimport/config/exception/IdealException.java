package ca.com.idealimport.config.exception;

import ca.com.idealimport.config.exception.enums.IdealResponseErrorCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter
@Getter
@Data
@ToString
public class IdealException extends RuntimeException {
    private final IdealResponseErrorCode error;
    private final String msg;
    private final String id;


    public IdealException(IdealResponseErrorCode error, String msg) {
        this.id = UUID.randomUUID().toString();
        this.error = error;
        this.msg = msg;
    }

    public IdealException(IdealResponseErrorCode error){
        this(error, error.getMsg());
    }
}
