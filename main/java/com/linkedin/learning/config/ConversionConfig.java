package com.linkedin.learning.config;

import com.linkedin.learning.convertor.ReservationEntityToReservationResponseConverter;
import com.linkedin.learning.convertor.ReservationRequestToReservationEntityConverter;
import com.linkedin.learning.convertor.RoomEntityToReservableRoomResponseConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/*register converters convert objects or response objects into entities*/
@Configuration
public class ConversionConfig {

    private Set<Converter> getConverters() {

        Set<Converter> converters = new HashSet<>();

        /*add converter to conversion config - new instance*/
        converters.add(new RoomEntityToReservableRoomResponseConverter());
        converters.add(new ReservationRequestToReservationEntityConverter());
        converters.add(new ReservationEntityToReservationResponseConverter());

        return converters;
    }

    /*bean makes the method available for @Autowired*/
    @Bean public ConversionService conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();

        return bean.getObject();
    }
}
