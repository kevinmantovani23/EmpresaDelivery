package labBd.trabalhoDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import labBd.trabalhoDelivery.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
