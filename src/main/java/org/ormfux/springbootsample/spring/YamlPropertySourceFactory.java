package org.ormfux.springbootsample.spring;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * A factory to load YAML properties with {@link org.springframework.context.annotation.PropertySource}.
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    /**
     * Uses a YAML loader to load the resource contents.
     */
    @Override
    public PropertySource<?> createPropertySource(final String name, final EncodedResource resource) throws IOException {
        final YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();
        final List<PropertySource<?>> yamlPropertySources = yamlLoader.load(name, resource.getResource());
        
        final CompositePropertySource propertySource = new CompositePropertySource("composite" + name);
        yamlPropertySources.forEach(yamlPropertySource -> propertySource.addPropertySource(yamlPropertySource));
        
        return propertySource;
    }
    
}
