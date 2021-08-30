package curso.springboot.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Telefones {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Campo Telefone não Pode se Nulo.")
	@NotEmpty(message = "Campo Telefone não pode Ser Vazio.")
	private String numero;
	//@NotBlank
	@NotNull(message = "Campo Tipo Pode se Nulo.")
	@NotEmpty(message = "Campo Tipo não pode Ser Vazio.")
	private String tipo;
	
	@ManyToOne
	private Pessoa pessoa;
}
