package dream.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;


public class MyImportSelector implements ImportSelector {
    /**
     *
     * @param annotationMetadata 当前标注@Import注解的类的所有注解信息
     * @return 导入到容器中的组件的全类名
     */
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
        for (String tupe:annotationTypes){
            System.out.println(tupe);
        }

        return new String[]{"dream.beans.Blue","dream.beans.Red"};
    }
}
