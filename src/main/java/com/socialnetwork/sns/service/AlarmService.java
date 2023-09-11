package com.socialnetwork.sns.service;

import com.socialnetwork.sns.exception.ErrorCode;
import com.socialnetwork.sns.exception.SnsApplicationException;
import com.socialnetwork.sns.model.AlarmArgs;
import com.socialnetwork.sns.model.AlarmType;
import com.socialnetwork.sns.model.entity.AlarmEntity;
import com.socialnetwork.sns.model.entity.UserEntity;
import com.socialnetwork.sns.repository.AlarmRepository;
import com.socialnetwork.sns.repository.EmitterRepository;
import com.socialnetwork.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {
    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";
    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;
    private final UserEntityRepository userEntityRepository;

    public void send(AlarmType type, AlarmArgs arg, Long receiverUserId) {
        UserEntity user = userEntityRepository.findById(receiverUserId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        AlarmEntity alarmEntity = alarmRepository.save(AlarmEntity.of(user, type, arg));

        emitterRepository.get(receiverUserId).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().id(alarmEntity.getId().toString()).name(ALARM_NAME).data("new alarm"));
            } catch (IOException e) {
                emitterRepository.delete(receiverUserId);
                throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        }, () -> log.info("No emiiter founded"));
    }

    public SseEmitter connectAlarm(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("Connect completed"));
        } catch (IOException e) {
            throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
        }

        return sseEmitter;
    }
}
