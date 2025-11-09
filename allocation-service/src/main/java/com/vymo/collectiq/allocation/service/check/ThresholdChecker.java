package com.vymo.collectiq.allocation.service.check;

import com.vymo.collectiq.allocation.model.User;

import java.util.Map;

public interface ThresholdChecker {
    public boolean eligible(User user);
}
