package net.itarray.automotion.validation.properties;

import net.itarray.automotion.internal.geometry.ExtendGiving;
import net.itarray.automotion.internal.geometry.MetricSpace;
import net.itarray.automotion.internal.geometry.Scalar;
import net.itarray.automotion.internal.properties.Between;
import net.itarray.automotion.internal.properties.BinaryScalarConditionWithFixedOperand;
import net.itarray.automotion.internal.properties.ConditionedExpressionDescription;
import net.itarray.automotion.internal.properties.ConditionedExpression;
import net.itarray.automotion.internal.properties.Context;
import net.itarray.automotion.internal.properties.ContextBiFunction;
import net.itarray.automotion.internal.properties.PixelConstant;

import static net.itarray.automotion.internal.geometry.Scalar.scalar;

public interface Condition<T> {

    static Condition<Scalar> equalTo(int limit) {
        return equalTo(scalar(limit));
    }

    static Condition<Scalar> equalTo(Scalar limit) {
        return equalTo(new PixelConstant(limit));
    }

    static Condition<Scalar> equalTo(Expression<Scalar> lowerLimit) {
        return new BinaryScalarConditionWithFixedOperand(lowerLimit, ContextBiFunction.equalToWithTolerance, "equal to %s");
    }

    static Condition<Scalar> greaterOrEqualTo(int limit) {
        return greaterOrEqualTo(scalar(limit));
    }

    static Condition<Scalar> greaterOrEqualTo(Scalar limit) {
        return greaterOrEqualTo(new PixelConstant(limit));
    }

    static Condition<Scalar> greaterOrEqualTo(Expression<Scalar> lowerLimit) {
        return new BinaryScalarConditionWithFixedOperand(lowerLimit, ContextBiFunction.greaterOrEqualTo, "greater or equal to %s");
    }

    static Condition<Scalar> greaterThan(int limit) {
        return greaterThan(scalar(limit));
    }

    static Condition<Scalar> greaterThan(Scalar limit) {
        return greaterThan(new PixelConstant(limit));
    }

    static Condition<Scalar> greaterThan(Expression<Scalar> lowerLimit) {
        return new BinaryScalarConditionWithFixedOperand(lowerLimit, ContextBiFunction.greaterThan, "greater than %s");
    }

    static Condition<Scalar> lessOrEqualTo(int limit) {
        return lessOrEqualTo(scalar(limit));
    }

    static Condition<Scalar> lessOrEqualTo(Scalar limit) {
        return lessOrEqualTo(new PixelConstant(limit));
    }

    static Condition<Scalar> lessOrEqualTo(Expression<Scalar> upperLimit) {
        return new BinaryScalarConditionWithFixedOperand(upperLimit, ContextBiFunction.lessOrEqualTo, "less or equal to %s");
    }

    static Condition<Scalar> lessThan(int limit) {
        return lessThan(scalar(limit));
    }

    static Condition<Scalar> lessThan(Scalar limit) {
        return lessThan(new PixelConstant(limit));
    }

    static Condition<Scalar> lessThan(Expression<Scalar> upperLimit) {
        return new BinaryScalarConditionWithFixedOperand(upperLimit, ContextBiFunction.lessThan, "less than %s");
    }

    static LowerLimit between(int lowerLimit) {
        return new LowerLimit(lowerLimit);
    }

    static LowerLimit between(Scalar lowerLimit) {
        return new LowerLimit(lowerLimit);
    }

    static LowerLimit between(Expression<Scalar> lowerLimit) {
        return new LowerLimit(lowerLimit);
    }

    class LowerLimit {

        private final Expression<Scalar> lowerLimit;

        public LowerLimit(Expression<Scalar> lowerLimit) {
            this.lowerLimit = lowerLimit;
        }

        public LowerLimit(Scalar lowerLimit) {
            this(new PixelConstant(lowerLimit));
        }

        public LowerLimit(int lowerLimit) {
            this(new PixelConstant(scalar(lowerLimit)));
        }

        public Condition<Scalar> and(int upperLimit) {
            return and(scalar(upperLimit));
        }

        public Condition<Scalar> and(Scalar upperLimit) {
            return and(new PixelConstant(upperLimit));
        }

        public Condition<Scalar> and(Expression<Scalar> upperLimit) {
            return new Between(lowerLimit, upperLimit);
        }
    }

    default <V extends MetricSpace<V>> boolean isSatisfiedOn(Expression<T> toBeConditioned, Context context, ExtendGiving<V> direction) {
        return applyTo(toBeConditioned).evaluateIn(context, direction);
    }

    default Expression<Boolean> applyTo(Expression<T> toBeConditioned) {
        return new ConditionedExpression<>(toBeConditioned, this);
    }

    default Expression<Boolean> applyTo(Expression<T> toBeConditioned, ConditionedExpressionDescription<T> description) {
        return new ConditionedExpression<>(toBeConditioned, this, description);
    }

    <V extends MetricSpace<V>> String getDescription(Context context, ExtendGiving<V> direction);
}
