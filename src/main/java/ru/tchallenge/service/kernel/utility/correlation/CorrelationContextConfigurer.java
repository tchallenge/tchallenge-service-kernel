package ru.tchallenge.service.kernel.utility.correlation;

public interface CorrelationContextConfigurer extends CorrelationContext {

    void setCorrelation(CorrelationInfo correlation);
}
