package com.lzj.workflow.mytodoprocess.menu;

public enum WorkFlowState {
    PENDING(0), // 待处理
    APPROVED(1), // 已批准
    REJECTED(2); // 已拒绝
    private final int value;

    WorkFlowState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WorkFlowState fromValue(int value) {
        for (WorkFlowState state : WorkFlowState.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("非法枚举值: " + value);
    }
}
