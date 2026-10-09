package com.Cathouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.Cathouse.mapper")
@SpringBootApplication
public class CatHouseRentalApplication {
    public static void main(String[] args) {

        SpringApplication.run(CatHouseRentalApplication.class, args);
        System.out.println("ฅ•ω•ฅ----------启动成功----------(ฅ^･ω･^ฅ)  \n" +
                "        ／＞　　 フ         　 ∧,,　          \n"+
                "        |  _　 _|  　　　　ヾ ｀. ､`フ        \n"+
                "       / `ミ＿꒳ノ   　　　(,｀'´ヽ､､ﾂﾞ         \n"+
                "     /        |    　 (ヽｖ'　　　`''ﾞつ      \n"+
                "     /  ヽ   ノ     　　,ゝ　 ⌒`ｙ'''´        \n"+
                "    |   | | |      　 （ (´＾ヽこつ           \n"+
                " ／￣|　  | | |     　　 ) )                 \n"+
                "|(￣ヽ__ヽ_)__)    　　(ノ                   ");
    }
}
