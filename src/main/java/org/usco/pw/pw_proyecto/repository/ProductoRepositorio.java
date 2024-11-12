package org.usco.pw.pw_proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.usco.pw.pw_proyecto.entity.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

}
