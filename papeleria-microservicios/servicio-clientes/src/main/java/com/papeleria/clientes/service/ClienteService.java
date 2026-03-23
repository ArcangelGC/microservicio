package com.papeleria.clientes.service;
import com.papeleria.clientes.dto.*;
import com.papeleria.clientes.exception.ResourceNotFoundException;
import com.papeleria.clientes.model.*;
import com.papeleria.clientes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service @RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepo;
    private final DireccionRepository direccionRepo;

    public List<Cliente> listarTodos() { return clienteRepo.findAll(); }
    public List<Cliente> listarActivos() { return clienteRepo.findByActivoTrue(); }
    public Cliente buscarPorId(Long id) {
        return clienteRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }
    public List<Cliente> buscarPorNombre(String q) {
        return clienteRepo.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(q, q);
    }
    public Cliente crear(ClienteDTO dto) {
        Cliente c = Cliente.builder()
            .nombre(dto.getNombre()).apellido(dto.getApellido())
            .email(dto.getEmail()).telefono(dto.getTelefono())
            .fechaRegistro(LocalDate.now()).activo(true).build();
        return clienteRepo.save(c);
    }
    public Cliente actualizar(Long id, ClienteDTO dto) {
        Cliente c = buscarPorId(id);
        c.setNombre(dto.getNombre()); c.setApellido(dto.getApellido());
        c.setEmail(dto.getEmail()); c.setTelefono(dto.getTelefono());
        return clienteRepo.save(c);
    }
    public void eliminar(Long id) { clienteRepo.delete(buscarPorId(id)); }

    public Direccion agregarDireccion(DireccionDTO dto) {
        Cliente c = buscarPorId(dto.getClienteId());
        Direccion d = Direccion.builder()
            .calle(dto.getCalle()).ciudad(dto.getCiudad())
            .estado(dto.getEstado()).codigoPostal(dto.getCodigoPostal())
            .esPrincipal(dto.getEsPrincipal() != null && dto.getEsPrincipal())
            .cliente(c).build();
        return direccionRepo.save(d);
    }
    public List<Direccion> direccionesPorCliente(Long clienteId) {
        return direccionRepo.findByClienteId(clienteId);
    }
}
