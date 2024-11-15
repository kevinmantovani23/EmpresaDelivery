package labBd.trabalhoDelivery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "porcao")
@Entity
public class Porcao {
	
	
	@Column(name = "nome", length = 20, nullable = false)
	private String nome;
	
	@Column(name = "multiplicador", nullable = false)
	private Double multiplicador;
}
