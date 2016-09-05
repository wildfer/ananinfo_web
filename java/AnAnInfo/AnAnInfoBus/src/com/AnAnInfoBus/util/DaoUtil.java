package com.AnAnInfoBus.util;

import java.io.*;

/**
 * Created by Administrator on 2015/11/3.
 */
public class DaoUtil {

    public static String daopath="";

    public static String table="";//表名
    public static String keyword="";//关键词
    public static String desc="";//描述
    public static StringBuffer daoclass=new StringBuffer("");

    public static void createclass(){
        String ctable=captureName(table);
        createhead();
        daoclass.append("@Repository(\"" + ctable + "Dao\")\n");
        daoclass.append("public class "+ctable+"Dao extends Dao {\n");
        daoclass.append("       @Resource(name=\"jdbcTemplate\")\n");
        daoclass.append("       private JdbcTemplate jdbcT;\n");
        createadd();
        createupdate();
        createdelete();
        createquery();
        daoclass.append("}");

    }

    public static void createadd(){
        String ctable=captureName(table);
        daoclass.append("\n");
        daoclass.append("\n");
        daoclass.append("       public void  add" + ctable + "(" + ctable + "bean bean) throws HTException {\n");
        daoclass.append("               try {\n");
        daoclass.append("                   DaoArg da=this.beanToPreInsertsql(bean, \""+table+"\", new String[]{\""+keyword+"\"});\n");
        daoclass.append("                   jdbcT.update(da.getSql(),da.getArgs());\n");
        daoclass.append("                }catch (Exception e) {\n");
        daoclass.append("                   e.printStackTrace();\n");
        daoclass.append("                   throw new HTException(HTException.DAO_ADD_ERROR,       \"" + desc + "新增错误\");\n");
        daoclass.append("               }\n");
        daoclass.append("       }\n");
    }

    public static void createupdate(){
        String ctable=captureName(table);
        daoclass.append("\n");
        daoclass.append("\n");
        daoclass.append("       public void  update"+ctable+"("+ctable+"bean bean) throws HTException {\n");
        daoclass.append("               try {\n");
        daoclass.append("                   DaoArg da=this.beanToPreUpdatesql(bean, \""+table+"\" , new String[]{\""+keyword+"\"}, \""+keyword+"=?\" , String.valueOf(bean.get"+captureName(keyword)+"()));\n");
        daoclass.append("                   jdbcT.update(da.getSql(),da.getArgs());\n");
        daoclass.append("                }catch (Exception e) {\n");
        daoclass.append("                   e.printStackTrace();\n");
        daoclass.append("                   throw new HTException(HTException.DAO_ADD_ERROR,       \""+desc+"修改错误\");\n");
        daoclass.append("               }\n");
        daoclass.append("       }\n");
    }

    public static void createdelete(){
        String ctable=captureName(table);
        daoclass.append("\n");
        daoclass.append("\n");
        daoclass.append("       public void  delete"+ctable+"(String "+keyword+") {\n");
        daoclass.append("                   jdbcT.update(\"delete from "+table+" where "+keyword+"=?\",new String[]{"+keyword+"});\n");
        daoclass.append("       }\n");
    }

    public static void createquery(){
        String ctable=captureName(table);
        daoclass.append("\n");
        daoclass.append("\n");
        daoclass.append("       public "+ctable+"bean get"+ctable+"By"+captureName(keyword)+"(String "+keyword+") throws HTException{\n");
        daoclass.append("               try {\n");
        daoclass.append("                   Map<String, Object> hashMap=jdbcT.queryForMap(\"SELECT t.* FROM "+table+" t  WHERE  t."+keyword+" = ? \", new Object[]{"+keyword+"});\n");
        daoclass.append("                   "+ctable+"bean "+table+"bean = new "+ctable+"bean();\n");
        daoclass.append("                   BeanUtils.populate("+table+"bean, hashMap);\n");
        daoclass.append("                   return "+table+"bean;\n");
        daoclass.append("               } catch (IllegalAccessException e) {\n");
        daoclass.append("                   e.printStackTrace();\n");
        daoclass.append("                   throw new HTException(HTException.DAO_SQL_CREATE, e.getMessage()+\"数据错误\");\n");
        daoclass.append("               } catch (InvocationTargetException e) {\n");
        daoclass.append("                   e.printStackTrace();\n");
        daoclass.append("                   throw new HTException(HTException.DAO_SQL_CREATE, e.getMessage()+\"数据错误\");\n");
        daoclass.append("               }catch (EmptyResultDataAccessException e){\n");
        daoclass.append("                   e.printStackTrace();\n");
        daoclass.append("                   throw new HTException(HTException.DAO_SQL_CREATE, e.getMessage()+\" 未找到数据\");\n");
        daoclass.append("               }\n");
        daoclass.append("       }\n");
    }

    public static void createhead(){
        String ctable=captureName(table);
        daoclass.append("package com.wwhaitao.business.dao;\n");
        daoclass.append("\n");
        daoclass.append("\n");
        daoclass.append("\n");
        daoclass.append("import com.wwhaitao.business.proxy.Dao;\n");
        daoclass.append("import org.springframework.jdbc.core.JdbcTemplate;\n");
        daoclass.append("import org.springframework.stereotype.Repository;\n");
        daoclass.append("import javax.annotation.Resource;\n");
        daoclass.append("import javax.annotation.Resource;\n");
        daoclass.append("import org.springframework.dao.EmptyResultDataAccessException;\n");
        daoclass.append("import com.wwhaitao.util.HTException;\n");
        daoclass.append("import java.util.Map;\n");
        daoclass.append("import org.apache.commons.beanutils.BeanUtils;\n");
        daoclass.append("import java.lang.reflect.InvocationTargetException;\n");


        daoclass.append("import com.wwhaitao.bean."+ctable+"bean;\n");
        daoclass.append("\n");
        daoclass.append("\n");
        daoclass.append("\n");
    }

    public static void main(String[] args){

        //表名
        table="ms_help";
        //表描述
        desc="ms_help";

        //dao位置
        daopath="G:\\svn\\usvn\\svn\\thrift_service\\MniShopService_trunk\\MiniShop\\src\\com\\MiniShop\\business\\dao\\";
        //表主键
        keyword="id";


        createclass();
        System.out.println(daoclass);
        writeData(daoclass.toString());
    }

    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;
    }

    private static void writeData(String message) {
        String file = daopath+captureName(table) + "Dao.java";
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(message);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
