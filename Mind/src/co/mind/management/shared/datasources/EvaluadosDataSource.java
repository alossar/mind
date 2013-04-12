package co.mind.management.shared.datasources;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;

public class EvaluadosDataSource extends DataSource {

	public EvaluadosDataSource(String id) {

		setID(id);

		DataSourceIntegerField pkField = new DataSourceIntegerField("id",
				"Cédula");
		pkField.setCanEdit(false);
		pkField.setPrimaryKey(true);

		DataSourceTextField nombreField = new DataSourceTextField("nombre",
				"Nombre");
		nombreField.setCanEdit(true);

		DataSourceTextField apellidosField = new DataSourceTextField(
				"apellidos", "Apellidos");
		apellidosField.setCanEdit(true);
		DataSourceTextField correoField = new DataSourceTextField("correo",
				"Correo");
		correoField.setCanEdit(false);

		setFields(pkField, nombreField, apellidosField, correoField);

		setClientOnly(true);
	}
}