package com.zyb.toc.service.converter;

import com.zyb.toc.service.domain.entity.TaskEntity;
import com.zyb.toc.service.domain.entity.TaskRecordEntity;
import com.zyb.toc.service.utils.IdUtils;
import com.zyb.toc.service.utils.JsonUtil;
import com.zyb.toc.spi.domain.CallbackBo;
import com.zyb.toc.spi.domain.TaskBo;
import com.zyb.toc.spi.domain.enums.LocationEnum;
import com.zyb.toc.spi.domain.enums.StatusEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * javadoc TaskConverter
 * <p>
 *     task data struct converter
 * <p>
 * @author zhang yebai
 * @date 2021/6/4 5:09 PM
 * @version 1.0.0
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskConverter {

    public static TaskEntity bo2entity(@NotNull TaskBo bo){
        final var now = LocalDateTime.now();
        return new TaskEntity()
                .setTaskId(IdUtils.balancedId())
                .setMessage(bo.getMessage())
                .setCallback(JsonUtil.toJsonString(bo.getCallback()))
                .setLocation(LocationEnum.DB.getLocation())
                .setStatus(StatusEnum.NEW.getStatus())
                .setCts(now)
                .setUts(now)
                .setTts(now.plusSeconds(bo.getDelay()))
                ;
    }

    public static TaskRecordEntity entity2record(@NotNull TaskEntity entity){
        return new TaskRecordEntity()
                .setTaskId(entity.getTaskId())
                .setMessage(entity.getMessage())
                .setCallback(entity.getCallback())
                .setLocation(LocationEnum.RECORD.getLocation())
                .setStatus(StatusEnum.TIMEOUT.getStatus())
                .setCts(entity.getCts())
                .setUts(entity.getUts())
                .setTts(entity.getTts())
                .setMts(LocalDateTime.now())
                ;
    }

    public static TaskBo entity2bo(@NotNull TaskEntity entity){
        final long cts = entity.getCts().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        final long tts = entity.getTts().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return new TaskBo()
                .setTaskId(entity.getTaskId())
                .setCts(cts)
                .setTts(tts)
                .setMessage(entity.getMessage())
                .setDelay(TimeUnit.MILLISECONDS.toSeconds(tts - cts))
                .setCallback(JsonUtil.parseObject(entity.getCallback(), CallbackBo.class))
                ;
    }
}
