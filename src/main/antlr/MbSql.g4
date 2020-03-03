grammar MbSql;

query : EOF
      | simpleStmt EOF
      ;

simpleStmt : selectStmt
           ;

selectStmt : SELECT (ID | ALL) FROM ID (WHERE EQ)
           ;

SELECT : ;
ID : ;
ALL : ;
FROM : ;
WHERE : ;
EQ : ;
