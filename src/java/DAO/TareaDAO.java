/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Interface.MetodosCRUD.Metodos;
import Modelo.Tarea;
import Modelo.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author RepjA
 */
public class TareaDAO implements Metodos<Tarea>{
    private static final String SQL_INSERT = "{call sp_insertar_tar(?,?,?,?,?,?,?,?)}";
    private static final String SQL_UPDATE = "{call sp_modificar_tar(?,?,?,?,?,?,?,?,?)}";
    private static final String SQL_DELETE = "{call sp_eliminar_tar(?)}";
    private static final String SQL_READALL = "{call sp_listar_tar(?)}";
    
    private static final Conexion conexion = Conexion.estado();

    @Override
    public boolean create(Tarea generico) {
        PreparedStatement pre;
        try{
            pre = conexion.getConnection().prepareCall(SQL_INSERT);
            pre.setString(1, generico.getResponsable());
            pre.setDate(2, generico.getPlazo());
            pre.setString(3, generico.getDescripcion());
            pre.setInt(4, generico.getCumplimiento());
            pre.setInt(5, generico.getId_usuario_asignado());
            pre.setInt(6, generico.getIndicador_id_indicador());
            pre.setString(7, generico.getNombre());
            pre.setInt(8, generico.getProceso_id_proceso());

            if (pre.executeUpdate() > 0) {
                return true;
            }
        }catch(Exception e){
            conexion.cerrarConexion();
        }finally{
            return false;
        }
    }

    @Override
    public boolean update(Tarea generico) {
        PreparedStatement pre;
        try{
            pre = conexion.getConnection().prepareCall(SQL_UPDATE);
            pre.setInt(1, generico.getId_tarea());
            pre.setString(2, generico.getResponsable());
            pre.setDate(3, generico.getPlazo());
            pre.setString(4, generico.getDescripcion());
            pre.setInt(5, generico.getCumplimiento());
            pre.setInt(6, generico.getId_usuario_asignado());
            pre.setInt(7, generico.getIndicador_id_indicador());
            pre.setString(8, generico.getNombre());
            pre.setInt(9, generico.getProceso_id_proceso());

            if (pre.executeUpdate() > 0) {
                return true;
            }
        }catch(Exception e){
            conexion.cerrarConexion();
        }finally{
            return false;
        }
    }

    @Override
    public boolean delete(Tarea generico) {
        PreparedStatement pre;
        try{
            pre = conexion.getConnection().prepareCall(SQL_DELETE);
            pre.setInt(1, generico.getId_tarea());
        
            if (pre.executeUpdate() > 0) {
                return true;
            }
        }catch(Exception e){
            conexion.cerrarConexion();
        }finally{
            return false;
        }
    }

    @Override
    public Usuario read(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tarea> readAll() {
        List<Tarea> lista = new ArrayList<Tarea>();
        try {
            Connection cn = conexion.getConnection();
            CallableStatement cs = cn.prepareCall(SQL_READALL);
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                Tarea tarea = new Tarea();

                tarea.setId_tarea(rs.getInt("id_tarea"));
                tarea.setResponsable(rs.getString("responsable"));
                tarea.setPlazo(rs.getDate("plazo"));
                tarea.setDescripcion(rs.getString("descripcion"));
                tarea.setCumplimiento(rs.getInt("cumplimiento"));
                tarea.setId_usuario_asignado(rs.getInt("id_usuario_asignado"));
                tarea.setIndicador_id_indicador(rs.getInt("indicador_id_indicador"));
                tarea.setNombre(rs.getString("nombre"));
                tarea.setProceso_id_proceso(rs.getInt("proceso_id_proceso"));
                lista.add(tarea);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.cerrarConexion();
            return lista;
        }
    }
    
}
