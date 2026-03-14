package gestionPersonnes.Interfaces;

import java.sql.SQLException;
import java.util.List;


public interface IPersonne {
	public int save() throws SQLException;
	public int update() throws SQLException;
	public int delete() throws SQLException;
	public List<IPersonne> listPersons() throws SQLException;
	public IPersonne personGet(int id) throws SQLException;
	public int getLastId() throws SQLException;
}
