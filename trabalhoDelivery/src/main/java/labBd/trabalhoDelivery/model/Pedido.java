package labBd.trabalhoDelivery.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Table(name = "pedido")
@Entity
public class Pedido {
	
	@Column(name = "dataRealizacao", nullable = false)
	private LocalDate dataRealizacao;
	
	@JoinColumn
	private Cliente cliente;
	
	
}
