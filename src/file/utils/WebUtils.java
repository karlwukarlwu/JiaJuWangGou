package file.utils;

/**
 * Karl Rules!
 * 2023/10/13
 * now File Encoding is UTF-8
 * 用来判断是否是ajax请求
 *
 */
public class WebUtils {

//    文件上传路径
    public static final String UPLOAD_PATH = "asset/images/product-images/";

    public static boolean isAjaxRequest(javax.servlet.http.HttpServletRequest request) {

        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }


}
