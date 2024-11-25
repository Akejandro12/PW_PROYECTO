package org.usco.pw.pw_proyecto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.usco.pw.pw_proyecto.service.UsuarioServicio;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Se encarga de definir las reglas de autenticación y autorización.
 */
@Configuration
@EnableWebSecurity  // Habilita la seguridad web para la aplicación.
@EnableGlobalMethodSecurity(prePostEnabled = true)  // Habilita la seguridad a nivel de métodos con anotaciones como @PreAuthorize.
public class SecurityConfiguration {

    /**
     * Bean para el codificador de contraseñas utilizando el algoritmo BCrypt.
     * Utilizado para codificar las contraseñas de los usuarios.
     *
     * @return Un objeto BCryptPasswordEncoder para codificar contraseñas.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para el proveedor de autenticación.
     * Utiliza el servicio de usuario personalizado y el codificador de contraseñas.
     *
     * @param usuarioServicio El servicio que maneja los detalles del usuario.
     * @param passwordEncoder El codificador de contraseñas para la autenticación.
     * @return Un DaoAuthenticationProvider que se usará para autenticar a los usuarios.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UsuarioServicio usuarioServicio, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioServicio);  // Establece el servicio para obtener los detalles del usuario.
        auth.setPasswordEncoder(passwordEncoder);  // Establece el codificador de contraseñas.
        return auth;
    }

    /**
     * Bean para el AuthenticationManager que maneja la autenticación de los usuarios.
     *
     * @param authenticationConfiguration Configuración de autenticación de Spring.
     * @return Un AuthenticationManager para manejar el proceso de autenticación.
     * @throws Exception Si ocurre un error al crear el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configuración de las reglas de seguridad para las peticiones HTTP.
     * Establece qué URLs pueden ser accedidas sin autenticación y cuáles requieren un usuario autenticado.
     *
     * @param http El objeto HttpSecurity que permite configurar las reglas de seguridad.
     * @return Un SecurityFilterChain configurado para proteger las rutas de la aplicación.
     * @throws Exception Si ocurre un error durante la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Configura las URL públicas (sin necesidad de autenticación)
                        .requestMatchers("/registro", "/js/**", "/css/**", "/multimedia/**", "/inicio/**", "/", "/clases/**").permitAll()
                        // Configura que solo los usuarios autenticados pueden acceder a /usuarios
                        .requestMatchers("/usuarios").authenticated()
                        // Configura que solo los usuarios autenticados pueden acceder a /productos
                        .requestMatchers("/productos/**").authenticated()
                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Configura la página de inicio de sesión personalizada
                        .loginPage("/login")
                        // Define la URL de procesamiento del inicio de sesión
                        .loginProcessingUrl("/login")
                        // URL de redirección después de un inicio de sesión exitoso
                        .defaultSuccessUrl("/productos", true)
                        // Permite acceso a la página de login para todos los usuarios
                        .permitAll()
                )
                .logout(logout -> logout
                        // Configura la URL de cierre de sesión
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        // Invalida la sesión después de cerrar sesión
                        .invalidateHttpSession(true)
                        // Borra la autenticación
                        .clearAuthentication(true)
                        // Redirige a la página de login después de cerrar sesión
                        .logoutSuccessUrl("/login?logout")
                        // Permite acceso a la página de logout para todos los usuarios
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        // Redirige a una página de error en caso de acceso denegado
                        .accessDeniedPage("/403")
                );
        return http.build();  // Construye y devuelve el SecurityFilterChain configurado
    }
}

