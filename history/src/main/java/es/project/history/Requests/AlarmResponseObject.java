package es.project.history.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlarmResponseObject {

    private int userID;

    ArrayList<Integer> alarmsThatAreTrue;
}
