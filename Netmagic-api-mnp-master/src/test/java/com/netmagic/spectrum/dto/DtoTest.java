package com.netmagic.spectrum.dto;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSettersExcluding;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("web")
@ContextConfiguration({ "classpath*:/WEB-INF/spring/appServlet/servlet-context.xml" })
public class DtoTest {

    private static final String OBJECT_KEY = "objectKey";

    private static final String CACHE_KEY = "cacheKey";

    private static final String EXCLUDE_CLASS_ASSET = "com.netmagic.spectrum.dto.asset.response.Asset";

    private final String basePackageName = "com.netmagic.spectrum.dto";

    private static final String ADDITIONAL_PROPERTIES = "additionalProperties";

    @Test
    public void testDto() throws ClassNotFoundException, IOException {
        for ( Class<?> clazz : getClasses(basePackageName) ) {
            if ( clazz.getName().equals(EXCLUDE_CLASS_ASSET) ) {
            } else if ( !clazz.isInterface() ) {
                assertThat(clazz, allOf(hasValidBeanConstructor(),
                        hasValidGettersAndSettersExcluding(CACHE_KEY, OBJECT_KEY, ADDITIONAL_PROPERTIES)));
            }
        }

    }

    @SuppressWarnings("rawtypes")
    private Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();

        while ( resources.hasMoreElements() ) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        ArrayList<Class> classes = new ArrayList<Class>();
        for ( File directory : dirs ) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    @SuppressWarnings("rawtypes")
    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();

        if ( !directory.exists() ) {
            return classes;
        }

        File[] files = directory.listFiles();
        for ( File file : files ) {
            if ( file.isDirectory() ) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if ( file.getName().endsWith(".class") ) {
                classes.add(
                        Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
