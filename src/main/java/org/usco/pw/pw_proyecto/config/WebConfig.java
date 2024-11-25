package org.usco.pw.pw_proyecto.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Clase de configuración para el manejo de la localización y los mensajes internacionales.
 * Implementa WebMvcConfigurer para añadir configuraciones personalizadas de la web.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Bean para configurar el resolver de locale basado en la sesión.
     * Esto permite mantener el idioma seleccionado por el usuario a lo largo de la sesión.
     *
     * @return Un objeto SessionLocaleResolver que se encargará de resolver el idioma de la sesión.
     */
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("es"));  // Establece el idioma por defecto a español.
        return resolver;
    }

    /**
     * Bean para configurar el interceptor que cambia el locale (idioma) de la aplicación.
     * El parámetro "lang" en la URL se usará para cambiar el idioma.
     *
     * @return Un objeto LocaleChangeInterceptor para interceptar los cambios de idioma.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");  // Establece el nombre del parámetro de la URL que indicará el cambio de idioma.
        return lci;
    }

    /**
     * Registra el interceptor que se encargará de manejar el cambio de idioma.
     * El parámetro 'lang' en la URL permitirá cambiar el idioma de la aplicación.
     *
     * @param registry El registro de interceptores donde se agregan los interceptores.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());  // Agrega el interceptor de cambio de idioma.
    }

    /**
     * Bean para configurar el sistema de mensajes internacionales.
     * El mensaje se carga desde archivos de propiedades y permite la localización de los mensajes de la aplicación.
     *
     * @return Un objeto MessageSource configurado con los archivos de mensajes.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");  // La ruta base de los archivos de mensajes.
        messageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.name());  // Establece la codificación de los archivos de mensajes.
        return messageSource;
    }
}
