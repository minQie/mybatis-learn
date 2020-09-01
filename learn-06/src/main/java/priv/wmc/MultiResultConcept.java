package priv.wmc;

/**
 * <ul>
 *     <b>结果集的一些补充：</b>
 *
 * <li>mybatis还支持存储过程，列如一个存储过程可以执行查询得到两种结果集，这时需要用到resultMap的resultSets属性</li>
 * <li>如果根据某些类型字段不同，可能返回不同的结果集，这时就需要用到 resultMap 标签下边的 discriminator 标签</li>
 *
 * <li><b>最佳实践</b>：最好逐步建立结果映射。单元测试可以在这个过程中起到很大帮助。
 * 如果你尝试一次性创建像上面示例那么巨大的结果映射，不仅容易出错，难度也会直线上升。
 * 所以，从最简单的形态开始，逐步迭代。而且别忘了单元测试！ 有时候，框架的行为像是一个黑盒子（无论是否开源）。
 * 因此，为了确保实现的行为与你的期望相一致，最好编写单元测试。 并且单元测试在提交 bug 时也能起到很大的作用
 * </li>
 *
 * </ul>
 *
 * @author Wang Mincong
 * @date 2020-07-29 20:10:41
 */
public final class MultiResultConcept {

    private MultiResultConcept() {}

}
