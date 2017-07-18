package net.itarray.automotion.internal.properties;

import net.itarray.automotion.internal.geometry.Scalar;
import net.itarray.automotion.validation.properties.Condition;

public class Between implements Condition<Scalar> {

    private final Condition<Scalar> lowerLimit;
    private final Condition<Scalar> upperLimit;

    public Between(Expression<Scalar> lowerLimit, Expression<Scalar> upperLimit) {
        this.lowerLimit = Condition.greaterOrEqualTo(lowerLimit);
        this.upperLimit = Condition.lessOrEqualTo(upperLimit);
    }

    @Override
    public boolean isSatisfiedOn(Scalar value) {
        return lowerLimit.isSatisfiedOn(value) && upperLimit.isSatisfiedOn(value);
    }

    public String shortName() {
        return "range";
    }

    @Override
    public String toStringWithUnits(String units) {
        return String.format("[%s, %s]", upperLimit.toStringWithUnits(units), upperLimit.toStringWithUnits(units));
    }

    @Override
    public String toString() {
        return String.format("between[%s, %s]", lowerLimit, upperLimit);
    }


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Between)) {
            return false;
        }
        Between other = (Between) object;
        return lowerLimit.equals(other.lowerLimit) && upperLimit.equals(other.upperLimit);
    }

    @Override
    public int hashCode() {
        return lowerLimit.hashCode() * 31 ^ upperLimit.hashCode();
    }
}