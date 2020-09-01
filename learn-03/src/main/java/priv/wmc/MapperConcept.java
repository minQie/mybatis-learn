package priv.wmc;

/**
 * 这里有一个概念，就是纯注解开发，将sql都写在Mapper接口方法上的注解里边，这个时候你会想，既然Mapper.xml都没有了，并且获取Mapper也是将Class
 * 传给SqlSession获取，Mybatis需要的一些都应该具备了，那么主配置文件中也无需配置mapper标签了对吧：
 *
 * org.apache.ibatis.binding.BindingException: Type interface xxx.XxxMapper is not known to the MapperRegistry.
 * 没错，不配置就调用时，就会收到这个错
 *
 * 详见：{@link org.apache.ibatis.binding.MapperRegistry#getMapper}，如果参数Mapper Class 不在已知的Mapper中，直接报错
 *
 * 查看 添加已知Mapper（knownMappers） 方法的调用者，如下：
 * 途径一，MapperRegistry.addMapper 被 Configuration.addMapper 调用，而这个方法都是被XML的相关类调用
 * 途径二，被MapperRegistry.addMappers调用 ，而这个方法还是被Configuration调用
 *
 * Mapper注解的作用：目前了解到的就是要配合Spring一起使用 - 现在项目的纯mybatis原生环境，甚至都找不到Mapper.class注解使用的地方
 *
 * @author Wang Mincong
 * @date 2020-08-06 20:53:47
 */
public final class MapperConcept {

    private MapperConcept() {}

}
