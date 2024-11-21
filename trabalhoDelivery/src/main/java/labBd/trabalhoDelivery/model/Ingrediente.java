package labBd.trabalhoDelivery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "ingrediente")
@Entity
public class Ingrediente {
	
	@Id
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "formaApresent", length = 40, nullable = false)
	private String formaApresent;
	
}
