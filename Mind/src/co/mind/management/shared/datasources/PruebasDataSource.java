package co.mind.management.shared.datasources;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PruebasDataSource extends DataSource {

	public PruebasDataSource(String id) {

		setID(id);
		DataSourceIntegerField pkField = new DataSourceIntegerField("pruebaID");
		pkField.setHidden(true);
		pkField.setPrimaryKey(true);

		DataSourceTextField nombreField = new DataSourceTextField("nombre",
				"Nombre");
		nombreField.setCanEdit(true);

		DataSourceTextField descripcionField = new DataSourceTextField(
				"descripcion", "Descripción");
		descripcionField.setCanEdit(true);

		DataSourceIntegerField preguntasField = new DataSourceIntegerField(
				"cantidadPreguntas", "Preguntas");
		preguntasField.setCanEdit(false);

		DataSourceIntegerField tiempoField = new DataSourceIntegerField(
				"tiempoProceso", "Tiempo (Minutos)");
		tiempoField.setCanEdit(false);

		setFields(pkField, nombreField, descripcionField, preguntasField,
				tiempoField);

		setClientOnly(true);
	}
}