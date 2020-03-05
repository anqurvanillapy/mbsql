grammar MbSql;

@header {
package io.anqur.mbsql;
}

query : EOF
      | simpleStmt EOF
      ;

simpleStmt : selectStmt
           ;

selectStmt : SELECT (ID | ALL) FROM ID (WHERE EQ AND EQ)
           ;

SELECT : ;
ID : ;
ALL : ;
FROM : ;
WHERE : ;
EQ : ;
STRING_LITERAL : ;
AND : ;
