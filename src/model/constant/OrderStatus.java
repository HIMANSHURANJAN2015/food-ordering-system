package model.constant;

public enum OrderStatus {
    PLACED,
    ACCEPTED,
    COMPLETED,
    CANCELLED
}

/*
PRD states, when the order is ready to be dispatched: its COMPLETED state.
So, I am using status accordingly and not using DISPATCHED status
 */
