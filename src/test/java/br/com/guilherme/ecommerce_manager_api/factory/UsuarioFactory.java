package br.com.guilherme.ecommerce_manager_api.factory;

import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;

public class UsuarioFactory {

        public static UsuarioEntity buildUser() {
            return UsuarioEntity.builder()
                    .id(2L)
                    .login("admin01")
                    .senha("adminSenha")
                    .nome("Administrador Teste")
                    .email("admin@teste.com")
                    .perfil(UsuarioEntity.PerfilEnum.ADMIN)
                    .build();
        }

}
