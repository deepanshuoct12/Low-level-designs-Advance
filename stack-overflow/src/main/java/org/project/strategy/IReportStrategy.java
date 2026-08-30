package org.project.strategy;

import org.project.enums.FlagReason;
import org.project.model.Flag;

public interface IReportStrategy {
    Flag report(String userId, String targetId, FlagReason reason);
}
