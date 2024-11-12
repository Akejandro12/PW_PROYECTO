package org.usco.pw.pw_proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.usco.pw.pw_proyecto.entity.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

    public Usuario findByEmail(String email);

}