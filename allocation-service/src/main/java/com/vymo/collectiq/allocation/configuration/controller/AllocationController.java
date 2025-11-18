package com.vymo.collectiq.allocation.configuration.controller;

import com.vymo.collectiq.allocation.dto.AllocationInput;
import com.vymo.collectiq.allocation.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.EventsSubscribedTo;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@ChenileController(value = "allocationService", serviceName = "_allocationService_",
		healthCheckerName = "allocationHealthChecker")
public class AllocationController extends ControllerSupport{
    @EventsSubscribedTo("xxx")
    @PostMapping("/allocation/filter")
    public ResponseEntity<GenericResponse<List<User>>> doAllocation(
            HttpServletRequest httpServletRequest,
            @RequestBody AllocationInput allocationInput){
        return process(httpServletRequest,allocationInput);
    }
}
