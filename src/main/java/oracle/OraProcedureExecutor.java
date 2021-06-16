package oracle;

import wanted.WantedPersonGeneral;

public class OraProcedureExecutor {

    private OracleProcedures oraProcessor = null;

    public enum Function
    {
        B2_WANTED_PERSONS
    }

    public void OraProcedureExecuter1(String dbServer, String login, String pwrd, WantedPersonGeneral wantedPersonGeneral) {

        ProcedureResult resp = null;

        oraProcessor = new OracleProcedures(dbServer, login, pwrd);

        resp = oraProcessor.callProcedureInsertWantedPersonGeneral(wantedPersonGeneral);

    }

}
