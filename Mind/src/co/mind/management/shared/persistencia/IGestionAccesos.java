package co.mind.management.shared.persistencia;

import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.bo.EvaluadoBO;

public interface IGestionAccesos {
	
	public int verificarUsuarioBasico(EvaluadoBO usuarioBasico, ParticipacionEnProcesoBO participacion);
	
	public UsuarioBO verificarTipoUsuario(String correoUsuario, String contrasenaUsuario);
	
}
