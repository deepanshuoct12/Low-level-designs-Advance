package org.project.strategy;

import org.project.enums.FlagReason;
import org.project.enums.FlagStatus;
import org.project.enums.TargetType;
import org.project.model.Flag;
import org.project.service.FlagService;

public class QuestionReportStrategy implements IReportStrategy {

    private final FlagService flagService = new FlagService();

    @Override
    public Flag report(String userId, String targetId, FlagReason reason) {
        Flag flag = new Flag();
        flag.setReportedBy(userId);
        flag.setTargetType(TargetType.QUESTION);
        flag.setTargetId(targetId);
        flag.setReason(reason);
        flag.setStatus(FlagStatus.PENDING);
        return flagService.create(flag);
    }
}
