package labBd.trabalhoDelivery.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "prato")
@Entity
public class Prato {
	
	public Prato() { 
		 this.id = "P" + UUID.randomUUID().toString().replace("-", "").substring(0, 8); //esse método cria um id com garantia de ser único.
	}
	
	@Id
	@Column(name = "id", length = 9, nullable = false)
	private String id;
	
	@Column(name = "tipo", length = 20, nullable = false)
	private String tipo;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "valorBase", nullable = false)
	private Double valorBase;
}
