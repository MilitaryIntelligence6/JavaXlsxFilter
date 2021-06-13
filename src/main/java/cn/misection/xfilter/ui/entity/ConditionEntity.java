package cn.misection.xfilter.ui.entity;

/**
 * @author Administrator
 */
public class ConditionEntity {

    private String value;

    public ConditionEntity() {
        // empty constructor
    }

    public ConditionEntity(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
