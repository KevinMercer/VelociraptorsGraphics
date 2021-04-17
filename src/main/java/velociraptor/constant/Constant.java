package velociraptor.constant;

/**
 * @author Velociraptor
 * @apiNote 常量
 */
public interface Constant {

    Boolean TRUE = true;

    Boolean FALSE = false;

    Integer ZERO = 0;

    Integer ONE = 1;

    Integer TWO = 2;

    Integer THREE = 3;

    Integer FOUR = 4;

    Integer FIVE = 5;

    Integer SIX = 6;

    Integer SEVEN = 7;

    Integer EIGHT = 8;

    Integer NINE = 9;

    Integer _0XFF = 0xFF;

    Integer SLIDER_MAX_VALUE = 6;

    Integer VELOCIRAPTOR_WINDOW_WIDTH = 500;

    Integer VELOCIRAPTOR_WINDOW_HEIGHT = 500;

    Integer DRAW_OFFSET = 200;

    Integer OFFSET_RATE = 25;

    Double HALF_RATE = 0.5;

    String EMPTY_BUT_NOT_NULL_STRING = "";

    String JPG_CODE = "FFD8FF";

    String JPEG_CODE = "FFD8FFE0";

    String PNG_CODE = "89504E47";

    String AUTHOR_S_WORD = "Velociraptor的像素图片处理器V1.0\n只针对CSO缔造者模式(Studio Mode)\n这个项目费力不讨好\n但是完全免费\n如果有能力的话可能会有后续版本\n谢谢使用";

    String ONLY_ONE_INSTANCE_OF_WINDOW = "Only one window program open is allowed.";

    String EMPTY_PIXEL_LIST = "像素点坐标表是空的。";

    String DRAW_IMAGE_ERROR = "绘图过程中出现异常。";

    String RIGHT_BRACE = "}";

    String COMMA = ",";

    String LEFT_BRACE = "{";

    String COPY_READY = "已复制到剪切板，共计耗费";

    String COPY_DESC = "个box，字符长度：";

    String TITLE = "Velociraptor的像素图片转换器V1.0";

    String OPTION = "选项";

    String SELECT_IMAGE = "选择图片";

    String ABOUT_ME = "关于Velociraptor的像素图片转换器";

    String TRANSLATE_IMAGE_TO_STREAM_ERROR = "对图片进行流转换时出现异常，请更换图源。";

    String ONLY_PNG_SUPPORT = "暂时只支持PNG格式的图片。";

    String UNKNOWN_ERROR = "啊哦，出现了未知异常，请重试！";

    String STREAM_TRANSFER_ERROR = "位图字节流传输过程中发生异常，导致字节流为空，请重试。";

}
