package com.AnAnInfoCmd.service;

import java.io.*;
import java.sql.*;

import org.springframework.jdbc.core.JdbcTemplate;

import com.AnAnInfoCmd.util.DBUtil;
import com.AnAnInfoCmd.util.SpringUtil;




/**
 * 数据库表转换成javaBean对象小工具(已用了很长时间),
 * 1 bean属性按原始数据库字段经过去掉下划线,并大写处理首字母等等.
 * 2 生成的bean带了数据库的字段说明.
 * 3 各位自己可以修改此工具用到项目中去.
 */
public class testSqltoBean {
    private static final int TYPE_TABLE = 1; //表
    private static final int TYPE_DATABASE = 2; //数据库
    
    private String tablename = "";
    private String[] colnames;
    private String[] colTypes;
    private int[] colSizes; // 列名大小
    private int[] colScale; // 列名小数精度
    private boolean importUtil = false;
    private boolean importSql = false;
    private boolean importMath = false;

    private OutputStreamWriter osw = null;
    private BufferedWriter bw = null;
    private FileOutputStream fos = null;
    private static StringBuffer coding = new StringBuffer();  //字符串缓冲区
    
    /**
     * 生成实体BEAN
     * @param name 表名或数据库名
     * @param nameType 类型：表或数据库
     * @param desDir java文件保存目录
     */
    public void generateEntity(String name,int nameType,String desDir){
        Connection conn = null;
        try {
        	JdbcTemplate jdbcT = (JdbcTemplate)SpringUtil.getBean("jdbcTemplate");
        	conn = jdbcT.getDataSource().getConnection();
            if (TYPE_TABLE == nameType) {
                tableToEntity(conn, name, desDir);
            } else if (TYPE_DATABASE == nameType) {
                String sql = "select table_name  from information_schema.`TABLES` where table_schema='" + name + "'";               
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(sql);
                while (rs.next()) {
                    String table = rs.getString(1);
                    tableToEntity(conn, table, desDir);
                }
                rs.close();
                smt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 各位按自己的
     */
    public void tableToEntity(Connection conn,String name,String desDir) {
        tablename = name;
        //数据连Connection获取,自己想办法就行.
        String strsql = "SELECT * FROM " + tablename;//+" WHERE ROWNUM=1" 读一行记录;
        try {
            if(conn==null || conn.isClosed()){
                conn = DBUtil.getConnection();
            }
            PreparedStatement stmt = conn.prepareStatement(strsql);
            ResultSet pstmt =stmt.executeQuery();
            ResultSetMetaData rsmd = pstmt.getMetaData();

            int size = rsmd.getColumnCount(); // 共有多少列
            colnames = new String[size];//字段名
            colTypes = new String[size];//字段类型
            colSizes = new int[size];
            colScale = new int[size];
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                rsmd.getCatalogName(i + 1);
                colnames[i] = rsmd.getColumnName(i + 1).toLowerCase();
                colTypes[i] = rsmd.getColumnTypeName(i + 1).toLowerCase();//  获取指定列的数据库特定的类型名称。
                colScale[i] = rsmd.getScale(i + 1);// 获取指定列的小数点右边的位数
                System.out.println("colnames............colTypes.:"+colnames[i]+":"+colTypes[i]);
                if ("datetime".equals(colTypes[i])) {
                    importUtil = true;
                }
                if ("image".equals(colTypes[i]) || "text".equals(colTypes[i])) {
                    importSql = true;
                }
                if(colScale[i]>0){
                    importMath = true;
                }
                colSizes[i] = rsmd.getPrecision(i + 1);//获取指定列的指定列宽
            }
            String content = parse(colnames, colTypes, colSizes);

            //写入文件
            writeData(content,tablename+"bean",desDir);
            pstmt.close();
            stmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }

    /**
     * 解析处理(生成实体类主体代码)
     */
    private String parse(String[] colNames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\npackage com.MiniShopCmd.bean;\r\n");
        if (importUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (importSql) {
            sb.append("import java.sql.*;\r\n\r\n");
        }
        if(importMath){
            sb.append("import java.math.*;\r\n\r\n");
        }
        //表注释
        processColnames(sb);
        sb.append("public class " + initcap((tablename))+ "bean"+ " implements java.io.Serializable {\r\n");
        processAllAttrs(sb);
        processAllMethod(sb);
        sb.append("}\r\n");
        System.out.println(sb.toString());
        return sb.toString();

    }
    /**
     * 处理列名,把空格下划线'_'去掉,同时把下划线后的首字母大写
     * 要是整个列在3个字符及以内,则去掉'_'后,不把"_"后首字母大写.
     * 同时把数据库列名,列类型写到注释中以便查看,
     * @param sb
     */
    private void processColnames(StringBuffer sb) {
        sb.append("\r\n/** " + tablename + "\r\n");
        String colsiz="";
        String colsca="";
        for (int i = 0; i < colnames.length; i++) {
            colsiz = colSizes[i]<=0? "" : (colScale[i]<=0? "("+colSizes[i]+")" : "("+colSizes[i]+","+colScale[i]+")");
            sb.append("\t" + colnames[i].toUpperCase() +"    "+colTypes[i].toUpperCase()+ colsiz+"\r\n");
            char[] ch = colnames[i].toCharArray();
            char c ='a';
            if(ch.length>3){
                for(int j=0;j <ch.length; j++){
                    c = ch[j];
                    if(c == '_'){
                        if (ch[j+1]>= 'a' && ch[j+1] <= 'z') {
                            ch[j+1]=(char) (ch[j+1]-32);
                        }
                    }
                }
            }
            String str = new String(ch);
            //colnames[i] = str.replaceAll("_", "");
        }
        sb.append("*/\r\n");
    }
    /**
     * 生成所有的方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tpublic void set" + initcap(colnames[i]) + "("
                    + oracleSqlType2JavaType(colTypes[i],colScale[i],colSizes[i]) + " " + colnames[i]
                    + "){\r\n");
            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");

            sb.append("\tpublic " + oracleSqlType2JavaType(colTypes[i],colScale[i],colSizes[i]) + " get"
                    + initcap(colnames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    /**
     * 解析输出属性
     *
     * @return
     */
    private void processAllAttrs(StringBuffer sb) {
        sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate " + oracleSqlType2JavaType(colTypes[i],colScale[i],colSizes[i]) + " "
                    + colnames[i] + " = null;\r\n");
        }
        sb.append("\r\n");
    }

    /**
     * 把输入字符串的首字母改成大写
     * @param str
     * @return
     */
    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
    /**
     * 写入指定的文件中
     * @param message
     */
    private void writeData(String message,String fileName,String desDir) {
        String file = desDir+initcap(fileName)+".java";
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            bw.write(message);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Oracle
     * @param sqlType
     * @param scale
     * @return
     */
    private String oracleSqlType2JavaType(String sqlType, int scale,int size) {
        if (sqlType.equalsIgnoreCase("integer")) {
            return "Integer";
        }else if (sqlType.toLowerCase().startsWith("int")) {
            return "Integer";
        }else if (sqlType.toLowerCase().startsWith("tinyint")
        		|| sqlType.toLowerCase().startsWith("mediumint")) {
            return "Integer";
        } else if (sqlType.toLowerCase().startsWith("long")) {
            return "Long";
        } else if (sqlType.toLowerCase().startsWith("float")
                || sqlType.toLowerCase().startsWith("double")
                ) {
            return "BigDecimal";
        }else if (sqlType.toLowerCase().startsWith("number")
                ||sqlType.toLowerCase().startsWith("decimal")
                || sqlType.toLowerCase().startsWith("numeric")
                || sqlType.toLowerCase().startsWith("real")) {
            return scale==0? (size<10? "Integer" : "Long") : "BigDecimal";
        }else if (sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("varchar2")
                || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar")
                || sqlType.equalsIgnoreCase("nchar")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")
                || sqlType.equalsIgnoreCase("date")
                || sqlType.equalsIgnoreCase("timestamp")) {
            return "Date";
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        testSqltoBean t = new testSqltoBean();

        //store_category  alliance alliance_store
        //注意修改文件路径
        t.generateEntity("ms_service",TYPE_TABLE,"E:\\workspace\\MiniShopCmd\\src\\com\\MiniShopCmd\\bean\\");
        
    }

}