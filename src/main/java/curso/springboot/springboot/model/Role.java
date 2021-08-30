package curso.springboot.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nomeRole;
	

	public String getNomeRole() {
		return nomeRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}


	@Override
	public String getAuthority() { //ROLE_DMIN< ROLE_SECRETARIO
		
		return nomeRole;
	}

}
