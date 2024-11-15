package labBd.trabalhoDelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "item")
@Entity
@IdClass(ItemID.class)
public class Item {

	@Id
	@ManyToOne
	@JoinColumn(name = "pratoCodigo", nullable = false)
	private Prato prato;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "porcaoNome", nullable = false)
	private Porcao porcao;

	private Double valor;

}
