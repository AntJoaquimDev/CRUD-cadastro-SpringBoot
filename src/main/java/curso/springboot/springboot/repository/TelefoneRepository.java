package curso.springboot.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.springboot.model.Telefones;

@Repository
@Transactional
public interface TelefoneRepository extends JpaRepository<Telefones, Long> {

	@Query("select t from Telefones t where t.pessoa.id =?1 ")
	public List<Telefones> getTelefones(Long pessoaId);
}
