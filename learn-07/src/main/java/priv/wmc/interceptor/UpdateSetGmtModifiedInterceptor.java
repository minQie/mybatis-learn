package priv.wmc.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import priv.wmc.util.TimeUtils;

/**
 * 执行更新时，自动设置更新时间 gmt_modified 字段
 *
 * @author Wang Mincong
 * @date 2020-08-08 16:22:56
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
    }
)
public class UpdateSetGmtModifiedInterceptor implements Interceptor {

    @Override
    @SneakyThrows
    public Object intercept(Invocation invocation) {
        Object[] args = invocation.getArgs();

        Object entity = args[1];
        Class<?> entityClass = entity.getClass();

        Method getModified = entityClass.getMethod("setGmtModified", LocalDateTime.class);
        getModified.invoke(entity, TimeUtils.now());

        return invocation.proceed();
    }
}
