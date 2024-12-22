package com.fhwedel.softwareprojekt.v1.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/** Configuration class for application-specific beans and settings. */
@Configuration
public class AppConfiguration {

    /**
     * Creates and configures a ModelMapper bean for mapping objects.
     *
     * @return a configured instance of ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     * Registers a bean definition named "filter" for creating a {@link CommonsRequestLoggingFilter}
     * to achieve simple logging of requests. Configures the filter to write request URI, query
     * string and request payload to the Commons Log.
     *
     * @return a configured instance of {@link CommonsRequestLoggingFilter}
     */
    @Bean
    public CommonsRequestLoggingFilter filter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();

        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);

        return filter;
    }
}
