package priv.wmc.base;

/**
 * 枚举定义
 *
 * @author 王敏聪
 * @date 2019/11/8 10:07
 */
public interface EnumDefine {

    /**
     * 返回枚举标识
     * @return 枚举标识
     */
    int getValue();

    /**
     * 返回枚举描述
     * @return 枚举描述
     */
    String getVerbose();

    /**
     * 反序列化枚举，而没有对应的value值能够反序列化，则该方法的返回值作为返回结果
     *
     * @return Enum<? extends EnumDefine>
     */
    default Enum<? extends EnumDefine> getDefault() {
        throw new IllegalArgumentException("No match value to deserialize");
    }

}
