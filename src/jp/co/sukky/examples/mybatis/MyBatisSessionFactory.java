package jp.co.sukky.examples.mybatis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * MyBatisSessionFactory.
 */
public class MyBatisSessionFactory {

    /** SqlSessionFactory for MyBatis. */
    private static SqlSessionFactory sqlSessionFactory;

    /** Path for config.xml. */
    private static final String CONFIG_FILE_PATH = "jp/co/sukky/examples/mybatis/config.xml";

    /**
     * Get SqlSessionFactory object.
     * 
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory != null) {
            return sqlSessionFactory;
        }
        try {
            String resource = CONFIG_FILE_PATH;
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return sqlSessionFactory;
    }
}
