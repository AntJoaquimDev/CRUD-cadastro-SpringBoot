package curso.springboot.springboot.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Campo nome não Pode se Nulo.")
	@NotEmpty(message = "Campo nome não pode Ser Vazio.")
	@Column	
	private String nome;
	@NotNull(message = "Campo sobrenome não Pode se Nulo.")
	@NotEmpty(message = "Campo Sobrenome não pode Ser Vazio.")
	@Column
	private String sobreNome;
	
	@Min( value = 1,message = "Idade não pode Ser [0].")
	@Column
	private int idade;
	
	
	@OneToMany(mappedBy = "pessoa", orphanRemoval = true,cascade = CascadeType.ALL )
	private List<Telefones> telefones;
	
	
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String uf;
	
}
