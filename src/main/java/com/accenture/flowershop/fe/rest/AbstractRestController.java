package com.accenture.flowershop.fe.rest;

import com.accenture.flowershop.fe.endpoints.xjc.OperationStatus;
import com.accenture.flowershop.fe.endpoints.xjc.Status;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class AbstractRestController {
    protected OperationStatus createStatus() {
        return  new OperationStatus();
    }

    protected OperationStatus createStatus(Status status) {
        OperationStatus operationStatus = createStatus();
        operationStatus.setStatus(status);
        return operationStatus;
    }

    protected OperationStatus createErrorStatus(String message) {
        OperationStatus operationStatus = createStatus(Status.ERROR);
        operationStatus.setMessage(message);
        return operationStatus;
    }
}
