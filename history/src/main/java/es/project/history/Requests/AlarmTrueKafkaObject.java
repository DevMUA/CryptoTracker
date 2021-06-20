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
public class AlarmTrueKafkaObject {

    private int userID;

    private ArrayList<AlarmQueryObject> alarms;
}
