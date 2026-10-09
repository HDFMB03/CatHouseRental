package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LivingFacilitiesEnum {

    FORM1("床", false),
    FORM2("冰箱",false),
    FORM3("宽带",false),
    FORM4("沙发", false),
    FORM5("空调",false),
    FORM6("厨房",false),
    FORM7("衣柜",false),
    FORM8("阳台",false),
    FORM9("暖气", false),
    FORM10("浴霸", false),
    FORM11("热水器", false),
    FORM12("洗衣机", false),
    FORM13("电视机", false),
    FORM14("电磁炉", false),
    FORM15("可做饭",false),
    FORM16("储物柜", false),
    FORM17("电风扇",false),
    FORM18("电梯房", false),
    FORM19("停车位", false),
    FORM20("微波炉", false),
    FORM21("置物架", false),
    FORM22("煤气灶", false),
    FORM23("电热炉", false),
    FORM24("天燃气灶", false),
    FORM25("淋浴设备", false),
    FORM26("门禁系统", false),
    FORM27("抽油烟机", false),
    FORM28("独立卫生间", false),
    FORM29("无生活设施", false);

    private final String type;
    private final Boolean selected;

}
