package com.socialnetwork.sns.model.event;

import com.socialnetwork.sns.model.AlarmArgs;
import com.socialnetwork.sns.model.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmEvent {
    private Long receiveUserId;
    private AlarmType alarmType;
    private AlarmArgs args;
}
