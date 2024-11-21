package labBd.trabalhoDelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import labBd.trabalhoDelivery.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

	List<Cliente> findByNome(String nome);
	
	List<Cliente> findByCpf(String cpf);
}
