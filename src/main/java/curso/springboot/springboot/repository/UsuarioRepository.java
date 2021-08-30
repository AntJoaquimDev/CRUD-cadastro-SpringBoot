package curso.springboot.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.springboot.model.Usuarios;
@Repository
@Transactional
public interface UsuarioRepository extends  JpaRepository<Usuarios, Long>{

	@Query("select u from Usuarios u where u.login =?1")
	Usuarios findUserByLogin(String login);
}
