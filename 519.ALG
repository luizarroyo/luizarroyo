00000000% VERSION   1.3;   SAVED 20041207 12:21:04                    |GHJ
00100000 $ SET LIST LINEINFO TARGET=LEVEL2
00100100%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
00100200%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
00100300%%%%%%%%                                                        %%%%%%%%
00100400%%%%%%%%                                                        %%%%%%%%
00100500%%%%%%%%           @@@@@@@@    @@@@@@@@     @@@@@@@@            %%%%%%%%
00100600%%%%%%%%           @@@@@@@@@   @@@@@@@@@   @@@@@@@@@@           %%%%%%%%
00100700%%%%%%%%           @@      @@  @@     @@@  @@      @@           %%%%%%%%
00100800%%%%%%%%           @@      @@  @@     @@@  @@      @@           %%%%%%%%
00100900%%%%%%%%           @@      @@  @@@@@@@@@   @@@@@@@@@@           %%%%%%%%
00101000%%%%%%%%           @@      @@  @@@@@@@@@   @@@@@@@@@@           %%%%%%%%
00101100%%%%%%%%           @@      @@  @@     @@@  @@      @@           %%%%%%%%
00101200%%%%%%%%           @@      @@  @@     @@@  @@      @@           %%%%%%%%
00101300%%%%%%%%           @@@@@@@@@   @@@@@@@@@   @@      @@           %%%%%%%%
00101400%%%%%%%%           @@@@@@@@    @@@@@@@@    @@      @@           %%%%%%%%
00101500%%%%%%%%                                                        %%%%%%%%
00101600%%%%%%%%                                                        %%%%%%%%
00101700%%%%%%%%           @@@@@@@@@@      @@        @@@@@@             %%%%%%%%
00101800%%%%%%%%           @@@@@@@@@@     @@@       @@@@@@@@            %%%%%%%%
00101900%%%%%%%%           @@            @@@@      @@@    @@@           %%%%%%%%
00102000%%%%%%%%           @@              @@      @@@    @@@           %%%%%%%%
00102100%%%%%%%%           @@@@@@@@        @@       @@@@@@@@@           %%%%%%%%
00102200%%%%%%%%           @@@@@@@@@       @@        @@@@@@@@           %%%%%%%%
00102300%%%%%%%%                   @@      @@              @@           %%%%%%%%
00102400%%%%%%%%           @@@    @@@      @@      @@@    @@@           %%%%%%%%
00102500%%%%%%%%            @@@@@@@@      @@@@      @@@@@@@@            %%%%%%%%
00102600%%%%%%%%             @@@@@@       @@@@       @@@@@@             %%%%%%%%
00102700%%%%%%%%                                                        %%%%%%%%
00102800%%%%%%%%                                                        %%%%%%%%
00102900%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
00103000%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
00103100
00103200
00103300%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
00103400%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
00103500%%%%%%%%                                                        %%%%%%%%
00103600%%%%%%%%                                                        %%%%%%%%
00103700%%%%%%%%                                                        %%%%%%%%
00103800%%%%%%%%                                                        %%%%%%%%
00103900%%%%%%%%                                                        %%%%%%%%
00104000%%%%%%%%                                                        %%%%%%%%
00104100%%%%%%%%                                                        %%%%%%%%
00104200%%%%%%%%                                                        %%%%%%%%
00104300%%%%%%%%                                                        %%%%%%%%
00104400%%%%%%%%                                                        %%%%%%%%
00104500%%%%%%%%                                                        %%%%%%%%
00104600%%%%%%%%                                                        %%%%%%%%
00104700%%%%%%%%                                                        %%%%%%%%
00104800%%%%%%%%                                                        %%%%%%%%
00104900%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
00105000%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
00105100 $ PAGE
00105200BEGIN
00105300    FILE
00105400         FDBA(KIND       = DISK,   % DESCRIPTION FILE
00105500              MAXRECSIZE = 256,
00105600              BUFFERS    = 5),
00105700         FTTY(KIND       = REMOTE, % REMOTE FILE
00105800              FRAMESIZE  = 8,
00105900              MAXRECSIZE = 1926,
00106000              MYUSE      = IO),
00106100         FXXX(KIND       = DISK,   % FICHIER DES TEST POUR PRESENCE
00106200              FILETYPE   = 7),
00106300         FOUT(KIND       = DISK,   % FICHIER DE SORTIE
00106400              MAXRECSIZE = 15,
00106500              BLOCKSIZE  = 420,
00106600              FILEKIND   = ALGOLSYMBOL);
00106700
00106800    LONG ARRAY
00106900         BL0_ARR[0: 0],            % BLOCK ZERO
00107000         BLB_ARR[0: 0],            % BLOCK DATA BASE
00107100         BLS_ARR[0: 0];            % BLOCK STRUCTURE
00107200
00107300    REAL ARRAY
00107400         LIG_BLK[0:14],            % LIGNE ALGOL BLANCHE
00107500         LIG_ARR[0:14],            % LIGNE ALGOL
00107600         LIN_ARR[0:14],            % IMAGE DU DEFINE
00107700         ONE_ARR[0: 0],            % BLOCK FIRST WORD
00107800         DBA_TIT[0: 9],            % TITLE DESCRIPTION FILE
00107900         OUT_TIT[0: 9],            % TITLE FILEOUT
00108000         OFF_ARR[0:19];            % OFFSET PAR LEVEL
00108100
00108200    REAL ARRAY
00108300         STR_ARR[0:999,0:4];       % STRUCTURES UTILES
00108400
00108500    EBCDIC ARRAY
00108600         EE01[0:1925],             % ECRAN DE SORTIE MENU
00108700         EE02[0:1925],             % ECRAN DE SORTIE SELECTION
00108800         EE99[0:  89],             % 25EME LIGNE
00108900         EI01[0:  89],             % ENTREE MENU
00109000         EI02[0:  89];             % ENTREE SELECTION
00109100
00109200     TRANSLATETABLE
00109300         LOWUP(EBCDIC                       TO
00109400               EBCDIC,
00109500               "abcdefghijklmnopqrstuvwxyz" TO
00109600               "ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
00109700         TRTIR(EBCDIC TO EBCDIC,"-" TO "_");
00109800
00109900    POINTER
00110000         PA,
00110100         PB,
00110200         PC;
00110300
00110400    LABEL
00110500         READ_MENU,
00110600         EXIT;
00110700
00110800    DEFINE
00110900         NODEBLOCK = 47:16 #,      % NODE: NUMERO DE BLOCK
00111000         NODESTRLS = 31:16 #,      % NODE: INDICE ADDR LIST
00111100         NODEPROLS = 15:16 #;      % NODE: INDICE ADDR LIST
00111200
00111300    DEFINE
00111400         PT = POINTER #,
00111500         RP = REPLACE #;
00111600
00111700    DEFINE
00111800         WRITE_DISK =
00111900              BEGIN
00112000                   IF ITM_BLK THEN
00112100                   BEGIN
00112200                        LIN_NUM:=* + 100;
00112300                        RP PT(LIG_BLK) BY " "     FOR 72,
00112400                                          LIN_NUM FOR  8 DIGITS;
00112500                        WRITE(FOUT,15,LIG_BLK);
00112600                   END;
00112700                   LIN_NUM:=* + 100;
00112800                   RP PT(LIG_ARR) + 72 BY LIN_NUM FOR  8 DIGITS,
00112900                                          " "     FOR 10;
00113000                           WRITE(FOUT,15,LIG_ARR);
00113100              END ; #;
00113200
00113300    INTEGER
00113400         I,
00113500         L,
00113600         N,
00113700         S,
00113800         NA,
00113900         NB;
00114000
00114100    INTEGER
00114200         LIN_NUM,                  % NUMERO DE LIGNE
00114300         DSK_ADD,                  % DISK ADDRESS
00114400         OLD_BST,                  % DERNIER BLOCK LU
00114500         OLD_LEV,                  % DERNIER LEVEL ITEM
00114600
00114700         RED_ADD,                  % ADRESSE DESCRIPTION FILE
00114800         RED_REC,                  % NOMBRE RECORDS D'UN BLOCK
00114900         RED_LEN,                  % LONGUEUR D'UN BLOCK
00115000         RED_IDX,                  % POSITION D'UN BLOCK
00115100         RED_KDX,                  % INDICE BOUCLE BLOCK
00115200
00115300         WRD_ADD,                  % INDICE DANS UN BLOCK
00115400         TXT_BAS,                  % INDICE DEBUT TEXTE DATA BASE
00115500         TXT_ADD,                  % INDICE TEXTE
00115600         NOF_BLK,                  % NOMBRE DE BLOCKS
00115700         DBA_STR,                  % NOMBRE DE STRUCTURES DATA BASE
00115800         BLB_TXT,                  % BASE TEXT BLOCK
00115900         BEG_BLK,                  % INDICE DEBUT ADD BLOCK
00116000         NOF_CHR,                  % NOMBRE DE CARACTERES
00116100
00116200         BLK_DBA,                  % NUMERO BLOCK DATA BASE
00116300         LST_DBA,                  % DEBUT LISTE DATA BASE
00116400         PRP_DBA,                  % DEBUT PROPERTIES DATA BASE
00116500         NOF_STR,                  % NOMBRE DE STRUCTURES
00116600         NOF_ITM,                  % NOMBRE D'ITEMS
00116700         NOF_VAR,                  % NOMBRE TYPES VARIABLES
00116800
00116900         STR_ADD,                  % DEBUT STRUCTURE DANS BLOCK 0
00117000         STR_WRD,                  % LONGUEUR RECORD EN MOTS
00117100         STR_BYT,                  % LONGUEUR RECORD EN BYTES - 1
00117200         BLK_STR,                  % NUMERO BLOCK STRUCTURE
00117300         LST_STR,                  % DEBUT LISTE STRUCTURE
00117400         PRP_STR,                  % DEBUT PROPERTIES STRUCTURE
00117500
00117600         STR_CHR,                  % LONGUEUR NOM STRUCTURE
00117700         STR_NUM,                  % STRUCTURE NUMBER
00117800         STR_TYP,                  % STRUCTURE TYPE
00117900         STR_SBT,                  % STRUCTURE SUBTYPE
00118000         STR_VAR,                  % VARIALBLE STRUCTURE
00118100         STR_RST,                  % RESTART DATASET
00118200         STR_RSZ,                  % STRUCTURE RECORD SIZE
00118300         STR_TVA,                  % REC TYPE SIZE IN BITS
00118400         STR_TSZ,                  % TABLE SIZE
00118500         STR_TSG,                  % TABLE SIZE IN SEG
00118600         STR_ESZ,                  % KEY ENTRY SIZE
00118700
00118800         NOF_KEY,                  % NUMBER OF KEYS
00118900         NOF_DAT,                  % NUMBER OF KEY DATA
00119000         KEY_NOD,                  % KEY NODE
00119100         DAT_NOD,                  % KET DATA NODE
00119200         KEY_LEN,                  % LENGTH OF KEYS
00119300         DAT_LEN,                  % LENGTH OF KEY DATA
00119400         LST_KEY,                  % DEBUT LIST KEY
00119500         LST_DAT,                  % DEBUT LIST KEY DATA
00119600         MST_NUM,                  % NUMERO STRUCTURE MAITRE
00119700         MST_ADD,                  % ADRESE STRUCTURE MAITRE
00119800         BEG_MST,                  % DEBUT LIST MAITRE
00119900         BEG_ITM,                  % DEBUT LIST IYEM
00120000         OFF_DIG,                  % OFFSET EN DIGITS
00120100
00120200         ITM_NOD,                  % INDICE ADD TETE LISTE ITEMS
00120300         ITM_PTR,                  % INDICE ADD DANS LISTE ITEMS
00120400         ITM_ADD,                  % INDICE ADD TETE PROPS ITEMS
00120500         ITM_VAR,                  % APPARTENANCE VARIABLE
00120600         ITM_LEV,                  % NIVEAU DE L'ITEM
00120700         ITM_TYP,                  % TYPE D'ITEM
00120800         ITM_CHR,                  % NOMBRE DE CARACTERES ITEM
00120900         ITM_SIZ,                  % LONGUEUR ITEM
00121000         ITM_OCC,                  % OCCURS ITEM
00121100         ITM_SIG,                  % ITEM SIGNE
00121200         ITM_DEC,                  % NOMBRE DE DECIMALES ITEM
00121300         ITM_OFF,                  % OFFSET DIGIT
00121400         ITM_WRD,                  % OFFSET WORD
00121500         ITM_BIT,                  % OFFSET BIT
00121600
00121700         FIX_LEN,                  % LONGUEUR PARTIE FIXE
00121800         VAR_NOD,                  % VARIABLE NODE
00121900         VAR_PTR,                  % POSITION ZONE VARIABLE
00122000         VAR_ADD,                  % ADRESSE ZONE VARIABLE
00122100         VAR_NUM,                  % NUMERO ZONE VARIABLE
00122200         VAR_LEN,                  % LONGUEUR ZONE VARIABLE
00122300         VAR_MAX;                  % LONGUEUR MAX ZONES VARIABLES
00122400
00122500    BOOLEAN
00122600         ITM_FST,                  % PREMIER ITEM
00122700         ITM_RIT,                  % ITEM UTILE
00122800         ITM_BLK,                  % LIGNE BLANCHE A ECRIRE
00122850         BEXT;                     % EXTENDED
00122900
00123000    VALUE ARRAY                    % POSITIONS DES SELECTIONS
00123100         STRPOS(0324,0351,0378,0404,0431,0458,0484,0511,0538,0564,
00123200                0591,0618,0644,0671,0698,0724,0751,0778,0804,0831,
00123300                0858,0884,0911,0938,0964,0991,1018,1044,1071,1098,
00123400                1124,1151,1178,1204,1231,1258,1284,1311,1338,1364,
00123500                1391,1418,1444,1471,1498,1524,1551,1578,1604,1631,
00123600                1658,1684,1711,1738);
00123700
00123800 $ INCLUDE PROPERTIES = "DATABASE/DMS501/PROPERTIES" 20000000-21999999
00123900 $ PAGE
00124000PROCEDURE MAKE_EE01;
00124100BEGIN
00124200    RP EE01 BY 48"0C0000",
00124300               "ó          ALGOL DEFINES GENERATION FOR ",
00124400               "THE STRUCTURES OF A DATA BASE           ",
00124500               "                                        ",
00124600               "                                        ",
00124700               "      ACTION........................  ",
00124800               "                                        ",
00124900               "         - W = Write                    ",
00125000               "                                        ",
00125100               "         - Q = Quit                     ",
00125200               "                                        ",
00125300               "                                        ",
00125400               "                                        ",
00125500               "      DESCRIPTION FILE PARAMETERS:      ",
00125600               "                                        ",
00125700               "         - Data Base Name...........   ",
00125800               "                                       ",
00125900               "         - User Code................   ",
00126000               "                                       ",
00126100               "         - Pack Name................   ",
00126200               "                                       ",
00126300               "                                        ",
00126400               "                                        ",
00126500               "      SELECTION PARAMETERS:             ",
00126600               "                                        ",
00126700               "         - Type of Structure........  ",
00126800               "                                        ",
00126900               "              - D = Dataset             ",
00127000               "                                        ",
00127100               "              - I = Index               ",
00127200               "                                        ",
00127300               "         - Type of Selection........  ",
00127400               "                                        ",
00127500               "              - A = All                 ",
00127600               "                                        ",
00127700               "              - S = Select              ",
00127800               "                                        ",
00127900               "                                        ",
00128000               "                                        ",
00128100               "      OUTPUT FILE TITLE.............   ",
00128200               "                                       ",
00128300               "                                        ",
00128400               "                                        ",
00128500               "      Xmit..........................  ",
00128600               "                                        ",
00128700               "                                        ",
00128800               "                                        ",
00128900               "ó                                      ",
00129000               "                                        ",
00129100               48"27E600";
00129200END;
00129300 $ PAGE
00129400PROCEDURE MAKE_EE02;
00129500BEGIN
00129600    RP EE02 BY 48"0C0000",
00129700               "    ALGOL DEFINES OR COBOL W-S GENERATI",
00129800               "ON FOR THE STRUCTURES OF A DATA BASE    ",
00129900               "                                        ",
00130000               "                                        ",
00130100               "     Selection of the structures (Enter ",
00130200               "any character to select a structure)    ",
00130300               "                                        ",
00130400               "                                        ",
00130500               " ...................   |  ............",
00130600               ".......   |  ...................    ",
00130700               " ...................   |  ............",
00130800               ".......   |  ...................    ",
00130900               " ...................   |  ............",
00131000               ".......   |  ...................    ",
00131100               " ...................   |  ............",
00131200               ".......   |  ...................    ",
00131300               " ...................   |  ............",
00131400               ".......   |  ...................    ",
00131500               " ...................   |  ............",
00131600               ".......   |  ...................    ",
00131700               " ...................   |  ............",
00131800               ".......   |  ...................    ",
00131900               " ...................   |  ............",
00132000               ".......   |  ...................    ",
00132100               " ...................   |  ............",
00132200               ".......   |  ...................    ",
00132300               " ...................   |  ............",
00132400               ".......   |  ...................    ",
00132500               " ...................   |  ............",
00132600               ".......   |  ...................    ",
00132700               " ...................   |  ............",
00132800               ".......   |  ...................    ",
00132900               " ...................   |  ............",
00133000               ".......   |  ...................    ",
00133100               " ...................   |  ............",
00133200               ".......   |  ...................    ",
00133300               " ...................   |  ............",
00133400               ".......   |  ...................    ",
00133500               " ...................   |  ............",
00133600               ".......   |  ...................    ",
00133700               " ...................   |  ............",
00133800               ".......   |  ...................    ",
00133900               " ...................   |  ............",
00134000               ".......   |  ...................    ",
00134100               "                                        ",
00134200               "                                        ",
00134300               "                                        ",
00134400               "                                      ",
00134500               48"27E600";
00134600END;
00134700 $ PAGE
00134800PROCEDURE READ_A_BLOCK(GET_ARR,DSK_ADD);
00134900VALUE    DSK_ADD;
00135000INTEGER  DSK_ADD;
00135100ARRAY    GET_ARR[0];
00135200BEGIN
00135300    READ(FDBA[DSK_ADD],1,ONE_ARR);
00135400
00135500    RED_REC:=ONE_ARR[DESCBLOCKRECS];
00135600    RED_LEN:=ONE_ARR[DESCBLOCKRECS] * 256 - 1;
00135700    RED_IDX:=0;
00135800
00135900    RESIZE(GET_ARR,RED_LEN,DISCARD);
00136000
00136100    FOR RED_KDX:=1 STEP 1 UNTIL RED_REC DO
00136200    BEGIN
00136300         READ(FDBA[DSK_ADD],256,GET_ARR[RED_IDX]);
00136400         RED_IDX:=* + 256;
00136500         DSK_ADD:=* + 1;
00136600    END;
00136700
00136800    TXT_BAS:=GET_ARR[TEXTBASELOC];
00136900END;
00137000 $ PAGE
00137100PROCEDURE SELECT_STRUCTURES;
00137200BEGIN
00137300    INTEGER
00137400         I,
00137500         J,
00137600         FIN_SRC,
00137700         DEB_ARR;
00137800
00137900
00138000    FIN_SRC:=-1;
00138100    DEB_ARR:=0;
00138200
00138300    MAKE_EE02;
00138400
00138500    FOR I:=0 STEP 1 UNTIL NOF_STR DO
00138600    BEGIN
00138700         IF FIN_SRC EQL 53 THEN
00138800         BEGIN
00138900              WRITE(FTTY,1926,EE02);
00139000              READ (FTTY,  54,EI02);
00139100              FOR J:=0 STEP 1 UNTIL FIN_SRC DO
00139200                  IF EI02[J] NEQ " " THEN
00139300                     STR_ARR[DEB_ARR+J,4]:=1;
00139400              MAKE_EE02;
00139500              FIN_SRC:=-1;
00139600              DEB_ARR:=I;
00139700         END;
00139800         FIN_SRC:=* + 1;
00139900         RP EE02[STRPOS[FIN_SRC]] BY STR_ARR[I,*] FOR 18 UNTIL = " ";
00140000    END;
00140100
00140200    WRITE(FTTY,1926,EE02);
00140300    READ (FTTY,  54,EI02);
00140400
00140500    FOR J:=0 STEP 1 UNTIL FIN_SRC DO
00140600        IF EI02[J] NEQ " " THEN
00140700           STR_ARR[DEB_ARR+J,4]:=1;
00140800END;
00140900 $ PAGE
00141000PROCEDURE GET_STRUCTURES;
00141100BEGIN
00141200    LONG ARRAY
00141300         BLS_ARR[0:0];
00141400
00141500    INTEGER
00141600         S,
00141700         STR_ADD,
00141800         STR_NUM,
00141900         BLK_STR,
00142000         LST_STR,
00142100         PRP_STR;
00142200
00142300    PT
00142400         PA,
00142500         PB;
00142600
00142700LABEL
00142800         LOOP_STR,
00142900         EXIT;
00143000
00143100    S:=0;
00143200
00143300 LOOP_STR:
00143400%---------
00143500    IF S:=* + 1 GTR DBA_STR THEN GO EXIT;
00143600
00143700    STR_ADD:=LST_DBA + S;
00143800    BLK_STR:=BLB_ARR[STR_ADD].[NODEBLOCK];
00143900    LST_STR:=BLB_ARR[STR_ADD].[NODESTRLS];
00144000    PRP_STR:=BLB_ARR[STR_ADD].[NODEPROLS];
00144100
00144200    WRD_ADD:=BEG_BLK + BLK_STR;
00144300    RED_ADD:=BL0_ARR[WRD_ADD];
00144400    READ_A_BLOCK(BLS_ARR,RED_ADD);
00144500
00144600    IF BLS_ARR[PRP_STR + LEVELF ] EQL 0 THEN GO LOOP_STR;
00144700    IF BLS_ARR[PRP_STR + DELETED] EQL 1 THEN GO LOOP_STR;
00144800
00144900    CASE REAL(EI01[49],1) OF
00145000    BEGIN
00145100         "D" : IF BLS_ARR[PRP_STR + TYPEF] NEQ 2 THEN GO LOOP_STR;
00145200         "I" : IF BLS_ARR[PRP_STR + TYPEF] NEQ 5 THEN GO LOOP_STR;
00145300         ELSE: GO LOOP_STR;
00145400    END;
00145500
00145600    NOF_CHR:=BLS_ARR[PRP_STR + IDENTIFIERSZ];
00145700    NOF_STR:=* + 1;
00145800    PA     :=PT(BLS_ARR[PRP_STR + WORDONE]) + 1;
00145900    PB     :=PT(STR_ARR[NOF_STR,*]);
00146000
00146100    RP PB BY PA FOR NOF_CHR," " FOR 18-NOF_CHR;
00146200
00146300    STR_ARR[NOF_STR,3]:=BLB_ARR[STR_ADD];
00146400
00146500    IF EI01[50] EQL "A"
00146600       THEN STR_ARR[NOF_STR,4]:=1
00146700       ELSE STR_ARR[NOF_STR,4]:=0;
00146800    GO LOOP_STR;
00146900
00147000 EXIT:
00147100%-----
00147200END;
00147300 $ PAGE
00147400PROCEDURE WRITE_HEADER;
00147500BEGIN
00147600    RP PT(LIG_ARR) BY " $ PAGE "," " FOR 82; WRITE_DISK;
00147700    RP PT(LIG_ARR) BY "%" FOR 72," " FOR 18; WRITE_DISK;
00147800
00147900    RP PT(LIG_ARR) BY "% DATABASE: ",
00148000                      EI01[1] FOR 17,
00148100                      " "     FOR 23 - STR_CHR,
00148200                      "STRUCTURE: (",
00148300                      STR_NUM FOR  4 DIGITS,") ",
00148400                      PT(BLS_ARR[PRP_STR + 2]) + 1 FOR STR_CHR,
00148500                      " %";
00148600    WRITE_DISK;
00148700
00148800    RP PT(LIG_ARR) BY "%" FOR 72," " FOR 18; WRITE_DISK;
00148900    RP PT(LIG_ARR) BY " " FOR 90;            WRITE_DISK;
00149000
00149100    RP PT(LIG_ARR) BY "EBCDIC ARRAY E",
00149200                      STR_NUM FOR 4 DIGITS,"[0:",
00149300                      STR_BYT FOR * DIGITS,"];       ";
00149400    WRITE_DISK;
00149500
00149600    RP PT(LIG_ARR) BY "REAL   ARRAY W",
00149700                      STR_NUM FOR 4 DIGITS,"[0] = E",
00149800                      STR_NUM FOR 4 DIGITS,";      ";
00149900    WRITE_DISK;
00150000
00150100    RP PT(LIG_ARR) BY "HEX    ARRAY H",
00150200                      STR_NUM FOR 4 DIGITS,"[0] = E",
00150300                      STR_NUM FOR 4 DIGITS,";      ";
00150400    WRITE_DISK;
00150500
00150600    RP PT(LIG_ARR) BY " " FOR 90; WRITE_DISK;
00150700    RP PT(LIG_ARR) BY "DEFINE  "; WRITE_DISK;
00150800
00150900    RP PT(LIG_ARR) BY " " FOR 90;
00151000END;
00151100 $ PAGE
00151200PROCEDURE REC_VAR;
00151300BEGIN
00151400    INTEGER   I;
00151500
00151600    FIX_LEN:=BLS_ARR[PRP_STR + USERRECORDSZ];
00151700    VAR_NOD:=BLS_ARR[PRP_STR + VFNODE];
00151800    VAR_PTR:=BLS_ARR[VAR_NOD].[NODESTRLS];
00151900    NOF_VAR:=BLS_ARR[VAR_PTR];
00152000    VAR_MAX:=0;
00152100
00152200    FOR I:=1 STEP 1 UNTIL NOF_VAR DO
00152300    BEGIN
00152400         VAR_PTR:=VAR_PTR + 1;
00152500         VAR_ADD:=BLS_ARR[VAR_PTR].[NODEPROLS];
00152600         VAR_LEN:=BLS_ARR[VAR_ADD + USERRECORDSZ];
00152700         IF VAR_LEN > VAR_MAX THEN VAR_MAX:=VAR_LEN;
00152800    END;
00152900
00153000    VAR_MAX:=(VAR_MAX + 1) DIV 2;
00153100    FIX_LEN:=(FIX_LEN + 1) DIV 2;
00153200
00153300    STR_WRD:=(FIX_LEN + VAR_MAX) DIV 6;
00153400
00153500    IF (FIX_LEN + VAR_MAX) MOD 6 GTR 0 THEN STR_WRD:=* + 1;
00153600END;
00153700 $ PAGE
00153800PROCEDURE WRITE_DATASET;
00153900BEGIN
00154000    LABEL     END_ITM,
00154100              EXIT;
00154200
00154300    OLD_LEV:=-1;
00154400    ITM_BLK:=FALSE;
00154500    ITM_FST:=TRUE;
00154600
00154700    BLK_STR:=STR_ARR[S,3].[NODEBLOCK];
00154800    PRP_STR:=STR_ARR[S,3].[NODEPROLS];
00154900
00155000    IF BLK_STR NEQ OLD_BST THEN
00155100    BEGIN
00155200         OLD_BST:=BLK_STR;
00155300         WRD_ADD:=BEG_BLK + BLK_STR;
00155400         DSK_ADD:=BL0_ARR[WRD_ADD];
00155500         READ_A_BLOCK(BLS_ARR,DSK_ADD);
00155600    END;
00155700
00155800    IF BLS_ARR[PRP_STR + TYPEF  ] NEQ 2 THEN GO EXIT;
00155900    IF BLS_ARR[PRP_STR + DELETED] NEQ 0 THEN GO EXIT;
00156000    IF BLS_ARR[PRP_STR + LEVELF ] EQL 0 AND
00156100       BLS_ARR[PRP_STR + GLOBALF] EQL 0 THEN GO EXIT;
00156200
00156220    IF BLS_ARR[PRP_STR + NEWPLATEAU] EQL 1
00156240       THEN BEXT:=TRUE
00156260       ELSE BEXT:=FALSE;
00156280
00156300    STR_CHR:=BLS_ARR[PRP_STR + IDENTIFIERSZ];
00156400    STR_NUM:=BLS_ARR[PRP_STR + STRUCTURENUM];
00156500
00156600    IF BLS_ARR[PRP_STR + VFNODE] = 0
00156700       THEN STR_WRD:=BLS_ARR[PRP_STR + RECORDSZ]
00156800       ELSE REC_VAR;
00156900
00157000    STR_BYT:=(STR_WRD * 6) - 1;
00157100
00157200    WRITE_HEADER;
00157300
00157400    ITM_NOD:=BLS_ARR[PRP_STR + DATAITEMNODE];
00157500    ITM_PTR:=BLS_ARR[ITM_NOD].[NODESTRLS];
00157600    NOF_ITM:=BLS_ARR[ITM_PTR];
00157700
00157800    FOR I:=1 STEP 1 UNTIL NOF_ITM DO
00157900    BEGIN
00158000         ITM_PTR:=ITM_PTR + 1;
00158100         ITM_ADD:=BLS_ARR[ITM_PTR].[NODEPROLS];
00158200
00158300         IF BLS_ARR[ITM_ADD + DUPNAMEB] GTR 0 THEN GO END_ITM;
00158400
00158500         ITM_TYP:=BLS_ARR[ITM_ADD + TYPEF       ];
00158600         ITM_CHR:=BLS_ARR[ITM_ADD + IDENTIFIERSZ];
00158700         ITM_SIG:=BLS_ARR[ITM_ADD + SIGNF       ];
00158800         ITM_LEV:=BLS_ARR[ITM_ADD + LEVELF      ];
00158900         ITM_OCC:=BLS_ARR[ITM_ADD + OCCURSMAX   ];
00159000         ITM_OFF:=BLS_ARR[ITM_ADD + ITEMOFFSET  ] +
00159100                  OFF_ARR[ITM_LEV - 1];
00159120
00159140         IF BEXT THEN ITM_OFF:=* - 24;
00159160
00159200         ITM_WRD:=ITM_OFF DIV 12;
00159300         ITM_BIT:=47 - ((ITM_OFF MOD 12) * 4);
00159400
00159500         IF ITM_LEV GTR 0 THEN
00159600         BEGIN
00159700              IF ITM_OCC GTR 0 THEN
00159710              BEGIN
00159720                   IF BEXT
00159730                      THEN OFF_ARR[ITM_LEV]:=ITM_OFF + 24
00159740                      ELSE OFF_ARR[ITM_LEV]:=ITM_OFF;
00159750              END ELSE
00159760              BEGIN
00159770                   OFF_ARR[ITM_LEV]:=OFF_ARR[ITM_LEV - 1];
00159780              END;
00159790         END;
00159800
00159900         IF ITM_TYP = ALPH OR ITM_TYP = GRP THEN
00160000         BEGIN
00160100              IF ITM_SIG = 0
00160200                THEN ITM_OFF:=ITM_OFF DIV 2;
00160300              IF ITM_SIG = 1
00160400                THEN ITM_SIZ:=BLS_ARR[ITM_ADD+TOTALSZ]*4
00160500                ELSE ITM_SIZ:=BLS_ARR[ITM_ADD+TOTALSZ]/2;
00160600              ITM_DEC:=0;
00160700         END ELSE
00160800         BEGIN
00160900              ITM_SIZ:=BLS_ARR[ITM_ADD + DECLAREDLENGTH];
00161000              ITM_DEC:=BLS_ARR[ITM_ADD + SCALEFACTOR   ];
00161100         END;
00161200
00161300         PA:=PT(LIN_ARR);
00161400         RP PA BY " " FOR 72;
00161500
00161600         PA:=PA + 4;
00161700
00161800         RP PA:PA BY "S",STR_NUM FOR 3 DIGITS,"_",
00161900                 PT(BLS_ARR[ITM_ADD + 2]) + 1
00162000                 FOR ITM_CHR," " FOR 18-ITM_CHR,"= ";
00162100
00162200         IF ITM_TYP = ALPH OR ITM_TYP = GRP THEN
00162300         BEGIN
00162400              IF ITM_SIG EQL 0
00162500                 THEN RP PA:PA BY "E"
00162600                 ELSE RP PA:PA BY "H";
00162700         END ELSE
00162800         BEGIN
00162900              IF ITM_TYP = BOLN OR ITM_TYP = LNK
00163000                 THEN RP PA:PA BY "W"
00163100                 ELSE RP PA:PA BY "H";
00163200         END;
00163300
00163400         RP PA:PA BY STR_NUM FOR 3 DIGITS,"[";
00163500
00163600         IF ITM_TYP = BOLN THEN
00163700            RP PA:PA BY ITM_WRD FOR 4 DIGITS,
00163800                             "].[" ELSE
00163900         IF ITM_TYP = LNK THEN
00164000            RP PA:PA BY ITM_WRD FOR 4 DIGITS,
00164100                             "]       #,%" ELSE
00164200            RP PA:PA BY ITM_OFF FOR 4 DIGITS,
00164300                             "]       #,%";
00164400
00164500         ITM_RIT:=TRUE;
00164600
00164700         CASE ITM_TYP OF
00164800         BEGIN
00164900              CNT :
00165000                   RP PA:PA BY "COUNT  (",
00165100                               ITM_SIZ FOR * DIGITS,")";
00165200
00165300              TYP :
00165400                   RP PA:PA BY "RECTYP (",
00165500                               ITM_SIZ FOR * DIGITS,")";
00165600
00165700              POPN:
00165800                   RP PA:PA BY "POPULA (",
00165900                               ITM_SIZ FOR * DIGITS,")";
00166000
00166100              GRP :
00166200                   IF ITM_SIG = 1
00166300                      THEN RP PA:PA BY "F-BOOL (",
00166400                                       ITM_SIZ FOR * DIGITS,")"
00166500                      ELSE RP PA:PA BY "GROUP  (",
00166600                                       ITM_SIZ FOR * DIGITS,")";
00166700
00166800              BOLN:
00166900                   BEGIN
00167000                        IF ITM_SIZ < 1
00167100                           THEN ITM_BIT:=* - 3 + ITM_DEC
00167200                           ELSE ITM_BIT:=* - 3;
00167300                           RP PA:PA BY ITM_BIT FOR 2
00167400                                   DIGITS,":1]#,%BOOLEAN ";
00167500                   END;
00167600
00167700              LNK:
00167800                  RP PA:PA BY "LINK   ";
00167900
00168000              FLD :
00168100                   ITM_SIZ:=* DIV 4;
00168200                   RP PA:PA BY "FIELD  (",
00168300                               ITM_SIZ FOR * DIGITS,")";
00168400
00168500              ALPH:
00168600                   RP PA:PA BY "ALPHA  (",
00168700                               ITM_SIZ FOR * DIGITS,")";
00168800
00168900              DECI:
00169000              DECF:
00169100                   BEGIN
00169200                        RP PA:PA BY "NUMBER (";
00169300                        IF ITM_SIG > 0 THEN RP PA:PA BY
00169400                                       "S";
00169500                        IF ITM_SIZ > 0 THEN RP PA:PA BY
00169600                                       ITM_SIZ FOR * DIGITS;
00169700                        IF ITM_DEC > 0 THEN RP PA:PA BY
00169800                                   ",",ITM_DEC FOR * DIGITS;
00169900                        RP PA:PA BY ")";
00170000                   END;
00170100
00170200              BINI:
00170300              BINF:
00170400                   BEGIN
00170500                        RP PA:PA BY "REAL   (";
00170600                        IF ITM_SIG > 0 THEN RP PA:PA BY
00170700                                       "S";
00170800                        IF ITM_SIZ > 0 THEN RP PA:PA BY
00170900                                       ITM_SIZ FOR * DIGITS;
00171000                        IF ITM_DEC > 0 THEN RP PA:PA BY
00171100                                   ",",ITM_DEC FOR * DIGITS;
00171200                        RP PA:PA BY ")";
00171300                   END;
00171400
00171500              BFLT:
00171600                   RP PA:PA BY "REAL    ";
00171700
00171800              ELSE:
00171900                   ITM_RIT:=FALSE;
00172000         END;
00172100
00172200         IF ITM_RIT THEN
00172300         BEGIN
00172400              IF BLS_ARR[ITM_ADD + ITEMISKEYF] EQL 1 THEN
00172500                 RP PT(LIN_ARR)+71 BY "K";
00172600
00172700              IF ITM_OCC GTR 0 THEN
00172800                 RP PA:PA BY  " OCC ",ITM_OCC FOR * DIGITS;
00172900
00173000              IF ITM_FST
00173100                 THEN ITM_FST:=FALSE
00173200                 ELSE WRITE_DISK
00173300
00173400              RP PT(LIG_ARR) BY PT(LIN_ARR)
00173500                 FOR 72 WITH TRTIR;
00173600
00173700              IF ITM_LEV LSS OLD_LEV OR
00173800                 ITM_TYP EQL GRP
00173900                 THEN ITM_BLK:=TRUE
00174000                 ELSE ITM_BLK:=FALSE;
00174100
00174200              OLD_LEV:=ITM_LEV;
00174300         END;
00174400
00174500 END_ITM:
00174600%-------
00174700    END;
00174800
00174900    IF ITM_FST THEN ELSE
00175000    BEGIN
00175100         RP PT(LIG_ARR) + 47 BY ";";
00175200         WRITE_DISK;
00175300    END;
00175400
00175500 EXIT:
00175600%-----
00175700END;
00175800 $ PAGE
00175900PROCEDURE WRITE_ITEM(B,X);
00176000    VALUE     B,X;
00176100    BOOLEAN   B;
00176200    INTEGER   X;
00176300BEGIN
00176400    LABEL     EXIT;
00176500
00176600    ITM_PTR:=BEG_ITM + X;
00176700    ITM_ADD:=BLS_ARR[ITM_PTR].[NODEPROLS];
00176800    ITM_TYP:=BLS_ARR[ITM_ADD + TYPEF       ];
00176900    ITM_CHR:=BLS_ARR[ITM_ADD + IDENTIFIERSZ];
00177000    ITM_SIZ:=BLS_ARR[ITM_ADD + TOTALSZ     ];
00177100
00177200    IF ITM_TYP = ALPH OR
00177300       ITM_TYP = GRP  THEN
00177400         IF OFF_DIG MOD 2 NEQ 0 THEN OFF_DIG:=* + 1;
00177500
00177600    RP PT(LIG_ARR) BY " " FOR 90;
00177700
00177800    RP PT(LIG_ARR)+4 BY
00177900       "S",STR_NUM FOR 3 DIGITS,"_",
00178000       PT(BLS_ARR[ITM_ADD + 2]) + 1 FOR ITM_CHR WITH TRTIR;
00178100
00178200    CASE ITM_TYP OF
00178300    BEGIN
00178400         GRP :
00178500         ALPH: RP PT(LIG_ARR)+27 BY
00178600                  "= E",STR_NUM FOR 3 DIGITS,"[",
00178700                  OFF_DIG DIV 2 FOR 4 DIGITS,"]#,",
00178800                  "% ALPHA  (",ITM_SIZ DIV 2 FOR 2 DIGITS,")";
00178900
00179000         DECI:
00179100         DECF:
00179200         BINI:
00179300         BINF:
00179400         BFLT: RP PT(LIG_ARR)+27 BY
00179500                  "= H",STR_NUM FOR 3 DIGITS,"[",
00179600                  OFF_DIG FOR 4 DIGITS,"]#,",
00179700                  "% NUMBER (",ITM_SIZ FOR 2 DIGITS,")";
00179800
00179900         ELSE:GO EXIT;
00180000    END;
00180100
00180200    IF B THEN
00180300    BEGIN
00180400         IF NOF_DAT EQL 0 THEN
00180500            IF I EQL NOF_KEY THEN RP PT(LIG_ARR)+40 BY ";";
00180600    END ELSE
00180700    BEGIN
00180800            IF I EQL NOF_DAT THEN RP PT(LIG_ARR)+40 BY ";";
00180900    END;
00181000
00181100    WRITE_DISK;
00181200
00181300 EXIT:
00181400%-----
00181500    OFF_DIG:=* + ITM_SIZ;
00181600END;
00181700 $ PAGE
00181800PROCEDURE WRITE_SET;
00181900BEGIN
00182000    LABEL EXIT;
00182100
00182200    BLK_STR:=STR_ARR[S,3].[NODEBLOCK];
00182300    PRP_STR:=STR_ARR[S,3].[NODEPROLS];
00182400
00182500    IF BLK_STR NEQ OLD_BST THEN
00182600    BEGIN
00182700         OLD_BST:=BLK_STR;
00182800         WRD_ADD:=BEG_BLK + BLK_STR;
00182900         DSK_ADD:=BL0_ARR[WRD_ADD];
00183000         READ_A_BLOCK(BLS_ARR,DSK_ADD);
00183100    END;
00183200
00183300    IF BLS_ARR[PRP_STR + TYPEF   ] NEQ 5 THEN GO EXIT;
00183400    IF BLS_ARR[PRP_STR + SUBTYPEF] NEQ 0 THEN GO EXIT;
00183500    IF BLS_ARR[PRP_STR + DELETED ] NEQ 0 THEN GO EXIT;
00183600    IF BLS_ARR[PRP_STR + LEVELF  ] EQL 0 AND
00183700       BLS_ARR[PRP_STR + GLOBALF ] EQL 0 THEN GO EXIT;
00183800
00183900    STR_WRD:=BLS_ARR[PRP_STR + KEYENTRYSIZE];
00184000    STR_NUM:=BLS_ARR[PRP_STR + STRUCTURENUM];
00184100    STR_CHR:=BLS_ARR[PRP_STR + IDENTIFIERSZ];
00184200    STR_BYT:=(STR_WRD * 6) - 1;
00184300
00184400    WRITE_HEADER;
00184500
00184600    NOF_KEY:=BLS_ARR[PRP_STR + KEYCOUNT         ];
00184700    NOF_DAT:=BLS_ARR[PRP_STR + KEYDATACOUNT     ];
00184800    KEY_NOD:=BLS_ARR[PRP_STR + KEYNODE          ];
00184900    DAT_NOD:=BLS_ARR[PRP_STR + KEYDATANODE      ];
00185000    KEY_LEN:=BLS_ARR[PRP_STR + KEYPORTIONSZ     ] * 12;
00185100    DAT_LEN:=BLS_ARR[PRP_STR + KEYDATASZ        ] * 12;
00185200
00185300    LST_KEY:=BLS_ARR[KEY_NOD].[NODESTRLS];
00185400    LST_DAT:=BLS_ARR[DAT_NOD].[NODESTRLS];
00185500
00185600    MST_NUM:=BLS_ARR[PRP_STR + MASTERSTRUCTURESN];
00185700    MST_ADD:=LST_DBA + MST_NUM;
00185800    BEG_MST:=BLB_ARR[MST_ADD].[NODEPROLS];
00185900
00186000    ITM_NOD:=BLS_ARR[BEG_MST + DATAITEMNODE];
00186100    BEG_ITM:=BLS_ARR[ITM_NOD].[NODESTRLS];
00186200
00186300    OFF_DIG:=0;
00186400
00186500    FOR I:=1 STEP 1 UNTIL NOF_KEY DO
00186600    BEGIN
00186700         LST_KEY:=* + 1;
00186800         WRITE_ITEM(TRUE,BLS_ARR[LST_KEY].[11:12]);
00186900    END;
00187000
00187100    OFF_DIG:= * + (OFF_DIG MOD 12);
00187200
00187300    FOR I:=1 STEP 1 UNTIL NOF_DAT DO
00187400    BEGIN
00187500         LST_DAT:=* + 1;
00187600         WRITE_ITEM(FALSE,BLS_ARR[LST_DAT].[11:12]);
00187700    END;
00187800
00187900 EXIT:
00188000%-----
00188100END;
00188200 $ PAGE
00188300    MAKE_EE01;
00188400
00188500 READ_MENU:
00188600%----------
00188700    WRITE(FTTY,1926,EE01);
00188800    READ (FTTY,  88,EI01);
00188900
00189000    RP EE01[0200] BY EI01[0000] FOR 001; % ACTION
00189100    RP EE01[0600] BY EI01[0001] FOR 018; % DATA BASE NAME
00189200    RP EE01[0680] BY EI01[0019] FOR 012; % DF USER CODE
00189300    RP EE01[0760] BY EI01[0031] FOR 018; % DF PACK
00189400    RP EE01[1000] BY EI01[0049] FOR 001; % TYPE OF STRUCTURE
00189500    RP EE01[1240] BY EI01[0050] FOR 001; % TYPE OF SELECTION
00189600    RP EE01[1560] BY EI01[0051] FOR 036; % OUTPUT TITLE
00189700    RP EE01[1720] BY EI01[0087] FOR 001; % XMIT
00189800    RP EE01[1868] BY " "        FOR 036; % ERROR MESSAGE
00189900
00190000    RP EI01 BY EI01 FOR 88 WITH LOWUP;
00190100
00190200    IF EI01[00] EQL "Q" THEN GO EXIT;
00190300
00190400    IF EI01[00] NEQ "W" THEN
00190500    BEGIN
00190600         RP EE01[1868] BY "     Invalid Action Code      ";
00190700         GO READ_MENU;
00190800    END;
00190900
00191000    IF EI01[01] EQL " " THEN
00191100    BEGIN
00191200         RP EE01[1868] BY "    Missing Data Base Name    ";
00191300         GO READ_MENU;
00191400    END;
00191500
00191600    CASE REAL(EI01[49],1) OF
00191700    BEGIN
00191800         "D" :
00191900         "I" : ;
00192000         ELSE: RP EE01[1868] BY "   Invalid Type of Stucture   ";
00192100               GO READ_MENU;
00192200    END;
00192300
00192400    CASE REAL(EI01[50],1) OF
00192500    BEGIN
00192600         "A" :
00192700         "S" : ;
00192800         ELSE: RP EE01[1868] BY "   Invalid Type of Selection  ";
00192900               GO READ_MENU;
00193000    END;
00193100
00193200    IF EI01[51] EQL " " FOR 36 THEN
00193300    BEGIN
00193400         RP EE01[1868] BY "     Output Title Missing     ";
00193500         GO READ_MENU;
00193600    END;
00193700
00193800    RP PT(OUT_TIT) BY EI01[51] FOR 36 UNTIL = " ",".";
00193900    RP FXXX.TITLE  BY PT(OUT_TIT);
00194000    RP FOUT.TITLE  BY PT(OUT_TIT);
00194100
00194200    IF FXXX.RESIDENT THEN
00194300    BEGIN
00194400         RP EE01[1868] BY "  Output File already exists  ";
00194500         GO READ_MENU;
00194600    END;
00194700
00194800    PB:=PT(DBA_TIT);
00194900
00195000    IF EI01[19] NEQ " " FOR 12 THEN
00195100    BEGIN
00195200         PA:=EI01[19];
00195300         NA:=12;
00195400         SCAN PA:PA FOR NA:NA WHILE = " ";
00195500         IF PA EQL "*"
00195600            THEN RP PB:PB BY "*"
00195700            ELSE RP PB:PB BY "(",PA FOR NA UNTIL = " ",")";
00195800    END;
00195900
00196000         RP PB:PB BY "DESCRIPTION/";
00196100
00196200    PA:=EI01[01];
00196300    NA:=18;
00196400    SCAN PA:=EI01[01] FOR NA:18 WHILE = " ";
00196500    RP PB:PB BY PA FOR NA UNTIL = " ";
00196600
00196700    IF EI01[31] NEQ " " FOR 18 THEN
00196800    BEGIN
00196900         PA:=EI01[31];
00197000         NA:=18;
00197100         SCAN PA:PA FOR NA:NA WHILE = " ";
00197200         RP PB:PB BY " ON ",PA FOR NA UNTIL = " ";
00197300    END;
00197400
00197500    RP PB         BY ".";
00197600    RP FDBA.TITLE BY PT(DBA_TIT);
00197700
00197800    IF NOT FDBA.RESIDENT THEN
00197900    BEGIN
00198000         RP EE01[1868] BY " Description File not Present ";
00198100         GO READ_MENU;
00198200    END;
00198300
00198400    FDBA.OPEN:=TRUE;
00198500
00198600    IF FDBA.FILEKIND NEQ VALUE(DASDLDATA) THEN
00198700    BEGIN
00198800         RP EE01[1868] BY "   Invalid Description File   ";
00198900         CLOSE(FDBA);
00199000         GO READ_MENU;
00199100    END;
00199200
00199300    RP EE99[00] BY 48"3C1327D4","ó"," " FOR 78,48"27E6";
00199400    RP EE99[30] BY "   READING DESCRIPTION FILE   ";
00199500    WRITE(FTTY,90,EE99);
00199600
00199700    READ_A_BLOCK(BL0_ARR,0);
00199800
00199900    BEG_BLK:=BL0_ARR[BLOCKDIRADDR];
00200000    NOF_BLK:=BL0_ARR[BLOCKDIRSZ] - 2;
00200100
00200200    BLK_DBA:=BL0_ARR[DBNODELOC].[NODEBLOCK];
00200300    LST_DBA:=BL0_ARR[DBNODELOC].[NODESTRLS];
00200400    PRP_DBA:=BL0_ARR[DBNODELOC].[NODEPROLS];
00200500
00200600    WRD_ADD:=BEG_BLK + 1;
00200700    RED_ADD:=BL0_ARR[WRD_ADD].[NODEPROLS];
00200800    READ_A_BLOCK(BLB_ARR,RED_ADD);
00200900
00201000    BLB_TXT:=BLB_ARR[TEXTBASELOC];
00201100    DBA_STR:=BLB_ARR[LST_DBA];
00201200
00201300    NOF_STR:=-1;
00201400    OLD_BST:=-1;
00201500
00201600    GET_STRUCTURES;
00201700
00201800    IF NOF_STR EQL -1 THEN
00201900    BEGIN
00202000         RP EE01[1868] BY "    There is no Structures    ";
00202100         CLOSE(FDBA);
00202200         GO READ_MENU;
00202300    END;
00202400
00202500    IF EI01[50] EQL "S" THEN SELECT_STRUCTURES;
00202600
00202700    RP EE99[00] BY 48"3C1327D4","ó"," " FOR 78,48"27E6";
00202800    RP EE99[30] BY "     WRITING OUTPUT FILE      ";
00202900    WRITE(FTTY,90,EE99);
00203000
00203100    FOR S:=0 STEP 1 UNTIL NOF_STR DO
00203200    BEGIN
00203300         IF STR_ARR[S,4] EQL 1 THEN
00203400         BEGIN
00203500              IF EI01[49] EQL "D"
00203600                 THEN WRITE_DATASET
00203700                 ELSE WRITE_SET;
00203800         END;
00203900    END;
00204000
00204100    CLOSE(FOUT,LOCK);
00204200    CLOSE(FDBA);
00204300
00204400    MAKE_EE01;
00204500    GO READ_MENU;
00204600
00204700 EXIT:
00204800%-----
00204900    RP EE99 BY 48"0C0000000000";
00205000    WRITE(FTTY,6,EE99);
00205100END.
