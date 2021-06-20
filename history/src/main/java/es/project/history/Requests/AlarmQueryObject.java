package es.project.history.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlarmQueryObject {

    private int aid;
    private String coin;
    private String condition;
    private long value;
    private boolean email;
    private boolean alert;
}
