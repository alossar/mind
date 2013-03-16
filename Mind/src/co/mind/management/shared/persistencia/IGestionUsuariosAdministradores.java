package co.mind.management.shared.persistencia;

import java.util.List;

import co.mind.management.shared.bo.UsuarioAdministradorBO;

public interface IGestionUsuariosAdministradores {
	/**
	 * <b> CUOO6 Agregar Usuario Administrador (Definicion)</b>
	 * <p>
	 * Agrega un Usuario Administrador a la Base de Datos.
	 * <p>
	 * Retorna un valor entero denotando si se pudo agregar exitosamente a la
	 * base de datos o no.
	 * 
	 * @param usuarioAdministrador
	 *            Datos del Usuario Administrador contenidos en un Business
	 *            Object.
	 * @return 0 si el objeto se ingreso correctamente.
	 */
	public int agregarUsuarioAdministrador(
			UsuarioAdministradorBO usuarioAdministrador);

	/**
	 * <b> CUOO7 Editar Usuario Administrador (Definicion)</b>
	 * <p>
	 * Edita un Usuario Administrador ya contenido en la Base de Datos.
	 * <p>
	 * Retorna un valor entero denotando si se pudo editar exitosamente a la
	 * base de datos o no.
	 * 
	 * @param usuarioAdministrador
	 *            Datos del Usuario Administrador contenidos en un Business
	 *            Object.
	 * @return 0 si el objeto se editó correctamente.
	 */
	public int editarUsuarioAdministrador(
			UsuarioAdministradorBO usuarioAdministrador);

	/**
	 * <b> CUOO8 Consultar Usuario Administrador (Definicion)</b>
	 * <p>
	 * Consulta el Usuario Administrador de la Base de Datos, dado el
	 * identificador del usuario Administrador.
	 * <p>
	 * Retorna los datos del Usuario Administrador.
	 * 
	 * @param usuarioAdministradorID
	 *            Identificador del Usuario Administrador.
	 * @return Un Business Object si el objeto se encontró. null en caso
	 *         contrario.
	 */
	public UsuarioAdministradorBO consultarUsuarioAdministrador(
			int usuarioAdministradorID);

	/**
	 * <b> CUOO9 Eliminar Usuario Administrador (Definicion)</b>
	 * <p>
	 * Elimina el Usuario Administrador de la Base de Datos, dado el
	 * identificador del usuario Maestro.
	 * <p>
	 * Retorna un valor entero denotando si se pudo eliminar exitosamente a la
	 * base de datos o no.
	 * 
	 * @param usuarioAdministradorID
	 *            Identificador del Usuario Administrador.
	 * @return 0 si el objeto se eliminó correctamente.
	 */
	public int eliminarUsuarioAdministrador(int usuarioAdministradorID);

	/**
	 * <b> CUO10 Listar Usuarios Administradores (Definicion)</b>
	 * <p>
	 * Consulta los Usuarios Administradores de la Base de Datos dada la cuenta
	 * de Usuario Administrador.
	 * <p>
	 * Retorna una lista de Usuarios Administradores encontrados.
	 * 
	 * @return Una lista de Business Object. Puede estar vacia.
	 */
	public List<UsuarioAdministradorBO> listarUsuariosAdministradores();
}
