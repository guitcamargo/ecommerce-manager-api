package br.com.guilherme.ecommerce_manager_api.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = UsuarioEntity.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    public static final String TABLE_NAME = "usuario";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String login;
    
    @Column(nullable = false)
    private String senha;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    
    @Enumerated(EnumType.STRING)
    private PerfilEnum perfil;


    public enum PerfilEnum {
        ADMIN, USER
    }
} 