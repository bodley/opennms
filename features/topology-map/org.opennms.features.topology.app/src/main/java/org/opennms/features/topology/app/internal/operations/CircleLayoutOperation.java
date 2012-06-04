package org.opennms.features.topology.app.internal.operations;

import java.util.List;

import org.opennms.features.topology.app.internal.jung.CircleLayoutAlgorithm;


public class CircleLayoutOperation implements Operation{

    @Override
    public Undoer execute(List<Object> targets, OperationContext operationContext) {
        operationContext.getGraphContainer().setLayoutAlgorithm(new CircleLayoutAlgorithm());
        return null;
    }

    @Override
    public boolean display(List<Object> targets, OperationContext operationContext) {
        return true;
    }

    @Override
    public boolean enabled(List<Object> targets, OperationContext operationContext) {
        return true;
    }

    @Override
    public String getId() {
        return null;
    }
}