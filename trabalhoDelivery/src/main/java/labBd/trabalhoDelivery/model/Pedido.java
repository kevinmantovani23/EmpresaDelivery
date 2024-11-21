package labBd.trabalhoDelivery.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Table(name = "pedido")
@Entity
public class Pedido {
	
	@Column(name = "dataRealizacao", nullable = false)
	private LocalDate dataRealizacao;
	
	@ManyToOne
	@JoinColumn(name = "clienteCpf", nullable = false)
	private Cliente cliente;
	
	@Id
	@Column(name="codigo", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itens;
	
}
