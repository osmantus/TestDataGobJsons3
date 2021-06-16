package oracle;

import java.sql.*;
import java.util.Properties;

import oracle.jdbc.*;
import oracle.sql.StructDescriptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wanted.WantedPersonGeneral;

public class OracleProcedures {

    private static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String ORATHINDRIVER = "jdbc:oracle:thin:@";
    private static final String WINCHAR = "cp1251";
    private static String dbServer = null;
    private static String login = null;
    private static String pwrd = null;

    final static public int B2STATUS_DONE  = 1;
    final static public int B2STATUS_NEW  = 0;

    static {
        final String method = "Instance driver "+DBDRIVER ;
        try {
            Class.forName(DBDRIVER).newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public OracleProcedures(String dbServer, String login, String pwrd) {
        this.dbServer = dbServer;
        this.login = login;
        this.pwrd = pwrd;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Properties p = new Properties();
            p.setProperty("user", login);
            p.setProperty("password", pwrd);
            p.setProperty("useUnicode", "true");
            p.setProperty("characterEncoding", WINCHAR);

            connection = DriverManager.getConnection(ORATHINDRIVER+dbServer, p);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public ProcedureResult callProcedureInsertWantedPersonGeneral(WantedPersonGeneral wantedPersonGeneral) {
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rset = null;

        ProcedureResult resp = new ProcedureResult(B2STATUS_NEW, "", "");

        con = getConnection();
        if(con!=null){
            try {
                String command = "{call mtb_pkg_debt_base.mtb_get_odes_gaz_info(?,?,?)}";
                cs = con.prepareCall(command);

                /*if(!ParameterUtils.setParameters(parameters, cs)){
                    String errStr = "В метод "+PROCEDURE_NAME+" пераданы некорректные типы параметров: "+params+
                            ". Ошибка возникла при подстановке значений в процедуру.";
                    logger.logError(0, errStr);
                    logger.addErrorToList(errStr);

                    resp.setStatus(b2status_done);
                    return resp;
                }*/

                cs.registerOutParameter(3, OracleTypes.CURSOR);

                cs.execute();
                rset = (ResultSet) cs.getObject(3);

                //print to json first 5 records
                int j = 1;
                /*set response to JSON
                 * format:
                 * {"response":
                 * [
                 * {FIELD1:VALUE1,FIELD2:VALUE2,...},
                 * {FIELD1:VALUE1,FIELD2:VALUE2,...},
                 * {rowN}
                 * ]
                 * }
                 */
                JSONObject response = new JSONObject();
                JSONArray rows = new JSONArray();

                while(rset.next() && j<=5){
                    JSONObject row = new JSONObject();
                    for (int i = 1; i < rset.getMetaData().getColumnCount() + 1; i++) {
                        row.put(rset.getMetaData().getColumnName(i).toString(),
                                rset.getObject(i)==null?JSONObject.NULL:rset.getObject(i));
                    }
                    rows.put(row);
                    j++;
                }
                response.put("response", rows);

                //set response to responseResult
                resp.setResponse(response.toString());
                resp.setStatus(B2STATUS_DONE);

            } catch (SQLException e) {
                e.printStackTrace();
                //logger.logError2Object(0, PROCEDURE_NAME, e);
            } catch (JSONException e) {
                e.printStackTrace();
                //logger.logError2Object(0, PROCEDURE_NAME, e);
            } finally {
                closeConnection(con, cs, rset);
            }
        }
        if(resp.getStatus() != B2STATUS_DONE){
            resp.setStatus(B2STATUS_DONE);
        }
        return resp;
    }

    private void closeConnection(Connection con, CallableStatement cs, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (cs != null) {
            try {
                cs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
