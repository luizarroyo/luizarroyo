$SET LEVEL 2 INSTALLATION 1
$SET TADS
PROCEDURE LISTDIRECTORY(PAR_ARR);
ARRAY         PAR_ARR[*];
BEGIN

%%% RUN ("[<USERCODE>]DIRECTORY [ON <FAMILY>] .")
FILE WFL(KIND=DISK,
         FILEKIND=JOBSYMBOL,
         MAXRECSIZE=15,
         BLOCKSIZE=420,
         UNITS=0,
         TITLE="DBA/WF/REMOVE/DBSTATS.");
DEFINE        RP = REPLACE #,
              PT = POINTER #;
DEFINE   CLEANWFL = REPLACE POINTER(AWFL) BY " " FOR 90#,
         WRWFL    = SEQ:=SEQ+100;
                    REPLACE POINTER(AWFL) BY SEQ FOR 8 DIGITS,
                           #;

ARRAY AWFL[0:15];
POINTER PWFL;

ARRAY         DIR_ARR[0:14];
EBCDIC ARRAY  EMSG [0: 89];
REAL          TT;

INTEGER SEQ,
        I;

BOOLEAN FIRST,
        COPYSYNTAX;

PROCEDURE WFLWRITE;
BEGIN
      SEQ:=SEQ+100;
      REPLACE POINTER(AWFL) + 82 BY
                 SEQ FOR 8 DIGITS,
                       ",";
      WRITE(WFL,15,POINTER(AWFL));
      CLEANWFL;
END;

PROCEDURE WFLBEGIN;
BEGIN

   REPLACE POINTER(AWFL) BY " BEGIN JOB DBA/WF/REMOVE/DBSTATS;";
   WFLWRITE;

   REPLACE POINTER(AWFL) BY " TASK T;";
   WFLWRITE;

   REPLACE POINTER(AWFL) BY "SUBROUTINE TRANSFER(STRING SOURCE);";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "BEGIN    ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  String  ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "    PCDestination,";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "    Statement; ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  Task T; ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY " ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  PCDestination:=SOURCE;";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY
       "  WHILE TAIL(PCDestination,NOT """/""") NEQ """""" DO";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  BEGIN ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY
       "     PCDestination:=HEAD(PCDestination, NOT """/""") & ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY       " """_""" &  ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY
       "          DROP(TAIL(PCDestination, NOT """/"""),1);    ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  END;  ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  Statement:="""10.243.66.101"""  &  ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "             """ TEXTDATATOPC """ & ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "             SOURCE & """ """     & ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY
       "                """Y:\DmsStats\"""   & PCDestination; ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  Display(Statement);  ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY " ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY
       "  Run *System/NXServices/PCDriver(Statement)[T]; ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  If (T ISNT CompletedOK) then ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY
       "    Abort("""Transfer of """ & SOURCE & """ failed"""); ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "END; ";
   WFLWRITE;

   REPLACE POINTER(AWFL) BY " COPY ";
   WFLWRITE;

END;

PROCEDURE WFLENDCOPY;
BEGIN

   REPLACE POINTER(AWFL) BY "  FROM BBLLOG(PACK) TO";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  BBLTS1(PACK)[T];";
   WFLWRITE;

   WFLWRITE;

   REPLACE POINTER(AWFL) BY "  IF T ISNT COMPLETEDOK THEN";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "     ABORT """ERROR WHEN CREATE A JOB""";";
   WFLWRITE;
   WFLWRITE;

   REPLACE POINTER(AWFL) BY "  IF T(VALUE) NEQ 0 THEN";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  BEGIN";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "     DISPLAY """NO FILES COPIED"""; ";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "     GO ENDJOB;";
   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  END;";
   WFLWRITE;

   REPLACE POINTER(AWFL) BY
      "  REMOVE ";
   WFLWRITE;

END;

PROCEDURE WFLENDREMOVE;
BEGIN

   REPLACE POINTER(AWFL) BY "  FROM BBLLOG(PACK); ";
   WFLWRITE;

   REPLACE POINTER(AWFL) BY "   ";
   WFLWRITE;

END;

PROCEDURE WFLEND;
BEGIN

   REPLACE POINTER(AWFL) BY "   ";
   WFLWRITE;

   REPLACE POINTER(AWFL) BY "  COPY (DBA)DBSTATS/= AS ";
   WFLWRITE;

   REPLACE POINTER(AWFL) BY "     DBSTATS/= ; ";
   WFLWRITE;

   WFLWRITE;

   REPLACE POINTER(AWFL) BY "  CHANGE (DBA)DBSTATS/= TO ";
   WFLWRITE;

   REPLACE POINTER(AWFL) BY "  (DBA)SAVE/DBSTATS/= ; ";
   WFLWRITE;

   WFLWRITE;
   REPLACE POINTER(AWFL) BY "  ENDJOB: ";
   WFLWRITE;

   WFLWRITE;

   REPLACE POINTER(AWFL) BY " END JOB; ";
   WFLWRITE;

END;

REAL PROCEDURE DIRREQUEST;
BEGIN
OWN ARRAY     STDFN  [0:  15],
              PKNARRY[0:   3],
              GSA    [0:1999];

ARRAY         TITRE  [0:10];
POINTER       PTITRE;

EBCDIC ARRAY  EGSA[0] = GSA[*];

POINTER       S,
              ST,
              FTP;

OWN REAL      I;

REAL          J,
              IX,
              LNG,
              FKIND,
              NEEDQUOTES;

BOOLEAN       USERCODE,
              SYSTEMFILE,
              B;

 ARRAY     FNAMEPOINTERS[1:14];

 LABEL        EXIT,
              CONTU;

DEFINE        SUBTYPEF     = [15: 8]#,
              TYPEF        = [ 7: 8]#,
              MASK         = 1#,
              ERRORF       = [47: 1]#,
              ADDINFOF     = [46: 8]#,
              SUBVALUE1F   = [35: 3]#,
              SUBVALUE2F   = [38: 2]#,
              SUBVALUE3F   = [36: 1]#,
              LINKF        = [32:17]#,
              LEVELF       = [ 3: 4]#,
              ONPARTLINKF  = [43:11]#,
              INFOF        = [15:16]#,
              RCTYPE       = GSA[I+14].[38: 4]#,
              RCUNTS       = GSA[I+14].[39: 1]#,
              LCMODE       = GSA[I+14].[42: 3]#,
              BLKSZ        = GSA[I+ 2].[47:16]#,
              MAXSZ        = GSA[I+ 2].[15:16]#,
              DEFINEND     = #;

              FTP    := PT(DIR_ARR);
              PTITRE := PT(TITRE);

              IF FTP = "(" FOR 1 THEN
              BEGIN
                   FTP := * + 1;
                   RP PTITRE BY "FNAME/",FTP:FTP UNTIL = ")","/",
                                FTP:FTP + 1 UNTIL = ".",".";
                   FTP := PT(DIR_ARR);
              END ELSE
              BEGIN
                   IF FTP = "*" FOR 1
                      THEN RP PTITRE BY "FNAME/",FTP + 1 UNTIL = ".","."
                      ELSE RP PTITRE BY "FNAME/",FTP UNTIL = ".",".";
              END;

              SCAN PTITRE:PTITRE FOR LNG:66 UNTIL = "=";

              IF LNG > 0 THEN
              BEGIN
                   LNG := 0;
                   RP PTITRE:PTITRE BY "ALL.";
              END;

              IF I = 0 THEN
              BEGIN
                   ST := PT(STDFN);
                   IF FTP = "=" THEN RP ST BY 48"03000000" ELSE
                   IF (B := DISPLAYTOSTANDARD(FTP,ST)) THEN
                   BEGIN
                        DIRREQUEST := REAL(B);
                        GO TO EXIT;
                   END;
                   GSA[0] := 200;
                   GSA[1] := 0;
                   RP PT(GSA[200]) BY ST FOR STDFN[0].[47:8];
                   B := GETSTATUS(0 & 1 [42:1] & 3 TYPEF & 1 SUBTYPEF,
                                  0,MASK,GSA);
CONTU:             IF B THEN
                   BEGIN
                        DIRREQUEST := GSA[1].ADDINFOF;
                        GO TO EXIT;
                   END;
              END;

              IF I = 0 THEN I := 2;
         DO
         BEGIN
              DO
              BEGIN
                   IF I GEQ GSA[0] THEN
                   BEGIN
                        I := 0;
                        IF GSA[0].ERRORF = 1 THEN
                        BEGIN
                             GSA[0] := 200;
                             B := GETSTATUS(0 & 1 [42:1] & 3 TYPEF &
                                  4 SUBTYPEF,0,MASK,GSA);
                             GO TO CONTU;
                        END ELSE
                             DIRREQUEST := - 1;
                        GO TO EXIT;
                   END;
                   IX := I;
                   IF GSA[I].LEVELF = 0 THEN I:=GSA[I].LINKF + 1;
                   IF GSA[I].LEVELF = 1 THEN
                   BEGIN
                        RP PT(PKNARRY) BY " ON ",
                        EGSA[(J := GSA[GSA[1].ONPARTLINKF].LINKF) + 1]
                        FOR REAL(EGSA[J],1), ".";
                        IF GSA[I].SUBVALUE1F = 3 THEN
                              USERCODE := TRUE
                           ELSE
                              IF GSA[I].SUBVALUE1F = 2 THEN
                                 SYSTEMFILE := TRUE;
                   END;
                   S := FTP + (FNAMEPOINTERS[GSA[I].LEVELF]);
                   SCAN EGSA[(J := GSA[I].LINKF) + 1] FOR
                        NEEDQUOTES:REAL(EGSA[J],1) WHILE IN ALPHA;
                   IF USERCODE
                      THEN RP S:S BY "("
                      ELSE IF SYSTEMFILE THEN RP S:S BY "*";
                   IF FNAMEPOINTERS[GSA[I].LEVELF] GEQ 1 THEN
                      IF S - 1 NEQ ")" THEN RP S - 1 BY "/";
                   IF NEEDQUOTES ISNT 0 THEN RP S:S BY """;
                   RP S:S BY EGSA[J + 1] FOR J := REAL(EGSA[J],1);
                   LNG := OFFSET(S);
                   RP S BY " " FOR 60 - LNG;
                   IF NEEDQUOTES ISNT 0 THEN RP S:S BY """;
                   IF USERCODE
                      THEN RP S:S BY ")"
                      ELSE RP S:S BY "/";

                   IF USERCODE OR SYSTEMFILE
                      THEN J := * + 2
                      ELSE J := * + 1;

                   IF NEEDQUOTES ISNT 0 THEN J:=* + 2;
                   FNAMEPOINTERS[GSA[I].LEVELF + 1] :=
                                 FNAMEPOINTERS[GSA[I].LEVELF] + J;
                   USERCODE := SYSTEMFILE := FALSE;
                   FKIND    := GSA[I].SUBVALUE2F;
                   I := IX + 1;
               END UNTIL FKIND NEQ 2;
               RP S - 1 BY "." FOR 1;
               DISPLAY(POINTER(DIR_ARR));
               IF COPYSYNTAX THEN
               BEGIN
                  IF FIRST THEN
                      REPLACE POINTER(AWFL) BY
                              "      " ,
                              POINTER(DIR_ARR)
                              UNTIL EQL "."
                   ELSE
                      REPLACE POINTER(AWFL) BY
                              "     ," ,
                              POINTER(DIR_ARR)
                              UNTIL EQL ".";

                  WFLWRITE;
                  FIRST:=FALSE;
                  END
               ELSE
               BEGIN
                    REPLACE POINTER(AWFL) BY
                              "  TRANSFER("""",
                              POINTER(DIR_ARR)
                              UNTIL EQL ".",
                              """");";
 %%%%%%                 WFLWRITE;
               END;

          END UNTIL FKIND < 1 OR FKIND > 3;
EXIT :   END;


%%%%% MAIN

   FIRST:=TRUE;
   COPYSYNTAX:=TRUE;

   CLEANWFL;

   WFLBEGIN;
   MYSELF.DISPLAYONLYTOMCS:=TRUE;

   RP POINTER(DIR_ARR) BY POINTER(PAR_ARR) UNTIL EQL 48"00";
   IF TT:=DIRREQUEST GTR 0 THEN
   BEGIN
      RP EMSG BY "GETSTATUS ERROR # ",
                 TT FOR 3 NUMERIC,48"00";
      %%DISPLAY(EMSG);
      MYSELF.VALUE:=TT;
   END;

   FIRST:=TRUE;
   COPYSYNTAX:=TRUE;
   WFLENDCOPY;

   RP POINTER(DIR_ARR) BY POINTER(PAR_ARR) UNTIL EQL 48"00";
   IF TT:=DIRREQUEST GTR 0 THEN
   BEGIN
      RP EMSG BY "GETSTATUS ERROR # ",
                 TT FOR 3 NUMERIC,48"00";
      %%DISPLAY(EMSG);
      MYSELF.VALUE:=TT;
   END;

   WFLENDREMOVE;

%   FIRST:=TRUE;
%   COPYSYNTAX:=FALSE;
%   RP POINTER(DIR_ARR) BY POINTER(PAR_ARR) UNTIL EQL 48"00";
%   IF TT:=DIRREQUEST GTR 0 THEN
%   BEGIN
%      RP EMSG BY "GETSTATUS ERROR # ",
%                 TT FOR 3 NUMERIC,48"00";
%      %%DISPLAY(EMSG);
%      MYSELF.VALUE:=TT;
%   END;

   FIRST:=TRUE;
   COPYSYNTAX:=TRUE;

   WFLEND;

   LOCK(WFL);

END.



