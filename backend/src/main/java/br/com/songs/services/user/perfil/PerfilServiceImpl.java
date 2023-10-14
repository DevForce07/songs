package br.com.songs.services.user.perfil;

import br.com.songs.repository.UserPerfilRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
public class PerfilServiceImpl implements PerfilService{

    private UserPerfilRepository repository;
    @Override
    public String findUserNameById(long id) {
        return repository.findUsernameById(id);
    }
}
