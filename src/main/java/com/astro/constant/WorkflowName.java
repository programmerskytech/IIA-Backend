package com.astro.constant;

public enum WorkflowName {

    INDENT("IndentWorkflow", "INDENT WORKFLOW"),
    CP("ContingencyPurchaseWorkflow", "CONTINGENCY PURCHASE WORKFLOW"),
    PO("PO Workflow", "PO WORKFLOW"),
    SO("SO Workflow", "SO WORKFLOW"),
    WO("WO Workflow", "WO WORKFLOW"),
    TENDER_APPROVER("Tender Approver Workflow", "TENDER APPROVER WORKFLOW"),
    TENDER_EVALUATOR("Tender Evaluator Workflow", "TENDER EVALUATOR WORKFLOW");

    private final String key;
    private final String value;

    WorkflowName(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
