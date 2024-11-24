package labBd.trabalhoDelivery.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "prato")
@Entity
public class Prato {
	
	@PrePersist
	public void gerarId() {
	    if (this.id == null) {
	        this.id = "P" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	    }
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
	
	@Column(name = "ingredientes", length = 200)
	private String ingredientes;
	
	
	
	@OneToMany(mappedBy = "prato", cascade = CascadeType.ALL)
    private List<PratoIngrediente> pratoIngredientes;
}
