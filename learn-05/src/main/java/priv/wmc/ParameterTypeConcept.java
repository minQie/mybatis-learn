package priv.wmc;

/**
 * <h3>parameterType</h3>
 * <ol>
 * <li><b>简单（基础）数据类型</b>
 *     <p>鉴于参数类型（parameterType）会被自动设置为 int，这个参数可以随意命名。原始类型或简单数据类型（比如 Integer 和 String）因为没有其它属性，会用它们的值来作为参数
 *     <p>多个简单的基本数据类型参数，应该使用map</p>
 * </li>
 * <li><b>复杂数据类型</b>
 *     <p>如果 User 类型的参数对象传递到了语句中，会查找 id、username 和 password 属性，然后将它们的值传入预处理语句的参数中(符合OGNL表达式的取值方式)
 *     <p>对象是一个 HashMap，你需要显式指定 javaType 来确保正确的类型处理器（TypeHandler）被使用
 * </li>
 *
 * <li><b>自定义类型的处理方式</b>
 *     <p>可以指定一个特殊的类型处理器类（或别名）
 *     <pre>#{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}</pre>
 *     <p>数值类型，还可以设置 numericScale 指定小数点后保留的位数
 *     <pre>#{height,javaType=double,jdbcType=NUMERIC,numericScale=2}</pre>
 * </li>
 *
 * <li><b>字符串替换</b>
 *     <p><code>
 *         &#64;Select("select * from user where ${column} = #{value}")
 *         User findByColumn(@Param("column") String column, @Param("value") String value);
 *     </code>
 *     <p>(其中 ${column} 会被直接替换，而 #{value} 会使用 ? 预处理)</p>
 * </li>
 *
 * </ol>
 *
 * @author Wang Mincong
 * @date 2020-07-27 08:44:59
 */
public final class ParameterTypeConcept {

    private ParameterTypeConcept() {}

}
