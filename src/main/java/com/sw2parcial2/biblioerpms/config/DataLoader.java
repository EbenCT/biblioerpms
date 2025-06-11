package com.sw2parcial2.biblioerpms.config;

import com.sw2parcial2.biblioerpms.entity.*;
import com.sw2parcial2.biblioerpms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoRepository tipoRepository;
    private final EstadoRepository estadoRepository;
    private final AutorRepository autorRepository;
    private final EjemplarRepository ejemplarRepository;
    private final MiembroRepository miembroRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.count() == 0) {
            loadInitialData();
        }
    }

    private void loadInitialData() {
        log.info("Cargando datos iniciales...");

        // Crear roles
        Rol adminRole = Rol.builder().name("ADMIN").build();
        Rol librarianRole = Rol.builder().name("LIBRARIAN").build();
        Rol userRole = Rol.builder().name("USER").build();

        rolRepository.saveAll(Arrays.asList(adminRole, librarianRole, userRole));
        log.info("Roles creados");

        // Crear usuarios
        Usuario admin = Usuario.builder()
                .name("Administrador")
                .email("admin@biblioteca.com")
                .password(passwordEncoder.encode("admin123"))
                .rol(adminRole)
                .build();

        Usuario librarian = Usuario.builder()
                .name("Bibliotecario")
                .email("librarian@biblioteca.com")
                .password(passwordEncoder.encode("librarian123"))
                .rol(librarianRole)
                .build();

        Usuario user = Usuario.builder()
                .name("Usuario Normal")
                .email("user@biblioteca.com")
                .password(passwordEncoder.encode("user123"))
                .rol(userRole)
                .build();

        usuarioRepository.saveAll(Arrays.asList(admin, librarian, user));
        log.info("Usuarios creados");

        // Crear tipos de ejemplares
        Tipo libro = Tipo.builder()
                .nombre("Libro")
                .descripcion("Publicación impresa con páginas encuadernadas")
                .build();

        Tipo revista = Tipo.builder()
                .nombre("Revista")
                .descripcion("Publicación periódica")
                .build();

        Tipo dvd = Tipo.builder()
                .nombre("DVD")
                .descripcion("Material audiovisual en formato digital")
                .build();

        tipoRepository.saveAll(Arrays.asList(libro, revista, dvd));
        log.info("Tipos creados");

        // Crear estados
        Estado disponible = Estado.builder()
                .nombre("Disponible")
                .descripcion("El ejemplar está disponible para préstamo")
                .build();

        Estado prestado = Estado.builder()
                .nombre("Prestado")
                .descripcion("El ejemplar está actualmente prestado")
                .build();

        Estado reservado = Estado.builder()
                .nombre("Reservado")
                .descripcion("El ejemplar está reservado")
                .build();

        Estado dañado = Estado.builder()
                .nombre("Dañado")
                .descripcion("El ejemplar tiene daños y no está disponible")
                .build();

        estadoRepository.saveAll(Arrays.asList(disponible, prestado, reservado, dañado));
        log.info("Estados creados");

        // Crear autores
        Autor garciaMarquez = Autor.builder()
                .nombre("Gabriel García Márquez")
                .nacionalidad("Colombiana")
                .build();

        Autor borges = Autor.builder()
                .nombre("Jorge Luis Borges")
                .nacionalidad("Argentina")
                .build();

        Autor cervantes = Autor.builder()
                .nombre("Miguel de Cervantes")
                .nacionalidad("Española")
                .build();

        Autor vargas = Autor.builder()
                .nombre("Mario Vargas Llosa")
                .nacionalidad("Peruana")
                .build();

        autorRepository.saveAll(Arrays.asList(garciaMarquez, borges, cervantes, vargas));
        log.info("Autores creados");

        // Crear ejemplares
        Ejemplar cienAños = Ejemplar.builder()
                .nombre("Cien años de soledad")
                .stock(5)
                .editorial("Editorial Sudamericana")
                .tipo(libro)
                .autor(garciaMarquez)
                .build();

        Ejemplar quijote = Ejemplar.builder()
                .nombre("Don Quijote de la Mancha")
                .stock(3)
                .editorial("Editorial Planeta")
                .tipo(libro)
                .autor(cervantes)
                .build();

        Ejemplar ficciones = Ejemplar.builder()
                .nombre("Ficciones")
                .stock(4)
                .editorial("Editorial Emecé")
                .tipo(libro)
                .autor(borges)
                .build();

        Ejemplar ciudad = Ejemplar.builder()
                .nombre("La ciudad y los perros")
                .stock(2)
                .editorial("Editorial Seix Barral")
                .tipo(libro)
                .autor(vargas)
                .build();

        ejemplarRepository.saveAll(Arrays.asList(cienAños, quijote, ficciones, ciudad));
        log.info("Ejemplares creados");

        // Crear miembros de ejemplo
        Miembro miembro1 = Miembro.builder()
                .ci("12345678")
                .nombre("Juan Pérez")
                .direccion("Av. Principal 123")
                .celular(70123456)
                .sexo("M")
                .edad(25)
                .usuario(user)
                .build();

        Miembro miembro2 = Miembro.builder()
                .ci("87654321")
                .nombre("María González")
                .direccion("Calle Secundaria 456")
                .celular(75987654)
                .sexo("F")
                .edad(30)
                .build();

        miembroRepository.saveAll(Arrays.asList(miembro1, miembro2));
        log.info("Miembros creados");

        log.info("Datos iniciales cargados exitosamente");
        log.info("Usuarios de prueba:");
        log.info("Admin: admin@biblioteca.com / admin123");
        log.info("Bibliotecario: librarian@biblioteca.com / librarian123");
        log.info("Usuario: user@biblioteca.com / user123");
    }
}
