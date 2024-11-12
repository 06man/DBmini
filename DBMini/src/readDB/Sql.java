package readDB;

import java.util.HashMap;
import java.util.Map;

public class Sql {
    String[] keys = { 
        "그룹 함수 조회 예제", 
        "조인 조회 예제 1", 
        "조인 조회 예제 2", 
        "서브쿼리 조회 예제", 
        "board 테이블 생성, 시퀀스, 트리거"
    };
    
    String[] sqls = { 
        "SELECT JOB, AVG(SAL) FROM EMP GROUP BY JOB HAVING AVG(SAL) >= 1000",
        "select d.deptno,d.dname,e.empno,e.ename,e.job,e.sal from emp e,dept d where e.deptno(+) = d.deptno order by d.deptno,e.empno",
        "select d.deptno,d.dname,e.empno,e.ename,e.mgr,e.sal,e.deptno as deptno_1,s.losal,s.hisal,s.grade,e2.empno mgr_empno,e2.ename mgr_ename from emp e right outer join dept d on (e.deptno = d.deptno) left outer join salgrade s on (e.sal between s.losal and s.hisal) left outer join emp e2 on (e.mgr = e2.empno) order by deptno, empno",
        "select * from emp where sal > any (select min(sal) from emp where job = 'SALESMAN')",
        
        "CREATE TABLE board (\n" + 
        "    boardNo NUMBER PRIMARY KEY,\n" + 
        "    title VARCHAR2(100) NOT NULL,\n" + 
        "    content VARCHAR2(1000) NOT NULL,\n" + 
        "    writer VARCHAR2(50) NOT NULL,\n" + 
        "    empno NUMBER,\n" + 
        "    regdate DATE DEFAULT SYSDATE,\n" + 
        "    CONSTRAINT fk_emp FOREIGN KEY (empno)\n" + 
        "        REFERENCES emp(empno)\n" + 
        "        ON DELETE CASCADE\n" + 
        ");\n" + 
        "\n" +
        "CREATE SEQUENCE board_seq\n" + 
        "    START WITH 1\n" + 
        "    INCREMENT BY 1\n" + 
        "    NOCYCLE\n" + 
        "    MAXVALUE 9999999999\n" + 
        "    MINVALUE 1\n" + 
        "    CACHE 20;\n" + 
        "\n" +
        "CREATE OR REPLACE TRIGGER trg_board_no\n" + 
        "BEFORE INSERT ON board\n" + 
        "FOR EACH ROW\n" + 
        "BEGIN\n" + 
        "    IF :NEW.boardNo IS NULL THEN\n" + 
        "        SELECT board_seq.NEXTVAL INTO :NEW.boardNo FROM dual;\n" + 
        "    END IF;\n" + 
        "END;"
    };

    Map<String, String> hashMap = new HashMap<String, String>();
    Map<String, String> createMap = new HashMap<String, String>();
    Map<String, String> deleteMap = new HashMap<String, String>();

    public Map<String, String> sql() {
        for (int i = 0; i < keys.length; i++) {
            hashMap.put(keys[i], sqls[i]);
        }
        return hashMap;
    }

    public Map<String, String> createSql() {
        // "board 테이블 생성, 시퀀스, 트리거 생성" 항목을 하나로 묶은 SQL을 createMap에 넣음
        createMap.put(keys[4], sqls[4]);
        return createMap;
    }

    public Map<String, String> deleteSql() {
        // 회원 삭제 시 게시글도 삭제하는 SQL을 deleteMap에 추가
        deleteMap.put(keys[7], sqls[7]);
        return deleteMap;
    }
}
