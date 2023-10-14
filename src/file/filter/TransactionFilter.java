package file.filter;

import file.utils.JDBCUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Karl Rules!
 * 2023/10/12
 * now File Encoding is UTF-8
 */
//管理事务的过滤器
public class TransactionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest,servletResponse);
            //这个filter来的时候不拦截 走的时候统一放行
            JDBCUtils.commitAndClose();
        } catch (Exception e) {
            JDBCUtils.rollbackAndClose();
//            e.printStackTrace();
//            如果xml中配置了error-page 那么这里就不写e.printStackTrace();
//            而要扔出异常
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {

    }
}
