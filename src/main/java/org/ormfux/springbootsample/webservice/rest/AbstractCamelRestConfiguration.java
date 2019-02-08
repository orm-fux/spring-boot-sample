package org.ormfux.springbootsample.webservice.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class AbstractCamelRestConfiguration extends RouteBuilder {
    
    @Autowired
    protected Environment environment;
    
    protected String config(final String key) {
        return environment.getProperty(key);
    }
    
    protected <T> void convertListTo(final Exchange exchange, final Class<T> type) {
        exchange.getOut().setBody(((List<?>) exchange.getIn().getBody()).stream()
                                                                        .map(val -> getContext().getTypeConverter().convertTo(type, val))
                                                                        .collect(Collectors.toList()));
    }
    
    protected <T> void convertTo(final Exchange exchange, final Class<T> type) {
        exchange.getOut().setBody(getContext().getTypeConverter().convertTo(type, exchange.getIn().getBody()));
    }
    
}
