package file.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Karl Rules!
 * 2023/9/23
 * now File Encoding is UTF-8
 */
//对那个反射进一步封装
public class DataUtils {
    //封装一个方法 用来将map中的数据拷贝到bean中
    public static <T> T copyParameter(Map value, T bean) {
        try {
            BeanUtils.populate(bean , value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //将字符串转化为整数
    //如果转化不成功 用defaultValue来保护有一个默认值
    //一直有null是因为pageSize一直没有传入 用的默认的 pageSize
    public static int parseInt(String val,int defaultValue){
        try {
            System.out.println(val+"is being parsed");
            return Integer.parseInt(val);
        }catch (Exception e){
            System.out.println(val+"wrong format");
        }
        return defaultValue;
    }
}
