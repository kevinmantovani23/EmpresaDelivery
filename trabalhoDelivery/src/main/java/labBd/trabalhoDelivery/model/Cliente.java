package labBd.trabalhoDelivery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "cliente")
@Entity
public class Cliente {
	
	@Id
    @Column(name = "cpf", length = 15, nullable = false)
	private String cpf;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "telefone", length = 20, nullable = false)
	private String telefone;
	
	@Column(name = "logradouro", length = 100, nullable = false)
	private String logradouro;
	
	@Column(name = "numero", nullable = false)
	private int número;
	
	@Column(name = "cep", length = 10, nullable = false)
	private String cep;
	
	@Column(name = "pontoRef", length = 100, nullable = false)
	private String pontoRef;
}
