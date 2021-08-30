package curso.springboot.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.springboot.model.Usuarios;
import curso.springboot.springboot.repository.UsuarioRepository;

@Service
@Transactional
public class ImplementsUserDetailsServices implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Usuarios usuario = usuarioRepository.findUserByLogin(username);
	
	if (usuario == null) {
		throw new UsernameNotFoundException("Usuario não foi encontrado.");
	}
		
		return new User(usuario.getLogin(), usuario.getPassword(),
				usuario.isEnabled(),true,
				true,true,usuario.getAuthorities());
	}

}
