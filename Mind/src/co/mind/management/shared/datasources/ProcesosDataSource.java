package co.mind.management.shared.datasources;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ProcesosDataSource extends DataSource {

	public ProcesosDataSource(String id) {

		setID(id);

		DataSourceIntegerField pkField = new DataSourceIntegerField("idProceso");
		pkField.setHidden(true);
		pkField.setPrimaryKey(true);

		DataSourceTextField nombreField = new DataSourceTextField("nombre",
				"Nombre");
		nombreField.setCanEdit(true);

		DataSourceTextField descripcionField = new DataSourceTextField(
				"descripcion", "Descripción");
		descripcionField.setCanEdit(true);

		DataSourceDateField fechaField = new DataSourceDateField(
				"fechaCreacion", "Fecha de Creación");
		fechaField.setCanEdit(false);

		DataSourceIntegerField preguntasField = new DataSourceIntegerField(
				"cantidadPreguntas", "Preguntas");
		preguntasField.setCanEdit(false);

		DataSourceIntegerField tiempoField = new DataSourceIntegerField(
				"tiempoProceso", "Tiempo (Minutos)");
		tiempoField.setCanEdit(false);

		setFields(pkField, nombreField, descripcionField, fechaField,
				preguntasField, tiempoField);

		setClientOnly(true);
	}
}